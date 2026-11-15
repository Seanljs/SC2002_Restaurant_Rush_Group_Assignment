#!/usr/bin/env bash
# Merge stage1/, stage2/ and stage3/ with their corresponding production projects.
# Then run: bash run_all_tests.sh
# Requires a JDK 17 or newer. No external test framework is needed.
set -euo pipefail
root="$(cd -- "$(dirname -- "$0")" && pwd)"
failed=0
for stage in 1 2 3; do
    bash "$root/stage$stage/tests/run_tests.sh" "$stage" || failed=1
done
exit "$failed"
