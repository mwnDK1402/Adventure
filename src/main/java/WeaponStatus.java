public enum WeaponStatus {
    Usable,
    OutOfAmmo,
    OutOfCharges;

    public UnusableStatus toUnusable() {
        return switch (this) {
            case Usable -> throw new IllegalStateException("Cannot convert Usable to Unusable");
            case OutOfAmmo -> UnusableStatus.OutOfAmmo;
            case OutOfCharges -> UnusableStatus.OutOfCharges;
        };
    }
}
