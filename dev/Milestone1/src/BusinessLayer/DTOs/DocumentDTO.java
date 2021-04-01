package BusinessLayer.DTOs;

import BusinessLayer.Objects.Location;

import java.util.Map;

public class DocumentDTO {
    private int trackingNumber;
    private Map<Integer, String> products;
    private Location destination;
}