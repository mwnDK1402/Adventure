package model;

class Food extends Item {
    private final int healthPoints;

    public Food(String shortName, String longName, int healthPoints) {
        super(shortName, longName);
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
