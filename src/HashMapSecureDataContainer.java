import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Implementazione di SecureDataContainer.java con HashMap
 * AUTHOR Federico Matteoni
 */
public abstract class HashMapSecureDataContainer<E> implements SecureDataContainer<E> {
    /*
    OVERVIEW: gli oggetti del tipo HashMapDataContainer<E> sono collezioni
    modificabili di oggetti di tipo E assegnati ad una coppia utente-password
    TYPICAL ELEMENT:
        usrPwd: <<nome-1, pwd-1>, <nome-2, pwd-2>, ..., <nome-n, pwd-n>>
                con i!=j => nome-i != nome-j
        usrData: <<nome-1, <data-1-1, ..., data-1-m1>, ...,
                 <nome-n, <data-n-1, ..., data-n-mn>>
    
    a(c) = {(s, c.usrPwd.get(s), c.usrData.get(s).get(i)) |
           c.usrPwd.containsKey(s) && c.usrData.containsKey(s)
           && 0 <= i < c.usrData.get(s).size()}
    
    I(c) = (c.usrPwd != null)
           && (c.usrData != null)
           && (c.usrData.containsKey(s) => c.usrPwd.containsKey(s))
           && (c.usrPwd.size() <= c.usrData.size())
           && (c.verifyData(c.usrData.get(s).get(i)) per ogni s,i |
              c.usrData.contains(s) && 0 <= i < c.usrData.get(s).size())
    */
    private HashMap<String, String> usrPwd;
    private HashMap<String, ArrayList<E>> usrData;
        
    public HashMapSecureDataContainer() {
        this.usrPwd = new HashMap();
        this.usrData = new HashMap(); //Inizializzazione delle due hashmap vuote
    }
            
    @Override
    public void createUser(String id, String passw) throws InvalidUserException{
        if (id != null && passw != null) {
            if (!(usrPwd.containsKey(id))) { //se non sono null verifico che il nome utente non sia già usato
                usrPwd.put(id, passw);
                usrData.put(id, new ArrayList<>()); //in caso non sia usato vado a creare una nuova mappatura in entrambe le hashmap, con chiave il nuovo nome utente
            } else throw new InvalidUserException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché id è aggiunto solo se nell'hashmap usrPwd non è presente
    una mappatura con chiave uguale a id.
    Se id è aggiunto in usrPwd, con assegnato valore passw, è anche aggiunto a
    usrData con un arraylist vuoto. Ciò mantiene le condizioni della I(c) di
    avere una mappatura con chiave id in usrData solo se è presente una
    mappatura con chiave id in usrPwd
    */

    @Override
    public int getSize(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (owner != null && passw != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    return usrData.get(owner).size();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean put(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    if (verifyData(data)) {
                        usrData.get(owner).add(data);
                        return true;
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché data è aggiunto solo quando è verificato e ciò non va ad
    invalidare nessuna delle condizioni della I(c)
    */

    @Override
    public E get(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        E datacopy = data;
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    if (verifyData(data)) {
                        for (E s : usrData.get(owner)) {
                            if (s.equals(data)) return datacopy;
                        }
                        throw new DataNotOwnedException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché data è ritornato solo se presente nella collezione e ciò
    non va ad invalidare nessuna delle condizioni della I(c)
    */

    @Override
    public E remove(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        E datacopy = data;
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    if (verifyData(data)) {
                        for (int i = 0; i < usrData.get(owner).size(); i++) {
                            if (usrData.get(owner).get(i).equals(data)) {
                                usrData.get(owner).remove(i);
                                return datacopy;
                            }
                        }
                        return null;
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché data è rimosso solo se presente nella collezione e viene
    rimossa la prima occorrenza e ciò non va ad invalidare nessuna
    delle condizioni della I(c)
    */

    @Override
    public void copy(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        E dataCopy = data;
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    if (verifyData(data)) {
                        if (usrData.get(owner).contains(data)) {
                                usrData.get(owner).add(dataCopy);
                            } else throw new DataNotOwnedException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché una copia di data è messa nella collezione di owner solo
    se owner già possiede una copia di data e ciò non va ad invalidare nessuna
    delle condizioni della I(c)
    */

    @Override
    public void share(String owner, String passw, String other, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        E dataCopy = data;
        if (owner != null && passw != null && other != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.containsKey(other)) {
                    if (usrPwd.get(owner).equals(passw)) {
                        if (verifyData(data)) {
                            if (usrData.get(owner).contains(data)) {
                                usrData.get(other).add(dataCopy);
                            } else throw new DataNotOwnedException();
                        } else throw new InvalidDataException();
                    } else throw new InvalidPasswordException();
                } else throw new UserNotFoundException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché una copia di data è messa nella collezione di other solo
    se owner già possiede una copia di data e ciò non va ad invalidare nessuna
    delle condizioni della I(c)
    */

    @Override
    public Iterator<E> getIterator(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (owner != null && passw != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    return usrData.get(owner).iterator();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyUser(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (user != null && passw != null) {
            if (usrPwd.containsKey(user)) {
                if (usrPwd.get(user).equals(passw)) {
                    return true;
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyOwnership(String user, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (user != null && passw != null && data != null) {
            if (usrPwd.containsKey(user)) {
                if (usrPwd.get(user).equals(passw)) {
                    if (verifyData(data)) {
                        for (E s : usrData.get(user)) {
                            if (s.equals(data)) return true;
                        }
                        return false;
                    }
                    else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public int getUsersN() {
        return usrPwd.size();
    }
}