package lab14;
import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private double period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(double period, double factor) {
        this.period = period;
        this.factor = factor;
        state = 0;
    }

    public double next() {
        state = (state + 1);
        if (state % this.period != 0) {
            return normalize(state);
        } else {
            this.period *= this.factor;
            this.period = (int) this.period;
            state = 0;
        }
        return normalize(state);
    }

    private double normalize(int v) {
        double normalize = ((v % this.period) * 2 / (this.period - 1)) - 1;
        return normalize;
    }
}
