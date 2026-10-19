
/**
 * Wheel.
 * Represents a slot machine's wheel.
 * 
 * This class is abstract: a wheel is always created as one of its concrete types.
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version September 5
 */
import java.util.Random;

public abstract class Wheel
{
    private Symbols symbols;
    protected Symbol currentSymbol;
    protected Random random;
    private boolean locked;
    protected int currentIndex;

    /** Create a roulette wheel without symbols, using a normal
     *  (non-seeded) random number generator to pick the symbol on each spin.
     */
    protected Wheel(){
        this(new Random());
    }

    /**
     * Create a roulette wheel without symbols, letting the caller decide
     * how the symbol shown on each spin is chosen.
     *
     * This cycle only requires a random simulator, so the default
     * constructor above is what SlotMachine uses. This constructor exists
     * so a future, deterministic way of choosing the symbol (for example,
     * one built to actually solve the marathon problem) can be plugged in
     * later without touching this class or Math.random() luck.
     *
     * @param random the random number generator used to pick the index
     *               of the symbol shown after a spin.
     */
    protected Wheel(Random random){
        symbols = new Symbols();
        this.random = random;
        locked = false;
        currentIndex = -1;
    }
    
    /**
     * To lock 
     */
    public void lock() {
        locked = true;
    }
    
    /**
     * To unlock 
     */
    public void unlock() {
        locked = false;
    }
    
    /**
     * Checks whether the specified wheel is locked. Returns true if locked, 
     * false otherwise.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * add a symbol of the default ("normal") type and the given color.
     */
    public void addSymbol (int pos, String color){
        addSymbol("normal", pos, color);
    }

    /**
     * add a symbol of the given type and color.
     * @param type one of "normal", "ephemeral", "shy".
     * @return true if the type was recognized and the symbol was added.
     */
    public boolean addSymbol(String type, int pos, String color){
        return symbols.add(pos, type, color);
    }
    
    /**
     * Choose the symbol that becomes visible on this wheel. Each concrete
     * wheel type decides how; see the class comment of each subclass.
     * 
     * @param leftNeighbor the wheel immediately to the left of this one in
     *                     the machine, or null if this is the leftmost
     *                     wheel. Most wheel types ignore it; LeftyWheel
     *                     uses it to copy that neighbor's current symbol.
     */
    public abstract boolean spin(Wheel leftNeighbor);
    
    /**
     * @return true if this wheel can be locked.
     */
    public boolean canBeLocked(){
        return true;
    }
    
    /**
     * @return true if this wheel can be swapped with another one.
     */
    public boolean canBeSwapped(){
        return true;
    }
    /**
     * @return true if this wheel can be removed from the machine.
     */
    public boolean canBeRemoved(){
        return true;
    }
    
    /**
     * @return a new, freshly created visual shape for this wheel, used
     *         only for rendering. Each wheel type picks its own shape;
     *         the default is a square (Rectangle), so machines built
     *         before cycle 4 look exactly as they did before.
     */
    public WheelShape newShape(){
        return new Rectangle();
    }
    
    /**
     * @return the current symbol's size, for rendering only; the neutral
     *         maximum size if no symbol is currently selected.
     */
    public int getCurrentSymbolSize(){
        return currentSymbol == null ? Symbol.MAX_SIZE : currentSymbol.getSize();
    }
    
    /**
     * @return true if the current symbol is visible, for rendering only;
     *         true if no symbol is currently selected (nothing to hide).
     */
    public boolean isCurrentSymbolVisible(){
        return currentSymbol == null || currentSymbol.isVisible();
    }
    
    /**
     * The symbol object at the given index on this wheel.
     */
    protected Symbol symbolAt(int index){
        return symbols.get(index);
    }

    /**
     * enter the current symbol
     */
    public boolean place(String symbol){
        int idx = indexOf(symbol);
        if (idx == -1) {
            return false;
        }
        currentIndex = idx;
        currentSymbol = symbolAt(idx);
        currentSymbol.onSelected();
        return true;
    }

    /**
     * @return the position of the given color in the symbol list, or -1
     *         if the wheel does not have that color.
     */
    private int indexOf(String color) {
        String[] all = symbols.toArray();
        for (int i = 0; i < all.length; i++) {
            if (all[i].equals(color)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * colors of the symbols on the wheel, in order.
     */
    public String[] getSymbols() {
        return symbols.toArray();
    }

    /**
     * The symbol currently visible on the wheel.
     */
    public String getCurrentSymbol() {
        return currentSymbol == null ? null: currentSymbol.getColor();
    }

    /**
     * @return Number of distinct symbols (unique colors) on the wheel.
     */
    public int distinctCount() {
        return symbols.distinctCount();
    }

    /**
     * Remove the specified color. If the symbol currently shown survives
     * the removal, currentIndex is resynced to its new position (removing
     * an earlier symbol shifts everything after it back by one); if the
     * symbol shown was the one removed, currentIndex resets to -1.
     */
    public boolean delSymbol(String color) {
        boolean removed = symbols.delete(color);
        if (removed && currentSymbol != null) {
            currentIndex = indexOf(currentSymbol.getColor());
        }
        return removed;
    }

    public int size() {
        return symbols.size();
    }
    
    /**
     * Roulette effect
     */
    
    public boolean advance() {
        if (locked || symbols.isEmpty()) {
            return false;
        }
        int size = symbols.size();
        currentIndex = (currentIndex + 1) % size;
        currentSymbol = symbolAt(currentIndex);
        currentSymbol.onSelected();
        return true;
    }
    
    
}
