/**
 *
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineC4Test
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
     * Verifies that a rebel wheel refuses to be locked.
     */
    @Test
    public void accordingPgRpShouldNotLockARebelWheel() {
        machine.addWheel("rebel", 1);
        machine.addSymbol(1, "red");

        machine.lock(1);

        assertFalse(machine.ok());
    }

    /**
     * Verifies that a rebel wheel refuses to be swapped with another wheel.
     */
    @Test
    public void accordingPgRpShouldNotSwapARebelWheel() {
        machine.addWheel(1);
        machine.addWheel("rebel", 2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");

        machine.swap(1, 2);

        assertFalse(machine.ok());
    }

    /**
     * Verifies that a rebel wheel refuses to be removed from the machine.
     */
    @Test
    public void accordingPgRpShouldNotRemoveARebelWheel() {
        machine.addWheel("rebel", 1);
        machine.addSymbol(1, "red");

        machine.delWheel(1);

        assertFalse(machine.ok());
    }

    /**
     * Verifies that a rebel wheel still spins normally: the only thing it
     * refuses is being locked, swapped, or removed.
     */
    @Test
    public void accordingPgRpShouldStillSpinARebelWheel() {
        machine.addWheel("rebel", 1);
        machine.addSymbol(1, "red");

        machine.spin(1);

        assertTrue(machine.ok());
    }

    /**
     * Verifies that a lefty wheel copies the symbol its left neighbor is
     * currently showing, instead of picking one at random.
     */
    @Test
    public void accordingPgRpShouldCopyLeftNeighborWhenSpinningALeftyWheel() {
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.spin(new String[]{"red", "blue"});

        machine.spin(2);

        assertEquals("red", machine.configuration()[1]);
        assertTrue(machine.ok());
    }

    /**
     * Verifies that a lefty wheel with no left neighbor (it is the leftmost
     * wheel in the machine) falls back to a normal random spin instead of
     * failing, since there is no neighbor to copy.
     */
    @Test
    public void accordingPgRpShouldSpinNormallyWhenLeftyWheelHasNoLeftNeighbor() {
        machine.addWheel("lefty", 1);
        machine.addSymbol(1, "red");

        machine.spin(1);

        assertTrue(machine.ok());
    }

    /**
     * Verifies that a lefty wheel fails to spin when its left neighbor's
     * current color is not one of its own symbols.
     */
    @Test
    public void accordingPgRpShouldFailWhenLeftyWheelDoesNotHaveNeighborsColor() {
        machine.addWheel(1);
        machine.addWheel("lefty", 2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");

        machine.spin(2);

        assertFalse(machine.ok());
    }

    /**
     * Verifies that adding a wheel of an unrecognized type fails instead of
     * silently creating a normal wheel.
     */
    @Test
    public void accordingPgRpShouldFailToAddWheelOfUnknownType() {
        machine.addWheel("galactic", 1);

        assertFalse(machine.ok());
    }
    
    /**
     * Verifies that an ephemeral symbol shrinks every time it is
     * selected, down to a floor (it never reaches zero or a negative
     * size).
     */
    @Test
    public void accordingPgRpShouldShrinkEphemeralSymbolDownToAFloor(){
        EphemeralSymbol symbol = new EphemeralSymbol("red");
        int initialSize = symbol.getSize();
        
        for (int i = 0; i < 50; i++){
            symbol.onSelected();
        }
        
        assertTrue(symbol.getSize() < initialSize);
        assertTrue(symbol.getSize() >= 1);
    }
    
    /**
     * Verifies that a growing symbol grows every time it is selected, up
     * to a ceiling (it never passes the maximum size).
     */
    @Test
    public void accordingPgRpShouldGrowGrowingSymbolUpToACeiling(){
        GrowingSymbol symbol = new GrowingSymbol("red");
        int initialSize = symbol.getSize();
        
        for (int i = 0; i < 50; i++){
            symbol.onSelected();
        }
        
        assertTrue(symbol.getSize() > initialSize);
        assertTrue(symbol.getSize() <= Symbol.MAX_SIZE);
    }
    /**
     * Verifies that a shy symbol toggles its visibility every time it is
     * selected: visible, then invisible, then visible again.
     */
    @Test
    public void accordingPgRpShouldToggleShySymbolVisibilityOnEachSelection(){
        ShySymbol symbol = new ShySymbol("red");
        boolean initiallyVisible = symbol.isVisible();
        
        symbol.onSelected();
        boolean afterOne = symbol.isVisible();
        symbol.onSelected();
        boolean afterTwo = symbol.isVisible();
        
        assertNotEquals(initiallyVisible, afterOne);
        assertEquals(initiallyVisible, afterTwo);
    }
    
    /**
     * Verifies that adding a symbol of an unrecognized type fails instead
     * of silently creating a normal symbol.
     */
    @Test
    public void accordingPgRpShouldFailToAddSymbolOfUnknownType(){
        machine.addWheel(1);
        machine.addSymbol("sparkly", 1, "red");
        assertFalse(machine.ok());
    }
    
    /**
     * Verifies that a shy symbol that has toggled itself invisible still
     * counts for isJackpot(): the visual component being off should not
     * change the underlying logic, only how it is drawn.
     */
    @Test
    public void accordingPgRpShouldStillComputeJackpotWhenAShySymbolIsInvisible(){
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol("shy", 1, "red");
        machine.addSymbol(2, "red");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        
        machine.spin(1,1);
        
        assertTrue(machine.isJackpot());
    }
}