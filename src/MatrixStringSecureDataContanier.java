import java.util.Iterator;

/**
 * @author Federico Matteoni
 */
public class MatrixStringSecureDataContanier implements SecureDataContainer<String> {

    /*Matrice ij
    i: numero utenti
    j: numero dati
    Mij = n >= 0
        0 -> il dato j non appartiene alla collezione dell'utente i
        n -> il dato j appartiene alla collezione dell'utente i in n copie
    
    Array utenti: posizione i utente <nome-i>
    Array password: posizione i password <pwd-i> dell'utente <nome-i>
    Array dati: posizione j dato <dato-j>
    */
    
    @Override
    public void createUser(String id, String passw) throws InvalidUserException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getSize(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean put(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public String get(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public String remove(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public void copy(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public void share(String owner, String passw, String other, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public Iterator<String> getIterator(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean verifyUser(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public boolean verifyOwnership(String user, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getUsersN() {
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getDataN(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        throw new UnsupportedOperationException("Non supportato.");
    }
    
}
