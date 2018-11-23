import java.util.Iterator;

/**
 * Implementaizione di SecureDataContainer con una matrice
 * AUTHOR Federico Matteoni
 */
public abstract class MatrixSecureDataContanier<E> implements SecureDataContainer<E> {
    /*
    OVERVIEW: Gli oggetti del tipo MatrixSecureDataContainer<E> sono
    collezioni modificabili di oggetti di tipo E assegnati ad una coppia
    utente-password
    TYPICAL ELEMENT:
        usrs: <usr-1, usr-2, ..., usr-n>
        pwds: <pwd-1, pwd-2, ..., pwd-n>
        data: <data-1, data-2, ..., data-m>
        usrData: matrice di n x m
            n = |usrs|
            m = |data|
            usrData[i][j] = c >= 0
                c = 0 -> il dato j non appartiene alla collezione dell'utente i
                c > 0 -> il dato j appartiene alla collezione dell'utente i in
                         c copie
    
    a(c) = {(c.usrs[i], c.pwds[i], c.data[j]) | 0 <= i < c.usrs.length
           && 0 <= j < c.usrData[i].length && c.usrData[i][j] > 0}
    
    I(c) = (c.usrs != null)
           && (c.pwds != null)
           && (c.data != null)
           && (c.usrData != null)
           && (c.usrs.length = c.pwds.length = c.usrData.length)
           && (c.usrData[i].length = c.data.length 
               per ogni 0 <= i < c.usrs.length)
           && (c.usrData[i][j] >= 0 
               perogni 0 <= i < c.usrs.length && 0 <= j < c.data.length)
           && (i != j => c.usrs[i] != c.usrs[j]
               per ogni 0 <= i,j < c.usrs.length)
           && (i != j => c.data[i] != c.data[j]
               per ogni 0 <= i,j < c.data.length)
    */
    private int[][] usrData;
    private String[] usrs;
    private String[] pwds;
    protected E[] data;

    public MatrixSecureDataContanier() {
        usrData = new int[0][0];
        usrs = new String[0];
        pwds = new String[0];
        //data = new E[0]; //Non possibile con tipi generici, dipende dal tipo E
    }
    /*
    I(c) valida poiché usrData.length = usrs.length = pwds.length = 0
    */
      
    /*
    Metodo di utilità per stampare la matrice dei conteggi usrData
    */
    public void printMatrix() {
        for (int i = 0; i < usrData.length; i++) {
            for (int j = 0; j < data.length; j++) {
                System.out.print(" " + ((usrData[i][j] > 9) ? "+" : usrData[i][j]));
                /*
                stampo il numero di copie, '+' se il numero di copie è a due
                cifre per mantenere la formattazione
                */
            }
            System.out.println("\t" + usrs[i]);
            //allego il nome utente a fine riga
        }
    }
    
    /*
    Funzione di utilità per controllare l'esistenza di un nome utente
    */
    public boolean checkExistingUser(String usr) {
        for (String s : usrs) {
            if (s.equals(usr)) return true;
        }
        return false;
    }
    /*
    REQUIRES: usr != null
    RETURNS: true se usr è presente in usrs, false altrimenti
    */
    
    /*
    Funzione di utilità per incrementare di una posizione il vettore di dati E
    */
    public abstract E[] increment(E[] v);
    /*
    REQUIRES: v != null
    RETURNS: una copia del vettore v con una posizione in più con valore null
    */
    
    //Funzione di utilità per incrementare di una posizione un vettore di String
    public String[] increment(String[] v) {
        String[] newV = new String[v.length + 1];
        for (int i = 0; i < v.length; i++) {
            newV[i] = v[i];
        }
        newV[newV.length-1] = null;
        return newV;
    }
    /*
    REQUIRES: v != null
    RETURNS: una copia del vettore v con una poszione in più con valore null
    */
    
    //Funzione di utilità che aggiunge una riga di 0 ad una matrice di int
    public int[][] addRow(int[][] m) {
        int[][] newM = new int[m.length+1][data.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                newM[i][j] = m[i][j];
            }
            for (int j = 0; j < newM[newM.length-1].length; j++) {
                newM[newM.length-1][j] = 0;
            }
        }
        return newM;
    }
    /*
    REQUIRES: m != null
    RETURNS: una matrice newM | newM.length = m.length+1, con l'ultima riga di 0
    */
    
    //Funzione di utilità che aggiunge una colonna di 0 ad una matrice di int
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
    /*
    REQUIRES: m != null
    RETURNS: una matrice newM | 
             newM[i].length = m[i].length+1 perogni 0 <= i < m.length
             con l'ultima colonna di 0
    */
        
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
    /*
    I(c) valida perché incrementa usrs, pwds e le righe di usrData insieme, e fa
    ciò solamente se id non è già presente in usrs. Inoltre all'inserimento di
    una nuova riga, tale riga è inizializzata a tutti 0, mantendo
    usrData[i][j] >= 0 per ogni 0 <= i < usrs.length && 0 <= j < data.length
    */

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
    public boolean put(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
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
                    if (verifyData(data)) {
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
    /*
    I(c) valida poiché in caso di validità dei parametri, sia le colonne di
    usrData che la lunghezza di data viene incrementata di 1 in caso di dato
    nuovo.
    La posizione relativa al dato, m se è già presente o l'ultima posizione di 
    data se il dato è nuovo, viene poi incrementata di 1.
    */

    @Override
    public E get(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        E newdata = data;
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
                    if (verifyData(data)) {
                        int m = -1;
                        for (int i = 0; i < this.data.length; i++) {
                            if (this.data[i].equals(data)) {
                                m = i;
                                break;
                            }
                        }
                        if (m != -1) {
                            if (usrData[n][m] > 0) {
                                return newdata;
                            } else throw new DataNotOwnedException();
                        } else throw new DataNotOwnedException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public E remove(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
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
                    if (verifyData(data)) {
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
    /*
    I(c) è valida poiché se il dato è presente nella collezione e i parametri
    sono validi, la posizione relativa al dato nella riga dell'utente è
    decrementata di 1 solo se la posizione ha valore > 0, mantenendo la
    validità dell'invariante
    */

    @Override
    public void copy(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
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
                    if (verifyData(data)) {
                        int m = -1;
                        for (int i = 0; i < this.data.length; i++) {
                            if (this.data[i].equals(data)) {
                                m = i;
                                break;
                            }
                        }
                        if (m != -1) {
                            if (usrData[n][m] > 0) {
                                usrData[n][m]++;
                            } else throw new DataNotOwnedException();
                        } else throw new InvalidDataException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida poiché se i parametri sono validi incrementa semplicemente il
    contatore relativo al dato nella collezione di owner solo se aveva almeno
    una copia di tale dato
    */

    @Override
    public void share(String owner, String passw, String other, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
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
                        if (verifyData(data)) {
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
    /*
    I(c) valida poiché se i parametri sono validi va a incrementare il contatore
    relativo al dato nella collezione di other solo se owner ha almeno una
    copia del dato.
    */

    @Override
    public Iterator<E> getIterator(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
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
                    final int[] datausr = usrData[n]; //preparo vettore delle quantità
                    final E[] data = this.data; //preparo il vettore dei dati
                    return new Iterator<E>() {
                        int i = -1; //base -1, al primo hasNext va a 0
                        int c = 0; //memorizza il conteggio del singolo dato
                        
                        @Override
                        public boolean hasNext() {
                            if (c > 0) return c <= datausr[i]; //finché di questo dato l'utente ha copie devo ritornare questo dato
                            else {
                                i++; //vado a vedere dalla posizione successiva il primo conteggio > 0
                                do {
                                    if (i < data.length) {
                                        c = datausr[i];
                                        if (c > 0) return true; //se il conteggio è > 0 allora ok, ci sono dati da tornare e sono in posizione i
                                        else i++; //altrimenti vado a vedere il dato successivo
                                    }
                                } while (i < data.length); //lo faccio per tutti i dati
                                return false; //se arrivo qua non ci sono più dati o conteggi >0 nella collezione dell'utente, non ho dati successivi
                            }
                        }

                        @Override
                        public E next() {
                            c--; //decremento il conteggio di questo dato che è sicuramente > 0
                            return data[i]; //ritorno il dato
                            
                        }
                    };
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyUser(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        if (user != null && passw != null && data != null) {
            if (checkExistingUser(user)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(user)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)){
                    return true;
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyOwnership(String user, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        if (user != null && passw != null && data != null) {
            if (checkExistingUser(user)) {
                int n = -1;
                for (int i = 0; i < usrs.length; i++) {
                    if (usrs[i].equals(user)) {
                        n = i;
                        break;
                    }
                }
                if (pwds[n].equals(passw)){
                    if (verifyData(data)) {
                        int m = -1;
                        for (int i = 0; i < this.data.length; i++) {
                            if (this.data[i].equals(data)) {
                                m = i;
                                break;
                            }
                        }
                        if (m != -1) {
                            return usrData[n][m] > 0;
                        } else throw new InvalidDataException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
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
