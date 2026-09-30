public class EatOutcome
{
    private EatResult result;
    private String foodName;
    private int healthChange;

    public EatOutcome(EatResult result, String foodName, int healthChange)
    {
        this.result = result;
        this.foodName = foodName;
        this.healthChange = healthChange;
    }

    public EatResult getResult()
    {
        return result;
    }

    public int getHealthChange()
    {
        return healthChange;
    }


}
