/**
 *  
 * @author Federico Matteoni
 */
public class StringMatrix extends MatrixSecureDataContanier<String> {
    
    public StringMatrix() {
        super();
        this.data = new String[0];
    }
    //L'inizializzazione di data mantiene consistente I(c) per tutti i metodi

    @Override
    public boolean verifyData(String data) {
        return (!(data.equals("")));
    }
    
}
