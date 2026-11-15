package restaurant.staff;

import java.util.Arrays;
import java.util.List;
import restaurant.model.Customer;

public class Host extends ManagedStaff {
    public Host() {
        super("Host");
    }

    @Override
    public List<String> getVerbs() {
        return Arrays.asList(SeatCustomerTask.VERB);
    }

    @Override
    protected StaffTask createTask(String verb, Customer target) {
        return new SeatCustomerTask(getRole(), target);
    }
}