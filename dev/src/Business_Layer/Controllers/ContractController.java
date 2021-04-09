package Business_Layer.Controllers;

import DTO.ContractDTO;
import Business_Layer.Objects.Contract;

import java.util.HashMap;
import java.util.Map;

public class ContractController {
    private static ContractController instance;
    private final Map<Integer, Contract> contracts;

    private ContractController() {
        contracts = new HashMap<>();
    }

    public static ContractController getInstance(){
        if (instance==null)
            instance = new ContractController();
        return instance;
    }

    /***
     *
     * @param company_id is the id of the company that the supplier works with it.
     * @param selfPickup its value is true or false, if the supplier is picking up the orders by himself or not.
     * @throws Exception
     */
    public void AddContract(int company_id, boolean selfPickup) throws Exception {
        if (contracts.containsKey(company_id))
            throw new Exception("There's already supplier working with this company!!!");
        contracts.putIfAbsent(company_id, new Contract(selfPickup));
    }

    /***
     * Adding a quantity report.
     * @param company_id is the id of the company that the supplier works with it.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is already a quantity report so that's an error.
     */
    public void AddQuantityReport(int company_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).AddQuantityReport();
    }

    /***
     * Adding a new discount to the quantity report.
     * @param company_id is the id of the company that the supplier works with it.
     * @param item_id is the id of the item.
     * @param quantity is the quantity of the item.
     * @param new_price is the price of the item.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is no a quantity report so that's an error.
     * also if there is a discount with this quantity then that's an error.
     */
    public void AddDiscount(int company_id, int item_id, int quantity, double new_price) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).AddDiscount(item_id, quantity, new_price);
    }

    /***
     * Removing a discount from the quantity report.
     * @param company_id is the id of the company that the supplier works with it.
     * @param item_id is the id of the item.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is no a quantity report so that's an error.
     * also if there is no discount for this item with this id so that's an error.
     */
    public void RemoveItemQuantity(int company_id, int item_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).RemoveItemQuantity(item_id);
    }

    /***
     * Adding a new item to the contract.
     * @param company_id is the id of the company that the supplier works with it.
     * @param item_id is the id of the item.
     * @param name is the name of the item.
     * @param price is the price of the item.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is already an item with this id in the contract so that's an error.
     */
    public void AddItem(int company_id, int item_id, String name, double price) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).AddItem(item_id, name, price);
    }

    /***
     * Removing a current item from the contract.
     * @param company_id is the id of the company that the supplier works with it.
     * @param item_id is the id of the item.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is already an item with this id in the contract so that's an error.
     */
    public void RemoveItem(int company_id, int item_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).RemoveItem(item_id);
    }

    /***
     * Changing the price of a current item in the contract.
     * @param company_id is the id of the company that the supplier works with it.
     * @param item_id is the id of the item.
     * @param price is the price of the item.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is already an item with this id in the contract so that's an error.
     */
    public void ChangePrice(int company_id, int item_id, double price) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.get(company_id).ChangePrice(item_id, price);
    }

    /***
     * Printing the contract of a current supplier.
     * @param company_id is the id of the company that the supplier works with it.
     * @return a dto object that holds the same data of the contract.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     * and if everything is okay the so the returned response holds an object with the same data of the contract.
     */
    public ContractDTO PrintContract(int company_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        return new ContractDTO(contracts.get(company_id));
    }

    /***
     *
     * @param company_id is the id of the company that the supplier works with it.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     */
    public void RemoveContract(int company_id) throws Exception {
        if (!contracts.containsKey(company_id))
            throw new Exception("There's no contract with this company id!!!");
        contracts.remove(company_id);
    }

    public Contract getContract(Integer supplierID) throws Exception {
        if (contracts.containsKey(supplierID))
            return contracts.get(supplierID);
        else
            throw new Exception("supplier does not exist!");
    }
}
