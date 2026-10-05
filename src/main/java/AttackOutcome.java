public class AttackOutcome {
    private AttackResult result;
    private int damage;
    private String message;

    public AttackOutcome(AttackResult result, int damage, String message){
        this.result = result;
        this.damage = damage;
        this.message = message;
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
}
