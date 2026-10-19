
/**
 * ShySymbol.
 * Toggles between visible and invisible every time it is selected (after
 * a spin, an advance, or a manual place). Its color and its visibility
 * are independent: isJackpot()/configuration()/distinctSymbols() always
 * use getColor(), regardless of whether this symbol is currently visible
 * -- isVisible() exists purely for rendering, decided separately.
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public class ShySymbol extends Symbol
{
    private boolean visible;
    
    public ShySymbol(String color){
        super(color);
        visible = true;
    }
    
    @Override
    public void onSelected(){
        visible = !visible;
    }
    
    /**
     * @return true if this symbol is currently visible
     */
    @Override
    public boolean isVisible(){
        return visible;
    }
}