public class AttackOutcome {
    private AttackResult result;
    private int damage;
    private String message;
    private String attackVerb;

    public AttackOutcome(AttackResult result, int damage, String message, String attackVerb){
        this.result = result;
        this.damage = damage;
        this.message = message;
        this.attackVerb = attackVerb;
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
}
