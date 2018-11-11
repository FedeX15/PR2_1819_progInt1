import java.util.HashMap;
import java.util.Iterator;

/**
 * Implementazione di SecureDataContainer.java con HashMap
 * @author Federico Matteoni
 */
public class HashMapSecureDataContainer implements SecureDataContainer<HashMap> {

    @Override
    public void createUser(String id, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getSize(String owner, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean put(String owner, String passw, HashMap data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public HashMap get(String owner, String passw, HashMap data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public HashMap remove(String owner, String passw, HashMap data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public void copy(String owner, String passw, HashMap data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public void share(String owner, String passw, String other, HashMap data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public Iterator<HashMap> getIterator(String owner, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean verifyUser(String user, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean verifyOwnership(String user, String passw, HashMap data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getUsersN() {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getDataN(String user, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }
    
}
