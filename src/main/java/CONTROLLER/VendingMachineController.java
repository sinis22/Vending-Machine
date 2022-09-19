package CONTROLLER;

import DAO.VendingMachinePersistenceException;
import DTO.Change;
import DTO.Coins;
import DTO.Item;
import SERVICE.VendingMachineInsufficientFundsException;
import SERVICE.VendingMachineNoItemInventoryException;
import SERVICE.VendingMachineService;
import UI.VendingMachineView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class VendingMachineController {
    private final VendingMachineView view;
    private final VendingMachineService service;
    BigDecimal balance = new BigDecimal(0.0);

    public void run()
    {

        boolean keepGoing  = true;
        try {
            while (keepGoing ) {
                int menuSelection  = getMenuSelection();
                switch(menuSelection ) {
                    case 1:  // add funds
                        balance = addMoney(balance);
                        break;
                    case 2:  // list all items
                        printAllItems();
                        break;
                    case 3: // vend item
                        printAllItems();
                        try {
                            balance = buyItem(balance);
                        }
                        catch(VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException e)
                        {
                            view.showBalance(balance);
                            view.displayErrorMessage(e.getMessage());
                        }
                        break;
                    case 4: //quit
                        quit();
                        keepGoing  = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
        }
        catch(VendingMachinePersistenceException e)
        {
            view.showBalance(balance);
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() throws VendingMachinePersistenceException {
        view.printMenu();
        Balance(balance);
        return view.getChoice();
    }
    private void Balance(BigDecimal balance)
    {
        view.showBalance(balance);
    }

    private BigDecimal addMoney(BigDecimal balance) {
        BigDecimal money = view.addMoney();
        return money.add(balance);
    }

    private void printAllItems() throws VendingMachinePersistenceException {
        List<Item> ListofItems = service.listAllItems();
        view.printAllItems(ListofItems);
    }

    private BigDecimal buyItem(BigDecimal balance) throws VendingMachinePersistenceException, 
            VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException {
        int choice;
        do {
            choice = view.itemChoiceMessage();
        } while(!CheckValidity(choice));

        Item selectedItem = getChoice(choice - 1);

        Vend(selectedItem, balance);

        return new BigDecimal(0 );
    }
    private Item getChoice(int item) throws VendingMachinePersistenceException {
        return service.listAllItems().get(item);
    }
    private boolean CheckValidity(int choice) throws VendingMachinePersistenceException {
        if(choice > service.listAllItems().size() || choice < 1) {
            view.invalidItem();
            return false;
        }
        return true;
    }

    private void Vend(Item item, BigDecimal balance) throws VendingMachineInsufficientFundsException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        BigDecimal newBalance = service.vendItem(balance, item);
        if(newBalance.doubleValue() >= 0)
        {
            Change change = new Change();
            Map<Coins, Integer> Change = change.getChange(balance, item.getPrice());
            view.Change(Change);
        }
    }
    private void quit()
    {
        view.displayExitBanner();
    }

    private void unknownCommand()
    {
        view.displayUnknownCommandBanner();
    }

    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }
}
