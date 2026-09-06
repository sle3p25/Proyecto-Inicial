/**
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version 
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineCC2Test
{
    private SlotMachine machine;

    /**
     * Sets up the test fixture before each test execution.
     * Initializes a fresh SlotMachine instance.
     */
    @Before
    public void setUp() {
        machine = new SlotMachine();
    }

    /**
     * Verifies that swapping two wheels is correctly reflected in the current configuration array.
     */
    @Test
    public void accordingPgRpShouldReflectSwapInConfiguration() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "green");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "green");

        machine.swap(1, 2);

        assertEquals("green", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Verifies that setting a configuration skips locked wheels while correctly updating unlocked ones.
     */
    @Test
    public void accordingPgRpShouldIgnoreLockedWheelWhenApplyingConfiguration() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.placeSymbol(1, "blue");

        machine.lock(1);
        machine.spin(new String[]{"red", "red"});

        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }
}