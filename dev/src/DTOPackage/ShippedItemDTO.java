package DTOPackage;

public class ShippedItemDTO {

    private int documentId;
    private String name;
    private double amount;
    private double weight;

    public ShippedItemDTO(String name, double amount, double weight) {
        this.name = name;
        this.amount = amount;
        this.weight = weight;
    }

    public int getDocumentId() {
        return documentId;
    }

    public String getName() {
        return name;
    }


    public double getAmount() {
        return amount;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public double getWeight() {
        return weight;
    }

}
