import java.util.Iterator;

/**
 * @author Federico Matteoni
 */
public class MatrixStringSecureDataContanier implements SecureDataContainer<String> {
    private int[][] usrData;
    private String[] usrs;
    private String[] pwds;
    private String[] data;

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
    
    public void printMatrix() {
        for (int i = 0; i < usrData.length; i++) {
            System.out.print(usrs[i] + ":\t");
            for (int j = 0; j < usrData[i].length; j++) {
                System.out.print("\t" + usrData[i][j]);
            }
            System.out.println("");
        }
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
        int[][] newM = new int[m.length+1][data.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j];
            }
        }
        return newM;
    }
    
    public int[][] addCol(int[][] m) {
        int[][] newM = new int[m.length][data.length+1];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j];
            }
            newM[i][newM[i].length-1] = 0;
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
                for (int j = 0; j < usrData[usrData.length-1].length; j++) {
                    usrData[usrData.length-1][j] = 0;
                }
            } else throw new InvalidUserException();
        } else throw new NullPointerException();
    }

    @Override
    public int getSize(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (owner != null && passw != null) {
            if (checkExistingUser(owner)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(owner)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)){
                    int cnt = 0;
                    for (int i = 0; i < usrData[n].length; i++) {
                        cnt += usrData[n][i];
                    }
                    return cnt;
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean put(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(owner)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)){
                    if (!(data.equals(""))) {
                        int m = -1;
                        for (int i = 0; i < this.data.length; i++) {
                            if (this.data[i].equals(data)) {
                                m = i;
                                break;
                            }
                        }
                        if (m == -1) {
                            usrData = addCol(usrData);
                            this.data = increment(this.data);
                            this.data[this.data.length-1] = data;
                            usrData[n][this.data.length-1]++;
                            return true;
                        } else {
                            usrData[n][m]++;
                            return true;
                        }
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public String get(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(owner)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)) {
                    if (!(data.equals(""))) {
                        int m = -1;
                        for (int i = 0; i < this.data.length; i++) {
                            if (this.data[i].equals(data)) {
                                m = i;
                                break;
                            }
                        }
                        if (m != -1) {
                            if (usrData[n][m] > 0) {
                                return data;
                            } else throw new DataNotOwnedException();
                        } else throw new InvalidDataException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public String remove(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(owner)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)) {
                    int m = -1;
                    if (!(data.equals(""))) {
                        for (int i = 0; i < this.data.length; i++) {
                            if (this.data[i].equals(data)) {
                                m = i;
                                break;
                            }
                        }
                        if (m != -1) {
                            if (usrData[n][m] > 0) {
                                usrData[n][m]--;
                                return data;
                            } else throw new DataNotOwnedException();
                        } else throw new InvalidDataException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public void copy(String owner, String passw, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(owner)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)){
                    if (!(data.equals(""))) {
                        int m = -1;
                        for (int i = 0; i < this.data.length; i++) {
                            if (this.data[i].equals(data)) {
                                m = i;
                                break;
                            }
                        }
                        if (m != -1) {
                            usrData[n][m]++;
                        } else throw new InvalidDataException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public void share(String owner, String passw, String other, String data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(owner)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)){
                    if (checkExistingUser(other)) {
                        int o = -1;
                        for (int i = 0; i < usrs.length; i++) {
                            if (usrs[i].equals(other)) {
                                o = i;
                                break;
                            }
                        }
                        if (!(data.equals(""))) {
                            int m = -1;
                            for (int i = 0; i < this.data.length; i++) {
                                if (this.data[i].equals(data)) {
                                    m = i;
                                    break;
                                }
                            }
                            if (m != -1) {
                                if (usrData[n][m] > 0) {
                                    usrData[o][m]++;
                                } else throw new DataNotOwnedException();
                            } else throw new InvalidDataException();
                        } else throw new InvalidDataException();
                    } else throw new UserNotFoundException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public Iterator<String> getIterator(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(owner)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)){
                    final int[] datausr = usrData[n];
                    final String[] data = this.data;
                    return new Iterator<String>() {
                        int i = 0;
                        int c = -1;
                        
                        @Override
                        public boolean hasNext() {
                            return i < data.length;
                        }

                        @Override
                        public String next() {
                            if (c < 0) {
                                i++;
                                c = datausr[i];
                                c--;
                            }
                            c--;
                            return data[i];
                            
                        }
                    };
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
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
        return usrs.length;
    }

    @Override
    public int getDataN(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
       if (checkExistingUser(user)) {
            int n = -1;
            for (int i = 0; i < usrs.length; i++) {
                if (usrs[i].equals(user)) {
                    n = i;
                    break;
                }
            }
            if (pwds[n].equals(passw)){
                int sum = 0;
                for (int i = 0; i < data.length; i++) {
                    sum += usrData[n][i];
                }
                return sum;
            } else throw new InvalidPasswordException();
        } else throw new UserNotFoundException();
    }
}
