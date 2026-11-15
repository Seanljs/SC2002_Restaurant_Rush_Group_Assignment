package restaurant.model;

public enum CustomerType {
    REGULAR;

    public Customer create(int id, int arrivalTurn) {
        return switch (this) {
            case REGULAR -> new RegularCustomer(id, arrivalTurn);
        };
    }
}
