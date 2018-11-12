import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Federico Matteoni
 */
public class UnitTest1 {
    SecureDataContainer<String> dataContainer;
    
    public UnitTest1() {
        this.dataContainer = new StringSecureDataContainer();
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
    
    public void testAlfa() {
        addUsers();
        addData();
    }

    @Test
    public void addUsers() {
        try {
            dataContainer.createUser("fexed", "abc123");
            dataContainer.createUser("dexef", "321cba");
            dataContainer.createUser("arduino", "nano");
            dataContainer.createUser("raspberry", "3.14159265");
            dataContainer.createUser("temistocle", "nonsaprei");
            assertEquals(dataContainer.getUsersN(), 5);
            
        } catch (SecureDataContainer.InvalidUserException ex) {
            fail("Errore su utenti che vanno bene");
        }
        
        try {
            dataContainer.createUser("fexed", "asdasd");
            fail("No errore su utente che non va bene");
        } catch (SecureDataContainer.InvalidUserException ex) {
            assertEquals(dataContainer.getUsersN(), 5);}
    }
    
    @Test
    public void addData() {
        addUsers();
        try {
            dataContainer.put("fexed", "abc123", "prova");
            dataContainer.put("fexed", "abc123", "termometro");
            dataContainer.put("fexed", "abc123", "lasagna");
            dataContainer.put("fexed", "abc123", "lenovo");
            dataContainer.put("fexed", "abc123", "australopiteco");
            dataContainer.put("fexed", "abc123", "stringa");
            assertEquals(dataContainer.getDataN("fexed", "abc123"), 6);
            dataContainer.put("dexef", "321cba", "gabbiano");
            dataContainer.put("dexef", "321cba", "stringapiulunga");
            dataContainer.put("dexef", "321cba", "stringa con spa z i");
            dataContainer.put("dexef", "321cba", "boh altra roba");
            assertEquals(dataContainer.getDataN("dexef", "321cba"), 4);
            assertEquals(dataContainer.getDataN("arduino", "nano"), 0);
            dataContainer.put("raspberry", "3.14159265", "torta");
            dataContainer.put("raspberry", "3.14159265", "scheda elettronica");
            dataContainer.put("raspberry", "3.14159265", "chip");
            assertEquals(dataContainer.getDataN("raspberry", "3.14159265"), 3);
            dataContainer.put("temistocle", "nonsaprei", "santino");
            assertEquals(dataContainer.getDataN("temistocle", "nonsaprei"), 1);
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException sul primo try");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException sul primo try");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidDataException sul primo try");
        }
        
        try {
            dataContainer.put("asd", "asd", "pippo");
            fail("Utente asd non ha dato UserNotFoundException sul secondo try");
        } catch (SecureDataContainer.UserNotFoundException ex) {
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException sul secondo try");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidPasswordException sul secondo try");
        }
        
        
        try {
            dataContainer.put("fexed", "asd", "pippo");
            fail("Utente fexed non ha dato InvalidPasswordException sul terzo try");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException sul terzo try");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidPasswordException sul terzo try");
        }
        
        
        
        try {
            dataContainer.put("fexed", "abc123", "");
            fail("Utente fexed non ha dato InvalidDataException sul quarto try");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException sul quarto try");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException sul quarto try");
        } catch (SecureDataContainer.InvalidDataException ex) {}
    }
}
