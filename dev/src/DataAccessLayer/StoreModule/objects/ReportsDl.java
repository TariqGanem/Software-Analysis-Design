package DataAccessLayer.StoreModule.objects;

public class ReportsDl {
    private String title;
    private String description;
    private String cname;
    private String date;
    private String reportBudy;
    private int reportID;

    public ReportsDl(int reportID, String title, String desc, String cname, String date, String reportBudy) {
        this.title = title;
        this.description = desc;
        this.cname = cname;
        this.date = date;
        this.reportBudy = reportBudy;
        this.reportID = reportID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return title;
    }

    public String getCname() {
        return title;
    }

    public String getDate() {
        return title;
    }

    public String getReportBudy() {
        return title;
    }

    public int getReportID() {
        return reportID;
    }
}