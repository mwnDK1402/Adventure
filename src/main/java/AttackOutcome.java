public class AttackOutcome {
    private AttackResult result;
    private int playerDamageDealt;
    private String message;
    private String playerAttackVerb;
    private int usesLeft;
    private int enemyHealth;
    private String enemyLongName;
    private int enemyDamageDealt;
    private int playerHealth;
    private String enemyAttackVerb;


    public AttackOutcome(AttackResult result, int playerDamageDealt, String message, String attackVerb, int usesLeft, int enemyHealth, String enemyLongName, int playerHealth) {
        this.result = result;
        this.playerDamageDealt = playerDamageDealt;
        this.message = message;
        this.playerAttackVerb = attackVerb;
        this.usesLeft = usesLeft;
        this.enemyHealth = enemyHealth;
        this.enemyLongName = enemyLongName;
        this.playerHealth = playerHealth;
    }

    public AttackResult getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getAttackVerb() {
        return playerAttackVerb;
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    public int getEnemyHealthOutcome() {
        return enemyHealth;
    }

    public String getEnemyLongName() {
        return enemyLongName;
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

    public int getPlayerDamageDealt() {
        return playerDamageDealt;
    }
}
