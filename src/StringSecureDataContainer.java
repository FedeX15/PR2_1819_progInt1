import java.util.Iterator;

/**
 * Implementazione di SecureDataContainer.java con String
 * @author Federico Matteoni
 */
public class StringSecureDataContainer implements SecureDataContainer<String> {
        
    public StringSecureDataContainer() {
    }

    @Override
    public void createUser(String id, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getSize(String owner, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean put(String owner, String passw, String data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public String get(String owner, String passw, String data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public String remove(String owner, String passw, String data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public void copy(String owner, String passw, String data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public void share(String owner, String passw, String other, String data) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public Iterator<String> getIterator(String owner, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean verifyUser(String user, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean verifyOwnership(String user, String passw, String data) {
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
