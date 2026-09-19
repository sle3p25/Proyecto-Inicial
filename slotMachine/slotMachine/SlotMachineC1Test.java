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

    @Before
    public void setUp() {
        machine = new SlotMachine();
    }

    @Test
    public void accordingPgRpShouldAddWheelAtGivenPosition() {
        machine.addWheel(1);
        machine.addWheel(2);

        assertEquals(2, machine.configuration().length);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingPgRpShouldFailToDeleteWheelWhenMachineIsEmpty() {
        machine.delWheel(1);

        assertFalse(machine.ok());
    }

    @Test
    public void accordingPgRpShouldDeleteWheelAtGivenPosition() {
        machine.addWheel(1);
        machine.addWheel(2);

        machine.delWheel(1);

        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingPgRpShouldFailToAddSymbolWhenNoWheelsExist() {
        machine.addSymbol(1, "red");

        assertFalse(machine.ok());
    }

    @Test
    public void accordingPgRpShouldAddSymbolToWheel() {
        machine.addWheel(1);

        machine.addSymbol(1, "red");

        assertTrue(machine.ok());
        assertEquals(1, machine.symbols().length);
        assertEquals("red", machine.symbols()[0]);
    }

    @Test
    public void accordingPgRpShouldRemoveExistingSymbol() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.delSymbol("red");

        assertTrue(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    @Test
    public void accordingPgRpShouldFailToRemoveNonExistingSymbol() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.delSymbol("blue");

        assertFalse(machine.ok());
    }

    @Test
    public void accordingPgRpShouldPlaceExistingSymbolAsCurrent() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");

        machine.placeSymbol(1, "blue");

        assertEquals("blue", machine.configuration()[0]);
        assertTrue(machine.ok());
    }

    @Test
    public void accordingPgRpShouldFailToPlaceNonExistingSymbol() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.placeSymbol(1, "green");

        assertFalse(machine.ok());
    }

    @Test
    public void accordingPgRpShouldSpinSingleWheelSuccessfully() {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.spin(1);

        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    @Test
    public void accordingPgRpShouldFailToSpinSingleWheelWhenNoWheelsExist() {
        machine.spin(1);

        assertFalse(machine.ok());
    }

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

    @Test
    public void accordingPgRpShouldFailToSpinAllWheelsWhenNoWheelsExist() {
        machine.spin();

        assertFalse(machine.ok());
    }

    @Test
    public void accordingPgRpShouldListAllSymbolsAcrossWheels() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "green");

        assertEquals(3, machine.symbols().length);
    }

    @Test
    public void accordingPgRpShouldCountDistinctSymbolsAcrossWheels() {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");

        assertEquals(2, machine.distinctSymbols());
    }

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