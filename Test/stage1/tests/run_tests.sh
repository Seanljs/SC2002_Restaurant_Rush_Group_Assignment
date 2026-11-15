#!/usr/bin/env bash
# Copy this stage's tests/ and src/test/ into the matching stage project.
# Usage from that project: bash tests/run_tests.sh 1
# Optional source folder: bash tests/run_tests.sh 1 /path/to/production/src
# Stage 3 expected values use a fixed Salad + Pasta Combo ($24.30, 3 prep units).
# A random-combo variant will correctly fail these fixed expected transcripts.
set -euo pipefail
stage="${1:-1}"
if [[ "$stage" != "1" ]]; then
    printf 'This runner belongs to Stage 1.\n' >&2
    exit 2
fi
stage_dir="$(cd -- "$(dirname -- "$0")/.." && pwd)"
source_dir="${2:-}"
if [[ -z "$source_dir" ]]; then
    if [[ -d "$stage_dir/src/main/java" ]]; then
        source_dir="$stage_dir/src/main/java"
    elif [[ -d "$stage_dir/src/restaurant" ]]; then
        source_dir="$stage_dir/src"
    else
        printf 'Production source not found. Copy these tests into the matching Stage 1 project, or supply the production source folder as argument 2.\n' >&2
        exit 2
    fi
fi
if [[ ! -d "$source_dir" ]]; then
    printf 'Source folder not found: %s\n' "$source_dir" >&2
    exit 2
fi
source_dir="$(cd -- "$source_dir" && pwd)"
build_dir="$(mktemp -d "${TMPDIR:-/tmp}/restaurant-stage1-tests.XXXXXX")"
trap 'rm -rf -- "$build_dir"' EXIT
mkdir -p "$build_dir/classes"
results_dir="$stage_dir/tests/results"
mkdir -p "$results_dir"
{
    find "$source_dir" -type f -name '*.java' ! -path '*/src/test/*' -print
    find "$stage_dir/src/test/java" -type f -name '*.java' -print
} | LC_ALL=C sort -u | while IFS= read -r source_file; do
    printf '"%s"\n' "$source_file"
done > "$build_dir/sources.txt"
if command -v javac >/dev/null 2>&1; then
    javac -encoding UTF-8 -source 17 -target 17 -Xlint:-options -d "$build_dir/classes" "@$build_dir/sources.txt"
else
    java com.sun.tools.javac.Main -encoding UTF-8 -source 17 -target 17 -Xlint:-options -d "$build_dir/classes" "@$build_dir/sources.txt"
fi
failed=0
printf '== Stage 1 rule tests ==\n'
java -cp "$build_dir/classes" restaurant.RuleTests || failed=1
printf '== Stage 1 boundary tests ==\n'
java -cp "$build_dir/classes" restaurant.BoundaryTests || failed=1
printf '== Stage 1 reproducible runs ==\n'
for input in "$stage_dir/tests/stage$stage/"*.in; do
    name="$(basename -- "$input" .in)"
    expected="${input%.in}.expected"
    actual="$results_dir/$name.actual"
    difference="$results_dir/$name.diff"
    if ! java -cp "$build_dir/classes" restaurant.Main --echo < "$input" > "$actual"; then
        printf 'FAIL %s.in (application exited with an error)\n' "$name"
        failed=1
    elif diff -u "$expected" "$actual" > "$difference"; then
        printf 'PASS %s.in\n' "$name"
    else
        printf 'FAIL %s.in (see %s)\n' "$name" "$difference"
        failed=1
    fi
done
exit "$failed"
