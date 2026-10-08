/**
 * Acceptance test - Cycle 1.
 *
 * Pre-made scenario: builds a
 * machine with 3 wheels that all share a single color ("red"), spins it,
 * and verifies it ends in jackpot. Since each wheel has only one symbol,
 * the result of spin() is deterministic even though it still uses real
 * randomness internally (there is only one option to pick from on each
 * wheel).
 *
 * Unlike unit tests (which run with the visual component disabled), this
 * one runs visible, so it can be demonstrated live: the Canvas window
 * opens and the "JACKPOT!" JOptionPane appears when it succeeds.
 *
 * To run it: object bench > right-click the class > new AcceptanceTestC1()
 * > right-click the object > run().
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public class AcceptanceTestC1
{
    public void run()
    {
        // 1. Create a machine
        SlotMachine machine = new SlotMachine();
        machine.makeVisible();

        // 2. Create wheels
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(3);

        // 3. Create and add symbols
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        machine.addSymbol(3, "red");

        // 4. Spin the wheels
        machine.spin();

        // 5. Ends in jackpot
        if (!machine.isJackpot()) {
            throw new AssertionError(
                "AcceptanceTestC1 failed: the machine did not end in jackpot. Configuration: "
                + java.util.Arrays.toString(machine.configuration()));
        }
        System.out.println("AcceptanceTestC1 PASSED - final configuration: "
            + java.util.Arrays.toString(machine.configuration()));
    }
}