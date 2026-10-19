
/**
 * GrowingSymbol.
 * Grows a little every time it is selected (after a spin, an advance,
 * or a manual place), up to a ceiling so it never grows past the shapes
 * that draw it -- it starts as a single point and ends up at full size,
 * the mirror image of EphemeralSymbol.
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public class GrowingSymbol extends Symbol
{
    private static final int MIN_SIZE = 1;
    
    private int size;
    
    public GrowingSymbol(String color){
        super(color);
        size = MIN_SIZE;
    }
    
    @Override
    public void onSelected(){
        if (size < MAX_SIZE){
            size++;
        }
    }
    
    /**
     * @return this symbol's current size: starts at MIN_SIZE and grows
     * by one on every selection, never past MAX_SIZE.
     */
    @Override
    public int getSize(){
        return size;
    }
}