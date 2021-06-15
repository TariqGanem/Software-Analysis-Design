package BusinessLayer.StoreModule.Controller;

import BusinessLayer.StoreModule.Objects.*;
import BusinessLayer.SuppliersModule.Controllers.ContractController;
import BusinessLayer.SuppliersModule.Controllers.OrderController;
import BusinessLayer.SuppliersModule.Objects.Contract;
import DataAccessLayer.StoreModule.Mappers.*;
import DataAccessLayer.StoreModule.objects.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class BusinessController {
    private static BusinessController instance;
    private LinkedList<Category> allCategories;
    private Map<String, Category> mainCategory;
    private Map<Integer, Defect> Defects;
    private HashMap<Integer, Report> reports;
    private int itemCounter;
    private int categoryCounter;
    private int report_id = 0;
    private int defectID;

    private CategoryMapper cmapper;
    private ItemSpecsMapper ismapper;
    private ItemMapper imapper;
    private DefectsMapper dmapper;
    private ReportsMapper rmapper;
    private Mapper mapper;

    public int getItemCounter() {
        itemCounter++;
        return itemCounter - 1;
    }

    public static BusinessController getInstance() {
        if (instance == null)
            instance = new BusinessController();
        return instance;
    }

    public BusinessController() {
        mainCategory = new HashMap<String, Category>();
        Defects = new HashMap<Integer, Defect>();
        reports = new HashMap<Integer, Report>();
        itemCounter = 1;
        categoryCounter = 1;
        defectID = 1;
        allCategories = new LinkedList<Category>();

        cmapper = CategoryMapper.getInstance();
        ismapper = ItemSpecsMapper.getInstance();
        imapper = ItemMapper.getInstance();
        dmapper = DefectsMapper.getInstance();
        rmapper = ReportsMapper.getInstance();
        mapper = Mapper.getInstance();

//            /////test
//            addingCategory("Milk","");
//            try {
//                addsubCategory("Milk","tnuva0",1,"");
//
//                addsubCategory("Milk","tnuva1",1,"");
//                addsubCategory("Milk","tnuva2",1,"");
//                addsubCategory("tnuva0","5per",2,"");
//                addsubCategory("tnuva0","3per",2,"");
//                addsubCategory("tnuva2","7per",2,"");
//                addingCategory("fish","");
//                addsubCategory("fish","fish1",1,"");
//                addsubCategory("fish1","fish2",2,"");
//                addItem("tuna","fish1",10,20,"30/05/2021","starkest",8,10,20);
//                addItem("tuna2","fish2",60,10,"08/05/2021","starkest",8,14,20);
//                AddNewAmount("fish1","tuna",15,"08/05/2021");
//                AddNewAmount("fish2","tuna2",55,"30/05/2021");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }

    public void run() throws Exception {
        //makeMinOrder();
        updateShipments();
        try {
            cmapper.restoreAllCategories();
            ismapper.restoreAllItemSpecs();
            imapper.restoreAllItems();
            dmapper.restoreAllDefects();
            rmapper.restoreAllReports();
            for (CategoryDl cdl : mapper.categories()) {
                if (cdl.getLevel() == 0) {
                    addingCategory(cdl.getCname(), "s");
                    if (cdl.getDiscount1() > 0) {
                        Category c = findSpecificCategory(cdl.getCname());
                        c.addcatDiscount(cdl.getDiscount1());
                    }
                }
            }
            for (CategoryDl cdl : mapper.categories()) {
                if (cdl.getLevel() == 1) {
                    addsubCategory(cdl.getUpper(), cdl.getCname(), 1, "s");
                    if (cdl.getDiscount1() > 0) {
                        Category c = findSpecificCategory(cdl.getCname());
                        c.addcatDiscount(cdl.getDiscount1());
                    }
                }
            }
            for (CategoryDl cdl : mapper.categories()) {
                if (cdl.getLevel() == 2) {
                    addsubCategory(cdl.getUpper(), cdl.getCname(), 2, "s");
                    if (cdl.getDiscount1() > 0) {
                        Category c = findSpecificCategory(cdl.getCname());
                        c.addcatDiscount(cdl.getDiscount1());
                    }
                }
            }
            for (ItemSpecsDl isdl : mapper.itemspecs()) {
                Category c = findSpecificCategory(isdl.getCname());
                ItemSpecs i = new ItemSpecs(isdl.getIname(), isdl.getCname(), isdl.getMinamount(), isdl.getTotalamount(), isdl.getManufacture(), isdl.getCompanyprice(), isdl.getStoreprice(), isdl.getDiscount());
                i.setFinalprice(isdl.getFinalprice());
                c.getItemSpecs().add(i);
                for (ItemsDl idl : mapper.items()) {
                    if (idl.getIname().equals(i.getName())) {
                        Item item = new Item(idl.getIname(), idl.getExpdate(), idl.getId(), idl.getShelveamount(), idl.getStorageamount());
                        item.setDefectReason(idl.getDefectreason());
                        item.setD(idl.getDefectamount());
                        i.getItemByDate().put(idl.getExpdate(), item);
                    }
                }
            }

            //reports
            for (ReportsDl rdl : mapper.reports()) {
                Report report = new Report(rdl.getTitle(), rdl.getDescription(), rdl.getCname(), rdl.getDate(), rdl.getReportID());
                report.setInformation(rdl.getReportBudy());
                this.reports.put(rdl.getReportID(), report);
                if (rdl.getReportID() > report_id) {
                    this.report_id = rdl.getReportID();
                }
            }
            this.report_id++;
            //defects
            for (DefectsDl ddl : mapper.defects()) {
                Defect defect = new Defect(ddl.getId(), ddl.getCname(), ddl.getIname(), ddl.getDefectReason(), ddl.getAmount());
                this.Defects.put(ddl.getId(), defect);
                if (ddl.getId() > defectID) {
                    this.defectID = ddl.getId();
                }
            }
            this.defectID++;


            makeMinOrder();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkIfItExist(String cname, String iname) throws Exception {
        boolean result = false;
        Category cat = findSpecificCategory(cname);
        if (cat != null) {
            for (ItemSpecs item : cat.getItemSpecs()) {
                if (item.getName().equals(iname)) {
                    result = true;
                }
            }
        }
        if (!result) {
            throw new Exception("There is no Item with this name\n");
        }
    }

    public void refresh(String date) {
        String s = "";
        for (Category c : allCategories) {
            for (ItemSpecs i : c.getItemSpecs()) {
                for (Item item : i.getItemByDate().values()) {
                    if (item.date(date) >= 0) {
                        Defect defect = new Defect(defectID, c.getName(), i.getName(), "Expired", (item.getShelveAmount() + item.getStorageAmount()));
                        try {
                            dmapper.insertDefect(defectID, item.getname(), c.getName(), "Expired", (item.getShelveAmount() + item.getStorageAmount()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        s = s + defect.Tostring();
                        Defects.put(defectID, defect);
                        defectID++;
                        i.subTotalamount(item.getShelveAmount() + item.getStorageAmount());
                        item.setDefectedAmount(item.getStorageAmount(), item.getShelveAmount());
                        item.setDefectReason("Expired items");
                    }
                }
            }
        }
        System.out.println(s);
    }

    public boolean checkIfCatExist(String cat) {
        for (Category c : allCategories) {
            if (c.getName().equals(cat))
                return true;
        }
        return false;
    }

    public void checkExp(String iname, String cname, String exp) {
        String s = "";
        if (iname == null) {
            if (cname == null) {
                for (Category c : allCategories) {
                    for (ItemSpecs i : c.getItemSpecs()) {
                        for (Item item : i.getItemByDate().values()) {
                            if (item.date(exp) > 0) {
                                s = s + item.Tostring();
                            }
                        }
                    }
                }
            } else {
                for (Category c : allCategories) {
                    if (c.getName().equals(cname)) {
                        for (ItemSpecs i : c.getItemSpecs()) {
                            for (Item item : i.getItemByDate().values()) {
                                if (item.date(exp) > 0) {
                                    s = s + item.Tostring();
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (Category c : allCategories) {
                if (c.getName().equals(cname)) {
                    for (ItemSpecs i : c.getItemSpecs()) {
                        if (i.getName().equals(iname)) {
                            for (Item item : i.getItemByDate().values()) {
                                if (item.date(exp) > 0) {
                                    s = s + item.Tostring();
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(s);
    }

    public void transferItems(String cname, String iname, String date, int amount) { //
        Category category = findSpecificCategory(cname);
        category.transferItems(iname, date, amount);
    }

    public boolean ifEnough(String cname, String iname, int amount, String expdate) {
        Category category = findSpecificCategory(cname);
        for (ItemSpecs item : category.getItemSpecs()) {
            if (item.getName().equals(iname)) {
                if (item.getItemByDate().get(expdate).getStorageAmount() >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ifEnough1(String cname, String iname, int amount, String expdate) {
        Category category = findSpecificCategory(cname);
        for (ItemSpecs item : category.getItemSpecs()) {
            if (item.getName().equals(iname)) {
                if ((item.getItemByDate().get(expdate).getStorageAmount() + item.getItemByDate().get(expdate).getShelveAmount()) >= amount) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean ifEnough2(String cname, String iname, int amount) {
        int counter = 0;
        Category category = findSpecificCategory(cname);
        for (ItemSpecs item : category.getItemSpecs()) {
            if (item.getName().equals(iname)) {
                for (Item it : item.getItemByDate().values()) {
                    counter = counter + it.getStorageAmount();
                }
            }
        }
        if (counter >= amount) {
            return true;
        }
        return false;
    }

    public void checkCategoryNameLegality(String categoryName) throws Exception {
        Category c = findSpecificCategory(categoryName);
        char ch;
        int counterNum = 0;
        for (int i = 0; i < categoryName.length(); i++) {
            ch = categoryName.charAt(i);
            if (ch >= '0' && ch <= '9') {
                counterNum = counterNum + 1;
            }
        }
        if (counterNum == categoryName.length()) { // does not have letters
            throw new Exception("Does not have letters\n");
        }
        boolean result = true;
        if (allCategories.contains(c)) {
            throw new Exception("Does not have letters\n");
        } else {
            for (Category cat : allCategories) {
                if (cat.getName().equals(categoryName))
                    throw new Exception("name is taken\n");
            }
        }
    }

    public void makingItemReport(String cname, String iname, String date, String desc) { //////
        Category category = findSpecificCategory(cname);
        String report1 = category.makingItemReport(iname, cname, desc, date, report_id);
        Report report = new Report("Item Report", desc, cname, date, report_id);
        this.reports.put(report_id, report);
        try {
            rmapper.insertReport(report_id, "Item Report", desc, cname, date, report1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        report.setInformation(report1);
        report_id++;
        System.out.println(report1);
    }

    public void makingDefectReport(String date, String desc) { ////
        Report report = new Report("Defect Report", desc, "", date, report_id);
        reports.put(report_id, report);


        String Rep = "Report's title: Defect Report " + "\nDate: " + date + "\n";
        if (!desc.equals("")) {
            Rep = Rep + desc + "\n";
        }
        for (Defect d : Defects.values()) {
            Rep = Rep + d.Tostring();
        }
        try {
            rmapper.insertReport(report_id, "Defect Report", desc, "", date, Rep);
        } catch (Exception e) {
            e.printStackTrace();
        }

        report.setInformation(Rep);
        report_id++;
        System.out.println(Rep);
    }

    public void makingLackReport(String desc, String date) {///////
        Report report = new Report("Lack Report", desc, "", date, report_id);
        reports.put(report_id, report);

        String Rep = "Report's title: Lack Report " + "\nDate: " + date + "\n";
        if (!desc.equals("")) {
            Rep = Rep + desc + "\n";
        }
        for (Category category : allCategories) {
            Rep = Rep + category.Categorylack();
        }
        try {
            rmapper.insertReport(report_id, "Lack Report", desc, "", date, Rep);
        } catch (Exception e) {
            e.printStackTrace();
        }
        report.setInformation(Rep);
        report_id++;
        System.out.println(Rep);
    }

    public void addingMinimumAttributeItem(String serialNumber, String iname, int min) throws Exception {
        String response = "There is no item with this date";
        Category category = findSpecificCategory(serialNumber);
        response = category.addingMinimumAttributeItem(iname, min);
        if (response.equals("there is no item with this date")) {
            throw new Exception("there is no item with this date");
        }
    }

//        public String makingOrder(String serialNumber) {
//            String order = "";
//            Category category = this.findSpecificCategory(serialNumber);
//            order = order + "Category's name: " + category.getName() + "\n";
//            return category.makingOrder(order);
//        }

    public void addingDefectItem(String cname, String iname, String date, int amount, String defectReason) { //////
        String result = "";
        Category category = this.findSpecificCategory(cname);
        Defect defect = new Defect(defectID, cname, iname, defectReason, amount);
        try {
            dmapper.insertDefect(defectID, iname, cname, defectReason, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Defects.put(defectID, defect);
        defectID++;
        category.addingDefectItem(iname, date, amount, defectReason);

    }

    public void addingCategory(String name, String a) {
        if (!a.equals("s")) {
            Category newCategory = new Category(0, name, null);
            try {
                cmapper.insertCategory(name, 0, 0, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            allCategories.add(newCategory);
            mainCategory.put(name, newCategory);
            System.out.println("Done Adding");
        } else {
            Category newCategory = new Category(0, name, null);
            allCategories.add(newCategory);
            mainCategory.put(name, newCategory);
        }
    }

    public void addsubCategory(String upperCategory, String subCategoryName, int subCategoryLevel, String a) throws Exception {
        if (!a.equals("s")) {
            Category newCategory = new Category(subCategoryLevel, subCategoryName, upperCategory);
            allCategories.add(newCategory);
            cmapper.insertCategory(subCategoryName, subCategoryLevel, 0, upperCategory);
            Category current = findSpecificCategory(upperCategory);
            if (current.isDiscount()) {
                newCategory.addcatDiscount(current.getDiscount1());
                cmapper.updateCategoryDiscount(subCategoryName, current.getDiscount1());
            }
            current.addsubCategory(newCategory);
        } else {
            Category newCategory = new Category(subCategoryLevel, subCategoryName, upperCategory);
            allCategories.add(newCategory);
            Category current = findSpecificCategory(upperCategory);
            if (current.isDiscount()) {
                newCategory.addcatDiscount(current.getDiscount1());
            }
            current.addsubCategory(newCategory);
        }
    }

    public void removeCategoryOrItem(String cname, String iname) {
        try {
            if (iname == null) {
                Category c0 = findSpecificCategory(cname);
                Category upperc = findSpecificCategory(c0.getUpperCat());
                if (c0.getType() == 0) {
                    for (Category c1 : c0.getSubCat()) {
                        for (Category c2 : c1.getSubCat()) {
                            System.out.println("Deleting this category 2 : ");
                            System.out.println(c2.getName());
                            for (ItemSpecs i : c2.getItemSpecs()) {
                                imapper.deleteItems(i.getName());
                                ismapper.deleteItemSpecs(i.getName());
                            }
                            cmapper.deleteCategory(c2.getName());
                            allCategories.remove(c2);
                        }
                        System.out.println("Deleting this category 1 : ");
                        System.out.println(c1.getName());
                        for (ItemSpecs i : c1.getItemSpecs()) {
                            imapper.deleteItems(i.getName());
                            ismapper.deleteItemSpecs(i.getName());
                        }
                        cmapper.deleteCategory(c1.getName());
                        allCategories.remove(c1);
                    }
                    for (ItemSpecs i : c0.getItemSpecs()) {
                        imapper.deleteItems(i.getName());
                        ismapper.deleteItemSpecs(i.getName());
                    }
                    cmapper.deleteCategory(c0.getName());
                    allCategories.remove(c0);
                    mainCategory.remove(c0);
                }
                if (c0.getType() == 1) {
                    for (Category c1 : c0.getSubCat()) {
                        System.out.println("Deleting this category : ");
                        System.out.println(c1.getName());
                        //  c0.getSubCat().remove(c1);
                        //  c0.getSubCatName().remove(c0.getName());
                        for (ItemSpecs i : c1.getItemSpecs()) {
                            imapper.deleteItems(i.getName());
                            ismapper.deleteItemSpecs(i.getName());
                        }
                        for (ItemSpecs i : c0.getItemSpecs()) {
                            imapper.deleteItems(i.getName());
                            ismapper.deleteItemSpecs(i.getName());
                        }
                        cmapper.deleteCategory(c1.getName());
                        allCategories.remove(c1);
                    }
                    upperc.getSubCat().remove(c0);
                    upperc.getSubCatName().remove(c0.getName());
                    cmapper.deleteCategory(c0.getName());
                    allCategories.remove(c0);
                }
                if (c0.getType() == 2) {
                    upperc.getSubCat().remove(c0);
                    upperc.getSubCatName().remove(c0.getName());
                    for (ItemSpecs i : c0.getItemSpecs()) {
                        imapper.deleteItems(i.getName());
                        ismapper.deleteItemSpecs(i.getName());
                    }
                    cmapper.deleteCategory(c0.getName());
                    allCategories.remove(c0);
                }
            } else {
                Category c0 = findSpecificCategory(cname);
                for (ItemSpecs item : c0.getItemSpecs()) {
                    if (item.getName().equals(iname)) {
                        c0.getItemSpecs().remove(item);
                        imapper.deleteItems(iname);
                        ismapper.deleteItemSpecs(iname);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkAmount(String serialNumber, String iname, int amount) {
        Category category = findSpecificCategory(serialNumber);
        return category.checkAmount(iname, amount);
    }

    //***//
    public void addItem(String iname, String cname, int amount, int minamount, String expdate
            , String manufacture, int companyPrice, int storePrice, int discount) {

        Category category = this.findSpecificCategory(cname);
        category.addItem(iname, cname, amount, minamount, expdate, manufacture, companyPrice, storePrice, discount, getItemCounter());
    }

    public void AddNewAmount(String cname, String iname, int amount, String expdate) {
        Category c = findSpecificCategory(cname);
        for (ItemSpecs items : c.getList()) {
            if (items.getName().equals(iname)) {
                if (items.getItemByDate().containsKey(expdate)) {
                    items.getItemByDate().get(expdate).updateStorageAmount(amount);
                } else {
                    Item item = new Item(iname, expdate, getItemCounter(), 0, amount);
                    try {
                        imapper.insertItem(iname, item.getID(), expdate, 0, amount, 0, "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    items.addItem(expdate, item);
                }
                items.updateTotalamount(amount);
            }
        }
    }


    public void addingDiscount(String cname, String iname, int discount) {
        try {
            Category c0 = findSpecificCategory(cname);
            if (iname != null) {
                c0.addItemDiscount(iname, discount);
            } else {
                if (c0.getType() == 0) {
                    for (Category c1 : c0.getSubCat()) {
                        for (Category c2 : c1.getSubCat()) {
                            c2.addcatDiscount(discount);
                            cmapper.updateCategoryDiscount(c2.getName(), discount);
                        }
                        c1.addcatDiscount(discount);
                        cmapper.updateCategoryDiscount(c1.getName(), discount);
                    }
                    c0.addcatDiscount(discount);
                    cmapper.updateCategoryDiscount(c0.getName(), discount);
                }
                if (c0.getType() == 1) {
                    for (Category c1 : c0.getSubCat()) {
                        c1.addcatDiscount(discount);
                        cmapper.updateCategoryDiscount(c1.getName(), discount);
                    }
                    c0.addcatDiscount(discount);
                    cmapper.updateCategoryDiscount(c0.getName(), discount);
                }
                if (c0.getType() == 2) {
                    c0.addcatDiscount(discount);
                    cmapper.updateCategoryDiscount(c0.getName(), discount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makingCategoryReport(String cname, String date, String desc) {
        String report1;
        Category category = findSpecificCategory(cname);
        report1 = category.makingCategoryReport(cname, date, desc, report_id);
        Report report = new Report("Category Report", desc, cname, date, report_id);
        this.reports.put(report_id, report);
        try {
            rmapper.insertReport(report_id, "Category Report", desc, cname, date, report1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        report_id++;
        report.setInformation(report1);
        System.out.println(report1);
    }


    private Category findSpecificCategory(String name) {
        for (Category c : allCategories) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    public void removeDiscount(String cname, String iname) {
        Category c0 = findSpecificCategory(cname);
        String itemName = iname;
        try {
            if (itemName != null) {
                c0.removeItemDiscount(iname);
            } else {
                if (c0.getType() == 0) {
                    for (Category c1 : c0.getSubCat()) {
                        for (Category c2 : c1.getSubCat()) {
                            c2.removeCategoryDiscount();
                            cmapper.updateCategoryDiscount(c2.getName(), 0);
                        }
                        c1.removeCategoryDiscount();
                        cmapper.updateCategoryDiscount(c1.getName(), 0);
                    }
                    c0.removeCategoryDiscount();
                    cmapper.updateCategoryDiscount(c0.getName(), 0);
                }
                if (c0.getType() == 1) {
                    for (Category c1 : c0.getSubCat()) {
                        c1.removeCategoryDiscount();
                        cmapper.updateCategoryDiscount(c1.getName(), 0);
                    }
                    c0.removeCategoryDiscount();
                    cmapper.updateCategoryDiscount(c0.getName(), 0);
                }
                if (c0.getType() == 2) {
                    c0.removeCategoryDiscount();
                    cmapper.updateCategoryDiscount(c0.getName(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void status() {
        System.out.println("************");
        for (Category c : allCategories) {
            System.out.println((c.getName()));
            for (ItemSpecs items : c.getItemSpecs()) {
                System.out.println("***** ITEM *****");
                System.out.println(items.Tostring());
            }
        }
        System.out.println("************");
    }

    public void makeMinOrder() throws Exception {
        for (Category category : allCategories) {
            for (ItemSpecs item : category.getItemSpecs()) {
                if (item.getTotalAmount() <= item.getMinAmount()) {
                    makingOrder(item.getName(), item.getMinAmount(), LocalDate.of(2021, 07, 04));
                }
            }
        }
    }

    public void makingOrder(String itemName, int amount, LocalDate dueDate) throws Exception {
        for (Category category : allCategories) {
            for (ItemSpecs item : category.getItemSpecs()) {
                if (item.getName().equals(itemName)) {

                    Map<Integer, Contract> map = ContractController.getInstance().ContractsOfItem(item.getName());
                    double price = -1;
                    int supplierId = 0;
                    for (Map.Entry<Integer, Contract> entry : map.entrySet()) {
                        if (price == -1) {
                            price = entry.getValue().finalPrice(item.getName(), item.getMinAmount());
                            supplierId = entry.getKey();
                        } else {
                            if (price > entry.getValue().finalPrice(item.getName(), item.getMinAmount())) {
                                price = entry.getValue().finalPrice(item.getName(), item.getMinAmount());
                                supplierId = entry.getKey();
                            }
                        }
                    }
                    int orderId = OrderController.getInstance().openSingleOrder(supplierId, dueDate).getId();
                    OrderController.getInstance().addItemToOrder(orderId, ContractController.getInstance().getItem(supplierId, itemName), amount);
                    try {
                        OrderController.getInstance().placeAnOrder(orderId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void updateShipments() throws Exception {
        LocalDate local = LocalDate.now();
        String today = local.toString();
        /*uuuu-mm-dd*/
        String day = today.substring(8, today.length());
        String month = today.substring(5, 7);
        String year = today.substring(0, 4);
        String actualDate = "";
        if (month.equals("12")) {
            day = "01";
            month = "01";
            int intYear = Integer.parseInt(year);
            intYear = intYear + 1;
            year = "" + intYear;
            actualDate = day + "/" + month + "/" + year;
        } else {
            int intMonth = Integer.parseInt(month);
            intMonth = intMonth + 1;
            month = "" + intMonth;
            actualDate = day + "/" + month + "/" + year;
        }
        List<String> itemNames = new LinkedList<>();

        itemNames = OrderController.getInstance().todayOrders(LocalDate.now());
        for (Category category : allCategories) {
            for (ItemSpecs item : category.getItemSpecs()) {
                if (itemNames.contains(item.getName())) {
                    AddNewAmount(category.getName(), item.getName(), item.getMinAmount(), actualDate);
                }
            }
        }
    }
}
