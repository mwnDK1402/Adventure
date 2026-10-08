public class CombatOutcome {
    public EnemyAttackOutcome enemyAttackOutcome;
    public PlayerAttackOutcome playerAttackOutcome;

    public CombatOutcome(PlayerAttackOutcome playerAttackOutcome, EnemyAttackOutcome enemyAttackOutcome) {
        this.playerAttackOutcome = playerAttackOutcome;
        this.enemyAttackOutcome = enemyAttackOutcome;
    }

    public EnemyAttackOutcome getEnemy() {
        return enemyAttackOutcome;
    }

    public PlayerAttackOutcome getPlayer() {
        return playerAttackOutcome;
    }
}
