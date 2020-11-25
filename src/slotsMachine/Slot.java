package slotsMachine;

public class Slot {
    public String getCode() {
        return code;
    }

    public double getMultiplier() {
        return multiplier;
    }

    private final String code;
    private final double multiplier;

    public Slot(String code, double multiplier) {
        this.code = code;
        this.multiplier = multiplier;
    }
}