import java.util.Iterator;

/**
 * Data Storage per la memorizzazione e condivisione sicura di oggetti di tipo E
 * @author Federico Matteoni
 * @param <E> il tipo degli oggetti contenuti
 */
public interface SecureDataContainer<E> {
    
    //Crea l'identità di un nuovo utente della collezione
    public void createUser(String id, String passw);
    
    //Restituisce il numero degli elementi di un utente presenti nella
    //collezione
    public int getSize(String owner, String passw);
    
    //Inserisce il valore del dato nella collezione se vengono rispettati i
    //controlli di identità
    public boolean put(String owner, String passw, E data);
    
    //Ottiene una copia del valore del dato nella collezione se vengono
    //rispettati i controlli di identità
    public E get(String owner, String passw, E data);
    
    //Rimuove il dato dalla collezione se vengono rispettati i controlli di
    //identità
    public E remove(String owner, String passw, E data);
    
    //Crea una copia del dato nella collezione se vengono rispettati i controlli
    //di identità
    public void copy(String owner, String passw, E data);
    
    //Condivide il dato nella collezione con un altro utente se vengono
    //rispettati i controlli di identità
    public void share(String owner, String passw, String other, E data);
    
    //Restituisce un iteratore (senza remove) che genera tutti i dati
    //dell'utente in ordine arbitrario se vengono rispettati i controlli di
    //identità
    public Iterator<E> getIterator(String owner, String passw);
    
    //Verifica che l'utente indicato e la password indicata corrispondano
    public boolean verifyUser(String user, String passw);
    
    //Verifica che l'utente indicato abbia i permessi per accedere ai dati
    public boolean verifyOwnership(String user, String passw, E data);
    
    //...altre operazioni da definire a scelta
}
