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
    
    @Test
    public void testAlfa() {
        addUsers();
        addData();
    }

    public void addUsers() {
        try {
            dataContainer.createUser("fexed", "abc123");
            dataContainer.createUser("dexef", "321cba");
        } catch (SecureDataContainer.InvalidUserException ex) {
            fail("Errore su utenti che vanno bene");
        }
        
        try {
            dataContainer.createUser("fexed", "asdasd");
            fail("No errore su utente che non va bene");
        } catch (SecureDataContainer.InvalidUserException ex) {}
    }
    
    public void addData() {
        try {
            dataContainer.put("fexed", "abc123", "prova");
            dataContainer.put("fexed", "abc123", "termometro");
            dataContainer.put("dexef", "321cba", "gabbiano");
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
        
        
    }
}
