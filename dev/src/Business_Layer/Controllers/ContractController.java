package Business_Layer.Controllers;

import DTO.ContractDTO;
import Business_Layer.Objects.Contract;

import java.util.HashMap;
import java.util.Map;

public class ContractController {
    private static ContractController instance;
    private Map<Integer, Contract> contracts;

    private ContractController() {
        contracts = new HashMap<>();
    }

    public static ContractController getInstance(){
        if (instance==null)
            instance = new ContractController();
        return instance;
    }
    public void AddContract(int company_id, boolean selfPickup) throws Exception {
        if (contracts.containsKey(company_id))
            throw new Exception("There's already supplier working with this company!!!");
        contracts.putIfAbsent(company_id, new Contract(selfPickup));
    }

    public void AddQuantityReport(int company_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).AddQuantityReport();
    }

    public void AddDiscount(int company_id, int item_id, int quantity, double new_price) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).AddDiscount(item_id, quantity, new_price);
    }

    public void RemoveItemQuantity(int company_id, int item_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).RemoveItemQuantity(item_id);
    }

    public void AddItem(int company_id, int item_id, String name, double price) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).AddItem(item_id, name, price);
    }

    public void RemoveItem(int company_id, int item_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).RemoveItem(item_id);
    }

    public void ChangePrice(int company_id, int item_id, double price) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).ChangePrice(item_id, price);
    }

    public ContractDTO PrintContract(int company_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        return new ContractDTO(contracts.get(company_id));
    }

    public void RemoveContract(int company_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.remove(company_id);
    }
}
