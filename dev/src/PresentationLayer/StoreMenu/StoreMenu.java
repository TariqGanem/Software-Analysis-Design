package PresentationLayer.StoreMenu;

import BusinessLayer.StoreModule.Application.Facade;
import BusinessLayer.SuppliersModule.Response.Response;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class StoreMenu {
    /*
    serial number length is 10 -> first three digits for category, fourth digit for subCategory(0-main, 1-sub, 2-subsub), fifth - tenth item digits
    example of item serial number 0150000345 - item that its id is 345 belongs to main category 15
    example of category serial number 4531000000 - sub category it is id is 453
    */
    private Facade facade = Facade.getInstance();
    //private static SystemManager systemManager = new SystemManager();
    private final static int minLevel = 0;
    private final static int maxLevel = 2;

    //BUSINESS SECTION---------------------------------------------------------------------------------------------------------
    public void showSpecificMenu() {
        try {
            facade.run();
        } catch (Exception e) {
        }
        System.out.println("----------->SuperLe Main Menu<-----------");
        int option = 0;
        Scanner scanner = new Scanner(System.in);
        boolean toStop = false;
        while (!toStop) {
            System.out.println("Please choose an operation to procces:\n1)Add an item\n2)Remove item or category\n3)Add discount\n"
                    + "4)Add category\n5)Add sub-category\n6)Add defect item\n7)Add minimum attribute for item\n"
                    + "8)Make report\n9)Transfer items\n10)Remove discount\n11)Status\n12)itesm until this expdate\n13)refresh Storage\n14)make an order\n15)Exit");
            option = scanner.nextInt();
            if (option == 1) {
                addingItem();
            } else if (option == 2) {
                removingItemOrCategory();
            } else if (option == 3) {
                addingDiscount();
            } else if (option == 4) {
                addingCategory();
            } else if (option == 5) {
                addingSubCategory();
            } else if (option == 6) {
                addingDefectItem();
            } else if (option == 7) {
                addingMinimumAttributeItem();
            } else if (option == 8) {
                makingReport();
            } else if (option == 9) {
                tranferItems();
            } else if (option == 10) {
                removeDiscount();
            } else if (option == 11) {
                facade.status();
            } else if (option == 12) {
                itemsTillExp();
            } else if (option == 13) {
                RefreshStorage();
            } else if (option == 14) {
                makingOrder();
            } else if (option == 15) {
                toStop = true;
            } else {
                System.out.println("Wrong choice");
            }
        }
    }

    private void makingOrder() {
        Scanner scanner = new Scanner(System.in);
        String itemName = "";
        String categoryName = "";
        int amount = 0;
        System.out.println("Please Enter item's category name to order");
        categoryName = scanner.nextLine();
        System.out.println("Please Enter item's name to order");
        itemName = scanner.nextLine();
        Response res = facade.CheckIfItemExist(categoryName, itemName);
        if (!res.isError()) {
            System.out.println("Please Enter amount to order");
            amount = scanner.nextInt();
            Response res2 = facade.makingOrder(itemName, amount, getDate());
            if (res2.isError())
                System.out.println(res2.getErrorMessage());
            System.out.println("order finished successfully");
        } else {
            System.out.println("Item does not exist");
        }
    }

    protected LocalDate getDate() {
        System.out.println("Enter order due date [dd/MM/yyyy]: ");
        while (true) {
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(new Scanner(System.in).nextLine());
                return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (Exception e) {
                System.out.println("Enter valid date format!");
            }
        }
    }

    public void RefreshStorage() {
        Scanner scanner = new Scanner(System.in);
        String date;
        System.out.println("Please Enter today's date in this format: dd/mm/yyyy");
        date = scanner.nextLine();
        facade.RefreshStorage(date);
    }

    private void removeDiscount() {
        Scanner scanner = new Scanner(System.in);
        String scan = "";
        String cname = "";
        String iname = "";
        System.out.println("is it a 1)category discount or 2)item discount? enter ('1'/'2') ");
        scan = scanner.nextLine();
        System.out.println("enter category name :");
        cname = scanner.nextLine();
        Response result1 = facade.CheckIfExist(cname);
        if (!result1.isError()) {
            if (scan.equals("1")) {
                facade.removeDiscount(cname, null);
            }
            if (scan.equals("2")) {
                System.out.println("enter Item name :");
                iname = scanner.nextLine();
                facade.removeDiscount(cname, iname);
            }
        } else {
            System.out.println("category does not exist!");
        }
    }


    //checked
    private void tranferItems() {
        Scanner scanner = new Scanner(System.in);

        String cname;
        String iname;
        String response;
        String date = null;
        int amount = 0;
        System.out.println("enter category name :");
        cname = scanner.nextLine();
        System.out.println("enter Item name :");
        iname = scanner.nextLine();

        System.out.println("Do you want to move specific expiration date items? y/n");
        response = scanner.nextLine();

        Response result1 = facade.CheckIfExist(cname);
        Response result2 = facade.CheckIfItemExist(cname, iname);
        if (!result1.isError() || !result2.isError()) {
            if (response.equals("y")) {
                System.out.println("Please Enter item's date in this format: dd/mm/yyyy");
                date = scanner.nextLine();
                System.out.println("Please enter amount to transfer from storage to shelves");
                amount = scanner.nextInt();
                if (amount > 0) {
                    Response result3 = facade.ifEnough1(cname, iname, amount, date);
                    if (!result3.isError()) {
                        facade.transferItems(cname, iname, date, amount);
                    }
                } else {
                    System.out.println("Error: Negative Number");
                }
            }
            if (response.equals("n")) {
                System.out.println("Please enter amount to transfer from storage to shelves");
                amount = scanner.nextInt();
                if (amount > 0) {
                    Response result4 = facade.ifEnough2(cname, iname, amount);
                    if (!result4.isError()) {
                        facade.transferItems(cname, iname, date, amount);
                    } else {
                        System.out.println("Error: No enough items to transfer!");
                    }
                } else {
                    System.out.println("Error: Negative Number");
                }
            }
        } else {
            System.out.println("Error: Category or item does not exist");
        }
    }

    //checked    **
    private void addingSubCategory() {
        Scanner scanner = new Scanner(System.in);
        String upperCategoryName;
        String subCategoryName;
        int subCategoryLevel;
        System.out.println("Please enter upper category name");
        upperCategoryName = scanner.nextLine();
        Response result1 = facade.CheckIfExist(upperCategoryName);
        if (!result1.isError()) {    //* boolean function that returns true if it found the serial number
            System.out.println("Please enter sub category's name");
            subCategoryName = scanner.nextLine();
            Response result2 = facade.checkCategoryNameLegality(subCategoryName);
            if (!result2.isError()) {                        //* boolean function should returns true is name has at least one letter and does not exist already
                System.out.println("Please enter sub category's Level");
                subCategoryLevel = scanner.nextInt();
                if (subCategoryLevel > minLevel && subCategoryLevel <= maxLevel)
                    facade.addsubCategory(upperCategoryName, subCategoryName, subCategoryLevel);    //* void function adds sub category to upper category
            } else {
                System.out.println("Illegal name");
            }
        } else {
            System.out.println("Upper Category Name does not exist");
        }
    }


    //checked
    private void makingReport() {
        Scanner scanner = new Scanner(System.in);
        String option = "";
        String cname;
        String iname;
        String desc = "";
        System.out.println("Please choose a report:\n1)Category report\n2)Item report\n3)Defect report\n4)Lack report");
        option = scanner.nextLine();

        if (option.equals("1")) {
            System.out.println("Please enter category name");
            cname = scanner.nextLine();
            Response result1 = facade.CheckIfExist(cname);
            if (!result1.isError()) {
                System.out.println("Please enter Date to add in format: dd/mm/yyyy");
                String date = scanner.nextLine();
                System.out.println("Do you want to add description? y/n");
                if (scanner.nextLine().equals("y")) {
                    System.out.println("Please enter description");
                    desc = scanner.nextLine();
                    facade.makingCategoryReport(cname, date, desc);
                }//* prints category report
                else {
                    facade.makingDefectReport(date, desc);
                }
            } else {
                System.out.println("category does not exist");
            }
        } else if (option.equals("2")) {
            System.out.println("Please enter category name: ");
            cname = scanner.nextLine();
            System.out.println("Please enter item name: ");
            iname = scanner.nextLine();
            Response result2 = facade.CheckIfExist(cname);
            Response result3 = facade.CheckIfItemExist(cname, iname);
            if (!result2.isError() || !result3.isError()) {    //* boolean function that returns true if it found the serial number
                System.out.println("Please enter Date to add in format: dd/mm/yyyy");
                String date = scanner.nextLine();
                System.out.println("Do you want to add description? y/n");
                if (scanner.nextLine().equals("y")) {
                    System.out.println("Please enter description");
                    desc = scanner.nextLine();
                    facade.makingItemReport(cname, iname, date, desc);
                }//* prints item report
                else {
                    facade.makingDefectReport(date, desc);
                }
            } else {
                System.out.println("Serial number does not exist");
            }
        } else if (option.equals("3")) {
            System.out.println("Please enter Date to add in format: dd/mm/yyyy");
            String date = scanner.nextLine();
            System.out.println("Do you want to add description? y/n");
            if (scanner.nextLine().equals("y")) {
                System.out.println("Please enter description");
                desc = scanner.nextLine();
                facade.makingDefectReport(date, desc);
            }//* prints defectReport
            else {
                facade.makingDefectReport(date, desc);
            }
        } else if (option.equals("4")) {
            System.out.println("Please enter Date to add in format: dd/mm/yyyy");
            String date = scanner.nextLine();
            System.out.println("Do you want to add description? y/n");
            if (scanner.nextLine().equals("y")) {
                System.out.println("Please enter description");
                desc = scanner.nextLine();
                facade.makingLackReport(date, desc);
            } else {
                facade.makingLackReport(date, desc);
            }
            //* prints LackReport

        } else {
            System.out.println("Wrong choice");
        }
    }

    //checked
    private void addingMinimumAttributeItem() {
        Scanner scanner = new Scanner(System.in);
        int min;
        String cname;
        String iname;
        System.out.println("Please Enter minimum amount");
        min = scanner.nextInt();
        if (min <= 0) {
            System.out.println("Please Enter minimum amount bigger than zero");
        } else {
            System.out.println("Please Enter category name");
            cname = scanner.nextLine();
            System.out.println("Please Enter item name");
            iname = scanner.nextLine();
            Response result1 = facade.CheckIfExist(cname);
            Response result2 = facade.CheckIfItemExist(cname, iname);
            if (!result1.isError() || !result2.isError()) {    //* boolean function that returns true if it found the serial number
                Response response = facade.addingMinimumAttributeItem(cname, iname, min);                //* we should return "operation ended successfully" or "there is no item with this date"
                System.out.println(response.getErrorMessage());
            } else {
                System.out.println("Serial number does not exist");
            }
        }
    }

    public void itemsTillExp() {
        Scanner scanner = new Scanner(System.in);
        String exp;
        String cname = null;
        String iname = null;
        String option1;
        String option2;
        System.out.println("do you want to search in specific category? (y / n)");
        option1 = scanner.nextLine();
        System.out.println("do you want to search for specific Item? (y / n)");
        option2 = scanner.nextLine();
        System.out.println("Enter EXPIRATION DATE int this format: dd/MM/yyy");
        exp = scanner.nextLine();
        if (option1.equals("y")) {
            System.out.println("enter Category name");
            cname = scanner.nextLine();
            Response result1 = facade.CheckIfExist(cname);
            if (result1.isError()) {
                System.out.println("Category does not exist");
                return;
            }
        }
        if (option2.equals("y")) {
            System.out.println("enter item name");
            iname = scanner.nextLine();
            System.out.println("enter Category name");
            cname = scanner.nextLine();
            Response result2 = facade.CheckIfItemExist(cname, iname);
            if (result2.isError()) {
                System.out.println("item does not exist");
                return;
            }
        }
        facade.checkExp(iname, cname, exp);

    }

    //checked
    private void addingDefectItem() {
        Scanner scanner = new Scanner(System.in);
        String iname;
        String cname;
        String date = null;
        int amount;
        String defectReason;
        String response;

        System.out.println("Please Enter defect item name");
        iname = scanner.nextLine();
        System.out.println("Please Enter defect item category");
        cname = scanner.nextLine();
        Response result1 = facade.CheckIfExist(cname);
        Response result2 = facade.CheckIfItemExist(cname, iname);
        if (!result1.isError() || !result2.isError()) {
            System.out.println("Please Enter defect reason of this item");
            defectReason = scanner.nextLine();
            System.out.println("Do you want to add specific expiration date  defect items? y/n");
            response = scanner.nextLine();
            if (response.equals("y")) {
                System.out.println("Please Enter item's date in this format: dd/MM/yyyy");
                date = scanner.nextLine();
                System.out.println("Please Enter amount");
                amount = scanner.nextInt();
                if (amount > 0) {
                    Response result3 = facade.ifEnough1(cname, iname, amount, date);
                    if (!result3.isError()) {
                        facade.addingDefectItem(cname, iname, date, amount, defectReason);
                    } else {
                        System.out.println("Error: No enough items!");
                    }
                } else {
                    System.out.println("Error: Negative Number");
                }
            }
            if (response.equals("n")) {
                System.out.println("Please Enter amount");
                amount = scanner.nextInt();
                if (amount > 0) {
                    Response result = facade.checkAmount(cname, iname, amount);
                    if (!result.isError()) {
                        facade.addingDefectItem(cname, iname, date, amount, defectReason);
                    } else {
                        System.out.println("Error: No enough items!");
                    }
                } else {
                    System.out.println("Error: Negative Number");
                }
            }
        }
    }

    //checked
    private boolean addingCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Enter category's name");
        String name = scanner.nextLine();
        Response result = facade.CheckIfExist(name);
        if (!result.isError()) {                        //* boolean function should returns true is name has at least one letter and does not exist already
            facade.addingCategory(name);
            System.out.println("New Category Was Added");
            return true;
        } else {
            System.out.println("Name is already exists or name with no letters");
            return false;
        }
    }

    //checked
    private void addingDiscount() {
        Scanner scanner = new Scanner(System.in);
        String cname;
        String iname;
        String scan;
        int discount = 0;
        System.out.println("is it a 1)category discount or 2)item discount? enter ('1'/'2')");
        scan = scanner.nextLine();
        System.out.println("enter category name:");
        cname = scanner.nextLine();
        Response result1 = facade.CheckIfExist(cname);
        if (!result1.isError()) {
            if (scan.equals("1")) {
                System.out.println("Please Enter discount percentage");
                discount = scanner.nextInt();
                if (discount <= 0 || discount >= 100) {
                    System.out.println("discount has to be positive and less than 100");
                    return;
                }
                facade.addingDiscount(cname, null, discount);
            }
            if (scan.equals("2")) {
                System.out.println("enter Item name:");
                iname = scanner.nextLine();
                System.out.println("Please Enter discount percentage");
                discount = scanner.nextInt();
                if (discount <= 0 || discount >= 100) {
                    System.out.println("discount has to be positive and less than 100");
                    return;
                }
                Response result2 = facade.CheckIfItemExist(cname, iname);
                if (!result2.isError()) {
                    facade.addingDiscount(cname, iname, discount);
                } else {
                    System.out.println("Item does not exist");
                }
            }
        } else {
            System.out.println("category does not exist");
        }
    }


    //checked
    private void removingItemOrCategory() {
        Scanner scanner = new Scanner(System.in);
        String scan;
        String cname;
        String iname;
        System.out.println("is it a 1) category or 2)item you would like to remove? enter ( '1' / '2' ) ");
        scan = scanner.nextLine();
        System.out.println("enter category name :");
        cname = scanner.nextLine();
        Response result1 = facade.CheckIfExist(cname);
        if (!result1.isError()) {
            if (scan.equals("1")) {
                facade.removeCategoryOrItem(cname, null);
                System.out.println("Category Deleted Successfully");
            }
            if (scan.equals("2")) {
                System.out.println("enter item name :");
                iname = scanner.nextLine();
                Response result2 = facade.CheckIfItemExist(cname, iname);
                if (!result2.isError())
                    facade.removeCategoryOrItem(cname, iname);
                System.out.println("Item Deleted Successfully");
            } else if (!(scan.equals("1") || scan.equals("2")))
                System.out.println("Wrong input");
        } else {
            System.out.println("Item does not exist");
        }
    }

    private void addingItem() {
        String cname;
        String iname;
        int minamount;
        String expdate = "";
        String manufacture = "";
        int companyprice;
        int storePrice;
        int discount;
        int amount;

        System.out.println("1) Do you want to add for an existing item \n2)Do you want to create new item ?\n Choose '1'/'2'");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.nextLine();
        if (option.equals("1")) {
            System.out.println("Please enter the name of the item");
            iname = scanner.nextLine();
            System.out.println("Please enter the item category name");
            cname = scanner.nextLine();
            //check if item exists
            Response result1 = facade.CheckIfExist(cname);
            Response result2 = facade.CheckIfItemExist(cname, iname);
            if (!result1.isError() || !result2.isError()) {
                System.out.println("Please Enter item's expiration date in this format: dd/mm/yyyy");
                expdate = scanner.nextLine();
                System.out.println("Please enter Amount of items");
                amount = scanner.nextInt();
                Response result3 = facade.AddNewAmount(cname, iname, amount, expdate);    ////////////******************
            } else {
                System.out.println("Item Does not exist");
            }
        }
        if (option.equals("2")) {
            System.out.println("1) do you want to create a new category?\n2) do you want to add for an existing category?\n Choose '1'\'2'");
            String option2 = scanner.nextLine();
            System.out.println("Please Enter category's name");
            cname = scanner.nextLine();

            if (option2.equals("1")) {
                Response result4 = facade.CheckIfExist(cname);
                if (!result4.isError())
                    facade.addingCategory(cname);
                else {
                    System.out.println("category Name is already exists");
                    return;
                }
            }
            if (option2.equals("2") && (facade.CheckIfExist(cname).isError())) {
                System.out.println("category Name does not exist");
                return;
            }
            System.out.println("Please enter the name of the new item");
            iname = scanner.nextLine();
            System.out.println("Please enter the Manufacture name of the new item");
            manufacture = scanner.nextLine();
            System.out.println("Please Enter item's expiration date in this format: dd/mm/yyyy");
            expdate = scanner.nextLine();
            System.out.println("Please enter the Minimum Amount of the new item");
            minamount = scanner.nextInt();
            System.out.println("Please enter Amount of items");
            amount = scanner.nextInt();
            System.out.println("Please enter the company price of the new item");
            companyprice = scanner.nextInt();
            System.out.println("Please enter the store price of the new item");
            storePrice = scanner.nextInt();
            System.out.println("Please enter a discount for this item, or you can enter 0");
            discount = scanner.nextInt();
            if (discount < 0 || discount >= 100) {
                System.out.println("discount percentage is not valid");
                return;
            }
            Response result5 = facade.addItem(iname, cname, amount, minamount, storePrice, discount, expdate, companyprice, manufacture);
        } else if (!option.equals("2") && !option.equals("1")) {
            System.out.println("Wrong input!");
        }
    }

    private void makeMinOrder() {
        Response result = facade.makeMinOrder();
    }
}
