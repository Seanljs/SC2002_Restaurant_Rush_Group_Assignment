# Restaurant Rush: Stage 1

Core game: Regular customers.

Plain Java using only what is taught in SC2002 (Chapters 2 to 10). No scripts, no build tools, no libraries.

## Run (in your usual IDE or JDK)

1. Use `src` as the source folder of a Java project (JDK 17 or newer).
2. Play: run `restaurant.Main`.
3. `sample_runs/` holds recorded games: paste the lines of a `*_input.txt` into the console of `restaurant.Main` to see the matching `*_transcript.txt`.

Commands: `seat <n>`, `order <n>`, `serve <n>`, `cook <n>`, `wait` (or Enter), `help`, `quit`.

## Game in one paragraph

18 turns, 2 tables. Win by ending with at least 4 customers paid, at least $45.00 revenue and average satisfaction of at least 60. Each turn: arrivals, your choices for each staff member, kitchen and serving, eating and payment, waiting losses, result check.

## What this stage adds

Regular customers, Waiter, Chef and Cashier; the six-step turn; victory and defeat; every invalid choice is refused with a reason.

## Stage comparison

| | Stage 1 | Stage 2 | Stage 3 |
|---|---|---|---|
| Git tag | `stage-1` | `stage-2` | `stage-3` |
| Customers | Regular | Regular, VIP, Critic | Regular, VIP, Critic |
| Staff | Waiter, Chef, Cashier | Waiter, Chef, Cashier | Host, Waiter, Chef, Cashier |
| Menu | Salad, Burger, Pasta | Salad, Burger, Pasta | plus Salad + Pasta Combo |
| Extra rules | none | VIP 10% off; Critic picks the most elaborate dish and writes a review | Happy Hour (once, 2 turns, 20% off, +15 satisfaction for waiting customers) |

Each stage is a complete game. The later stage extends the earlier one by adding classes and overriding methods, not by rewriting the turn loop.

## Packages

* `restaurant.model`: entities (Restaurant, Customer, Order, Table, Menu, Money...)
* `restaurant.staff`: staff and their tasks
* `restaurant.control`: GameController, GameConfig, TurnPlan, GameUI interface, exceptions
* `restaurant.ui`: ConsoleUI (the only class that touches the keyboard and screen)

See CODE_EXPLANATION.pdf (in the zip root) for a file-by-file explanation.
