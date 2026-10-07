public class AttackOutcome {
    private AttackResult result;
    private int playerDamageDealt;
    private String message;
    private String attackVerb;
    private int usesLeft;
    private int enemyHealth;
    private String enemyLongName;
    private int enemyDamageDealt;
    private int playerHealth;

    public AttackOutcome(AttackResult result, int playerDamageDealt, String message, String attackVerb, int usesLeft, int enemyHealth, String enemyLongName, int enemyDamageDealt, int playerHealth) {
        this.result = result;
        this.playerDamageDealt = playerDamageDealt;
        this.message = message;
        this.attackVerb = attackVerb;
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
        return attackVerb;
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
}
