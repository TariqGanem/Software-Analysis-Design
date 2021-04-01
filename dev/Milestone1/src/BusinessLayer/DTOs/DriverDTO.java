package BusinessLayer.DTOs;

import BusinessLayer.Objects.Driver;

public class DriverDTO {
    private String id;
    private String name;
    private double allowedWeight;

    public DriverDTO(Driver d){
        id = d.getId();
        name = d.getName();
        allowedWeight = d.getAllowedWeight();
    }
}