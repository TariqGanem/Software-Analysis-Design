package BusinessLayer.Application;

import BusinessLayer.Application.Response.Response;
import BusinessLayer.Controller.BusinessController;

public class Facade {
    private static Facade instance;
    private BusinessController bc;

    public Facade() {
        bc = BusinessController.getInstance();
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public void run(){
        bc.run();
    }

    public Response CheckIfExist(String cname) {
        try{
             bc.checkIfCatExist(cname);
             return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response CheckIfItemExist(String cname, String iname) {
        try{
            bc.checkIfItExist(cname,iname);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response AddNewAmount(String cname, String iname, int amount, String expdate) {
        try{
            bc.AddNewAmount(cname,iname,amount,expdate);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    // adds a new category
    public void addingCategory(String name) {
        try {
            bc.addingCategory(name,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response addItem(String iname, String cname, int amount, int minamount, int storePrice, int discount, String expdate, int companyprice, String manufacture) {
        try{
            bc.addItem(iname,cname, amount,minamount,expdate,manufacture,companyprice,storePrice,discount);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addingDiscount(String cname, String iname, int discount) {
        try{
            bc.addingDiscount(cname, iname, discount);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response checkAmount(String cname, String iname, int amount) {
        try{
            bc.checkAmount(cname,iname, amount);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addingDefectItem(String cname, String iname, String date, int amount, String defectReason) {
        try{
            bc.addingDefectItem(cname,iname, date, amount, defectReason);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response ifEnough1(String cname, String iname, int amount, String date) {
        try{
            bc.ifEnough1(cname,iname,amount,date);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response checkExp(String iname, String cname, String exp) {
        try{
            bc.checkExp(iname,cname,exp);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addingMinimumAttributeItem(String cname, String iname, int min) {
        try{
            bc.addingMinimumAttributeItem(cname,iname, min);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response makingCategoryReport(String cname, String date, String desc) {
        try{
            bc.makingCategoryReport(cname, date,desc);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response makingDefectReport(String date, String desc) {
        try{
            bc.makingDefectReport(date,desc);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response makingItemReport(String cname, String iname, String date, String desc) {
        try{
            bc.makingItemReport(cname,iname, date,desc);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response makingLackReport(String date, String desc) {
        try{
            bc.makingLackReport(desc,date);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response checkCategoryNameLegality(String subCategoryName) {
        try{
            bc.checkCategoryNameLegality(subCategoryName);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public void addsubCategory(String upperCategorySerialNumber, String subCategoryName, int subCategoryLevel) {
        try {
            bc.addsubCategory(upperCategorySerialNumber, subCategoryName, subCategoryLevel,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response transferItems(String cname, String iname, String date, int amount) {
        try{
            bc.transferItems(cname,iname,date, amount);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response ifEnough2(String cname, String iname, int amount) {
        try{
            bc.ifEnough2(cname,iname,amount);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeDiscount(String cname, String o) {
        try{
            this.bc.removeDiscount(cname, o);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response RefreshStorage(String date) {
        try{
            bc.refresh(date);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public void status(){
        bc.status();
    }

    public Response removeCategoryOrItem(String cname, String iname) {
        try{
            bc.removeCategoryOrItem(cname, iname);
            return new Response();
        }catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response makeMinOrder() {
        try{
            bc.makeMinOrder();
        }catch(Exception e){
            return new Response(e.getMessage());
        }
    }
}
