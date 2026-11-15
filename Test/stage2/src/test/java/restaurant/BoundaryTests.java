package restaurant;

import restaurant.control.GameConfig;
import restaurant.control.GameResult;
import restaurant.model.ArrivalSchedule;
import restaurant.model.CriticCustomer;
import restaurant.model.Customer;
import restaurant.model.CustomerState;
import restaurant.model.Menu;
import restaurant.model.Order;
import restaurant.model.RegularCustomer;
import restaurant.model.Restaurant;
import restaurant.staff.Cashier;

/** Submission checks for exact thresholds and oldest-first payment. */
public class BoundaryTests {
    private static int passed;
    private static void check(String name, boolean ok) {
        if (!ok) throw new AssertionError(name);
        passed++;
        System.out.println("  ok   " + name);
    }

    private static class MetricsRestaurant extends Restaurant {
        private final int paid, revenue, total;
        MetricsRestaurant(int paid, int revenue, int total) {
            super(Menu.standard(), 2);
            this.paid=paid; this.revenue=revenue; this.total=total;
        }
        @Override public int getPaidCount() { return paid; }
        @Override public int getRevenueCents() { return revenue; }
        @Override public int getPaidSatisfactionTotal() { return total; }
    }

    private static Customer ready(Restaurant r, int id, int servedTurn) {
        Customer c = new RegularCustomer(id, 1);
        r.admit(c); r.seat(c);
        Order o = r.takeOrder(c);
        while (o.isCookable()) o.cookOneUnit();
        r.serve(c, servedTurn);
        r.finishEating(servedTurn + 1);
        return c;
    }

    public static void main(String[] args) {
        GameConfig cfg=GameConfig.base();
        check("all three exact base thresholds pass", cfg.evaluate(new MetricsRestaurant(4,4500,240)).isVictory());
        GameResult paid=cfg.evaluate(new MetricsRestaurant(3,4500,180));
        check("paid count alone can fail", !paid.isVictory() && !paid.isPaidTargetMet()
                && paid.isRevenueTargetMet() && paid.isSatisfactionTargetMet());
        GameResult revenue=cfg.evaluate(new MetricsRestaurant(4,4499,240));
        check("one cent below revenue fails only revenue", !revenue.isVictory() && revenue.isPaidTargetMet()
                && !revenue.isRevenueTargetMet() && revenue.isSatisfactionTargetMet());
        GameResult average=cfg.evaluate(new MetricsRestaurant(4,4500,239));
        check("average just below 60 fails only satisfaction", !average.isVictory() && average.isPaidTargetMet()
                && average.isRevenueTargetMet() && !average.isSatisfactionTargetMet());
        Restaurant r=cfg.newRestaurant();
        Customer older=ready(r,1,2);
        Customer newer=ready(r,2,3);
        Cashier cashier=new Cashier();
        cashier.collectPayment(r,5);
        check("cashier chooses oldest READY_TO_PAY customer", older.getState()==CustomerState.PAID
                && newer.getState()==CustomerState.READY_TO_PAY && r.getPaidCount()==1);
        r.applyWaitingLoss();
        check("READY_TO_PAY customers lose no satisfaction", newer.getSatisfaction()==100);

        class ReviewCritic extends CriticCustomer {
            private final int reported;
            ReviewCritic(int reported) { super(1,1); this.reported=reported; }
            @Override public int getSatisfaction() { return reported; }
            int review() { return satisfactionAtPayment(); }
        }
        check("Critic satisfaction 59 records 49", new ReviewCritic(59).review()==49);
        check("Critic satisfaction 60 records 70", new ReviewCritic(60).review()==70);

        System.out.println(passed + " boundary checks passed");
    }
}
