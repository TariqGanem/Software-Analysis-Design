package BusinessLayer.StoreModule.Objects;

import DataAccessLayer.StoreModule.Mappers.ItemSpecsMapper;

import java.util.HashMap;

public class ItemSpecs {
    private ItemSpecsMapper ismapper;
    private HashMap<String, Item> itemByDate;
    private String iname;
    private String cname;
    private int minAmount;
    private int totalAmount = 0;
    private String manufacture;
    private int companyPrice;
    private int storePrice;
    private int discount;
    private int finalprice = 0;

    public ItemSpecs(String iname, String cname, int minAmount, int totalAmount, String manufacture, int companyPrice, int storePrice, int discount) {
        ismapper = ItemSpecsMapper.getInstance();
        itemByDate = new HashMap<String, Item>();
        this.iname = iname;
        this.cname = cname;
        this.totalAmount = totalAmount;
        this.minAmount = minAmount;
        this.manufacture = manufacture;
        this.companyPrice = companyPrice;
        this.storePrice = storePrice;
        this.discount = discount;
        this.finalprice = storePrice;
    }

    public HashMap<String, Item> getItemByDate() {
        return itemByDate;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
        this.finalprice = storePrice - (storePrice * discount / 100);
        try {
            ismapper.updateItemSpecsDiscount(iname, discount);
            ismapper.updateItemSpecsFinalPrice(iname, this.finalprice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeDiscount() {
        this.discount = 0;
        finalprice = storePrice;
        try {
            ismapper.updateItemSpecsDiscount(iname, this.discount);
            ismapper.updateItemSpecsFinalPrice(iname, this.finalprice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTotalamount(int amount) {
        this.totalAmount = this.totalAmount + amount;
        try {
            ismapper.updateItemSpecsTotalAmount(iname, this.totalAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subTotalamount(int amount) {
        this.totalAmount = this.totalAmount - amount;
        try {
            ismapper.updateItemSpecsTotalAmount(iname, this.totalAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Tostring() {
        String result = "";
        result = result + "item's Name is: " + this.iname + "\n";
        if (discount > 0 && discount < 100) {
            result = result + "item's Discount percentage is: " + this.discount + "\n";
        }
        result = result + "item's company price is: " + this.companyPrice + "\n";
        result = result + "item's original price is: " + this.storePrice + "\n";
        result = result + "item's final price is: " + this.finalprice + "\n";
        result = result + "item's Total amount is: " + (this.totalAmount) + "\n";
        result = result + "item's Company Name is: " + (this.manufacture) + "\n";
        result = result + "----------------------\n";
        for (Item item : itemByDate.values()) {
            result = result + item.Tostring() + "\n";
            result = result + "----------------------\n";
        }
        return result;
    }

    public void moveToShelf(int amount, String date) {
        int counter = 0;
        int i = 0;
        if (date == null) {
            for (Item item : itemByDate.values()) {
                if (counter >= amount) {
                    break;
                }
                for (i = 0; i < item.getStorageAmount(); i++) {
                    if (counter >= amount) {
                        break;
                    }
                    counter += 1;
                }
                item.moveToShelf(i);
            }
        } else {
            itemByDate.get(date).moveToShelf(amount);
        }
    }

    public void addingDefectItem(String date, int amount, String defectReason) {
        int counter = 0;
        int i = 0;
        int j = 0;
        if (date == null) {
            for (Item item : itemByDate.values()) {
                if (counter >= amount) {
                    break;
                }
                for (i = 0; i < item.getShelveAmount(); i++) {
                    if (counter >= amount) {
                        break;
                    }
                    counter += 1;
                }
                for (j = 0; j < item.getStorageAmount(); j++) {
                    if (counter >= amount) {
                        break;
                    }
                    counter += 1;
                }
                item.setDefectedAmount(j, i);
                item.setDefectReason(defectReason);
            }
            this.totalAmount = totalAmount - amount;
            try {
                ismapper.updateItemSpecsTotalAmount(iname, this.totalAmount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            itemByDate.get(date).setdefamount(amount);
            itemByDate.get(date).setDefectReason(defectReason);
            this.totalAmount = totalAmount - amount;
            try {
                ismapper.updateItemSpecsTotalAmount(iname, this.totalAmount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addItem(String expdate, Item item) {
        itemByDate.put(expdate, item);
    }

    public String getName() {
        return iname;
    }

    public String getManufactureName() {
        return manufacture;
    }

    public int getCompanyPrice() {
        return companyPrice;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public boolean setMinAmount(int amount) {
        if (totalAmount < amount) {
            return false;
        } else {
            this.minAmount = amount;
            return true;
        }
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setFinalprice(int i) {
        this.finalprice = i;
    }

    public String getCname() {
        return cname;
    }
}
