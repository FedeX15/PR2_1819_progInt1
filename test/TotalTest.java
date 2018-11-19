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
    
    @Test
    public void bigTestHashMap() {
        SecureDataContainer<String> container = new StringHashMap();
        addUsers(container);
        addBigData(container);
    }
    
    @Test
    public void bigTestMatrix() {
        SecureDataContainer<String> container = new StringMatrix();
        addUsers(container);
        addBigData(container);
        ((StringMatrix) container).printMatrix();
    }

    public void addUsers(SecureDataContainer<String> container) {
        try {
            container.createUser("fexed", "abc123");
            container.createUser("dexef", "321cba");
            container.createUser("arduino", "nano");
            container.createUser("raspberry", "3.14159265");
            container.createUser("temistocle", "nonsaprei");
            container.createUser("a", "a");
            container.createUser("b", "b");
            container.createUser("c", "c");
            container.createUser("d", "d");
            container.createUser("e", "e");
            assertEquals(container.getUsersN(), 10);
            
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
            container.remove("dexef", "321cba", "lasagna");
            container.remove("dexef", "321cba", "lasagna");
            container.remove("dexef", "321cba", "lenovo");
            assertEquals(container.getDataN("dexef", "321cba"), 22);
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
            assertEquals(container.getDataN("dexef", "321cba"), 23);
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
            assertEquals(container.getUsersN(), 10);
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
            fail("Utente fexed non ha dato DataNotOwnedException");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("DataNotOwnedException");
        } catch (SecureDataContainer.DataNotOwnedException ex) {
        }
        
        try {
            container.copy("fexed", "abc123", "stringa con spa z i");
            fail("Utente fexed non ha dato DataNotOwnedException");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("DataNotOwnedException");
        } catch (SecureDataContainer.DataNotOwnedException ex) {
        }
        
    }
    
    public void addBigData(SecureDataContainer<String> container) {
        try {
            container.put("fexed", "abc123", "LGUrXMltBy");
            container.put("b", "b", "T3zSsRa5BX");
            container.put("raspberry", "3.14159265", "8NgGIB94KF");
            container.put("arduino", "nano", "z5zmlbi9O9");
            container.put("c", "c", "4LRLO3018o");
            container.put("temistocle", "nonsaprei", "HOfVeuvHeh");
            container.put("fexed", "abc123", "bQe3JufKKE");
            container.put("fexed", "abc123", "9JYSOCeUHP");
            container.put("temistocle", "nonsaprei", "pBTxxkf1CA");
            container.put("dexef", "321cba", "NvpluEIUvT");
            container.put("dexef", "321cba", "5Ca8JQdw0w");
            container.put("a", "a", "i0dJsJkgAp");
            container.put("fexed", "abc123", "faftnEW8Zd");
            container.put("b", "b", "73PJc6ayXo");
            container.put("temistocle", "nonsaprei", "hlAldUvn9C");
            container.put("fexed", "abc123", "KsO5TOcZ91");
            container.put("c", "c", "2KiXPYBjqs");
            container.put("fexed", "abc123", "QDswIL890q");
            container.put("d", "d", "wBK8ptXPIJ");
            container.put("fexed", "abc123", "oofFl8ArKR");
            container.put("dexef", "321cba", "mNNLtfubkT");
            container.put("a", "a", "400Q5uCL4W");
            container.put("fexed", "abc123", "Iena20doo1");
            container.put("a", "a", "FIH34IY3lM");
            container.put("raspberry", "3.14159265", "eXCb7Mdq1v");
            container.put("fexed", "abc123", "mtzppj7mRA");
            container.put("temistocle", "nonsaprei", "Dl7aDrRhKr");
            container.put("fexed", "abc123", "xeiMblRBpz");
            container.put("temistocle", "nonsaprei", "QiNzfV16OQ");
            container.put("temistocle", "nonsaprei", "1U3pAe4XjC");
            container.put("fexed", "abc123", "ATiyqAyDs5");
            container.put("raspberry", "3.14159265", "srmNgHvHMr");
            container.put("a", "a", "kNfR98AWYu");
            container.put("raspberry", "3.14159265", "C3NxjUBtWQ");
            container.put("raspberry", "3.14159265", "ianlIhkvQQ");
            container.put("raspberry", "3.14159265", "d7eL0SNjtg");
            container.put("raspberry", "3.14159265", "JoTtSXAASs");
            container.put("fexed", "abc123", "21KKMWMrPo");
            container.put("fexed", "abc123", "3XnZZUY7Ow");
            container.put("a", "a", "kDo7pU8F9c");
            container.put("fexed", "abc123", "u7i9Q5De8i");
            container.put("raspberry", "3.14159265", "IzPAFZDf90");
            container.put("fexed", "abc123", "T2dwy2hxxF");
            container.put("a", "a", "FYRZdcaRjx");
            container.put("fexed", "abc123", "BC71E0WE26");
            container.put("dexef", "321cba", "NioR5uobCH");
            container.put("fexed", "abc123", "qyRidFsOyu");
            container.put("raspberry", "3.14159265", "abpl1VHdCR");
            container.put("fexed", "abc123", "VJmCahYaTq");
            container.put("dexef", "321cba", "GJOnT2ttf7");
            container.put("fexed", "abc123", "RCMNLgpK17");
            container.put("fexed", "abc123", "rngX9JrYvW");
            container.put("raspberry", "3.14159265", "C5Mq6dUyz0");
            container.put("raspberry", "3.14159265", "nsNoqciZWO");
            container.put("fexed", "abc123", "KtUlEVtvBN");
            container.put("dexef", "321cba", "VblRb13TQh");
            container.put("fexed", "abc123", "7C4nfJnWpd");
            container.put("fexed", "abc123", "ayXCmELaao");
            container.put("fexed", "abc123", "iOZRqmsfPP");
            container.put("a", "a", "5pkLkEva6F");
            container.put("raspberry", "3.14159265", "3sdf7Y8ox5");
            container.put("fexed", "abc123", "hZ5khuOSWW");
            container.put("fexed", "abc123", "Rm74NHigMH");
            container.put("fexed", "abc123", "HYWGNQBPDM");
            container.put("raspberry", "3.14159265", "pAMgWZ1ssL");
            container.put("fexed", "abc123", "FIxm8Pgq8T");
            container.put("a", "a", "rloHieStqx");
            container.put("fexed", "abc123", "MV6r99jpKw");
            container.put("fexed", "abc123", "7dIcshJyGh");
            container.put("fexed", "abc123", "uuHXcAt0Ak");
            container.put("fexed", "abc123", "4DqwNmIVjo");
            container.put("fexed", "abc123", "cnEonmOoCR");
            container.put("fexed", "abc123", "wTa2q4y6xe");
            container.put("raspberry", "3.14159265", "9TS3aE4crx");
            container.put("raspberry", "3.14159265", "HW1WfgEzaH");
            container.put("fexed", "abc123", "yMqUYJozBk");
            container.put("dexef", "321cba", "kBqdxANS8N");
            container.put("fexed", "abc123", "M5sebF4yVE");
            container.put("raspberry", "3.14159265", "uwGoSy9HBf");
            container.put("fexed", "abc123", "i6pLceyeoZ");
            container.put("fexed", "abc123", "zSgDqQvu7J");
            container.put("fexed", "abc123", "xFBSg4sqOf");
            container.put("dexef", "321cba", "aMpfepHIBf");
            container.put("fexed", "abc123", "DeNHce0R0Z");
            container.put("fexed", "abc123", "cLN39qmiBM");
            container.put("fexed", "abc123", "oej3t9nJd7");
            container.put("a", "a", "hkdoEXLEGm");
            container.put("fexed", "abc123", "mozlcttHAV");
            container.put("d", "d", "QVbpBy9KXP");
            container.put("fexed", "abc123", "mnflUXFGy1");
            container.put("d", "d", "opwKuM2Y9w");
            container.put("c", "c", "YutzwX6GTk");
            container.put("b", "b", "rx7goXqw4D");
            container.put("dexef", "321cba", "NZLoQ9oIDZ");
            container.put("e", "e", "Gvjdj9KeZD");
            container.put("d", "d", "JDDTEcELk0");
            container.put("d", "d", "ZGfRZQpc9X");
            container.put("c", "c", "p71grUeFZG");
            container.put("b", "b", "EXpZmME00Y");
            container.put("a", "a", "JYDTyl0Iq1");
        } catch (SecureDataContainer.UserNotFoundException ex) {
            fail("UserNotFoundException sul primo try");
        } catch (SecureDataContainer.InvalidPasswordException ex) {
            fail("InvalidPasswordException sul primo try");
        } catch (SecureDataContainer.InvalidDataException ex) {
            fail("InvalidDataException sul primo try");
        }
        
    }
}