public class AttackOutcome {
    private AttackResult result;
    private int damage;
    private String message;
    private String attackVerb;
    private int usesLeft;
    private int enemyHealth;

    public AttackOutcome(AttackResult result, int damage, String message, String attackVerb, int usesLeft, int enemyHealth) {
        this.result = result;
        this.damage = damage;
        this.message = message;
        this.attackVerb = attackVerb;
        this.usesLeft = usesLeft;
        this.enemyHealth = enemyHealth;
    }

    public AttackResult getResult() {
        return result;
    }

    public int getDamage() {
        return damage;
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
}
