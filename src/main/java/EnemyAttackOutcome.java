public class EnemyAttackOutcome {
    private final int enemyDamageDealt;
    private final int playerHealth;
    private final String enemyAttackVerb;
    private final String enemyLongName;

    public EnemyAttackOutcome(int enemyDamageDealt, int playerHealth, String enemyAttackVerb, String enemyLongName) {
        this.enemyDamageDealt = enemyDamageDealt;
        this.playerHealth = playerHealth;
        this.enemyAttackVerb = enemyAttackVerb;
        this.enemyLongName = enemyLongName;
    }

    public String getEnemyAttackVerb() {
        return enemyAttackVerb;
    }

    public int getEnemyDamageDealt() {
        return enemyDamageDealt;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public String getEnemyLongName() {
        return enemyLongName;
    }

    public boolean isPlayerAlive() {
        return playerHealth > 0;
    }
}
