
/**
 * Symbol.
 * One colored symbol placed on a wheel. This class is abstract: a symbol is always created
 * as one of its concrete types.
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public abstract class Symbol
{
    public static final int MAX_SIZE = 10;
    private String color;
    
    protected Symbol(String color){
        this.color = color;
    }
    
    /**
     * @return this symbol's color.
     */
    public String getColor(){
        return color;
    }
    
    /**
     * Called every time this symbol becomes the one currently showing on
     * a wheel (after a spin, an advance, or a manual place). Each concrete symbol
     * type decides what, if anything, happens; the default does nothing.
     */
    public void onSelected(){
    }
    
    /**
     * @return this symbol's current size, used only for rendering.
     *         MAX_SIZE by default; EphemeralSymbol overrides this with
     *         its own shrinking size.
     */
    public int getSize(){
        return MAX_SIZE;
    }
    
    /**
     * @return true if this symbol is currently visible, used only for
     *         rendering. True by default; ShySymbol overrides this with
     *         its own toggling visibility.
     */
    public boolean isVisible(){
        return true;
    }
}