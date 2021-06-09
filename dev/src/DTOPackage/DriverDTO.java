package DTOPackage;

public class DriverDTO {
    private String id;
    private String name;
    private double allowedWeight;

    public DriverDTO(String id, String name, double allowedWeight) {
        this.id = id;
        this.name = name;
        this.allowedWeight = allowedWeight;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAllowedWeight() {
        return allowedWeight;
    }

}