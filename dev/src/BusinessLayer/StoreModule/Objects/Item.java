package BusinessLayer.StoreModule.Objects;

import DataAccessLayer.StoreModule.Mappers.ItemMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class Item {
    private ItemMapper imapper;
    private String iname;
    private String expDate;
    private int ID;
    private int shelveAmount;
    private int storageAmount;
    private int defectedAmount = 0;
    private String defectReason = "";

    public Item(String iname, String expDate, int ID, int shelveAmount, int storageAmount) {
        imapper = ItemMapper.getInstance();
        this.iname = iname;
        this.expDate = expDate;
        this.ID = ID;
        this.shelveAmount = shelveAmount;
        this.storageAmount = storageAmount;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public int getShelveAmount() {
        return shelveAmount;
    }

    public void setDefectedAmount(int storage, int shelve) {
        this.storageAmount = storageAmount - storage;
        this.shelveAmount = shelveAmount - shelve;
        this.defectedAmount = storage + shelve;
        try {
            imapper.updateItemShelveamount(ID, this.shelveAmount);
            imapper.updateItemStorageAmount(ID, this.storageAmount);
            imapper.updateItemDefecedamount(ID, this.defectedAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDefectReason(String defectReason) {
        this.defectReason = "item's defect reason is: " + defectReason;
    }

    public void setdefamount(int amount) {
        int counter = 0;
        int j = 0;
        int i = 0;
        for (i = 0; i < shelveAmount; i++) {
            if (counter >= amount) {
                break;
            }
            counter++;
        }
        this.shelveAmount = shelveAmount - i;
        try {
            imapper.updateItemShelveamount(ID, this.shelveAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (j = 0; j < storageAmount; j++) {
            if (counter >= amount) {
                break;
            }
            counter++;
        }
        this.storageAmount = storageAmount - j;
        this.defectedAmount = defectedAmount + j + i;
        try {
            imapper.updateItemStorageAmount(ID, this.storageAmount);
            imapper.updateItemDefecedamount(ID, this.defectedAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getExpDate() {
        return expDate;
    }

    public String isDefect() {
        return "Defected amount is : " + defectedAmount + "\n";
    }

    public void updateStorageAmount(int amount) {
        this.storageAmount = this.storageAmount + amount;
        try {
            imapper.updateItemStorageAmount(ID, this.storageAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Tostring() {
        String result = "";
        result = result + "item's id is: " + this.ID + "\n";
        result = result + "item's expiration date is: " + this.expDate + "\n";
        result = result + "amount of defect items: " + this.defectedAmount + "\n";
        if (!defectReason.equals("")) {
            result = result + this.defectReason + "\n";
        }
        result = result + "item's Shelve amount is: " + (this.shelveAmount) + "\n";
        result = result + "item's Storage amount is: " + this.storageAmount + "\n";
        return result;
    }

    public int date(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date firstDate = null;
        Date secondDate = null;

        try {
            firstDate = sdf.parse(this.expDate);
            secondDate = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = secondDate.getTime() - firstDate.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
        return (int) diffrence;
    }

    public boolean moveToShelf(int amount) {
        if (this.storageAmount >= amount) {
            this.storageAmount = this.storageAmount - amount;
            this.shelveAmount = this.shelveAmount + amount;
            try {
                imapper.updateItemShelveamount(ID, this.shelveAmount);
                imapper.updateItemStorageAmount(ID, this.storageAmount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public int getID() {
        return ID;
    }

    public void setD(int i) {
        this.defectedAmount = i;
    }

    public String getname() {
        return iname;
    }
}
