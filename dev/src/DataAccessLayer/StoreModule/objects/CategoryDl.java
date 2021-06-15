package DataAccessLayer.StoreModule.objects;

public class CategoryDl {
    private String cname;
    private int level;
    private String upper;
    private int discount1;

    public CategoryDl(String cname, int level, int discount1, String upper) {
        this.cname = cname;
        this.discount1 = discount1;
        this.level = level;
        this.upper = upper;
    }

    public String getCname() {
        return cname;
    }

    public String getUpper() {
        return upper;
    }

    public int getDiscount1() {
        return discount1;
    }

    public int getLevel() {
        return level;
    }
}