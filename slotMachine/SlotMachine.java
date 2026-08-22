
/**
 * A slot machine simulator compound of several wheels
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version August 22
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SlotMachine
{
    private static final int SLOT_WIDTH = 60;
    private static final int SLOT_X0 = 20;
    private static final int SLOT_Y = 100;
    private static final int JACKPOT_Y = 40;

    private ArrayList<Wheel> wheels;
    private ArrayList<Rectangle> wheelShapes;
    private Rectangle jackpotShape;
    private boolean visible;
    private boolean ok;

    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine(){
        wheels = new ArrayList<Wheel>();
        wheelShapes = new ArrayList<Rectangle>();
        jackpotShape = null;
        visible = false;
        ok = true;
    }

    /**
     * Adds an empty wheel on the indicated position.
     */
    public void addWheel(int pos) {
        int index = clamp(pos - 1, 0, wheels.size());
        wheels.add(index, new Wheel());
        rebuildShapes();
        succeed();
    }
    
    

    public void makeVisible() {
        visible = true;
        syncVisualState();
        succeed();
    }

    
    public void makeInvisible() {
        visible = false;
        syncVisualState();
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
    
    public void placeSymbol(int wheel, String symbol){
        if (wheels.isEmpty()){
            fail();
            return;
        }
        int val = clamp (wheel-1, 0, wheels.size()-1);
            if (wheels.get(val).place(symbol)){
                succeed();
            }  else {
                fail(); 
            }
        
    }
    
    /**
     * Turn only the indicated wheel.
     */
    public void spin(int wheel) {
    if (wheels.isEmpty()) {
        fail();
        return;
    }
    int val = clamp(wheel - 1, 0, wheels.size() - 1);
    if (wheels.get(val).spin()) {
        refreshShapes();
        succeed();
    } else {
        fail();
    }
    }
    
    /**
     * Turn all the wheels on the machine.
     */
    public void spin() {
    if (wheels.isEmpty()) {
        fail();
        return;
    }
    boolean any = false;
    for (Wheel w : wheels) {
        any = w.spin() || any;
    }
    if (any) {
        refreshShapes();
        succeed();
    } else {
        fail();
    }
    }
    
    
    
    
    
    
    /**
     * List the colors of all the machines
     */
    public String[] symbols() {
        ArrayList<String> all = new ArrayList<String>();
        for (Wheel w : wheels) {
            for (String s : w.getSymbols()) {
                all.add(s);
            }
        }
        return all.toArray(new String[0]);
    }

    /**
     * number of different colors on all the roulette wheels.
     */
    public int distinctSymbols() {
        ArrayList<String> all = new ArrayList<String>();
        for (String s : symbols()) {
            all.add(s);
        }
        return (int) all.stream().distinct().count();
    }

    /**
     * Visible colors on each wheel, from left to right.
     */
    public String[] configuration() {
        String[] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            config[i] = wheels.get(i).getCurrentSymbol();
        }
        return config;
    }

    /**
     * true if all wheels display the same symbol.
     */
    public boolean isJackpot() {
        String[] config = configuration();
        if (config.length == 0 || config[0] == null) {
            return false;
        }
        for (String c : config) {
            if (c == null || !c.equals(config[0])) {
                return false;
            }
        }
        return true;
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
    
    
    private void rebuildShapes() {
        for (Rectangle r : wheelShapes) {
            r.makeInvisible();
        }
        wheelShapes.clear();
        for (int i = 0; i < wheels.size(); i++) {
            Rectangle r = new Rectangle();
            r.changeSize(40, 40);
            r.moveHorizontal(SLOT_X0 + i * SLOT_WIDTH - 70);
            r.moveVertical(SLOT_Y - 15);
            wheelShapes.add(r);
        }
        if (jackpotShape != null) {
            jackpotShape.makeInvisible();
        }
        jackpotShape = new Rectangle();
        int bannerWidth = Math.max(40, wheels.size() * SLOT_WIDTH);
        jackpotShape.changeSize(20, bannerWidth);
        jackpotShape.moveHorizontal(SLOT_X0 - 70);
        jackpotShape.moveVertical(JACKPOT_Y - 15);
        refreshShapes();
    }

    private void refreshShapes() {
        for (int i = 0; i < wheelShapes.size() && i < wheels.size(); i++) {
            String current = wheels.get(i).getCurrentSymbol();
            if (current != null) {
                wheelShapes.get(i).changeColor(current);
            }
        }
        if (jackpotShape != null) {
            jackpotShape.changeColor("gold");
        }
        syncVisualState();
    }

    private void syncVisualState() {
        for (Rectangle r : wheelShapes) {
            if (visible) {
                r.makeVisible();
            } else {
                r.makeInvisible();
            }
        }
        if (jackpotShape != null) {
            if (visible && isJackpot()) {
                jackpotShape.makeVisible();
            } else {
                jackpotShape.makeInvisible();
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    private int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }
    
    
    public void exit() {
        for (Rectangle r : wheelShapes) {
            r.makeInvisible();
        }
        if (jackpotShape != null) {
            jackpotShape.makeInvisible();
        }
        wheelShapes.clear();
        succeed();
    }
}
