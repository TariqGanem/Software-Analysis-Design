package BusinessLayer.Objects;

import Enums.Status;

import java.time.LocalDate;

public class FixedOrder extends Order {

    public FixedOrder(int id, Status status, LocalDate placementDate, LocalDate dueDate){
        super(id, status, LocalDate.now(), dueDate);
    }
}
