import java.util.Iterator;

/**
 * Data Storage per la memorizzazione e condivisione sicura di oggetti di tipo E
 * @author Federico Matteoni
 * @param <E> il tipo degli oggetti contenuti
 */
public interface SecureDataContainer<E> {
    //OVERVIEW: Gli oggetti di tipo SecureDataContainer sono collezioni
    //modificabili di oggetti di tipo E
    //TYPICAL ELEMENT: <usr-i, pwd-i, <E-elem-0, ..., E-elem-n>> con
    //0<=i<=getUsersN() e n=getDataN(usr-i, pwd-i) e i!=j => (usr-i)!=(usr-j)
    
    //Crea l'identità di un nuovo utente della collezione
    public void createUser(String id, String passw) throws InvalidUserException;
    //REQUIRES: id e passw non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //InvalidUserException se id è già un utente registrato
    //EFFECTS: inserisce il nuovo utente nella lista degli utenti e la
    //corrispondente password assegnata
    //MODIFIES: la lista degli utenti
    
    //Restituisce il numero degli elementi di un utente presenti nella
    //collezione
    public int getSize(String owner, String passw) throws UserNotFoundException, InvalidPasswordException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata
    //RETURNS: il numero di elementi della collezione di owner
    
    //Inserisce il valore del dato nella collezione se vengono rispettati i
    //controlli di identità
    public boolean put(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente e data che sia un dato valido non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //InvalidDataException (unchecked, non Java) se il dato è invalido o è NULL
    //EFFECTS: inserisce data all'interno della collezione di owner
    //MODIFIES: la collezione di owner
    //RETURNS: true se data è stato aggiunto correttamente, false altrimenti
    
    //Ottiene una copia del valore del dato nella collezione se vengono
    //rispettati i controlli di identità
    public E get(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente e data che sia un dato valido non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //InvalidDataException (unchecked, non Java) se il dato è invalido o è NULL
    //DataNotOwnedException (unchecked, non Java) se il dato non appartiene alla
    //collezione di owner
    //RETURNS: NULL se il dato non è presente o una copia di data altrimenti
    
    //Rimuove il dato dalla collezione se vengono rispettati i controlli di
    //identità
    public E remove(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //InvalidDataException (unchecked, non Java) se il dato è invalido o è NULL
    //EFFECTS: rimuove la prima occorrenza di data dalla collezione di owner
    //MODIFIES: la collezione di owner
    //RETURNS: data se il dato è stato rimosso correttamente, NULL altrimenti
    
    //Crea una copia del dato nella collezione se vengono rispettati i controlli
    //di identità
    public void copy(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente e data valido e non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //InvalidDataException (unchecked, non Java) se il dato è invalido o è NULL
    //EFFECTS: viene effettuata una copia di data all'interno della collezione
    //di owner e inserita in fondo alla collezione
    //MODIFIES: la collezione di owner
    
    //Condivide il dato nella collezione con un altro utente se vengono
    //rispettati i controlli di identità
    public void share(String owner, String passw, String other, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente e other che sia un altro utente esistente e data valido
    //e non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //InvalidDataException (unchecked, non Java) se il dato è invalido o è NULL
    //EFFECTS: viene effettuata una copia di data all'interno della collezione
    //di other e messa in fondo alla collezione
    //MODIFIES: la collezione di other
    
    //Restituisce un iteratore (senza remove) che genera tutti i dati
    //dell'utente in ordine arbitrario se vengono rispettati i controlli di
    //identità
    public Iterator<E> getIterator(String owner, String passw) throws UserNotFoundException, InvalidPasswordException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata
    //RETURNS: un oggetto Iterator<E> sulla collezione di owner
    
    //Verifica che l'utente indicato e la password indicata corrispondano
    public boolean verifyUser(String user, String passw) throws UserNotFoundException, InvalidPasswordException;
    //REQUIRES: user e passw non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //RETURNS: true se l'utente user esiste con password passw, false altrimenti
    
    //Verifica che l'utente indicato abbia i permessi per accedere ai dati
    public boolean verifyOwnership(String user, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException;
    //REQUIRES: owner e passw che siano utente e password corrispondente di un
    //utente esistente e data non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //InvalidDataException (unchecked, non Java) se il dato è invalido o è NULL
    //RETURNS: true se l'oggetto data appartiene all'utente user, false
    //altrimenti
    
    //Verifica che data sia un dato valido
    public boolean verifyData(E data);
    //REQUIRES: data non NULL
    //THROWS: NullPointerException se data è null
    //RETURNS: true se data è valido, falso se data non è valido
    
    //Ritorna il numero di utenti registrati
    public int getUsersN();
    //RETURNS: il numero di utenti registrati
    
    //Ritorna il numero di dati dell'utente indicato
    public int getDataN(String user, String passw) throws UserNotFoundException, InvalidPasswordException;
    //REQUIRES: user e passw utente valido non NULL
    //THROWS: NullPointerException (unchecked, Java) se id o passw sono NULL,
    //UserNotFoundException (unchecked, non Java) se l'utente non esiste,
    //InvalidPasswordException (unchecked, non Java) se la password è sbagliata,
    //RETURNS: il numero di dati dell'utente, -1 se l'utente non esiste

    //...altre operazioni da definire a scelta
    
     
    class DataNotOwnedException extends Exception {
        public DataNotOwnedException() {
            super();
        }
    }
    
    class InvalidUserException extends Exception {
        public InvalidUserException() {
            super();
        }
    }
    
    class UserNotFoundException extends Exception {
        public UserNotFoundException() {
            super();
        }
    }
    
    class InvalidPasswordException extends Exception {
        public InvalidPasswordException() {
            super();
        }
    }
    
    class InvalidDataException extends Exception {
        public InvalidDataException() {
            super();
        }
    }
}