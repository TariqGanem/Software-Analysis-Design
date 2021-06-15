package BusinessLayer.StoreModule.Objects;

import DataAccessLayer.StoreModule.Mappers.CategoryMapper;
import DataAccessLayer.StoreModule.Mappers.ItemMapper;
import DataAccessLayer.StoreModule.Mappers.ItemSpecsMapper;

import java.util.HashMap;
import java.util.LinkedList;

public class Category {
    private HashMap<String, Category> subCatName;
    private LinkedList<Category> subCat;
    private LinkedList<ItemSpecs> products;
    private int level;
    private String name;
    private boolean discount = false;
    private int discount1 = 0;
    private String upperCat;
    private int itemID = 1;
    private int ReportID = 1;

    private ItemSpecsMapper ismapper;
    private CategoryMapper cmapper;
    private ItemMapper imapper;

//        Table(){


    public Category(int type, String name, String upperCat) {
        subCatName = new HashMap<String, Category>();
        this.level = type;
        this.name = name;
        cmapper = CategoryMapper.getInstance();
        ismapper = ItemSpecsMapper.getInstance();
        imapper = ItemMapper.getInstance();
        this.subCat = new LinkedList<Category>();
        products = new LinkedList<ItemSpecs>();
        this.upperCat = upperCat;
    }

    public String getUpperCat() {
        return upperCat;
    }

    public int getItemID() {
        itemID++;
        return (itemID - 1);
    }

    public void addItem(String iname, String cname, int amount, int minamount, String expdate
            , String manufacture, int companyPrice, int storePrice, int discount, int ID) {
        ItemSpecs items = new ItemSpecs(iname, cname, minamount, amount, manufacture, companyPrice, storePrice, discount);
        try {
            ismapper.insertItemSpecs(iname, cname, minamount, amount, manufacture, companyPrice, storePrice, discount, storePrice);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (discount == 0 && this.discount == true) {
            items.setDiscount(discount1);
        }
        if (discount > 0 && discount < 100) {
            items.setDiscount(discount);
        }
        products.add(items);
        Item item = new Item(iname, expdate, ID, 0, amount);
        items.getItemByDate().put(expdate, item);
        try {
            imapper.insertItem(iname, item.getID(), expdate, 0, amount, 0, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void addsubCategory(Category category) {
        subCatName.put(category.getName(), category);
        subCat.add(category);
    }

    public boolean isDiscount() {
        return this.discount;
    }

    public int getDiscount1() {
        return discount1;
    }

    public boolean checkAmount(String iname, int amount) {
        for (ItemSpecs item : products) {
            if (item.getName().equals(iname)) {
                if (item.getTotalAmount() >= amount)
                    return true;
            }
        }
        return false;
    }

    public void transferItems(String itemName, String date, int amount) {
        for (ItemSpecs it : products) {
            if (it.getName().equals(itemName)) {
                it.moveToShelf(amount, date);
            }
        }
    }

    public String Categorylack() {
        String result = "";
        for (ItemSpecs item : products) {
            if (item.getTotalAmount() <= item.getMinAmount()) {
                result = result + item.Tostring();
            }
        }
        return result;
    }

    public String makingItemReport(String iname, String cname, String desc, String date, int reportID) {
        String Rep = "Report's title: Category Report \n" + "Category's Name is: " + cname + "\nDate: " + date + "\n";
        if (!desc.equals("")) {
            Rep = Rep + desc + "\n";
        }
        for (ItemSpecs item : products) {
            if (item.getName().equals(iname)) {
                Rep = Rep + item.Tostring();
            }
        }
        return Rep;
    }

    public String makingCategoryReport(String cname, String date, String description, int reportID) {
        String Rep = "Report's title: Category Report \n" + "Category's Name is: " + cname + "\nDate: " + date + "\n";
        if (!description.equals("")) {
            Rep = Rep + description + "\n";
        }
        if (level == 0) {
            for (Category c1 : subCat) {
                for (Category c2 : c1.getSubCat()) {
                    for (ItemSpecs item : c2.getItemSpecs()) {
                        Rep = Rep + item.Tostring();
                    }
                }
                for (ItemSpecs item : c1.getItemSpecs()) {
                    Rep = Rep + item.Tostring();
                }
            }
            for (ItemSpecs item : products) {
                Rep = Rep + item.Tostring();
            }
        }
        if (level == 1) {
            for (Category c1 : subCat) {
                for (ItemSpecs item : c1.getItemSpecs()) {
                    Rep = Rep + item.Tostring();
                }
            }
            for (ItemSpecs item : products) {
                Rep = Rep + item.Tostring();
            }
        }
        if (level == 2) {
            for (ItemSpecs item : products) {
                Rep = Rep + item.Tostring();
            }
        }
        return Rep;
    }

    public void addingDefectItem(String iname, String date, int amount, String defectReason) {
        for (ItemSpecs item : products) {
            if (item.getName().equals(iname)) {
                item.addingDefectItem(date, amount, defectReason);
            }
        }
    }

    public void addItemDiscount(String iname, int discount) {
        for (ItemSpecs it : products) {
            if (it.getName().equals(iname)) {
                it.setDiscount(discount);
            }
        }
    }

    public void addcatDiscount(int discount) {
        for (ItemSpecs it : products) {
            it.setDiscount(discount);
        }
        this.discount = true;
        this.discount1 = discount;
        try {
            cmapper.updateCategoryDiscount(this.name, discount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addingMinimumAttributeItem(String iname, int min) {
        String result = "there is no item with this date";
        for (ItemSpecs item : products) {
            if (item.getName().equals(iname)) {
                if (item.setMinAmount(min)) {
                    result = "operation ended successfully";
                }
            }
        }
        return result;
    }

    public int getType() {
        return level;
    }

    public HashMap<String, Category> getSubCatName() {
        return this.subCatName;
    }

    public LinkedList<Category> getSubCat() {
        return this.subCat;
    }

    public LinkedList<ItemSpecs> getList() {
        return products;
    }

    public String getName() {
        return name;
    }


    public LinkedList<ItemSpecs> getItemSpecs() {
        return products;
    }

    public void addItem(ItemSpecs product) {
        for (ItemSpecs p : products) {
            if (p.getName().equals(product.getName()))
                return;
        }
        products.add(product);
    }

//    public String makingOrder(String order) {
//        Scanner scanner = new Scanner(System.in);
//        String response = "";
//        System.out.println("Starting ordering process, if you need information about a specific product entet i");
//        int amount = 0;
//        for (ItemSpecs item : products) {
//            System.out.println("Do you want to order: " + item.getName()+" y/n ?");
//            response = scanner.nextLine();
//            if(response.equals("i")){
//                System.out.println("Please enter date in format: dd/mm/yyyy");
//                String date = scanner.nextLine();
//                this.makingItemReport(item.getId(), date);
//                System.out.println("Do you want to order: " + item.getName()+" y/n ?");
//                response = scanner.nextLine();
//            }
//            if(response.equals("y")){
//                System.out.println("Please enter amount to order");
//                amount = scanner.nextInt();
//                order = order + "Item's name: " + item.getName() + "Item's id: " + item.getId() + "amount to order: " + amount;
//                order = order + "\n";
//            }
//        }
//        if(level != 2){                             //NOT A SUB SUB
//            for(Category category : subCat){
//                order = category.makingOrder(order);
//            }
//        }
//        return order;
//    }

    public void removeItemDiscount(String name) {
        for (ItemSpecs item : products) {
            if (item.getName().equals(name)) {
                item.removeDiscount();
            }
        }
    }

    public void removeCategoryDiscount() {
        for (ItemSpecs item : products) {
            item.removeDiscount();
        }
        this.discount = false;
        this.discount1 = 0;
        try {
            cmapper.updateCategoryDiscount(this.name, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
