
/**
 * RebelWheel.
 * On spin(), picks one of its symbols uniformly at random, exactly like
 * NormalWheel -- what makes a rebel wheel different is not how it spins,
 * but that it refuses to be locked, swapped, or removed: it overrides
 * the three capability checks from Wheel to false, so SlotMachine knows
 * to refuse those operations before even trying them.
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */

import java.util.Random;
public class RebelWheel extends Wheel
{
    public RebelWheel(){
        super();
    }
    
    public RebelWheel(Random random){
        super(random);
    }
    
    @Override
    public boolean spin(Wheel leftNeighbor){
        if(isLocked()){
            return false;
        }
        
        if (size()==0){
            return false;
        }
        int i = random.nextInt(size());
        currentIndex = i;
        currentSymbol = symbolAt(i);
        currentSymbol.onSelected();
        return true;
    }
    
    @Override
    public boolean canBeLocked(){
        return false;
    }
    
    @Override
    public boolean canBeSwapped(){
        return false;
    }
    
    @Override
    public boolean canBeRemoved(){
        return false;
    }
    
    @Override
    public WheelShape newShape(){
        return new Circle();
    }
}