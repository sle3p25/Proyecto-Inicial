/**
 * Acceptance test - Cycle 2.
 *
 * Pre-made scenario that, on top of the same 5 steps from Cycle 1, also
 * demonstrates the capabilities added in Cycle 2: swap, lock/unlock, and
 * the deterministic spin via spin(String[]) (setting an exact
 * configuration in one shot). Ends in jackpot guaranteed, not by luck.
 *
 * Runs visible, same as AcceptanceTestC1, so it can be demonstrated live
 * (Canvas + "JACKPOT!" JOptionPane).
 *
 * To run it: object bench > right-click the class > new AcceptanceTestC2()
 * > right-click the object > run().
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public class AcceptanceTestC2
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
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "green");
        machine.addSymbol(3, "red");

        // Demonstrates swap and lock/unlock, capabilities added in Cycle 2
        machine.placeSymbol(1, "blue");
        machine.placeSymbol(2, "green");
        machine.swap(1, 2);
        machine.lock(3);
        machine.unlock(3);

        // 4. Spin the wheels (deterministically, via spin(String[]),
        //    also added in Cycle 2)
        machine.spin(new String[]{"red", "red", "red"});

        // 5. Ends in jackpot
        if (!machine.isJackpot()) {
            throw new AssertionError(
                "AcceptanceTestC2 failed: the machine did not end in jackpot. Configuration: "
                + java.util.Arrays.toString(machine.configuration()));
        }
        System.out.println("AcceptanceTestC2 PASSED - final configuration: "
            + java.util.Arrays.toString(machine.configuration()));
    }
}