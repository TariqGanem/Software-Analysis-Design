package BusinessLayer.StoreModule.Objects;

public class Report {
    private String title;
    private String description;
    private String cname;
    private String date;
    private String reportBudy;
    private int reportID;

    public Report(String title, String desc, String cname, String date, int reportID) {
        this.title = title;
        this.description = desc;
        this.cname = cname;
        this.date = date;
        this.reportBudy = "";
        this.reportID = reportID;
    }

    public void setInformation(String s) {
        this.reportBudy = s;
    }

    public String getInformation() {
        return reportBudy;
    }
}
