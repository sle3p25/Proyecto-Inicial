/**
 * Individual unit tests for SlotMachineContest (mini-ciclo 20).
 *
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineContestTest
{
    private SlotMachineContest contest;

    @Before
    public void setUp() {
        contest = new SlotMachineContest();
    }

    // solve(machine, n) runs the exact same algorithm as solve(n), but on a
    // machine we build and keep a reference to ourselves, so we can inspect
    // it afterward (isJackpot(), configuration()...) - something solve(n)
    // does not allow, since it builds and keeps its own SlotMachine
    // internally and never exposes it.

    // Simplest real case: build a 2-wheel machine, run the algorithm on
    // it, and check it actually ended up on a jackpot.
    @Test
    public void accordingPgRpShouldReachJackpotForTwoWheels() {
        SlotMachine machine = new SlotMachine(2);

        contest.solve(machine, 2);

        assertTrue(machine.isJackpot());
    }

    // Same idea but with 3 wheels, just to make sure it's not only
    // working for the even-number case.
    @Test
    public void accordingPgRpShouldReachJackpotForThreeWheels() {
        SlotMachine machine = new SlotMachine(3);

        contest.solve(machine, 3);

        assertTrue(machine.isJackpot());
    }

    // Push it further with a 10-wheel machine, so we're not only trusting
    // the tiny cases.
    @Test
    public void accordingPgRpShouldReachJackpotForATenWheelMachine() {
        SlotMachine machine = new SlotMachine(10);

        contest.solve(machine, 10);

        assertTrue(machine.isJackpot());
    }

    // With just 1 wheel, the machine is already a jackpot before doing
    // anything (nothing to compare it to). We save the symbol it's
    // showing, run solve() on it, and check that symbol didn't change.
    @Test
    public void accordingPgRpShouldDoNothingWhenMachineHasFewerThanTwoWheels() {
        SlotMachine oneWheel = new SlotMachine(1);
        String before = oneWheel.configuration()[0];

        contest.solve(oneWheel, 1);

        assertEquals(before, oneWheel.configuration()[0]);
        assertTrue(oneWheel.isJackpot());
    }

    // Now testing solve(n) itself: for 0 or 1 wheel there's nothing to
    // solve, so it should come back with an empty list of actions.
    @Test
    public void accordingPgRpSolveShouldReturnNoActionsForATrivialMachine() {
        assertEquals(0, contest.solve(0).length);
        assertEquals(0, contest.solve(1).length);
    }

    // Every action solve() hands back needs to be usable directly with
    // spin(wheel, steps): exactly 2 numbers, the wheel number has to be
    // one of the real wheels (1 to n), and steps has to be between 1 and
    // n-1 (never 0, never a full extra spin around the wheel).
    @Test
    public void accordingPgRpSolveShouldReturnOnlyActionsWithValidWheelAndStepBounds() {
        int n = 6;
        int[][] actions = contest.solve(n);

        assertTrue(actions.length > 0);
        for (int[] action : actions) {
            assertEquals(2, action.length);
            int wheel = action[0];
            int steps = action[1];
            assertTrue("wheel out of range: " + wheel, wheel >= 1 && wheel <= n);
            assertTrue("steps out of range: " + steps, steps >= 1 && steps <= n - 1);
        }
    }

    // Design requirement #4 says the machine has to stay invisible the
    // whole time it's solving. solve(machine, n) runs the exact same code
    // solve(n) runs, so we build our own machine, run it, and check
    // isVisible() stayed false the whole time (it's never set to true
    // anywhere in the solving code).
    @Test
    public void accordingPgRpSolveShouldKeepTheMachineInvisibleWhileSolving() {
        SlotMachine machine = new SlotMachine(4);

        contest.solve(machine, 4);

        assertFalse(machine.isVisible());
    }

    // Just a rough safety check: make sure solve() doesn't need a crazy
    // number of moves for a 15-wheel machine. The limit is way bigger
    // than it should ever need, so this only fails if something is
    // actually broken (like an infinite loop or a runaway algorithm),
    // not because of bad luck with the random symbols.
    @Test
    public void accordingPgRpSolveShouldNeverExceedALooseActionBudget() {
        int n = 15;
        int[][] actions = contest.solve(n);

        assertTrue(actions.length < 10 * n * n);
    }
}