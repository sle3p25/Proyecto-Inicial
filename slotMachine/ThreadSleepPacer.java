
/**
 * Default StepPacer: pauses the current thread using Thread.sleep. This
 * is the implementation SlotMachine uses unless a different StepPacer is
 * injected through its constructor (see SlotMachine(StepPacer)).
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version September 19
 */
public class ThreadSleepPacer implements StepPacer
{
    public void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // ignore: a skipped pause does not affect correctness.
        }
    }
}
