import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Federico Matteoni
 */
public class MatrixStringSecureDataContanier implements SecureDataContainer<String> {
    
    int[][] usrData;
    String[] usrs;
    String[] pwds;
    String[] data;

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
    
    public MatrixStringSecureDataContanier() {
        usrData = new int[0][0];
        usrs = new String[0];
        pwds = new String[0];
        data = new String[0];
    }
    
    public boolean checkExistingUser(String usr) {
        for (String s : usrs) {
            if (s.equals(usr)) return true;
        }
        return false;
    }
    
    public String[] increment(String[] v) {
        String[] newV = new String[v.length + 1];
        for (int i = 0; i < v.length; i++) {
            newV[i] = v[i];
        }
        return newV;
    }
    
    public int[][] addRow(int[][] m) {
        int[][] newM = new int[m.length+1][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j];
            }
        }
        return newM;
    }
    
    public int[][] addCol(int[][] m) {
        int[][] newM = new int[m.length][m[0].length+1];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j];
            }
        }
        return newM;
    }
        
    @Override
    public void createUser(String id, String passw) throws InvalidUserException {
        if (id != null && passw != null) {
            if (!(checkExistingUser(id))) {
                usrs = increment(usrs);
                pwds = increment(pwds);
                usrs[usrs.length - 1] = id;
                pwds[pwds.length - 1] = passw;
                usrData = addRow(usrData);
            } else throw new InvalidUserException();
        } else throw new NullPointerException();
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
