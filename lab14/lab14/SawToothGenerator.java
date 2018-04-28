package lab14;
import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        this.period = period;
        state = 0;
    }

    public double next() {
        state = (state + 1);
        state = state % period;
        return normalize();
    }

    private double normalize() {
        double normalize = (2 * (double) state / period) - 1;
        return normalize;
    }
}
