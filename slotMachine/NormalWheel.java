
/**
 * NormalWheel.
 * The default wheel type: on spin(), picks one of its symbols uniformly
 * at random. This is exactly the behavior every Wheel had before Cycle 4
 * introduced other wheel types -- moved here unchanged, mini-ciclo 1 only
 * opens the extension point, it does not add new behavior.
 * 
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */

import java.util.Random;

public class NormalWheel extends Wheel
{
    /**
     * Create a normal wheel without symbols, using a normal random number generator
     * to pick the symbol on each spin
     */
    public NormalWheel(){
        super();
    }
    
    /**
     * Create a normal wheel without symbols, letting the caller decide how to 
     * the symbol shown on each spin is chosen.
     * 
     * @param random the random number generator used to pick the index of the symbol shown after a spin.
     */
    public NormalWheel(Random random){
        super(random);
    }
    
    @Override
    public boolean spin(Wheel leftNeighbor){
        if (isLocked()){
            return false;
        }
        if (size() == 0){
            return false;
        }
        
        int i = random.nextInt(size());
        currentIndex = i;
        currentSymbol = symbolAt(i);
        currentSymbol.onSelected();
        return true;
    }
}