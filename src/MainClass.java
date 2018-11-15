
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    + "\tZ\tStampa lo stato della collezione di un utente\n"
                    + "\tX\tChiudi\n\t");
            c = in.next();
            
            switch (c) {
                case "A":
                case "a":
                    String usr, pwd;
                    System.out.print("Nome utente: ");
                    usr = in.next();
                    System.out.print("Password: ");
                    pwd = in.next();
                    try {
                        container.createUser(usr, pwd);
                    } catch (SecureDataContainer.InvalidUserException ex) {
                        System.out.println("***ERRORE: nome utente già registrato");
                    }
                    
                    break;
                
                case "D":
                case "d":
                    System.out.print("Nome utente: ");
                    usr = in.next();
                    System.out.print("Password: ");
                    pwd = in.next();
                    try {
                        System.out.println("Dimensione della collezione dell'utente " + usr + ": " + container.getSize(usr, pwd));
                    } catch (SecureDataContainer.UserNotFoundException ex) {
                        System.out.println("***ERRORE: utente non esistente");
                    } catch (SecureDataContainer.InvalidPasswordException ex) {
                        System.out.println("***ERRORE: password errata");
                    }
                    break;
                    
                case "I":
                case "i":
                    String data;
                    System.out.print("Nome utente: ");
                    usr = in.next();
                    System.out.print("Password: ");
                    pwd = in.next();
                    System.out.print("Stringa da inserire: ");
                    data = in.next();
                    try {
                        if (container.put(usr, pwd, data)) {
                            System.out.println(data + " inserito correttamente nella collezione dell'utente " + usr);
                        } else {
                            System.out.println(data + " NON è stato inserito nella collezione dell'utente " + usr);
                        }
                    } catch (SecureDataContainer.UserNotFoundException ex) {
                        System.out.println("***ERRORE: utente non esistente");
                    } catch (SecureDataContainer.InvalidPasswordException ex) {
                        System.out.println("***ERRORE: password errata");
                    } catch (SecureDataContainer.InvalidDataException ex) {
                        System.out.println("***ERRORE: dato non valido");
                    }
                    break;
                
                case "O":
                case "o":
                    
                    break;
                
                case "R":
                case "r":
                    
                    break;
                    
                case "C":
                case "c":
                    
                    break;
                    
                case "S":
                case "s":
                    
                    break;
                    
                case "Z":
                case "z":
                    System.out.print("Nome utente: ");
                    usr = in.next();
                    System.out.print("Password: ");
                    pwd = in.next();
                    try {
                        System.out.print(usr+ ": ");
                        for (Iterator i = container.getIterator(usr, pwd); i.hasNext(); ) {
                            System.out.print(i.next()+"|");
                        }
                        System.out.println("");
                    } catch (SecureDataContainer.UserNotFoundException ex) {
                        System.out.println("***ERRORE: utente non esistente");
                    } catch (SecureDataContainer.InvalidPasswordException ex) {
                        System.out.println("***ERRORE: password errata");
                    }
                    
                    break;
            }

        } while (!"X".equals(c) && !"x".equals(c));
        
        
    }
}