/**
 *
 * @author Federico Matteoni
 */
public class StringHashMap extends HashMapSecureDataContainer<String> {
    
    public StringHashMap() {
        super();
    }
    
    @Override
    public boolean verifyData(String data) {
        return (!(data.equals("")));
    }
    
}
