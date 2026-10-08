public class PlayerAttackOutcome {
    private AttackResult result;
    private int playerDamageDealt;
    private String message;
    private String playerAttackVerb;
    private int usesLeft;
    private int enemyHealth;
    private String enemyLongName;



    public PlayerAttackOutcome(AttackResult result, int playerDamageDealt, String message, String attackVerb, int usesLeft, int enemyHealth, String enemyLongName) {
        this.result = result;
        this.playerDamageDealt = playerDamageDealt;
        this.message = message;
        this.playerAttackVerb = attackVerb;
        this.usesLeft = usesLeft;
        this.enemyHealth = enemyHealth;
        this.enemyLongName = enemyLongName;
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

    public int getPlayerDamageDealt() {
        return playerDamageDealt;
    }

    public boolean isEnemyAlive() {
        return enemyHealth > 0;
    }
}
