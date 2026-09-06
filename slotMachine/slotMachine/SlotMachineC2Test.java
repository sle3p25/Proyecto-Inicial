/**
 *
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version 
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineC2Test
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
     * Verifies that swapping two wheels correctly exchanges their active symbols.
     */
    @Test
    public void accordingPgRpShouldSwapTwoWheelsSymbols() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);

        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Verifies that attempting to swap when there are not enough wheels fails gracefully
     * without throwing an unhandled exception.
     */
    @Test
    public void accordingPgRpShouldNotThrowWhenSwappingWithoutEnoughWheels() {
        machine.addWheel(1);

        machine.swap(1, 2);

        assertFalse(machine.ok());
    }

    /**
     * Verifies that a locked wheel retains its current symbol when the machine spins.
     */
    @Test
    public void accordingPgRpShouldKeepLockedWheelUnchangedWhenSpinningAll() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.placeSymbol(1, "red");

        machine.lock(1);
        machine.spin();

        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Verifies that unlocking a previously locked wheel allows it to spin again successfully.
     */
    @Test
    public void accordingPgRpShouldAllowSpinAfterUnlock() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.lock(1);
        machine.unlock(1);
        machine.spin();

        assertTrue(machine.ok());
    }

    /**
     * Verifies that spinning a specific wheel by one step advances to the next symbol in sequence.
     */
    @Test
    public void accordingPgRpShouldAdvanceExactlyOneStep() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");
        machine.placeSymbol(1, "red");

        machine.spin(1, 1);

        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Verifies that spinning a wheel for a full cycle (equal to symbol count) wraps around to the initial symbol.
     */
    @Test
    public void accordingPgRpShouldWrapAroundAfterFullRotation() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");
        machine.placeSymbol(1, "red");

        machine.spin(1, 3);

        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Verifies that attempting to spin a locked wheel by steps leaves it unmoved and reports a failure status.
     */
    @Test
    public void accordingPgRpShouldNotMoveLockedWheelWhenRotatingSteps() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.placeSymbol(1, "red");

        machine.lock(1);
        machine.spin(1, 3);

        assertEquals("red", machine.configuration()[0]);
        assertFalse(machine.ok());
    }

    /**
     * Verifies that applying a matching symbol array triggers a jackpot state.
     */
    @Test
    public void accordingPgRpShouldReachJackpotWithGivenConfiguration() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");

        machine.spin(new String[]{"red", "red"});

        assertTrue(machine.isJackpot());
    }

    /**
     * Verifies that passing a symbol array whose length does not match the wheel count causes the operation to fail.
     */
    @Test
    public void accordingPgRpShouldFailWhenConfigurationSizeDoesNotMatchWheelCount() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.spin(new String[]{"red", "blue"});

        assertFalse(machine.ok());
    }
}