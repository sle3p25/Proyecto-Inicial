
/**
 * Wheel.
 * Represents a slot machine's wheel.
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version August 22
 */
import java.util.ArrayList;
public class Wheel
{
    private ArrayList<String> symbols;
    
    /** Create a roulette wheel without symbols 
     * 
     */
    public Wheel(){
        symbols = new ArrayList<String>();
    }
    /** 
     * add a color 
     */
    public void addSymbol (int pos, String color){
        int val = clamp(pos -1, 0, symbols.size());
        symbols.add(val, color);
    }
    /**
     * Remove the specified color
     */
    public boolean delSymbol(String color) {
        return symbols.remove(color);
    }
    
    public int size() {
        return symbols.size();
    }

    private int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
    
}