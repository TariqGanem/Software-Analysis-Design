package Business_Layer.Application;

import DTO.ContractDTO;
import DTO.SupplierDTO;
import Business_Layer.Application.Response.Response;
import Business_Layer.Controllers.*;
import Business_Layer.Controllers.OrderController;
import Business_Layer.Controllers.SupplierController;

import java.util.Map;

public class Facade {
    private static Facade instance = null;
    private SupplierController sc;
    private ContractController cc;
    private OrderController oc;

    public Facade() {
        sc = new SupplierController();
        cc = new ContractController();
        oc = new OrderController();
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    //=======================================================Suppliers=======================================================

    public Response AddSupplier(String name, String Manifactur, int company_id, int BankAccount,
                            String PaymentConditions, String OrderType, boolean SelfPickup) {
        try {
            sc.AddSupplier(name, Manifactur, company_id, BankAccount,
                    PaymentConditions, OrderType, SelfPickup);
            cc.AddContract(company_id, SelfPickup);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response RemoveSupplier(int company_id) {
        try {
            sc.RemoveSupplier(company_id);
            cc.RemoveContract(company_id);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response ChangePaymentConditions(int company_id, String paymentConditions) {
        try {
            sc.ChangePaymentConditions(company_id, paymentConditions);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response ChangeBankAccount(int company_id, int bankAccount) {
        try {
            sc.ChangeBankAccount(company_id, bankAccount);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response<SupplierDTO> PrintSupplierCard(int company_id) {
        try {
            return new Response<SupplierDTO>(sc.PrintSupplierCard(company_id));
        } catch (Exception e) {
            return new Response<SupplierDTO>(e.getMessage());
        }
    }

    public Response AddContactPerson(int company_id, String name, Map<String, String> contactMethods) {
        try {
            sc.AddContactPerson(company_id, name, contactMethods);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response RemoveContact(int company_id, String name) {
        try {
            sc.RemoveContact(company_id, name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response AddMethod(int company_id, String name, String method, String method_data) {
        try {
            sc.AddMethod(company_id, name, method, method_data);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response RemoveMethod(int company_id, String name, String method) {
        try {
            sc.RemoveMethod(company_id, name, method);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response EditMethod(int company_id, String name, String method, String method_data) {
        try {
            sc.EditMethod(company_id, name, method, method_data);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response<SupplierDTO> PrintAllContacts(int company_id) {
        try {
            return new Response<SupplierDTO>(sc.PrintAllContacts(company_id));
        } catch (Exception e) {
            return new Response<SupplierDTO>(e.getMessage());
        }
    }

    //=======================================================Contracts=======================================================

    public Response AddQuantityReport(int company_id) {
        try {
            cc.AddQuantityReport(company_id);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response AddDiscount(int company_id, int item_id, int quantity, double new_price) {
        try {
            cc.AddDiscount(company_id, item_id, quantity, new_price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response RemoveItemQuantity(int company_id, int item_id) {
        try {
            cc.RemoveItemQuantity(company_id, item_id);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response AddItem(int company_id, int item_id, String name, double price) {
        try {
            cc.AddItem(company_id, item_id, name, price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response RemoveItem(int company_id, int item_id) {
        try {
            cc.RemoveItem(company_id, item_id);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response ChangePrice(int company_id, int item_id, double price) {
        try {
            cc.ChangePrice(company_id, item_id, price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response<ContractDTO> PrintContract(int company_id){
        try {
            return new Response<ContractDTO>(cc.PrintContract(company_id));
        } catch (Exception e) {
            return new Response<ContractDTO>(e.getMessage());
        }
    }
}
