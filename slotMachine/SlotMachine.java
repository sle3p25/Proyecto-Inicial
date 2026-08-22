
/**
 * A slot machine simulator compound of several wheels
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version August 22
 */

import java.util.ArrayList;

public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean ok;

    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        ok = true;
    }

    /**
     * Adds an empty wheel on the indicated position.
     */
    public void addWheel(int pos) 
    {
        int index = clamp(pos - 1, 0, wheels.size());
        wheels.add(index, new Wheel());
        succeed();
    }
    
    /**
     * Delete a wheel on the indicated position.
     */
    public void delWheel(int pos) {
        if (wheels.isEmpty()){
            fail();
            return;
        }
        int index = clamp(pos - 1, 0, wheels.size() -1);
        wheels.remove(index);
        succeed();
    }
    
    /**
     * Add a symbol of a given color.
     */
    
    
    public void addSymbol (int pos, String color){
        if ( wheels.isEmpty()){
            fail();
            return;
        }
        int val = clamp(pos-1,0,wheels.size()-1);
        Wheel w = wheels.get(val);
        w.addSymbol(w.size()+1,color);
        succeed();
    }
    
     public void delSymbol(String symbol) {
        boolean removed = false;
        for (Wheel w : wheels) {
            removed = w.delSymbol(symbol) || removed;
        }
        if (removed) {
            succeed();
        } else {
            fail();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @return true if the last operation was succeed
     */
    public boolean ok(){
        return ok;
    }
    
    private void succeed(){
        ok = true;
    }
    
    private void fail(){
        ok = false;
    }
    
    private int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
}
