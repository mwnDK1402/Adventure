package model;

public sealed interface EatOutcome {
    record NotFound() implements EatOutcome {}
    record NotFood() implements EatOutcome {}
    record Eaten(int healthChange) implements EatOutcome {}
}
