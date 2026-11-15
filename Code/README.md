# SC2002 Restaurant Rush — Video Demonstration Timeline

The following timestamps identify where each required component is demonstrated in the submitted video.

| No. | Demonstration requirement | Timestamp |
|---|---|---|
| 1 | Launching the game from the submitted project | 0:00 |
| 2 | Meaningful manager choices, including a decision with staff constraints | 0:05 |
| 3 | Dish progression: **READY → SERVED → payment** | 0:35 |
| 4 | VIP or Critic behaviour | 1:27 |
| 5 | Stage 3 feature | 2:23 |
| 6 | One victory and one defeat (separate reproducible runs or an accelerated replay) | 3:21 |
| 7 | Stage tag comparisons and diagrams | 5:14 |

# Restaurant Rush

## Run 

1. Use `src` as the source folder of a Java project (JDK 17 or newer).
2. Play: run `restaurant.Main`.

Commands: `seat <n>`, `order <n>`, `serve <n>`, `cook <n>`, `wait` (or Enter), `help`, `quit`.

## Description

18 turns, 2 tables. Win by ending with at least 4 customers paid, at least $45.00 revenue and average satisfaction of at least 60. 
Each turn: arrivals, your choices for each staff member, kitchen and serving, eating and payment, waiting losses, result check.

## Stage comparison

| | Stage 1 | Stage 2 | Stage 3 |
|---|---|---|---|
| Git tag | `stage-1` | `stage-2` | `stage-3` |
| Customers | Regular | Regular, VIP, Critic | Regular, VIP, Critic |
| Staff | Waiter, Chef, Cashier | Waiter, Chef, Cashier | Host, Waiter, Chef, Cashier |
| Menu | Salad, Burger, Pasta | Salad, Burger, Pasta | Salad, Burger, Pasta and ComboMeal |
| Extra rules | none | VIP 10% off, Critic picks the most elaborate dish and writes a review | Happy Hour (once, 2 turns, 20% off, +15 satisfaction for waiting customers) |
