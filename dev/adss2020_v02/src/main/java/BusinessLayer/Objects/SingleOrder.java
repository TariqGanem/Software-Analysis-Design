package BusinessLayer.Objects;

import Enums.Status;

import java.time.LocalDate;

public class SingleOrder extends Order{


    public SingleOrder(int id, Status status, LocalDate placementDate, LocalDate dueDate){
        super(id, status, LocalDate.now(), dueDate);
    }

}
