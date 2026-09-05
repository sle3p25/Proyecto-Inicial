
/**
 * Wheel.
 * Represents a slot machine's wheel.
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version September 5
 */
import java.util.Random;

public class Wheel
{
    private Symbols symbols;
    private String currentSymbol;
    private Random random;

    /** Create a roulette wheel without symbols, using a normal
     *  (non-seeded) random number generator to pick the symbol on each spin.
     */
    public Wheel(){
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
    public Wheel(Random random){
        symbols = new Symbols();
        this.random = random;
    }

    /**
     * add a color
     */
    public void addSymbol (int pos, String color){
        symbols.add(pos, color);
    }

    /**
     * Choose the symbol that becomes visible on this wheel.
     * For this delivery the choice is random, as a real slot machine
     * simulator behaves; the source of randomness is the Random instance
     * supplied to this wheel (see the constructors above).
     */
    public boolean spin(){
        if (symbols.isEmpty()){
            return false;
        }
        int i = random.nextInt(symbols.size());
        currentSymbol = symbols.toArray()[i];
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
        return symbols.toArray();
    }

    /**
     * The symbol currently visible on the wheel.
     */
    public String getCurrentSymbol() {
        return currentSymbol;
    }

    /**
     * @return Number of distinct symbols (unique colors) on the wheel.
     */
    public int distinctCount() {
        return symbols.distinctCount();
    }

    /**
     * Remove the specified color
     */
    public boolean delSymbol(String color) {
        return symbols.delete(color);
    }

    public int size() {
        return symbols.size();
    }

}
