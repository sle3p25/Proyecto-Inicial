
/**
 * WheelShape.
 * Whatever a wheel draws itself as on the Canvas: something that can be
 * shown, hidden, moved, resized and recolored. Rectangle, Circle and
 * Triangle each implement this the same way they always drew themselves, so SlotMachine can treat
 * "the shape for this wheel" the same way no matter which one it is --
 * it never needs to ask which concrete shape it has.
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public interface WheelShape
{
    void makeVisible();
    void makeInvisible();
    void moveHorizontal(int distance);
    void moveVertical(int distance);
    void changeSize(int newHeight, int newWidth);
    void changeColor(String newColor);
}