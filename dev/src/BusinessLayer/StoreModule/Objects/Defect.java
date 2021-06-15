package BusinessLayer.StoreModule.Objects;

public class Defect {
    private int id;
    private String iname;
    private String cname;
    private String defectReason;
    private int amount;

    public Defect(int id, String cname, String iname, String defectReason, int amount) {
        this.id = id;
        this.defectReason = defectReason;
        this.amount = amount;
        this.iname = iname;
        this.cname = cname;
    }

    public String Tostring() {
        String s = "";
        s = s + "item ID is: " + id + "\nitem Name: " + iname + "\n category Name: " + cname + "\ndefect Reason: " + defectReason + "\namount of defected items: " + amount + "\n";
        return s;
    }
}
