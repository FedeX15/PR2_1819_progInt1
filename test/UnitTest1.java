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
}
