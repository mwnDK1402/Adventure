public class AttackOutcome {
    private AttackResult result;
    private int damage;
    private String message;
    private String attackVerb;
    private int usesLeft;

    public AttackOutcome(AttackResult result, int damage, String message, String attackVerb, int usesLeft){
        this.result = result;
        this.damage = damage;
        this.message = message;
        this.attackVerb = attackVerb;
        this.usesLeft = usesLeft;
    }

    public AttackResult getResult(){
        return result;
    }

    public int getDamage(){
        return damage;
    }

    public String getMessage(){
        return message;
    }

    public String getAttackVerb(){
        return attackVerb;
    }

    public int getUsesLeft(){
        return usesLeft;
    }
}
