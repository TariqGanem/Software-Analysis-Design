package DataAccessLayer.StoreModule.objects;

public class DefectsDl {

    private int id;
    private String iname;
    private String cname;
    private String defectReason;
    private int amount;

    public DefectsDl(int id, String iname, String cname, String defectReason, int amount) {
        this.id = id;
        this.defectReason = defectReason;
        this.amount = amount;
        this.iname = iname;
        this.cname = cname;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getCname() {
        return cname;
    }

    public String getIname() {
        return iname;
    }

    public String getDefectReason() {
        return defectReason;
    }

}