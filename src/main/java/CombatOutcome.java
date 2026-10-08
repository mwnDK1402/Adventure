public class CombatOutcome {
    private final EnemyAttackOutcome enemyAttackOutcome;
    private final PlayerAttackOutcome playerAttackOutcome;

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
