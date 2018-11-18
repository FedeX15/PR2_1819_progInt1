import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Federico Matteoni
 */
public class TotalTest {
    
    public TotalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testHashMap() {
        SecureDataContainer<String> container = new StringHashMap();
        addUsers(container);
        addData(container);
        shareData(container);
        errors(container);
    }
    
    @Test
    public void testMatrix() {
        SecureDataContainer<String> container = new StringMatrix();
        addUsers(container);
        addData(container);
        shareData(container);
        errors(container);
    }

    public void addUsers(SecureDataContainer<String> container) {
        try {
            container.createUser("fexed", "abc123");
            container.createUser("dexef", "321cba");
            container.createUser("arduino", "nano");
            container.createUser("raspberry", "3.14159265");
            container.createUser("temistocle", "nonsaprei");
            assertEquals(container.getUsersN(), 5);
            
        } catch (SecureDataContainer.InvalidUserException ex) {
            fail("Errore su utenti che vanno bene");
        }
    }
    
    public void addData(SecureDataContainer<String> container) {
        try {
            container.put("fexed", "abc123", "prova");
            container.put("fexed", "abc123", "termometro");
            container.put("fexed", "abc123", "lasagna");
            container.put("fexed", "abc123", "lenovo");
            container.put("fexed", "abc123", "australopiteco");
            container.put("fexed", "abc123", "stringa");
            assertEquals(container.getDataN("fexed", "abc123"), 6);
            container.put("dexef", "321cba", "gabbiano");
            container.put("dexef", "321cba", "stringapiulunga");
            container.put("dexef", "321cba", "stringa con spa z i");
            container.put("dexef", "321cba", "boh altra roba");
            assertEquals(container.getDataN("dexef", "321cba"), 4);
            container.put("arduino", "nano", "ojyUBJaZ");
            container.put("arduino", "nano", "2pBaSuUh");
            container.put("arduino", "nano", "J7NWHjrs");
            container.put("arduino", "nano", "y8UeXiXo");
            container.put("arduino", "nano", "RmVgUXoT");
            container.put("arduino", "nano", "cNV6mwvS");
            container.put("arduino", "nano", "f20wmLKM");
            container.put("arduino", "nano", "qkB42iYv");
            container.put("arduino", "nano", "Jcu9ZG7f");
            container.put("arduino", "nano", "4sZaty6t");
            assertEquals(container.getDataN("arduino", "nano"), 10);
            container.put("raspberry", "3.14159265", "torta");
            container.put("raspberry", "3.14159265", "scheda elettronica");
            container.put("raspberry", "3.14159265", "chip");
            assertEquals(container.getDataN("raspberry", "3.14159265"), 3);
            container.put("temistocle", "nonsaprei", "santino");
            assertEquals(container.getDataN("temistocle", "nonsaprei"), 1);
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException sul primo try");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException sul primo try");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidDataException sul primo try");
        }
        
    }
    
    public void shareData(SecureDataContainer<String> container) {
        try {
            container.share("fexed", "abc123", "dexef", "lasagna");
            assertEquals(container.getDataN("dexef", "321cba"), 5);
            container.remove("dexef", "321cba", "stringapiulunga");
            assertEquals(container.getDataN("dexef", "321cba"), 4);
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lasagna");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.share("fexed", "abc123", "dexef", "lenovo");
            container.remove("dexef", "321cba", "lasagna");
            assertEquals(container.getDataN("dexef", "321cba"), 25);
            container.share("dexef", "321cba", "fexed", "gabbiano");
            container.share("dexef", "321cba", "fexed", "gabbiano");
            container.share("dexef", "321cba", "fexed", "gabbiano");
            container.share("dexef", "321cba", "fexed", "gabbiano");
            container.share("dexef", "321cba", "fexed", "gabbiano");
            container.share("dexef", "321cba", "fexed", "gabbiano");
            container.share("dexef", "321cba", "fexed", "gabbiano");
            assertEquals(container.getDataN("fexed", "abc123"), 13);
            container.share("arduino", "nano", "fexed", "ojyUBJaZ");
            assertEquals(container.getDataN("fexed", "abc123"), 14);
            container.share("arduino", "nano", "dexef", "ojyUBJaZ");
            assertEquals(container.getDataN("dexef", "321cba"), 26);
            container.share("arduino", "nano", "raspberry", "ojyUBJaZ");
            assertEquals(container.getDataN("raspberry", "3.14159265"), 4);
            container.share("arduino", "nano", "temistocle", "ojyUBJaZ");
            assertEquals(container.getDataN("temistocle", "nonsaprei"), 2);
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidDataException");
        } catch(SecureDataContainer.DataNotOwnedException ex) {
            fail("InvalidDataException");  
        }
    }
    
    public void errors(SecureDataContainer<String> container) {
        try {
            container.createUser("fexed", "asdasd");
            container.createUser("dexef", "asdasd");
            fail("No errore su utenti che non vanno bene");
        } catch (SecureDataContainer.InvalidUserException ex) {
            assertEquals(container.getUsersN(), 5);
        }
        
        try {
            container.put("asd", "asd", "pippo");
            fail("Utente asd non ha dato UserNotFoundException");
        } catch (SecureDataContainer.UserNotFoundException ex) {
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException sul secondo try");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidPasswordException sul secondo try");
        }
        
        try {
            container.put("fexed", "asd", "pippo");
            fail("Utente fexed non ha dato InvalidPasswordException");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidPasswordException");
        }
        
        try {
            container.put("fexed", "abc123", "");
            fail("Utente fexed non ha dato InvalidDataException");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException");
        } catch (SecureDataContainer.InvalidDataException ex) {}
        
        
        try {
            container.get("fexed", "abc123", "Frank Sinatra");
            fail("Utente fexed non ha dato DataNotOwnedException o InvalidDataException");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException");
        } catch (SecureDataContainer.InvalidDataException ex) {
        } catch (SecureDataContainer.DataNotOwnedException ex) {}
    }
}