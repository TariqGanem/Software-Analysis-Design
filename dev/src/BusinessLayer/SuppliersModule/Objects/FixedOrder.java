package BusinessLayer.SuppliersModule.Objects;

import Resources.Status;

import java.time.LocalDate;

public class FixedOrder extends Order {

    public FixedOrder(int id, Status status, LocalDate placementDate, LocalDate dueDate) {
        super(id, status, dueDate);
    }

    public void setID(int ID) {
        this.id = ID;
    }

}
