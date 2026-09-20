
/**
 * Generalizes how SlotMachine paces a step-by-step animation (used by
 * spin(wheel, steps) to make each rotation step visible instead of
 * jumping straight to the final symbol).
 *
 * SlotMachine does not know or care HOW a pause is produced; it only
 * knows it can ask a StepPacer to pause(milliseconds). Today the only
 * implementation (ThreadSleepPacer) uses Thread.sleep, with nothing
 * Canvas-specific about it, but the point of the interface is that
 * SlotMachine's logic would not change even if that changed: a future
 * pacer could drive a different animation mechanism entirely, or a test
 * could inject one that does not really wait at all.
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version September 19
 */
public interface StepPacer
{
    /**
     * Pauses for approximately the given number of milliseconds.
     * @param milliseconds how long to pause.
     */
    void pause(int milliseconds);
}
