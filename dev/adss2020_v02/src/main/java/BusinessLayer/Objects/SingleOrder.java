package BusinessLayer.Objects;

import Enums.Status;

import java.time.LocalDate;

public class SingleOrder extends Order{


    public SingleOrder(Status status, LocalDate placementDate, LocalDate dueDate){
        super(status, LocalDate.now(), dueDate);
    }

    public void setID(int ID){
        this.id = ID;
    }


}
