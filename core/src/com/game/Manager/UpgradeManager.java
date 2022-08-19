package com.game.Manager;

public enum UpgradeManager {
    FORK,
    SCYTHE,
    DAGGER,
    SWORD,
    BATTLEAXE,
    MACE,
    BOW,
    CROSSBOW,
    CANNON,
    CANNONBALL,
    BETTERCANNON,
    SCEPTRE,
    BOOK,
    GEAR,
    SONAR,
    HEALTH,
    BETTERHEALTH,
    BETTERBETTERHEALTH,
    REGENERATION,
    SHIELD,
    GOLD,
    BETTERGOLD,
    DIAMONDS,
    BETTERDIAMONDS,
    DISCOUNT10,
    DISCOUNT20,
    DISCOUNT30,
    UPGRADE,
    HAMMER,
    BETTERUPGRADE,
    LUCK,
    LOCKED,
    BOUGHT;

    private static UpgradeManager fork = FORK;
    private static UpgradeManager scythe = LOCKED;
    private static UpgradeManager dagger = LOCKED;
    private static UpgradeManager sword = LOCKED;
    private static UpgradeManager battleAxe = LOCKED;
    private static UpgradeManager mace = LOCKED;
    private static UpgradeManager bow = LOCKED;
    private static UpgradeManager crossbow = LOCKED;
    private static UpgradeManager sceptre = LOCKED;
    private static UpgradeManager book = LOCKED;
    private static UpgradeManager cannon = LOCKED;
    private static UpgradeManager cannonBall = LOCKED;
    private static UpgradeManager betterCannon = LOCKED;
    private static UpgradeManager gear = GEAR;
    private static UpgradeManager sonar = LOCKED;
    private static UpgradeManager health = HEALTH;
    private static UpgradeManager betterHealth = LOCKED;
    private static UpgradeManager betterBetterHealth = LOCKED;
    private static UpgradeManager regeneration = LOCKED;
    private static UpgradeManager shield = LOCKED;
    private static UpgradeManager gold = GOLD;
    private static UpgradeManager betterGold = LOCKED;
    private static UpgradeManager diamonds = LOCKED;
    private static UpgradeManager betterDiamonds = LOCKED;
    private static UpgradeManager discount10 = LOCKED;
    private static UpgradeManager discount20 = LOCKED;
    private static UpgradeManager discount30 = LOCKED;
    private static UpgradeManager upgrade = LOCKED;
    private static UpgradeManager hammer = LOCKED;
    private static UpgradeManager betterUpgrade = LOCKED;
    private static UpgradeManager luck = LOCKED;


    public static UpgradeManager getFork() {
        return fork;
    }

    public static void setFork(UpgradeManager upgradeName){
        UpgradeManager.fork = upgradeName;
    }

    public static UpgradeManager getScythe() {
        return scythe;
    }

    public static void setScythe(UpgradeManager upgradeName) {
        UpgradeManager.scythe = upgradeName;
    }

    public static UpgradeManager getDagger() {
        return dagger;
    }

    public static void setDagger(UpgradeManager upgradeName) {
        UpgradeManager.dagger = upgradeName;
    }

    public static UpgradeManager getSword() {
        return sword;
    }

    public static void setSword(UpgradeManager upgradeName) {
        UpgradeManager.sword = upgradeName;
    }

    public static UpgradeManager getBattleAxe() {
        return battleAxe;
    }

    public static void setBattleAxe(UpgradeManager upgradeName) {
        UpgradeManager.battleAxe = upgradeName;
    }

    public static UpgradeManager getMace() {
        return mace;
    }

    public static void setMace(UpgradeManager upgradeName) {
        UpgradeManager.mace = upgradeName;
    }

    public static UpgradeManager getBow() {
        return bow;
    }

    public static void setBow(UpgradeManager upgradeName) {
        UpgradeManager.bow = upgradeName;
    }

    public static UpgradeManager getCrossbow() {
        return crossbow;
    }

    public static void setCrossbow(UpgradeManager upgradeName) {
        UpgradeManager.crossbow = upgradeName;
    }

    public static UpgradeManager getSceptre() {
        return sceptre;
    }

    public static void setSceptre(UpgradeManager upgradeName) {
        UpgradeManager.sceptre = upgradeName;
    }

    public static UpgradeManager getBook() {
        return book;
    }

    public static void setBook(UpgradeManager upgradeName) {
        UpgradeManager.book = upgradeName;
    }

    public static UpgradeManager getCannon() {
        return cannon;
    }

    public static void setCannon(UpgradeManager upgradeName) {
        UpgradeManager.cannon = upgradeName;
    }

    public static UpgradeManager getCannonBall() {
        return cannonBall;
    }

    public static void setCannonBall(UpgradeManager upgradeName) {
        UpgradeManager.cannonBall = upgradeName;
    }

    public static UpgradeManager getBetterCannon() {
        return betterCannon;
    }

    public static void setBetterCannon(UpgradeManager upgradeName) {
        UpgradeManager.betterCannon = upgradeName;
    }

    public static UpgradeManager getGear() {
        return gear;
    }

    public static void setGear(UpgradeManager upgradeName) {
        UpgradeManager.gear = upgradeName;
    }

    public static UpgradeManager getSonar() {
        return sonar;
    }

    public static void setSonar(UpgradeManager upgradeName) {
        UpgradeManager.sonar = upgradeName;
    }

    public static UpgradeManager getHealth() {
        return health;
    }

    public static void setHealth(UpgradeManager upgradeName) {
        UpgradeManager.health = upgradeName;
    }

    public static UpgradeManager getBetterHealth() {
        return betterHealth;
    }

    public static void setBetterHealth(UpgradeManager upgradeName) {
        UpgradeManager.betterHealth = upgradeName;
    }

    public static UpgradeManager getBetterBetterHealth() {
        return betterBetterHealth;
    }

    public static void setBetterBetterHealth(UpgradeManager upgradeName) {
        UpgradeManager.betterBetterHealth = upgradeName;
    }

    public static UpgradeManager getRegeneration() {
        return regeneration;
    }

    public static void setRegeneration(UpgradeManager upgradeName) {
        UpgradeManager.regeneration = upgradeName;
    }

    public static UpgradeManager getShield() {
        return shield;
    }

    public static void setShield(UpgradeManager upgradeName) {
        UpgradeManager.shield = upgradeName;
    }

    public static UpgradeManager getGold() {
        return gold;
    }

    public static void setGold(UpgradeManager upgradeName) {
        UpgradeManager.gold = upgradeName;
    }

    public static UpgradeManager getBetterGold() {
        return betterGold;
    }

    public static void setBetterGold(UpgradeManager upgradeName) {
        UpgradeManager.betterGold = upgradeName;
    }

    public static UpgradeManager getDiamonds() {
        return diamonds;
    }

    public static void setDiamonds(UpgradeManager upgradeName) {
        UpgradeManager.diamonds = upgradeName;
    }

    public static UpgradeManager getBetterDiamonds() {
        return betterDiamonds;
    }

    public static void setBetterDiamonds(UpgradeManager upgradeName) {
        UpgradeManager.betterDiamonds = upgradeName;
    }

    public static UpgradeManager getDiscount10() {
        return discount10;
    }

    public static void setDiscount10(UpgradeManager upgradeName) {
        UpgradeManager.discount10 = upgradeName;
    }

    public static UpgradeManager getDiscount20() {
        return discount20;
    }

    public static void setDiscount20(UpgradeManager upgradeName) {
        UpgradeManager.discount20 = upgradeName;
    }

    public static UpgradeManager getDiscount30() {
        return discount30;
    }

    public static void setDiscount30(UpgradeManager upgradeName) {
        UpgradeManager.discount30 = upgradeName;
    }

    public static UpgradeManager getUpgrade() {
        return upgrade;
    }

    public static void setUpgrade(UpgradeManager upgradeName) {
        UpgradeManager.upgrade = upgradeName;
    }

    public static UpgradeManager getHammer() {
        return hammer;
    }

    public static void setHammer(UpgradeManager upgradeName) {
        UpgradeManager.hammer = upgradeName;
    }

    public static UpgradeManager getBetterUpgrade() {
        return betterUpgrade;
    }

    public static void setBetterUpgrade(UpgradeManager upgradeName) {
        UpgradeManager.betterUpgrade = upgradeName;
    }

    public static UpgradeManager getLuck() {
        return luck;
    }

    public static void setLuck(UpgradeManager upgradeName) {
        UpgradeManager.luck = upgradeName;
    }
}
