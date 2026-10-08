public class EatOutcome {
    private final EatResult result;
    private final int healthChange;
    private final int healthPostFood;
    private final EnemyAttackOutcome enemyOutcome;

    public EatOutcome(EatResult result, int healthChange, EnemyAttackOutcome enemyOutcome, int healthPostFood) {
        this.result = result;
        this.healthChange = healthChange;
        this.enemyOutcome = enemyOutcome;
        this.healthPostFood = healthPostFood;
    }

    public EatResult getResult() {
        return result;
    }

    public int getHealthChange() {
        return healthChange;
    }

    public EnemyAttackOutcome getEnemyOutcome() {
        return enemyOutcome;
    }

    public int getHealthPostFood() {
        return healthPostFood;
    }

    public boolean isPlayerAlive() {
        return !(healthPostFood <= 0 || (enemyOutcome != null && !enemyOutcome.isPlayerAlive()));
    }
}
