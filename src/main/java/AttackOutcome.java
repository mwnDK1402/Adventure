import java.util.OptionalInt;

public sealed interface AttackOutcome {
    record NoEnemy() implements AttackOutcome {}
    record NoWeapon() implements AttackOutcome {}
    record CannotUse(UnusableStatus status) implements AttackOutcome {}
    record Missed() implements AttackOutcome {}
    record Attacked(WeaponVerb verb, EnemyNoun noun, int damage, int enemyRemainingHealth, OptionalInt usesLeft) implements AttackOutcome {}
}
