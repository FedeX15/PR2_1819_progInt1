import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Implementazione di SecureDataContainer.java con String
 * @author Federico Matteoni
 */
public class HashMapStringSecureDataContainer implements SecureDataContainer<String> {
    private HashMap<String, Integer> usrPwd;
    private HashMap<String, ArrayList<String>> usrData;
        
    public HashMapStringSecureDataContainer() {
        this.usrPwd = new HashMap();
        this.usrData = new HashMap();
    }

    @Override
    public void createUser(String id, String passw) throws InvalidUserException{
        if (id != null && passw != null) {
            if (!(usrPwd.containsKey(id))) {
                usrPwd.put(id, passw.hashCode());
                usrData.put(id, new ArrayList<>());
            } else throw new InvalidUserException();
        } else throw new NullPointerException();
    }

    @Override
    public int getSize(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (owner != null && passw != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw.hashCode())) {
                    return usrData.get(owner).size();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean put(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw.hashCode())) {
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
                if (usrPwd.get(owner).equals(passw.hashCode())) {
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
    public String remove(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw.hashCode())) {
                    if (!(data.equals(""))) {
                        for (int i = 0; i < usrData.size(); i++) {
                            if (usrData.get(owner).get(i).equals(data)) {
                                usrData.get(owner).remove(i);
                                return data;
                            }
                        }
                        return null;
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public void copy(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        if (owner != null && passw != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw.hashCode())) {
                    if (!(data.equals(""))) {
                        if (usrData.get(owner).contains(data)) {
                                usrData.get(owner).add(data);
                            } else throw new DataNotOwnedException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public void share(String owner, String passw, String other, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        if (owner != null && passw != null && other != null && data != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.containsKey(other)) {
                    if (usrPwd.get(owner).equals(passw.hashCode())) {
                        if (!(data.equals(""))) {
                            if (usrData.get(owner).contains(data)) {
                                usrData.get(other).add(data);
                            } else throw new DataNotOwnedException();
                        } else throw new InvalidDataException();
                    } else throw new InvalidPasswordException();
                } else throw new UserNotFoundException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public Iterator<String> getIterator(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (owner != null && passw != null) {
            if (usrPwd.containsKey(owner)) {
                if (usrPwd.get(owner).equals(passw.hashCode())) {
                    return usrData.get(owner).iterator();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyUser(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (user != null && passw != null) {
            if (usrPwd.containsKey(user)) {
                if (usrPwd.get(user).equals(passw.hashCode())) {
                    return true;
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyOwnership(String user, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (user != null && passw != null && data != null) {
            if (usrPwd.containsKey(user)) {
                if (usrPwd.get(user).equals(passw.hashCode())) {
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
        return usrPwd.size();
    }

    @Override
    public int getDataN(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (verifyUser(user, passw)) {
            return usrData.get(user).size();
        } else return -1;
    }
    
}