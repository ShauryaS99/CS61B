package lab14;
import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;
    private int weirdState;

    public StrangeBitwiseGenerator(int period) {
        this.state = 0;
        this.period = period;
    }

    public double next() {
        state += 1;
        weirdState = state & (state >>> 3) % period;
        return normalize();
    }

    private double normalize() {
        double normalize = (2 * (double) weirdState / period) - 1;
        return normalize;
    }
}
