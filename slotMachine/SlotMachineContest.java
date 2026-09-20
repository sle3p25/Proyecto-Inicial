
/**
 * Solves and simulates the maratón problem (ICPC 2025 World Finals,
 * Problem I - "Slot Machine"): given a machine of n wheels, each showing
 * one of n shared symbols in the same cyclic order, find a sequence of
 * rotations that brings every wheel to show the same symbol, using only
 * the number of distinct symbols currently visible as feedback.
 *
 * This class never touches SlotMachine's internals directly, and never
 * decides anything from information SlotMachine does not expose through
 * its public methods: as required, only SlotMachine(n), spin(wheel,steps)
 * and distinctSymbols() are used to test and resolve the puzzle, and
 * makeVisible() is used exclusively to let simulate() show the result.
 *
 * The strategy has three phases:
 *  1) Spread every wheel apart so all n symbols are visible at once
 *     (a "no two wheels agree" baseline, with no ambiguity left).
 *  2) Discover the shared cyclic order between wheels: for wheels i and
 *     j, rotating i forward one step and j backward one step keeps all n
 *     symbols visible again exactly when j was one position ahead of i in
 *     the cycle - a fact this class can prove from the wheels' own
 *     colors without ever seeing which color is which.
 *  3) Once the order between all n wheels is known, rotate every wheel
 *     back onto the first wheel's symbol - one rotation per wheel.
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version September 19
 */
import java.util.ArrayList;

public class SlotMachineContest
{
    /**
     * Solves the maratón problem for a machine of n wheels/symbols: builds
     * its own invisible SlotMachine(n) and returns the sequence of
     * rotations that brings it to a jackpot (all wheels showing the same
     * symbol).
     *
     * @param n number of wheels (and symbols) of the machine to solve.
     * @return the sequence of actions taken, each row {wheel, steps}
     *         (both matching spin(wheel, steps)'s own 1-based numbering).
     */
    public int[][] solve(int n) {
        SlotMachine machine = new SlotMachine(n);
        ArrayList<int[]> actions = new ArrayList<int[]>();
        align(machine, n, actions);
        return actions.toArray(new int[0][]);
    }

    /**
     * Simulates, visibly, the resolution of the maratón problem for a
     * machine of n wheels/symbols: builds its own visible SlotMachine(n)
     * and runs the exact same strategy as solve(), so the animation shows
     * the machine actually reaching a jackpot.
     *
     * @param n number of wheels (and symbols) of the machine to simulate.
     */
    public void simulate(int n) {
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();
        align(machine, n, null);
    }

    /**
     * Runs the full three-phase strategy against the given machine.
     * @param actions if not null, every spin(wheel, steps) performed is
     *                recorded here as {wheel, steps}; solve() passes a
     *                list to build its return value, simulate() passes
     *                null since it does not need one.
     */
    private void align(SlotMachine machine, int n, ArrayList<int[]> actions) {
        if (n < 2) {
            return; // zero or one wheel is a jackpot already, nothing to do.
        }
        spread(machine, n, actions);
        int[] successorOf = discoverOrder(machine, n, actions);
        settle(machine, n, successorOf, actions);
    }

    /**
     * Phase 1: leaves every wheel, one at a time, at whichever of its n
     * positions makes distinctSymbols() as high as possible. After all n
     * wheels have been processed this way, all n wheels show different
     * symbols (a full spread, with no collisions left to resolve blindly).
     */
    private void spread(SlotMachine machine, int n, ArrayList<int[]> actions) {
        for (int wheel = 1; wheel <= n; wheel++) {
            int bestSteps = 0;
            int bestK = machine.distinctSymbols();
            int stepsScanned = 0;
            for (int step = 1; step <= n - 1; step++) {
                move(machine, wheel, 1, actions);
                stepsScanned = step;
                int k = machine.distinctSymbols();
                if (k > bestK) {
                    bestK = k;
                    bestSteps = step;
                }
                if (k == n) {
                    break; // already the best a wheel can do, stop scanning it.
                }
            }
            int back = ((bestSteps - stepsScanned) % n + n) % n;
            if (back > 0) {
                move(machine, wheel, back, actions);
            }
        }
    }

    /**
     * Phase 2: for every wheel i, finds the other wheel j whose position
     * is exactly one step ahead of i's in the shared cyclic order, by
     * rotating i forward one step and, for each candidate j, rotating j
     * backward one step and checking whether all n symbols are still
     * distinct. Every probing move is undone immediately after, so the
     * full-spread state from phase 1 is intact for the next wheel.
     *
     * @return successorOf[i] = the wheel that is one position ahead of
     *         wheel i (1-based wheel numbers, index 0 unused).
     */
    private int[] discoverOrder(SlotMachine machine, int n, ArrayList<int[]> actions) {
        int[] successorOf = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            move(machine, i, 1, actions);
            int found = -1;
            for (int j = 1; j <= n && found == -1; j++) {
                if (j == i) {
                    continue;
                }
                move(machine, j, n - 1, actions); // one step "backward"
                if (machine.distinctSymbols() == n) {
                    found = j;
                }
                move(machine, j, 1, actions); // undo j, back to phase-1 spread
            }
            move(machine, i, n - 1, actions); // undo i
            successorOf[i] = found;
        }
        return successorOf;
    }

    /**
     * Phase 3: walks the cycle discovered in phase 2 starting at wheel 1,
     * and rotates every other wheel back by exactly the number of steps
     * that separated it from wheel 1 in that cycle - one rotation per
     * wheel, n-1 rotations in total, landing every wheel on wheel 1's
     * symbol.
     */
    private void settle(SlotMachine machine, int n, int[] successorOf, ArrayList<int[]> actions) {
        int current = 1;
        for (int rank = 1; rank < n; rank++) {
            current = successorOf[current];
            int forward = ((n - rank) % n + n) % n;
            if (forward > 0) {
                move(machine, current, forward, actions);
            }
        }
    }

    private void move(SlotMachine machine, int wheel, int steps, ArrayList<int[]> actions) {
        machine.spin(wheel, steps);
        if (actions != null) {
            actions.add(new int[]{wheel, steps});
        }
    }
}
