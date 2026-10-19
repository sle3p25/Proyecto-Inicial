
/**
 * LeftyWheel.
 * On spin(), if this wheel has a left neighbor, copies that neighbor's
 * current symbol instead of picking one at random -- as long as this
 * wheel actually has that color among its own symbols; otherwise the
 * spin fails, the same way it fails when locked or empty. With no left
 * neighbor (this wheel is the leftmost one), it falls back to a normal
 * random spin, since there is no state to copy.
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */

import java.util.Random;
public class LeftyWheel extends Wheel
{
    public LeftyWheel(){
        super();
    }
    
    public LeftyWheel(Random random){
        super(random);
    }
    
    @Override
    public boolean spin(Wheel leftNeighbor){
        if(isLocked()){
            return false;
        }
        
        if(size() == 0){
            return false;
        }
        
        if(leftNeighbor == null){
            int i = random.nextInt(size());
            currentIndex = i;
            currentSymbol = symbolAt(i);
            currentSymbol.onSelected();
            return true;
        }
        String neighborColor = leftNeighbor.getCurrentSymbol();
        if (neighborColor == null){
            return false;
        }
        return place(neighborColor);
    }
    
    @Override
    public WheelShape newShape(){
        return new Triangle();
    }
}