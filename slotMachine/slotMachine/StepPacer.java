
/**
 * Generalizes how SlotMachine paces a step-by-step animation (used by spin(wheel,steps) to 
 * make each rotation step visible instead of jumping straight to the final symbol).
 *
 * @author Juan Pulido - Julian Rodriguez
 * @version September 19
 */

public interface StepPacer
{
    /**
     * Pauses for approximately the given number of miliseconds
     *
     * @param  milliseconds how long to pause.
     */
    void pause(int milliseconds);
}