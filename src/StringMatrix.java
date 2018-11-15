/**
 *  
 * @author Federico Matteoni
 */
public class StringMatrix extends MatrixSecureDataContanier<String> {
    
    public StringMatrix() {
        super();
        this.data = new String[0];
    }

    @Override
    public boolean verifyData(String data) {
        return (!(data.equals("")));
    }
    
}
