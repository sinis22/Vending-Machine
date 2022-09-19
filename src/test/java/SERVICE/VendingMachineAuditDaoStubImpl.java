package SERVICE;


import DAO.VendingMachineAudioDao;
import DAO.VendingMachinePersistenceException;

public class VendingMachineAuditDaoStubImpl implements VendingMachineAudioDao {
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        // testing. does nothing
    }
}
