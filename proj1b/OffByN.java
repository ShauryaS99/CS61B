public class OffByN implements CharacterComparator{

    private int expecteddifference;

    @Override
    public boolean equalChars(char x, char y) {
        int difference = x - y;
        if (java.lang.Math.abs(difference) == this.expecteddifference) {
            return true;
        }
        return false;
    }

    public OffByN(int N) {
        this.expecteddifference = N;
    }
}

