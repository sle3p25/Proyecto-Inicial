/**
 * Unit tests for the Cycle 1 functionality of SlotMachine: adding and
 * removing wheels, adding and removing symbols, placing a symbol as
 * current, spinning one wheel or all wheels, and the read-only queries
 * (symbols, distinctSymbols, configuration, isJackpot). Always runs in
 * invisible mode, so no JOptionPane is ever shown during the run.
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineC1Test
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
     * Verifies that adding wheels grows the machine's configuration by one
     * entry per wheel.
     */
    @Test
    public void accordingPgRpShouldAddWheelAtGivenPosition() {
        machine.addWheel(1);
        machine.addWheel(2);

        assertEquals(2, machine.configuration().length);
        assertTrue(machine.ok());
    }

    /**
     * Verifies that deleting a wheel from an empty machine fails instead
     * of throwing an unhandled exception.
     */
    @Test
    public void accordingPgRpShouldFailToDeleteWheelWhenMachineIsEmpty() {
        machine.delWheel(1);

        assertFalse(machine.ok());
    }

    /**
     * Verifies that deleting a wheel removes exactly that wheel from the
     * configuration.
     */
    @Test
    public void accordingPgRpShouldDeleteWheelAtGivenPosition() {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.delWheel(1);

        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }

    /**
     * Verifies that adding a symbol when there are no wheels fails
     * instead of throwing an unhandled exception.
     */
    @Test
    public void accordingPgRpShouldFailToAddSymbolWhenNoWheelsExist() {
        machine.addSymbol(1, "red");

        assertFalse(machine.ok());
    }

    /**
     * Verifies that a symbol added to a wheel shows up in the machine's
     * full symbol list.
     */
    @Test
    public void accordingPgRpShouldAddSymbolToWheel() {
        machine.addWheel(1);

        machine.addSymbol(1, "red");

        assertTrue(machine.ok());
        assertEquals(1, machine.symbols().length);
        assertEquals("red", machine.symbols()[0]);
    }

    /**
     * Verifies that removing an existing symbol takes it out of the
     * machine's full symbol list.
     */
    @Test
    public void accordingPgRpShouldRemoveExistingSymbol() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.delSymbol("red");

        assertTrue(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    /**
     * Verifies that removing a symbol that is not on any wheel fails
     * instead of silently succeeding.
     */
    @Test
    public void accordingPgRpShouldFailToRemoveNonExistingSymbol() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.delSymbol("blue");

        assertFalse(machine.ok());
    }

    /**
     * Verifies that placing an existing symbol makes it the wheel's
     * current symbol.
     */
    @Test
    public void accordingPgRpShouldPlaceExistingSymbolAsCurrent() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.placeSymbol(1, "blue");

        assertEquals("blue", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    /**
     * Verifies that placing a symbol that does not exist on the wheel
     * fails instead of silently succeeding.
     */
    @Test
    public void accordingPgRpShouldFailToPlaceNonExistingSymbol() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.placeSymbol(1, "green");

        assertFalse(machine.ok());
    }

    /**
     * Verifies that spinning a single wheel with just one symbol always
     * lands on that symbol and reports success.
     */
    @Test
    public void accordingPgRpShouldSpinSingleWheelSuccessfully() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.spin(1);

        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    /**
     * Verifies that spinning a wheel when there are no wheels fails
     * instead of throwing an unhandled exception.
     */
    @Test
    public void accordingPgRpShouldFailToSpinSingleWheelWhenNoWheelsExist() {
        machine.spin(1);

        assertFalse(machine.ok());
    }

    /**
     * Verifies that spinning all wheels moves every wheel that has
     * symbols to spin.
     */
    @Test
    public void accordingPgRpShouldSpinAllWheels() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        machine.spin();

        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    /**
     * Verifies that spinning all wheels when there are no wheels fails
     * instead of throwing an unhandled exception.
     */
    @Test
    public void accordingPgRpShouldFailToSpinAllWheelsWhenNoWheelsExist() {
        machine.spin();

        assertFalse(machine.ok());
    }

    /**
     * Verifies that symbols() lists every symbol on every wheel, in the
     * order they were added.
     */
    @Test
    public void accordingPgRpShouldListAllSymbolsAcrossWheels() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "green");

        assertEquals(3, machine.symbols().length);
    }

    /**
     * Verifies that distinctSymbols() counts distinct colors currently
     * showing across wheels (their configuration), not colors sitting
     * unused in a wheel's inventory.
     */
    @Test
    public void accordingPgRpShouldCountDistinctSymbolsCurrentlyShowing() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(2, "red");

        assertEquals(2, machine.distinctSymbols());
    }

    /**
     * Verifies that a wheel which has never spun or been placed (still
     * showing no color) is not counted as an extra distinct symbol.
     */
    @Test
    public void accordingPgRpShouldNotCountWheelsWithNoCurrentSymbol() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");

        assertEquals(1, machine.distinctSymbols());
    }

    /**
     * Verifies that the machine reports a jackpot when every wheel shows
     * the same symbol.
     */
    @Test
    public void accordingPgRpShouldDetectJackpotWhenAllWheelsShowSameSymbol() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");

        assertTrue(machine.isJackpot());
    }

    /**
     * Verifies that the machine does not report a jackpot when the
     * wheels show different symbols.
     */
    @Test
    public void accordingPgRpShouldNotDetectJackpotWhenWheelsDiffer() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        assertFalse(machine.isJackpot());
    }
}
