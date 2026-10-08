public class EnemyAttackOutcome {
    private int enemyDamageDealt;
    private int playerHealth;
    private String enemyAttackVerb;
    private String enemyLongName;

    public EnemyAttackOutcome(int enemyDamageDealt, int playerHealth, String enemyAttackVerb, String enemyLongName) {
        this.enemyDamageDealt = enemyDamageDealt;
        this.playerHealth = playerHealth;
        this.enemyAttackVerb = enemyAttackVerb;
        this.enemyLongName = enemyLongName;
    }

    public void setEnemyAttackVerb(String enemyAttackVerb) {
        this.enemyAttackVerb = enemyAttackVerb;
    }

    public String getEnemyAttackVerb() {
        return enemyAttackVerb;
    }

    public void setEnemyDamageDealt(int enemyDamageDealt) {
        this.enemyDamageDealt = enemyDamageDealt;
    }

    public int getEnemyDamageDealt() {
        return enemyDamageDealt;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public String getEnemyLongName() {
        return enemyLongName;
    }
}
