
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
    private ArrayList<String> colors;

    /**
     * Create an empty collection of symbols.
     */
    public Symbols(){
        colors = new ArrayList<String>();
    }

    /**
     * Add a color at the given position. Positions are 1-based; a
     * position below 1 is clamped to 1 and a position past the end is
     * clamped to the end.
     */
    public void add(int pos, String color){
        int val = clamp(pos - 1, 0, colors.size());
        colors.add(val, color);
    }

    /**
     * Remove one occurrence of the given color.
     * @return true if the color was present and was removed.
     */
    public boolean delete(String color){
        return colors.remove(color);
    }

    /**
     * @return true if the given color belongs to this collection.
     */
    public boolean contains(String color){
        return colors.contains(color);
    }

    /**
     * @return the colors in this collection, in order, starting at
     * position 1.
     */
    public String[] toArray(){
        return colors.toArray(new String[0]);
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
        return (int) colors.stream().distinct().count();
    }

    private int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
