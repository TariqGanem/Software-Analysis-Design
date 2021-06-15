package DataAccessLayer.StoreModule.objects;

public class ItemSpecsDl {

    private String iname;
    private String cname;
    private int minamount;
    private int totalamount;
    private String manufacture;
    private int companyprice;
    private int storeprice;
    private int discount;
    private int finalprice;

    public ItemSpecsDl(String iname, String cname, int minamount, int totalamount, String manufacture,
                       int companyprice, int storeprice, int discount, int finalprice) {
        this.cname = cname;
        this.iname = iname;
        this.manufacture = manufacture;
        this.discount = discount;
        this.companyprice = companyprice;
        this.storeprice = storeprice;
        this.finalprice = finalprice;
        this.minamount = minamount;
        this.totalamount = totalamount;
    }

    public int getCompanyprice() {
        return companyprice;
    }

    public int getDiscount() {
        return discount;
    }

    public int getFinalprice() {
        return finalprice;
    }

    public int getMinamount() {
        return minamount;
    }

    public int getStoreprice() {
        return storeprice;
    }

    public int getTotalamount() {
        return totalamount;
    }

    public String getCname() {
        return cname;
    }

    public String getIname() {
        return iname;
    }

    public String getManufacture() {
        return manufacture;
    }

}