package BusinessLayer.SuppliersModule.Objects;

import Resources.Status;

import java.time.LocalDate;

public class SingleOrder extends Order {


    public SingleOrder(int id, Status status, LocalDate placementDate, LocalDate dueDate) {
        super(id, status, dueDate);
    }

    public void setID(int ID) {
        this.id = ID;
    }


}
