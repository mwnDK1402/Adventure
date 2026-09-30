public class EatOutcome {
    private final EatResult result;
    private final String foodName;
    private final int healthChange;

    public EatOutcome(EatResult result, String foodName, int healthChange) {
        this.result = result;
        this.foodName = foodName;
        this.healthChange = healthChange;
    }

    public EatResult getResult() {
        return result;
    }

    public int getHealthChange() {
        return healthChange;
    }
}
