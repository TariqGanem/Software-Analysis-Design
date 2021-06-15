package DataAccessLayer.StoreModule.objects;

public class ItemsDl {
    private String iname;
    private String expdate;
    private int id;
    private int shelveamount;
    private String defectreason;
    private int storageamount;
    private int defectamount;


    public ItemsDl(String iname, int id, String expdate, int shelveamount, int storageamount,
                   int defectamount, String defectreason) {
        this.expdate = expdate;
        this.iname = iname;
        this.defectamount = defectamount;
        this.id = id;
        this.defectreason = defectreason;
        this.storageamount = storageamount;
        this.shelveamount = shelveamount;
    }

    public String getIname() {
        return iname;
    }

    public int getDefectamount() {
        return defectamount;
    }

    public int getId() {
        return id;
    }

    public int getShelveamount() {
        return shelveamount;
    }

    public int getStorageamount() {
        return storageamount;
    }

    public String getDefectreason() {
        return defectreason;
    }

    public String getExpdate() {
        return expdate;
    }

}