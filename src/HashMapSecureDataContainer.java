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
        //verifico la validità dei parametri
        if (id != null && passw != null) {
            //verifico che il nome utente non sia già stato registrato
            if (!(usrPwd.containsKey(id))) {
                 /*
                in caso non sia usato vado a creare una nuova mappatura
                in entrambe le hashmap, con chiave il nuovo nome utente:
                    in usrPwd assegno come valore la password passw
                    in usrData preparo l'ArrayList per memorizzare i dati E
                */
                usrPwd.put(id, passw);
                usrData.put(id, new ArrayList<>());
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
        //verifico la validità dei paramtri
        if (owner != null && passw != null) {
            //verifco se il nome utente owner esiste
            if (usrPwd.containsKey(owner)) {
                //verifco se la password è esatta
                if (usrPwd.get(owner).equals(passw)) {
                    /*
                    ritorno la dimensione dell'ArrayList di owner, posso farlo
                    direttamente senza controllare la presenza della chiave
                    owner in usrData perché se la chiave esiste in usrPwd allora
                    esiste anche in usrData
                    */
                    return usrData.get(owner).size();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean put(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        //controllo la validità dei parametri
        if (owner != null && passw != null && data != null) {
            //verifco se il nome utente esiste
            if (usrPwd.containsKey(owner)) {
                //verifco se la password è esatta
                if (usrPwd.get(owner).equals(passw)) {
                    //verifico la validità del dato data
                    if (verifyData(data)) {
                        /*
                        se il dato è valido e verificato, lo inserisco
                        all'intero dell'ArrayList di owner
                        */
                        usrData.get(owner).add(data);
                        return true;
                    } else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }
    /*
    I(c) valida perché data è aggiunto solo quando se è valido e ciò non va ad
    invalidare nessuna delle condizioni della I(c)
    */

    @Override
    public E get(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        //preparo la shallow copy
        E datacopy = data;
        //verifico che la validità dei parametri
        if (owner != null && passw != null && data != null) {
            //verifico se l'utente esiste
            if (usrPwd.containsKey(owner)) {
                //verifico che la password sia esatta
                if (usrPwd.get(owner).equals(passw)) {
                    //verifico se il dato data è valido
                    if (verifyData(data)) {
                        /*
                        controllo se owner possiede almeno una copia di data
                        e, non appena ne trovo una, ritorno la shallow copy
                        */
                        if (usrData.get(owner).contains(data)) return datacopy;
                        //se non ne trovo nessuna, owner non possiede data
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
    public E remove(String owner, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException, DataNotOwnedException {
        //preparo la shallow copy
        E datacopy = data;
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            //verifico se l'utente esiste
            if (usrPwd.containsKey(owner)) {
                //verifico che la password corrisponda
                if (usrPwd.get(owner).equals(passw)) {
                    //verifico se data è un dato valido
                    if (verifyData(data)) {
                        /*
                        vado a cercare la prima occorrenza di data
                        nell'ArrayList di owner
                        */
                        for (int i = 0; i < usrData.get(owner).size(); i++) {
                            if (usrData.get(owner).get(i).equals(data)) {
                                //rimuovo la prima occorrenza che trovo e torno
                                usrData.get(owner).remove(i);
                                return datacopy;
                            }
                        }
                        //altrimenti il dato non è posseduto, non rimuovo nulla
                        throw new DataNotOwnedException();
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
        //verifico la validità dei parametri
        if (owner != null && passw != null && data != null) {
            //verifico che il nome utente esista
            if (usrPwd.containsKey(owner)) {
                //verifico che la password sia esatta
                if (usrPwd.get(owner).equals(passw)) {
                    //verifico che il dato sia valido
                    if (verifyData(data)) {
                        if (usrData.get(owner).contains(data)) {
                            /*
                            aggiungo la shallow copy alla collezione se essa
                            contiene già un esemplare di data
                            */
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
        //preparo la shallow copy
        E dataCopy = data;
        if (owner != null && passw != null && other != null && data != null) {
            //verifico che il nome utente owner esista
            if (usrPwd.containsKey(owner)) {
                //verifico che il nome utente other esista
                if (usrPwd.containsKey(other)) {
                    //verifico che la password per owner sia esatta
                    if (usrPwd.get(owner).equals(passw)) {
                        //verifico che il dato sia valido
                        if (verifyData(data)) {
                            //controllore se owner possiede data
                            if (usrData.get(owner).contains(data)) {
                                /*
                                in tal caso metto la shallow copy nella
                                collezione di other
                                */
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
        //verifico la validità dei parametri
        if (owner != null && passw != null){
            //verifico che l'utente esista
            if (usrPwd.containsKey(owner)) {
                //verifico che la password sia esatta
                if (usrPwd.get(owner).equals(passw)) {
                    //ritorno l'iteratore già pronto della classe ArrayList
                    return usrData.get(owner).iterator();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyUser(String user, String passw) throws UserNotFoundException, InvalidPasswordException {
        //verifico la validità dei parametri
        if (user != null && passw != null) {
            //verifico che l'utente esista
            if (usrPwd.containsKey(user)) {
                //verifico che la password sia esatta
                if (usrPwd.get(user).equals(passw)) {
                    //tutto ok
                    return true;
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public boolean verifyOwnership(String user, String passw, E data) throws UserNotFoundException, InvalidPasswordException, InvalidDataException {
        //verifico che i parametri siano validi
        if (user != null && passw != null && data != null) {
            //verifico che il nome utente esista
            if (usrPwd.containsKey(user)) {
                //verifico che la password sia esatta
                if (usrPwd.get(user).equals(passw)) {
                    //verifico che data sia un dato valido
                    if (verifyData(data)) {
                        //comunico se data appartiene alla collezione o meno
                        return usrData.get(user).contains(data);
                    }
                    else throw new InvalidDataException();
                } else throw new InvalidPasswordException();
            } else throw new UserNotFoundException();
        } else throw new NullPointerException();
    }

    @Override
    public int getUsersN() {
        //ritorno la dimensione della collezione di utenti
        return usrPwd.size();
    }
}