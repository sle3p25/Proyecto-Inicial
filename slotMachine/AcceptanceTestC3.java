/**
 * Acceptance test - Cycle 3.
 *
 * Pre-made scenario that demonstrates the full maratón problem:
 * SlotMachine(n) already covers steps 1-3 (creates the machine, the n
 * wheels, and the n shared symbols, with a random initial spin). For
 * step 4, SlotMachineContest.solve(machine, n) runs the three-phase
 * solving algorithm directly on this same machine, so isJackpot() can be
 * verified on it afterward - solve(n) alone would not let us do this,
 * since it builds and keeps its own SlotMachine internally and never
 * exposes it.
 *
 * Runs visible so it can be demonstrated live.
 *
 * To run it: object bench > right-click the class > new AcceptanceTestC3()
 * > right-click the object > run().
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version October 2026
 */
public class AcceptanceTestC3
{
    public void run()
    {
        int n = 5;

        // 1-3. Create the machine, the wheels and the symbols (via SlotMachine(n))
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();

        // 4. Spin the wheels, using SlotMachineContest's algorithm on our
        //    own machine, so we can check its state afterward.
        SlotMachineContest contest = new SlotMachineContest();
        contest.solve(machine, n);

        // 5. Ends in jackpot
        if (!machine.isJackpot()) {
            throw new AssertionError(
                "AcceptanceTestC3 failed: the machine did not end in jackpot. Configuration: "
                + java.util.Arrays.toString(machine.configuration()));
        }
        System.out.println("AcceptanceTestC3 PASSED - final configuration: "
            + java.util.Arrays.toString(machine.configuration()));
    }
}