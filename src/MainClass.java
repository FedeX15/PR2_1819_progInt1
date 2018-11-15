
import java.util.Scanner;

/**
 * @author Federico Matteoni
 */
public class MainClass {
    public static void main(String[] argv) {
        System.out.print("Scegliere l'implementazione da testare:\n\tH\tHashMap\n\tM\tMatrice\n\t");
        Scanner in = new Scanner(System.in);
        String c = in.next();
        while (!"H".equals(c) && !"M".equals(c) && !"m".equals(c) && !"h".equals(c)) {
            System.out.print("**Non valido**\n\t");
            c = in.next();
        }
    }
}
