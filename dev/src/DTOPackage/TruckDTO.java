package DTOPackage;

public class TruckDTO {
    private String truckPlateNumber;
    private String model;
    private double natoWeight;
    private double maxWeight;

    public TruckDTO(String truckPlateNumber, String model, double natoWeight, double maxWeight) {
        this.truckPlateNumber = truckPlateNumber;
        this.model = model;
        this.natoWeight = natoWeight;
        this.maxWeight = maxWeight;
    }


    public String getTruckPlateNumber() {
        return truckPlateNumber;
    }

    public String getModel() {
        return model;
    }

    public double getNatoWeight() {
        return natoWeight;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

}