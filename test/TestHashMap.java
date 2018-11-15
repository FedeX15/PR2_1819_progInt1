import java.util.Iterator;
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
public class TestHashMap {
    SecureDataContainer<String> dataContainer;
    
    public TestHashMap() {
        this.dataContainer = new StringHashMap();
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
    
    public void printData() {
        System.out.println("====================");
        try {
            System.out.print("fexed: ");
            for (Iterator i = dataContainer.getIterator("fexed", "abc123"); i.hasNext(); ) {
                System.out.print(i.next()+"|");
            }
            System.out.println("");
            System.out.print("dexef: ");
            for (Iterator i = dataContainer.getIterator("dexef", "321cba"); i.hasNext(); ) {
                System.out.print(i.next()+"|");
            }
            System.out.println("");
            System.out.print("arduino: ");
            for (Iterator i = dataContainer.getIterator("arduino", "nano"); i.hasNext(); ) {
                System.out.print(i.next()+"|");
            }
            System.out.println("");
            System.out.print("raspberry: ");
            for (Iterator i = dataContainer.getIterator("raspberry", "3.14159265"); i.hasNext(); ) {
                System.out.print(i.next()+"|");
            }
            System.out.println("");
            System.out.print("temistocle: ");
            for (Iterator i = dataContainer.getIterator("temistocle", "nonsaprei"); i.hasNext(); ) {
                System.out.print(i.next()+"|");
            }
            System.out.println("");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException");
        }
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
        printData();
    }
    
    @Test
    public void shareData() {
        addData();
        try {
            dataContainer.share("fexed", "abc123", "dexef", "lasagna");
            assertEquals(dataContainer.getDataN("dexef", "321cba"), 5);
            dataContainer.remove("dexef", "321cba", "stringapiulunga");
            assertEquals(dataContainer.getDataN("dexef", "321cba"), 4);
            dataContainer.share("fexed", "abc123", "dexef", "lasagna");
            dataContainer.share("fexed", "abc123", "dexef", "lasagna");
            dataContainer.share("fexed", "abc123", "dexef", "lenovo");
            dataContainer.remove("dexef", "321cba", "lasagna");
            assertEquals(dataContainer.getDataN("dexef", "321cba"), 6);
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidDataException");
        } catch(SecureDataContainer.DataNotOwnedException ex) {
            fail("InvalidDataException");  
        }
        printData();
    }
}
