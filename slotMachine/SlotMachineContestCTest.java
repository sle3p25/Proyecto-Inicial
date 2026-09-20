/**
 *
 * @author Juan Sebastián Pulido Gómez - Julian Rodríguez Pérez
 * @version
 */
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SlotMachineContestCTest
{
    private SlotMachineContest contest;

    @Before
    public void setUp() {
        contest = new SlotMachineContest();
    }

    // With 0 or 1 wheel there's nothing to solve, so solve() should come
    // back with zero actions in both cases.
    @Test
    public void accordingPgRpSolveShouldReturnNoActionsWhenThereIsAtMostOneWheel() {
        assertEquals(0, contest.solve(0).length);
        assertEquals(0, contest.solve(1).length);
    }

    // Every action solve() returns should be safe to plug straight into
    // spin(wheel, steps): a real wheel number and a step count between
    // 1 and n-1.
    @Test
    public void accordingPgRpSolveShouldReturnActionsWithWheelAndStepsInValidRange() {
        int n = 7;
        int[][] actions = contest.solve(n);

        for (int[] action : actions) {
            int wheel = action[0];
            int steps = action[1];
            assertTrue(wheel >= 1 && wheel <= n);
            assertTrue(steps >= 1 && steps <= n - 1);
        }
    }
}