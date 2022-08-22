package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.game.Entity.Upgrades;

public class UpgradeManager {
    private Image fork, scythe, dagger, sword, battleAxe, mace, sceptre, book, bow, crossbow, cannon, betterCannon, cannonBall;
    private Image sonar, gear;
    private Image health, betterHealth, betterBetterHealth, shield, regeneration;
    private Image gold, diamonds, betterGold, betterDiamonds, upgrade, betterUpgrade, hammer, discount10, discount20, discount30;
    private Image luck;
    private Upgrades uFork, uScythe, uDagger, uSword, uBattleAxe, uMace, uBow, uCrossBow, uCannon, uCannonBall, uBetterCannon, uSceptre, uBook, uGear, uSonar, uHealth, uBetterHealth, uBetterBetterHealth, uRegeneration, uShield, uGold, uBetterGold, uDiamonds, uBetterDiamonds, uDiscount10, uDiscount20, uDiscount30, uUpgrade, uHammer, uBetterUpgrade, uLuck;
    private Skin images_upgrades;
    private Table table_upgrade;
    private Dialog upgradeDialog;
    private LanguageManager languageManager;
    public UpgradeManager(Skin images_upgrades, LanguageManager languageManager){
        this.images_upgrades = images_upgrades;
        this.languageManager = languageManager;
        initImages();
        initUpgrades();
    }
    public Upgrades returnUfork(){
        return uFork;
    }
    public Table returnUpgradeTable(){
        return  table_upgrade;
    }
    public void setUpgradeTable(Table table_upgrade){
        this.table_upgrade = table_upgrade;
        table_upgrade.setBounds(0,0,Gdx.graphics.getWidth()/10*8, Gdx.graphics.getHeight()/10*7);
        table_upgrade.add(fork);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(scythe);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(dagger);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(sword);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(battleAxe);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(mace);

        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(bow);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(crossbow);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(sceptre);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(book);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(cannon);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(cannonBall);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(betterCannon);

        table_upgrade.row().padTop(10);
        table_upgrade.add(gear);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(sonar);

        table_upgrade.row().padTop(10);
        table_upgrade.add(health);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(betterHealth);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(regeneration);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(shield);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(betterBetterHealth);
        table_upgrade.row();

        table_upgrade.row().padTop(10);
        table_upgrade.add(gold);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(betterGold);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(diamonds);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(betterDiamonds);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(discount10);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(discount20);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(discount30);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(upgrade);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(hammer);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect")).width(32);
        table_upgrade.add(betterUpgrade);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty")).height(32);
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical")).height(32);
        table_upgrade.row();

        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
        table_upgrade.add(luck);
        table_upgrade.padBottom(16);
    }
    public Dialog returnUpgradeDialog(){
        return upgradeDialog;
    }
    public void setUpgradeDialog(Dialog upgradeDialog, Table table_upgrade, Label.LabelStyle labelStyle){
        this.upgradeDialog = upgradeDialog;
        upgradeDialog.setBounds(0,0, Gdx.graphics.getWidth()/10*8,Gdx.graphics.getHeight()/10*8);
        upgradeDialog.text(languageManager.getValue(languageManager.getLanguage(), "upgrade_dialog_field_text"), labelStyle);
        upgradeDialog.add(table_upgrade);
        upgradeDialog.row();
    }

    private void initImages(){
        fork = new Image(images_upgrades, "upgradeIcons_attackFork");
        scythe = new Image(images_upgrades, "upgradeIcons_lock");
        dagger = new Image(images_upgrades, "upgradeIcons_lock");
        sword = new Image(images_upgrades, "upgradeIcons_lock");
        battleAxe = new Image(images_upgrades, "upgradeIcons_lock");
        mace = new Image(images_upgrades, "upgradeIcons_lock");
        bow = new Image(images_upgrades, "upgradeIcons_lock");
        crossbow = new Image(images_upgrades, "upgradeIcons_lock");
        cannon = new Image(images_upgrades, "upgradeIcons_lock");
        betterCannon = new Image(images_upgrades, "upgradeIcons_lock");
        cannonBall = new Image(images_upgrades, "upgradeIcons_lock");
        sceptre = new Image(images_upgrades, "upgradeIcons_lock");
        book = new Image(images_upgrades, "upgradeIcons_lock");
        gear = new Image(images_upgrades, "upgradeIcons_attackGear");
        sonar = new Image(images_upgrades, "upgradeIcons_lock");
        health = new Image(images_upgrades, "upgradeIcons_healthHealth");
        betterHealth = new Image(images_upgrades, "upgradeIcons_lock");
        betterBetterHealth = new Image(images_upgrades, "upgradeIcons_lock");
        regeneration = new Image(images_upgrades, "upgradeIcons_lock");
        shield = new Image(images_upgrades, "upgradeIcons_lock");
        gold = new Image(images_upgrades, "upgradeIcons_incomeGold");
        betterGold = new Image(images_upgrades, "upgradeIcons_lock");
        diamonds = new Image(images_upgrades, "upgradeIcons_lock");
        betterDiamonds = new Image(images_upgrades, "upgradeIcons_lock");
        discount10 = new Image(images_upgrades, "upgradeIcons_lock");
        discount20 =  new Image(images_upgrades, "upgradeIcons_lock");
        discount30 =  new Image(images_upgrades, "upgradeIcons_lock");
        upgrade = new Image(images_upgrades, "upgradeIcons_lock");
        hammer = new Image(images_upgrades, "upgradeIcons_lock");
        betterUpgrade = new Image(images_upgrades, "upgradeIcons_lock");
        luck = new Image(images_upgrades, "upgradeIcons_lock");

    }
    private void initUpgrades(){
        uFork = new Upgrades("fork", 0, 3, fork);
        uScythe = new Upgrades("scythe", -1, 1, scythe);
        uDagger = new Upgrades("dagger", -1, 3, dagger);
        uSword = new Upgrades("sword", -1, 3, sword);
        uBattleAxe = new Upgrades("battleaxe", -1, 1, battleAxe);
        uMace = new Upgrades("mace", -1, 3, mace);
        uBow = new Upgrades("bow", -1, 3, bow);
        uCrossBow = new Upgrades("crossbow", -1, 3, crossbow);
        uCannon = new Upgrades("cannon", -1, 3, cannon);
        uBetterCannon = new Upgrades("bettercannon", -1,3, betterCannon);
        uCannonBall = new Upgrades("cannonball", -1, 3, cannonBall);
        uSceptre = new Upgrades("sceptre", -1, 3, sceptre);
        uBook = new Upgrades("book", -1, 3, book);
        uGear = new Upgrades("gear", 0, 3, gear);
        uSonar = new Upgrades("sonar", -1, 3, sonar);
        uHealth = new Upgrades("health", 0, 3, health);
        uBetterHealth = new Upgrades("betterHealth", -1, 3, betterHealth);
        uBetterBetterHealth = new Upgrades("betterBetterHealth", -1, 3, betterBetterHealth);
        uRegeneration = new Upgrades("regeneration", -1, 3, regeneration);
        uShield = new Upgrades("shield", -1, 3, shield);
        uGold = new Upgrades("gold", 0, 3, gold);
        uBetterGold = new Upgrades("betterGold", -1, 1, betterGold);
        uDiamonds = new Upgrades("diamonds", -1, 3, diamonds);
        uBetterDiamonds = new Upgrades("betterDiamonds", -1, 3, betterDiamonds);
        uDiscount10 = new Upgrades("discount10", -1, 1, discount10);
        uDiscount20 = new Upgrades("discount20", -1, 1, discount20);
        uDiscount30 = new Upgrades("discount30", -1, 1, discount30);
        uUpgrade = new Upgrades("upgrade", -1, 1, upgrade);
        uHammer = new Upgrades("hammer", -1, 3, hammer);
        uBetterUpgrade = new Upgrades("betterUpgrade", -1, 1, betterUpgrade);
        uLuck = new Upgrades("luck", -1, 3, luck);
    }

    public Image getFork() {
        return fork;
    }

    public Image getScythe() {
        return scythe;
    }

    public Image getDagger() {
        return dagger;
    }

    public Image getSword() {
        return sword;
    }

    public Image getBattleAxe() {
        return battleAxe;
    }

    public Image getMace() {
        return mace;
    }

    public Image getSceptre() {
        return sceptre;
    }

    public Image getBook() {
        return book;
    }

    public Image getBow() {
        return bow;
    }

    public Image getCrossbow() {
        return crossbow;
    }

    public Image getCannon() {
        return cannon;
    }

    public Image getBetterCannon() {
        return betterCannon;
    }

    public Image getCannonBall() {
        return cannonBall;
    }

    public Image getSonar() {
        return sonar;
    }

    public Image getGear() {
        return gear;
    }

    public Image getHealth() {
        return health;
    }

    public Image getBetterHealth() {
        return betterHealth;
    }

    public Image getBetterBetterHealth() {
        return betterBetterHealth;
    }

    public Image getShield() {
        return shield;
    }

    public Image getRegeneration() {
        return regeneration;
    }

    public Image getGold() {
        return gold;
    }

    public Image getDiamonds() {
        return diamonds;
    }

    public Image getBetterGold() {
        return betterGold;
    }

    public Image getBetterDiamonds() {
        return betterDiamonds;
    }

    public Image getUpgrade() {
        return upgrade;
    }

    public Image getBetterUpgrade() {
        return betterUpgrade;
    }

    public Image getHammer() {
        return hammer;
    }

    public Image getDiscount10() {
        return discount10;
    }

    public Image getDiscount20() {
        return discount20;
    }

    public Image getDiscount30() {
        return discount30;
    }

    public Image getLuck() {
        return luck;
    }

    public Upgrades getuFork() {
        return uFork;
    }

    public Upgrades getuScythe() {
        return uScythe;
    }

    public Upgrades getuDagger() {
        return uDagger;
    }

    public Upgrades getuSword() {
        return uSword;
    }

    public Upgrades getuBattleAxe() {
        return uBattleAxe;
    }

    public Upgrades getuMace() {
        return uMace;
    }

    public Upgrades getuBow() {
        return uBow;
    }

    public Upgrades getuCrossBow() {
        return uCrossBow;
    }

    public Upgrades getuCannon() {
        return uCannon;
    }

    public Upgrades getuCannonBall() {
        return uCannonBall;
    }

    public Upgrades getuBetterCannon() {
        return uBetterCannon;
    }

    public Upgrades getuSceptre() {
        return uSceptre;
    }

    public Upgrades getuBook() {
        return uBook;
    }

    public Upgrades getuGear() {
        return uGear;
    }

    public Upgrades getuSonar() {
        return uSonar;
    }

    public Upgrades getuHealth() {
        return uHealth;
    }

    public Upgrades getuBetterHealth() {
        return uBetterHealth;
    }

    public Upgrades getuBetterBetterHealth() {
        return uBetterBetterHealth;
    }

    public Upgrades getuRegeneration() {
        return uRegeneration;
    }

    public Upgrades getuShield() {
        return uShield;
    }

    public Upgrades getuGold() {
        return uGold;
    }

    public Upgrades getuBetterGold() {
        return uBetterGold;
    }

    public Upgrades getuDiamonds() {
        return uDiamonds;
    }

    public Upgrades getuBetterDiamonds() {
        return uBetterDiamonds;
    }

    public Upgrades getuDiscount10() {
        return uDiscount10;
    }

    public Upgrades getuDiscount20() {
        return uDiscount20;
    }

    public Upgrades getuDiscount30() {
        return uDiscount30;
    }

    public Upgrades getuUpgrade() {
        return uUpgrade;
    }

    public Upgrades getuHammer() {
        return uHammer;
    }

    public Upgrades getuBetterUpgrade() {
        return uBetterUpgrade;
    }

    public Upgrades getuLuck() {
        return uLuck;
    }
}
