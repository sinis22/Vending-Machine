import CONTROLLER.VendingMachineController;
import DAO.*;
import SERVICE.VendingMachineService;
import SERVICE.VendingMachineServiceImpl;
import UI.UserIO;
import UI.UserIOConsoleImpl;
import UI.VendingMachineView;

public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException {
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineDao dao = new VendingMachineDaoFileImpl();
        VendingMachineAudioDao audioDao = new VendingMachineAudioDaoFileImpl();
        VendingMachineService service = new VendingMachineServiceImpl(dao, audioDao);
        VendingMachineController controller = new VendingMachineController(view, service);
        controller.run();
    }
}
