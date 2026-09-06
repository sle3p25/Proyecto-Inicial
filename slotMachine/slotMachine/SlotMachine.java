
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
    private static final int BODY_PADDING = 15;
    private static final String BODY_COLOR = "dimgray";
    private static final String BODY_JACKPOT_COLOR = "gold";
    private static final String JACKPOT_LIGHT_COLOR = "gold";

    private ArrayList<Wheel> wheels;
    private ArrayList<Rectangle> wheelShapes;
    private Rectangle jackpotShape;
    private Rectangle bodyShape;
    private boolean visible;
    private boolean ok;

    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine(){
        wheels = new ArrayList<Wheel>();
        wheelShapes = new ArrayList<Rectangle>();
        jackpotShape = null;
        bodyShape = null;
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
            fail("There's no wheels in the machine.");
            return;
        }
        int index = clamp(pos - 1, 0, wheels.size() -1);
        wheels.remove(index);
        rebuildShapes();
        succeed();
    }

    /**
     * Add a symbol of a given color.
     */
    public void addSymbol (int pos, String color){
        if ( wheels.isEmpty()){
            fail("There's no wheels in the machine to add the symbol.");
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
            fail("The symbol '" + symbol + "' does not exist on any wheel.");
        }
    }

    public void placeSymbol(int wheel, String symbol){
        if (wheels.isEmpty()){
            fail("There's no wheels in the machine.");
            return;
        }
        int val = clamp (wheel-1, 0, wheels.size()-1);
        if (wheels.get(val).place(symbol)){
            refreshShapes();
            succeed();
        }  else {
            fail("The selected wheel does not have the symbol '" + symbol + "'.");
        }
    }

    /**
     * Turn only the indicated wheel.
     */
    public void spin(int wheel) {
        if (wheels.isEmpty()) {
            fail("There's no wheels in the machine.");
            return;
        }
        int val = clamp(wheel - 1, 0, wheels.size() - 1);
        if (wheels.get(val).spin()) {
            refreshShapes();
            succeed();
            announceJackpotIfReached();
        } else {
            fail("The selected wheel has no symbols to spin.");
        }
    }

    /**
     * Turn all the wheels on the machine.
     */
    public void spin() {
        if (wheels.isEmpty()) {
            fail("There's no wheels in the machine.");
            return;
        }
        boolean any = false;
        for (Wheel w : wheels) {
            any = w.spin() || any;
        }
        if (any) {
            refreshShapes();
            succeed();
            announceJackpotIfReached();
        } else {
            fail("None of the wheels have symbols to spin.");
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
     * This is a plain, deterministic comparison of the current
     * configuration: it does not depend on chance in any way, only the
     * spin that produced that configuration does (see Wheel.spin()).
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

    /**
     * Marks the last operation as failed and, if the simulator is
     * visible, shows the reason to the user through a JOptionPane.
     */
    private void fail(String reason){
        ok = false;
        if (visible) {
            JOptionPane.showMessageDialog(null, reason,
                "Operation not completed", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * If the machine is visible and just reached a winning configuration,
     * congratulate the user through a JOptionPane.
     */
    private void announceJackpotIfReached() {
        if (visible && isJackpot()) {
            JOptionPane.showMessageDialog(null,
                "JACKPOT! All wheels show " + configuration()[0] + ".",
                "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * (Re)builds the machine's body, the jackpot light and one square per
     * wheel. Called whenever the number of wheels changes.
     */
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

        int bannerWidth = Math.max(40, wheels.size() * SLOT_WIDTH);

        if (jackpotShape != null) {
            jackpotShape.makeInvisible();
        }
        jackpotShape = new Rectangle();
        jackpotShape.changeSize(20, bannerWidth);
        jackpotShape.moveHorizontal(SLOT_X0 - 70);
        jackpotShape.moveVertical(JACKPOT_Y - 15);

        if (bodyShape != null) {
            bodyShape.makeInvisible();
        }
        bodyShape = new Rectangle();
        bodyShape.changeSize((SLOT_Y + 40) - JACKPOT_Y + 2 * BODY_PADDING,
            bannerWidth + 2 * BODY_PADDING);
        bodyShape.moveHorizontal(SLOT_X0 - BODY_PADDING - 70);
        bodyShape.moveVertical(JACKPOT_Y - BODY_PADDING - 15);

        refreshShapes();
    }

    /**
     * Repaints the wheels' squares, the jackpot light and the machine's
     * body to match the current configuration.
     */
    private void refreshShapes() {
        for (int i = 0; i < wheelShapes.size() && i < wheels.size(); i++) {
            String current = wheels.get(i).getCurrentSymbol();
            if (current != null) {
                wheelShapes.get(i).changeColor(current);
            }
        }
        boolean jackpot = isJackpot();
        if (jackpotShape != null) {
            jackpotShape.changeColor(JACKPOT_LIGHT_COLOR);
        }
        if (bodyShape != null) {
            bodyShape.changeColor(jackpot ? BODY_JACKPOT_COLOR : BODY_COLOR);
        }
        syncVisualState();
    }

    /**
     * Shows or hides every shape according to the visible flag, and keeps
     * the jackpot light on only while the machine is visible and won.
     */
    private void syncVisualState() {
        if (bodyShape != null) {
            if (visible) {
                bodyShape.makeVisible();
            } else {
                bodyShape.makeInvisible();
            }
        }
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
        if (bodyShape != null) {
            bodyShape.makeInvisible();
        }
        wheelShapes.clear();
        succeed();
    }
    
    
    /**
     * exchanges the positions of two roulette wheels. 
     * It temporarily saves the first wheel in memory, moves the second wheel 
     * into the first wheel's slot, and 
     * then assigns the saved copy of the first wheel to the second wheel's original 
     * slot.
     */
    public void swap(int wheel1, int wheel2) {
        if (wheels.size() < 2) {
            fail("Not enough wheels to swap.");
            return;
        }
        int i1 = clamp(wheel1 - 1, 0, wheels.size() - 1);
        int i2 = clamp(wheel2 - 1, 0, wheels.size() - 1);
        Wheel temp = wheels.get(i1);
        wheels.set(i1, wheels.get(i2));
        wheels.set(i2, temp);
        refreshShapes();
        succeed();
    }
     /**
     * Locks a wheel
     */
    public void lock(int wheel) {
        if (wheels.isEmpty()) {
            fail("No wheels available to lock");
            return;
        }
        int idx = clamp(wheel - 1, 0, wheels.size() - 1);
        wheels.get(idx).lock();
        succeed();
    }
    /**
     * Unlocks a wheel
     */
    public void unlock(int wheel) {
        if (wheels.isEmpty()) {
            fail("No wheels available to unlock");
            return;
        }
        int idx = clamp(wheel - 1, 0, wheels.size() - 1);
        wheels.get(idx).unlock();
        succeed();
    }
    
    /**
     * Rotates the indicated wheel forward the given number of steps. If
     * the machine is visible, the motion is shown one step at a time
     * (with a short pause between steps) instead of jumping straight to
     * the final symbol.
     */
    public void spin(int wheel, int steps) {
        if (wheels.isEmpty()) {
            fail("There are no wheels in the machine.");
            return;
        }
        if (steps < 0) {
            fail("The number of steps cannot be negative.");
            return;
        }
        int idx = clamp(wheel - 1, 0, wheels.size() - 1);
        Wheel w = wheels.get(idx);
        boolean moved = false;
        for (int s = 0; s < steps; s++) {
            if (!w.advance()) {
                break;
            }
            moved = true;
            refreshShapes();
            if (visible) {
                Canvas.getCanvas().wait(150);
            }
        }
        if (moved || steps == 0) {
            succeed();
            announceJackpotIfReached();
        } else {
            fail("The wheel is locked or has no symbols to rotate.");
        }
    }

    /**
     * Sets the exact configuration of the machine: applies one color per
     * wheel, skipping any wheel that is locked. Mainly useful for testing
     * isJackpot() deterministically, without depending on chance.
     */
    public void spin(String[] setSymbols) {
        if (setSymbols == null) {
            fail("Configuration cannot be null.");
            return;
        }
        if (setSymbols.length != wheels.size()) {
            fail("Configuration must provide one color for each wheel.");
            return;
        }
        boolean allPlaced = true;
        for (int i = 0; i < wheels.size(); i++) {
            Wheel w = wheels.get(i);
            if (w.isLocked()) {
                continue;
            }
            if (!w.place(setSymbols[i])) {
                allPlaced = false;
            }
        }
        refreshShapes();
        if (allPlaced) {
            succeed();
            announceJackpotIfReached();
        } else {
            fail("One of the specified colors does not exist on its corresponding wheel.");
        }
    }


    
    
    
    
    
}








