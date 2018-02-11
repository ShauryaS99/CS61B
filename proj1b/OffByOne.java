public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int difference = x - y;
        if (java.lang.Math.abs(difference) == 1) {
            return true;
        }
        return false;
    }
}
