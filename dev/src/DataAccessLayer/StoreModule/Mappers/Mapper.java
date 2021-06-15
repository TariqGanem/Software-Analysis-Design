package DataAccessLayer.StoreModule.Mappers;

import DataAccessLayer.StoreModule.objects.*;

import java.util.LinkedList;
import java.util.List;

public class Mapper {
    private static Mapper instance = null;
    private List<CategoryDl> categories;
    private List<ItemSpecsDl> itemspecs;
    private List<ItemsDl> items;
    private List<ReportsDl> reports;
    private List<DefectsDl> defects;

    private Mapper() {
        //new InitMapper().initialize();
        categories = new LinkedList<CategoryDl>();
        itemspecs = new LinkedList<ItemSpecsDl>();
        items = new LinkedList<ItemsDl>();
        reports = new LinkedList<ReportsDl>();
        defects = new LinkedList<DefectsDl>();
    }

    public static Mapper getInstance() {
        if (instance == null)
            instance = new Mapper();
        return instance;
    }

    public List<CategoryDl> categories() {
        return categories;
    }

    public List<ItemSpecsDl> itemspecs() {
        return itemspecs;
    }

    public List<ItemsDl> items() {
        return items;
    }

    public List<ReportsDl> reports() {
        return reports;
    }

    public List<DefectsDl> defects() {
        return defects;
    }

    public void setCategories(List<CategoryDl> categories) {
        this.categories = categories;
    }

    public void setItemspecs(List<ItemSpecsDl> itemspecs) {
        this.itemspecs = itemspecs;
    }

    public void setItems(List<ItemsDl> items) {
        this.items = items;
    }

    public void setReports(List<ReportsDl> reports) {
        this.reports = reports;
    }

    public void setDefects(List<DefectsDl> defects) {
        this.defects = defects;
    }
}
