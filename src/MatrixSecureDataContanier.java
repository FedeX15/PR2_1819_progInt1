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
        //data = new E[0]; //Non possibile con tipi generici, dipende da E
    }
    /*
    I(c) valida poiché usrData.length = usrs.length = pwds.length = 0, quindi
    non esistono indici i che invalidano l'invariante
    */
      
    /*
    Metodo di utilità per stampare la matrice dei conteggi usrData
    */
    public void printMatrix() {
        for (int i = 0; i < usrData.length; i++) {
            for (int j = 0; j < data.length; j++) {
                System.out.print(" " + ((usrData[i][j] > 9) ? "+" : usrData[i][j]));
                /*
                stampo il numero di copie, o '+' se il numero di copie è a due
                cifre (per mantenere la formattazione), il tutto preceduto da
                uno spazio
                */
            }
            System.out.println("\t" + usrs[i]);
            //allego il nome utente a fine riga
        }
    }
    
    //Funzione di utilità per controllare l'esistenza di un nome utente
    public boolean checkExistingUser(String usr) {
        for (String s : usrs) {
            //ritorno true non appena trovo un elemento in usrs che corrisponde
            if (s.equals(usr)) return true;
        }
        return false; //se non l'ho trovato torno false, utente non esistente
    }
    /*
    REQUIRES: usr != null
    RETURNS: true se usr è presente in usrs, false altrimenti
    */
    
    //Funzione di utilità per incrementare di una posizione un array di E
    public abstract E[] increment(E[] v);
    /*
    REQUIRES: v != null
    RETURNS: una copia del array v con una posizione in più con valore null
    */
    
    //Funzione di utilità per incrementare di una posizione un array di String
    public String[] increment(String[] v) {
         //preparo il nuovo array con una posizione in più
        String[] newV = new String[v.length + 1];
        for (int i = 0; i < v.length; i++) {
            newV[i] = v[i]; //copio i valori del vecchio array nel nuovo array
        }
        newV[newV.length-1] = null; //pulisco l'ultima posizione del nuovo array
        return newV;
    }
    /*
    REQUIRES: v != null
    RETURNS: una copia del array v con una poszione in più con valore null
    */
    
    //Funzione di utilità che aggiunge una riga di 0 ad una matrice di int
    public int[][] addRow(int[][] m) {
        //preparo la nuova matrice con una riga in più
        int[][] newM = new int[m.length+1][data.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length-1; j++) {
                //copio i vecchi valori
                newM[i][j] = m[i][j];
            }
        }
        for (int j = 0; j < newM[newM.length-1].length; j++) {
            //tutti 0 nell'ultima riga della nuova matrice
            newM[newM.length-1][j] = 0;
        }
        return newM;
    }
    /*
    REQUIRES: m != null
    RETURNS: una matrice newM | newM.length = m.length+1, con l'ultima riga di 0
    */
    
    //Funzione di utilità che aggiunge una colonna di 0 ad una matrice di int
    public int[][] addCol(int[][] m) {
        //preparo la nuova matrice con una colonna in più
        int[][] newM = new int[m.length][data.length+1];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                //copio i vecchi valori
                newM[i][j] = m[i][j];
            }
            //l'ultima posizione, quella nuova, ha valore 0
            newM[i][data.length-1] = 0;
        }
        return newM;
    }
    /*
    REQUIRES: m != null
    RETURNS: una matrice newM | 
             newM[i].length = m[i].length+1 perogni 0 <= i < m.length
             con l'ultima colonna di 0
    */
    
    //Funzione di utilità che ritorna l'indice dell'utente user
    public int getUserIndex(String user) {
        int n = -1;
        for (int i = 0; i < usrs.length; i++) {
            if (usrs[i].equals(user)) {
                n = i; //trovo l'indice corrispondente
                break;
            }
        }
        return n;
    }
    /*
    REQUIRES: user != null
    RETURNS: l'indice di user all'interno dell'array this.usrs
    */
    
    //Funzione di utilità che ritorna l'indice del dato data di tipo generico E
    public int getDataIndex(E data) {
        int m = -1;
        for (int i = 0; i < this.data.length; i++) {
            if (this.data[i].equals(data)) {
                m = i; //trovo l'indice corrispondente
                break;
            }
        }
        return m;
    }
    /*
    REQUIRES: data != null
    RETURNS: l'indice di data all'interno dell'array this.data
    */
    
    @Override
    public void createUser(String id, String passw) throws InvalidUserException {
        if (id != null && passw != null) { //controllo validità dei parametri
            if (!(checkExistingUser(id))) { //se l'utente non esiste già
                //incremento gli array usrs e pwds
                usrs = increment(usrs);
                pwds = increment(pwds);
                //inserisco il nuovo utente in fondo agli array
                usrs[usrs.length - 1] = id;
                pwds[pwds.length - 1] = passw;
                //aggiungo la riga del nuovo utente nella matrice usrData
                usrData = addRow(usrData);
            } else throw new InvalidUserException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché incrementa usrs, pwds e le righe di usrData
    contemporaneamente, e lo fa solamente se id non è già presente in usrs.
    All'inserimento di una nuova riga, tale riga è inizializzata a tutti 0,
    mantendo usrData[i][j] >= 0 perogni 0 <= i < usrs.length
    && 0 <= j < data.length
    */

    @Override
    public int getSize(String owner, String passw) throws UserNotFoundException, InvalidPasswordException {
        //controllo la validità dei parametri
        if (owner != null && passw != null) {
            if (checkExistingUser(owner)) { //se l'utente esiste
                int n = getUserIndex(owner); //trovo l'indice corrispondente
                if (pwds[n].equals(passw)){ //se la password è esatta
                    int cnt = 0;
                    for (int i = 0; i < usrData[n].length; i++) {
                        /*
                        sommo i conteggi presenti nella riga della matrice
                        corrispondente ad owner
                        */
                        cnt += usrData[n][i];
                    }
                    /*
                    ritorno il conteggio, che sarebbe il numore totale di dati
                    presenti nella collezione di owner
                    */
                    return cnt;
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    getSize è un osservatore e non va a modificare le strutture dati, pertanto
    I(c) è valida
    */
    

    @Override
    public boolean put(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) { //se l'utente esiste
                int n = getUserIndex(owner); //trovo l'indice corrisppondente
                if (pwds[n].equals(passw)){ //se la password è esatta
                    if (verifyData(data)) { //e se il dato è valido e verificato
                        int m = getDataIndex(data); //trovo l'indice
                        if (m == -1) { //in questo caso è un dato nuovo
                            //incremento l'array dei dati
                            this.data = increment(this.data);
                            //creo una nuova colonna nella matrice
                            usrData = addCol(usrData);
                            //inserisco il dato nuovo nell'array dei dati
                            this.data[this.data.length-1] = data;
                            /*
                            incremento il conteggio del dato nella riga di
                            owner
                            */
                            usrData[n][this.data.length-1]++;
                            return true;
                        } else {
                            /*
                            il dato esiste, quindi incremento la colonna
                            corrispondente nella riga di owner
                            */
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
        //preparo la shallow copy del dato
        E newdata = data;
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) { //se l'utente esiste
                int n = getUserIndex(owner);
                if (pwds[n].equals(passw)) { //se la password è esatta
                    if (verifyData(data)) { //e se il dato è valido e verificato
                        int m = getDataIndex(data);
                        if (m != -1) { //se l'indice è stato trovato
                            if (usrData[n][m] > 0) { //e l'utente lo possiede
                                return newdata; //ritorno la copia
                            } else throw new DataNotOwnedException();
                        } else throw new DataNotOwnedException();
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    //get è un osservatore, pertanto non va a modificare le strutture dati

    @Override
    public E remove(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) { //se l'utente esiste
                int n = getUserIndex(owner);
                if (pwds[n].equals(passw)) { //se la password è esatta
                    if (verifyData(data)) { //e se il dato è valido e verificato
                        int m = getDataIndex(data);
                        if (m != -1) { //se l'indice è stato trovato
                            if (usrData[n][m] > 0) { //e l'utente lo possiede
                                usrData[n][m]--; //ne elimino una copia
                                return data;
                            } else throw new DataNotOwnedException();
                        } else throw new DataNotOwnedException();
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
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) { //se l'utente esiste
                int n = getUserIndex(owner);
                if (pwds[n].equals(passw)){ //se la password è esatta
                    if (verifyData(data)) { //e se il dato è valido e verificato
                        int m = getDataIndex(data);
                        if (m != -1) {
                            //verifico che owner possieda il dato
                            if (usrData[n][m] > 0) {
                                /*
                                e in caso incremento il conteggio nella riga
                                di owner
                                */
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
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) { //se l'utente esiste
                int n = getUserIndex(owner);
                if (pwds[n].equals(passw)){ //se la password è corretta
                    if (checkExistingUser(other)) { //verifico che other esista
                        int o = getUserIndex(other);
                        if (verifyData(data)) { //se data è valido e verificato
                            int m = getDataIndex(data);
                            if (m != -1) {
                                //se owner possiede data in almeno una copia
                                if (usrData[n][m] > 0) {
                                    /*
                                    incremento il contatore di data nella riga
                                    di other
                                    */
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
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            if (checkExistingUser(owner)) { //se l'utente esiste
                int n = getUserIndex(owner);
                if (pwds[n].equals(passw)){ //se la password è esatta
                     //preparo vettore delle quantità
                    final int[] datausr = usrData[n];
                    //preparo il vettore dei dati
                    final E[] data = this.data;
                    return new Iterator<E>() {
                        int i = -1; //base -1, al primo hasNext va a 0
                        int c = 0; //memorizza il conteggio del singolo dato
                        
                        @Override
                        public boolean hasNext() {
                             /*
                            finché di questo dato l'utente ha copie devo
                            ritornare questo dato
                            */
                            if (c > 0) return c <= datausr[i];
                            else {
                                /*
                                dalla posizione successiva vado a cercare il
                                primo conteggio > 0
                                */
                                i++;
                                do {
                                    if (i < data.length) {
                                        c = datausr[i];
                                        /*
                                        se il conteggio è > 0 allora ok, ci sono
                                        dati da tornare e sono in posizione i
                                        */
                                        if (c > 0) return true;
                                        //altrimenti vado al dato successivo
                                        else i++;
                                    }
                                } while (i < data.length);
                                /*
                                lo faccio per tutti i dati, se arrivo qua non ci
                                sono più dati o non ci sono più conteggi > 0
                                nella collezione dell'utente,
                                quindi non ho dati successivi
                                */
                                return false;
                            }
                        }

                        @Override
                        public E next() {
                            /*
                            decremento il conteggio di questo dato che è
                            sicuramente > 0
                            */
                            c--;
                            return data[i]; //ritorno il dato
                            
                        }
                    };
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyUser(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        //verifico la validità dei parametri
        if (user != null && passw != null && data != null) {
            if (checkExistingUser(user)) { //se l'utente esiste
                int n = getUserIndex(user);
                if (pwds[n].equals(passw)){ //se la password è esatta
                    return true; //tutto ok, utente verificato
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyOwnership(String user, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        //verifico la validità dei dati
        if (user != null && passw != null && data != null) {
            if (verifyUser(user, passw)) { //se l'utente esiste
                int n = getUserIndex(user);
                if (verifyData(data)) { //se il dato è verificato e valido
                    int m = getDataIndex(data);
                    if (m != -1) { //se l'ho trovato
                        return usrData[n][m] > 0; //true se ho almeno una copia
                    } else throw new InvalidDataException();
                } else throw new InvalidDataException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public int getUsersN() {
        return usrs.length;
    }
}
