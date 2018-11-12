import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Implementazione di SecureDataContainer.java con String
 * @author Federico Matteoni
 */
public class StringSecureDataContainer implements SecureDataContainer<String> {
    private HashMap<String, String> usrPwd;
    private HashMap<String, List<String>> usrData;
        
    public StringSecureDataContainer() {
        this.usrPwd = new HashMap();
        this.usrData = new HashMap();
    }

    @Override
    public void createUser(String id, String passw) throws InvalidUserException{
        if (id != null && passw != null) {
            if (!(usrPwd.containsKey(id))) {
                usrPwd.put(id, passw);
                usrData.put(id, new ArrayList<>());
            } else throw new InvalidUserException();
        } else throw new NullPointerException();
    }

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
    public boolean put(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    if (!(data.equals(""))) {
                        usrData.get(owner).add(data);
                        return true;
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public String get(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw)) {
                    if (!(data.equals(""))) {
                        for (String s : usrData.get(owner)) {
                            if (s.equals(data)) return data;
                        }
                        throw new DataNotOwnedException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
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
    public boolean verifyOwnership(String user, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (user != null && passw != null && data != null) {
            if (usrPwd.containsKey(user)) {
                if (usrPwd.get(user).equals(passw)) {
                    if (!(data.equals(""))) {
                        for (String s : usrData.get(user)) {
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
        throw new UnsupportedOperationException("Non supportato.");
    }

    @Override
    public int getDataN(String user, String passw) {
        throw new UnsupportedOperationException("Non supportato.");
    }
    
}
