package SERVICE;


import DAO.VendingMachineDao;
import DAO.VendingMachinePersistenceException;
import DTO.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Item testItem;

    public VendingMachineDaoStubImpl()
    {
        testItem = new Item("Chips", new BigDecimal(2.99), 2);
    }

    @Override
    public Item getItem(String name) {
        if(name.equals(testItem.getName()))
        {
            return testItem;
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Item> listAllItems() throws VendingMachinePersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(testItem);
        return itemList;
    }

    @Override
    public Item addItem(Item item) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public Item removeItem(Item item) throws VendingMachinePersistenceException {
        return null;
    }

    @Override
    public void changeInventoryCount(Item item, int newCount) throws VendingMachinePersistenceException {
        if(item != null) {
            item.setInventory(newCount);
        }
    }
}
