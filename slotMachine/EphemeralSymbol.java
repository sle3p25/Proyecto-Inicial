
/**
 * EphemeralSymbol.
 * Shrinks a little every time it is selected (after a spin, an advance,
 * or a manual place), down to a floor so it never disappears entirely
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public class EphemeralSymbol extends Symbol
{
    private static final int MIN_SIZE = 1;
    
    private int size;
    
    public EphemeralSymbol(String color){
        super(color);
        size = MAX_SIZE;
    }
    
    @Override
    public void onSelected(){
        if (size > MIN_SIZE){
            size--;
        }
    }
    
    /**
     * @return this symbol's current size: starts at INITIAL_SIZE and
     *         shrinks by one on every selection, never below MIN_SIZE.
     */    
    
    public int getSize(){
        return size;
    }
}