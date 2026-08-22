
/**
 * Wheel.
 * Represents a slot machine's wheel.
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version August 22
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Wheel
{
    private ArrayList<String> symbols;
    private String currentSymbol;
    
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
     * Choose a color at random 
     */
    public boolean spin(){
        if (symbols.isEmpty()){
            return false;
        }
        int i = (int)(Math.random() * symbols.size());
        currentSymbol = symbols.get(i); 
        return true;
    }
    /** 
     * enter the current symbol 
     */
    public boolean place(String symbol){
        if (symbols.contains(symbol)){
            currentSymbol = symbol; 
            return true;
        }
        return false;
    }
    
    
    
    
    /**
     * colors of the symbols on the wheel, in order.
     */
    public String[] getSymbols() {
        return symbols.toArray(new String[0]);
    }

    /**
     * The symbol currently visible on the wheel.
     */
    public String getCurrentSymbol() {
        return currentSymbol;
    }

    /**
     * @return Number of distinct symbols (unique colors) on the wheel. (AI usage)
     */
    public int distinctCount() {
        return (int) symbols.stream().distinct().count();
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