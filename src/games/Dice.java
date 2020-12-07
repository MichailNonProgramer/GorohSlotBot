package games;

public class Dice {
    private static final Integer maxValue = 6;
    private static final Integer minValue = 1;

    public static int generateValue() {
        return minValue + (int) (Math.random() * maxValue);
    }
}