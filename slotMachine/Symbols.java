
/**
 * Symbols.
 * The ordered collection of colored symbols placed on a wheel.
 * Extracted out of Wheel so that "which symbols exist on the wheel"
 * (this class) is a separate responsibility from "which symbol is
 * currently showing on the wheel" (Wheel).
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version September 5
 */
import java.util.ArrayList;

public class Symbols
{
    private ArrayList<Symbol> colors;

    /**
     * Create an empty collection of symbols.
     */
    public Symbols(){
        colors = new ArrayList<Symbol>();
    }

    /**
     * Add a symbol of the default ("normal") type and the given color at
     * the given position. Kept so every call site from cycles 1-3 keeps
     * working unchanged.
     */
    public void add(int pos, String color){
        add(pos, "normal", color);
    }

    /**
     * Add a symbol of the given type and color at the given position.
     * Positions are 1-based; a position below 1 is clamped to 1 and a
     * position past the end is clamped to the end.
     * @param type one of "normal", "ephemeral", "shy", "growing".
     * @return true if the type was recognized and the symbol was added.
     */
    public boolean add(int pos, String type, String color){
        Symbol s = createSymbol(type, color);
        if (s == null) {
            return false;
        }
        int val = clamp(pos - 1, 0, colors.size());
        colors.add(val, new NormalSymbol(color));
        return true;
    }
    
    /**
     * Translates a symbol type name into the matching concrete Symbol,
     * comparing strings.
     * @return the new symbol, or null if the type is not recognized.
     */
    private Symbol createSymbol(String type, String color){
        if (type.equals("normal")){
            return new NormalSymbol(color);
        } else if (type.equals("ephemeral")){
            return new EphemeralSymbol(color);
        } else if (type.equals("shy")){
            return new ShySymbol(color);
        } else if (type.equals("growing")){
            return new GrowingSymbol(color);
        }
        return null;
    }
    
    /**
     * Remove one occurrence of the given color.
     * @return true if the color was present and was removed.
     */
    public boolean delete(String color){
        for (int i = 0; i < colors.size(); i++){
            if(colors.get(i).getColor().equals(color)){
                colors.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the given color belongs to this collection.
     */
    public boolean contains(String color){
        for (Symbol s: colors){
            if(s.getColor().equals(color)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return the symbol object at the given index
     */
    public Symbol get(int index){
        return colors.get(index);
    }

    /**
     * @return the colors in this collection, in order, starting at
     * position 1.
     */
    public String[] toArray(){
        String[] result = new String[colors.size()];
        for (int i = 0; i < colors.size(); i++){
            result[i] = colors.get(i).getColor();
        }
        return result;
    }

    /**
     * @return the number of colors currently in the collection.
     */
    public int size(){
        return colors.size();
    }

    /**
     * @return true if the collection has no colors.
     */
    public boolean isEmpty(){
        return colors.isEmpty();
    }

    /**
     * @return the number of distinct colors in the collection.
     */
    public int distinctCount(){
        ArrayList<String> names = new ArrayList<String>();
        for(Symbol s: colors){
            names.add(s.getColor());
        }
        return(int) colors.stream().distinct().count();
    }

    private int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
