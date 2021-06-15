package BusinessLayer.SuppliersModule.Controllers;

import BusinessLayer.SuppliersModule.Objects.Contract;
import BusinessLayer.SuppliersModule.Objects.Item;
import DTOPackage.ContractDTO;
import DataAccessLayer.SuppliersModule.Mappers.ContractsMapper;

import java.util.HashMap;
import java.util.Map;

public class ContractController {
    private static ContractController instance;
    private ContractsMapper mapper;

    private ContractController() {
        mapper = new ContractsMapper();
    }

    public static ContractController getInstance() {
        if (instance == null)
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
        if (mapper.getContract(company_id) != null)
            throw new Exception("There's already supplier working with this company!!!");
        mapper.addContract(company_id, selfPickup);
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
        if (mapper.getContract(company_id) == null)
            throw new Exception("There's no contract with this company id!!!");
        Contract contract = new Contract(mapper.getContract(company_id));
        contract.AddDiscount(item_id, quantity, new_price);
        mapper.addDiscount(company_id, item_id, quantity, new_price);
    }

    /***
     * Removing a discount from the quantity report.
     * @param company_id is the id of the company that the supplier works with it.
     * @param item_id is the id of the item.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is no a quantity report so that's an error.
     * also if there is no discount for this item with this id so that's an error.
     */
    public void RemoveItemQuantity(int company_id, int item_id, int quantity) throws Exception {
        if (mapper.getContract(company_id) == null)
            throw new Exception("There's no contract with this company id!!!");
        Contract contract = new Contract(mapper.getContract(company_id));
        contract.RemoveItemQuantity(item_id);
        mapper.removeDiscount(company_id, item_id, quantity);
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
    public void AddItem(int company_id, int item_id, String name, double price, double weight) throws Exception {
        if (mapper.getContract(company_id) == null)
            throw new Exception("There's no contract with this company id!!!");
        Contract contract = new Contract(mapper.getContract(company_id));
        contract.AddItem(item_id, name, price, weight);
        mapper.addItem(company_id, item_id, name, price, weight);
    }

    /***
     * Removing a current item from the contract.
     * @param company_id is the id of the company that the supplier works with it.
     * @param item_id is the id of the item.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     * also if there is already an item with this id in the contract so that's an error.
     */
    public void RemoveItem(int company_id, int item_id) throws Exception {
        if (mapper.getContract(company_id) == null)
            throw new Exception("There's no contract with this company id!!!");
        Contract contract = new Contract(mapper.getContract(company_id));
        contract.RemoveItem(item_id);
        mapper.removeItem(company_id, item_id);
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
        if (mapper.getContract(company_id) == null)
            throw new Exception("There's no contract with this company id!!!");
        Contract contract = new Contract(mapper.getContract(company_id));
        contract.ChangePrice(item_id, price);
        mapper.updatePrice(company_id, item_id, price);
    }

    /***
     * Printing the contract of a current supplier.
     * @param company_id is the id of the company that the supplier works with it.
     * @return a dto object that holds the same data of the contract.
     * @throws Exception if there is no supplier that works with the company with this id so there is an error.
     * and if everything is okay the so the returned response holds an object with the same data of the contract.
     */
    public ContractDTO PrintContract(int company_id) throws Exception {
        if (mapper.getContract(company_id) == null)
            throw new Exception("There's no contract with this company id!!!");
        return mapper.getContract(company_id);
    }

    /***
     *
     * @param company_id is the id of the company that the supplier works with it.
     * @throws Exception if the there is no a supplier that works with the same company so that's an error.
     */
    public void RemoveContract(int company_id) throws Exception {
        if (mapper.getContract(company_id) == null)
            throw new Exception("There's no contract with this company id!!!");
        mapper.removeContract(company_id);
    }

    public Contract getContract(int supplierID) throws Exception {
        if (mapper.getContract(supplierID) != null)
            return new Contract(mapper.getContract(supplierID));
        else
            throw new Exception("supplier does not exist!");
    }

    public Map<Integer, Contract> ContractsOfItem(String name) {
        Map<Integer, ContractDTO> contracts = mapper.ContractsOfItem(name);
        Map<Integer, Contract> output = new HashMap<>();
        for (int id : contracts.keySet()) {
            output.putIfAbsent(id, new Contract(contracts.get(id)));
        }
        return output;
    }

    public Item getItem(int supplierId, String name) {
        try {
            Contract contract = getContract(supplierId);
            for (int id : contract.getItems().keySet()) {
                if (name.equals(contract.getItems().get(id).getName())) {
                    return contract.getItems().get(id);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
