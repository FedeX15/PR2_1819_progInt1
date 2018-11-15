
import java.util.Scanner;

/**
 * @author Federico Matteoni
 */
public class MainClass {
    public static void main(String[] argv) {
        Scanner in = new Scanner(System.in);
        String c;
        SecureDataContainer<String> container = null;
        
        
        System.out.print("Scegliere l'implementazione da testare:\n\tH\tHashMap\n\tM\tMatrice\n\t");
        c = in.next();
        while (!"H".equals(c) && !"M".equals(c) && !"m".equals(c) && !"h".equals(c)) {
            System.out.print("**Non valido**\n\t");
            c = in.next();
        }
        
        if (c.equals("h") || c.equals("H")) {
            container = new StringHashMap();
        } else if (c.equals("m") || c.equals("M")) {
            container = new StringMatrix();
        }
        assert (container != null);
        System.out.println("Test in esecuzione con dati di tipo stringa.");

        do {
            System.out.print("Operazioni:\n"
                    + "\tA\tAggiungi un utente\n"
                    + "\tD\tDimensione della collezione di un utente\n"
                    + "\tI\tInserisci un dato nella collezione di un utente\n"
                    + "\tO\tOttieni un dato dalla collezione di un utente\n"
                    + "\tR\tRimuovi un dato dalla collezione di un utente\n"
                    + "\tC\tCopia un dato nella collezione dell'utente\n"
                    + "\tS\tCondividi un dato nella collezione di un altro utente\n"
                    + "\tZ\tStampa lo stato dell'intero container\n"
                    + "\tX\tChiudi\n\t");
            c = in.next();

        } while (!"X".equals(c) && !"x".equals(c));
        
        
    }
}
