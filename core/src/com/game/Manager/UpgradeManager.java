package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.Entity.Base;
import com.game.Entity.Upgrade;
import org.json.JSONObject;

public class UpgradeManager {
    private Image fork, scythe, dagger, sword, battleAxe, mace, sceptre, book, bow, crossbow, cannon, betterCannon, cannonBall;
    private Image sonar, gear;
    private Image health, betterHealth, betterBetterHealth, shield, regeneration;
    private Image gold, diamonds, betterGold, betterDiamonds, upgrade, betterUpgrade, hammer, discount10, discount20, discount30;
    private Image luck;
    private Upgrade uFork, uScythe, uDagger, uSword, uBattleAxe, uMace, uBow, uCrossBow, uCannon, uCannonBall, uBetterCannon, uSceptre, uBook, uGear, uSonar, uHealth, uBetterHealth, uBetterBetterHealth, uRegeneration, uShield, uGold, uBetterGold, uDiamonds, uBetterDiamonds, uDiscount10, uDiscount20, uDiscount30, uUpgrade, uHammer, uBetterUpgrade, uLuck;
    private Skin images_upgrades;
    private Table table_upgrade;
    private Dialog upgradeDialog;
    private LanguageManager languageManager;

    private TextTooltip.TextTooltipStyle textTooltipStyle;
    private TextTooltip tooltip;

    private Base base;

    private JSONObject upgrades;

    public UpgradeManager(LanguageManager languageManager, BitmapFont font, Base base, JSONObject upgrades){
        this.images_upgrades = new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack"));;
        this.languageManager = languageManager;
        this.base = base;
        this.upgrades = upgrades;

        Drawable tooltipBackground = new TextureRegionDrawable(new TextureRegion(new Texture(new FileHandle("assets/dialog/settings_dialog.png"))));
        textTooltipStyle = new TextTooltip.TextTooltipStyle();
        textTooltipStyle.label = new Label.LabelStyle(font, Color.WHITE);
        textTooltipStyle.background = tooltipBackground;
        textTooltipStyle.wrapWidth = 400; //nie może być 100.0f

        initImages();
        initUpgrades();

        createUpgradeTable();
        initListeners();



    }

    public Table returnUpgradeTable(){
        return  table_upgrade;
    }

    public void createUpgradeTable(){
        this.table_upgrade = new Table();
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
        uFork = new Upgrade("fork", 0, 3, fork, upgrades.getJSONArray("fork"));
        uScythe = new Upgrade("scythe", -1, 1, scythe);
        uDagger = new Upgrade("dagger", -1, 3, dagger);
        uSword = new Upgrade("sword", -1, 3, sword);
        uBattleAxe = new Upgrade("battleaxe", -1, 1, battleAxe);
        uMace = new Upgrade("mace", -1, 3, mace);
        uBow = new Upgrade("bow", -1, 3, bow);
        uCrossBow = new Upgrade("crossbow", -1, 3, crossbow);
        uCannon = new Upgrade("cannon", -1, 3, cannon);
        uBetterCannon = new Upgrade("betterCannon", -1,3, betterCannon);
        uCannonBall = new Upgrade("cannonball", -1, 3, cannonBall);
        uSceptre = new Upgrade("sceptre", -1, 3, sceptre);
        uBook = new Upgrade("book", -1, 3, book);
        uGear = new Upgrade("gear", 0, 3, gear);
        uSonar = new Upgrade("sonar", -1, 3, sonar);
        uHealth = new Upgrade("health", 0, 3, health);
        uBetterHealth = new Upgrade("betterHealth", -1, 3, betterHealth);
        uBetterBetterHealth = new Upgrade("betterBetterHealth", -1, 3, betterBetterHealth);
        uRegeneration = new Upgrade("regeneration", -1, 3, regeneration);
        uShield = new Upgrade("shield", -1, 3, shield);
        uGold = new Upgrade("gold", 0, 3, gold);
        uBetterGold = new Upgrade("betterGold", -1, 1, betterGold);
        uDiamonds = new Upgrade("diamonds", -1, 3, diamonds);
        uBetterDiamonds = new Upgrade("betterDiamonds", -1, 3, betterDiamonds);
        uDiscount10 = new Upgrade("discount10", -1, 1, discount10);
        uDiscount20 = new Upgrade("discount20", -1, 1, discount20);
        uDiscount30 = new Upgrade("discount30", -1, 1, discount30);
        uUpgrade = new Upgrade("upgrade", -1, 1, upgrade);
        uHammer = new Upgrade("hammer", -1, 3, hammer);
        uBetterUpgrade = new Upgrade("betterUpgrade", -1, 1, betterUpgrade);
        uLuck = new Upgrade("luck", -1, 3, luck);
    }

    public void initListeners()
    {
        fork.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (!uFork.isMaxLevel())
                {
                    if (base.getDiamonds()>=uFork.getCostToUpgrade())
                    {
                        base.setPassiveUpgrade(uFork.getUpgrade());


                        uFork.levelUp();

                        if(uFork.getLevel() == 1){
                            uScythe.unlock(images_upgrades, "upgradeIcons_attackScythe");
                            //uScythe.setDamageMultiplayer(uScythe.getLevel());
                            scythe.setDrawable(images_upgrades, "upgradeIcons_attackScythe");
                        }
                    }
                }

            }
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                Label information;
                information = uFork.returnInformation(textTooltipStyle.label);
                tooltip = new TextTooltip("", textTooltipStyle);
                tooltip.setActor(information);
                tooltip.setInstant(true);
                tooltip.enter(event, -72, y, pointer, fromActor);
            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                tooltip.exit(event, x, y, pointer, fromActor);
            }
        });


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

    public Upgrade getuFork() {
        return uFork;
    }

    public Upgrade getuScythe() {
        return uScythe;
    }

    public Upgrade getuDagger() {
        return uDagger;
    }

    public Upgrade getuSword() {
        return uSword;
    }

    public Upgrade getuBattleAxe() {
        return uBattleAxe;
    }

    public Upgrade getuMace() {
        return uMace;
    }

    public Upgrade getuBow() {
        return uBow;
    }

    public Upgrade getuCrossBow() {
        return uCrossBow;
    }

    public Upgrade getuCannon() {
        return uCannon;
    }

    public Upgrade getuCannonBall() {
        return uCannonBall;
    }

    public Upgrade getuBetterCannon() {
        return uBetterCannon;
    }

    public Upgrade getuSceptre() {
        return uSceptre;
    }

    public Upgrade getuBook() {
        return uBook;
    }

    public Upgrade getuGear() {
        return uGear;
    }

    public Upgrade getuSonar() {
        return uSonar;
    }

    public Upgrade getuHealth() {
        return uHealth;
    }

    public Upgrade getuBetterHealth() {
        return uBetterHealth;
    }

    public Upgrade getuBetterBetterHealth() {
        return uBetterBetterHealth;
    }

    public Upgrade getuRegeneration() {
        return uRegeneration;
    }

    public Upgrade getuShield() {
        return uShield;
    }

    public Upgrade getuGold() {
        return uGold;
    }

    public Upgrade getuBetterGold() {
        return uBetterGold;
    }

    public Upgrade getuDiamonds() {
        return uDiamonds;
    }

    public Upgrade getuBetterDiamonds() {
        return uBetterDiamonds;
    }

    public Upgrade getuDiscount10() {
        return uDiscount10;
    }

    public Upgrade getuDiscount20() {
        return uDiscount20;
    }

    public Upgrade getuDiscount30() {
        return uDiscount30;
    }

    public Upgrade getuUpgrade() {
        return uUpgrade;
    }

    public Upgrade getuHammer() {
        return uHammer;
    }

    public Upgrade getuBetterUpgrade() {
        return uBetterUpgrade;
    }

    public Upgrade getuLuck() {
        return uLuck;
    }



}




/*
upgradeManager.getFork().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        uFork.setLevel(uFork.getLevel()+1, 3, upgradeManager.getFork());
                        uFork.setDamagemultiplayer(uFork.getLevel());
                        if(uFork.getLevel() == 1){
                            uScythe.setLevel(0, 3, upgradeManager.getScythe());
                            uScythe.setDamagemultiplayer(uScythe.getLevel());
                            upgradeManager.getScythe().setDrawable(images_upgrades, "upgradeIcons_attackScythe");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uFork.returnInformation(uFork, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -72, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getScythe().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uScythe.getLevel()>=0){
                            uScythe.setLevel(uScythe.getLevel()+1, 1, upgradeManager.getScythe());
                            uScythe.setDamagemultiplayer(uScythe.getLevel());
                        }
                        if(uScythe.getLevel() == 1) {
                            uDagger.setLevel(0, 3, upgradeManager.getDagger());
                            uDagger.setDamagemultiplayer(uDagger.getLevel());
                            upgradeManager.getDagger().setDrawable(images_upgrades, "upgradeIcons_attackDagger");
                            uSceptre.setLevel(0,3,upgradeManager.getSceptre());
                            uSceptre.setDamagemultiplayer(uSceptre.getLevel());
                            upgradeManager.getSceptre().setDrawable(images_upgrades, "upgradeIcons_attackSceptre");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uScythe.returnInformation(uScythe, 1, labelStyle);
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getDagger().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uDagger.getLevel()>=0){
                            uDagger.setLevel(uDagger.getLevel()+1, 3, upgradeManager.getDagger());
                            uDagger.setDamagemultiplayer(uDagger.getLevel());
                        }
                        if(uDagger.getLevel() == 1){
                            uSword.setLevel(0,3,upgradeManager.getSword());
                            uSword.setDamagemultiplayer(uSword.getLevel());
                            upgradeManager.getSword().setDrawable(images_upgrades, "upgradeIcons_attackSword");
                            uBow.setLevel(0,3,upgradeManager.getBow());
                            uBow.setDamagemultiplayer(uBow.getLevel());
                            upgradeManager.getBow().setDrawable(images_upgrades, "upgradeIcons_attackBow");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uDagger.returnInformation(uDagger, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }

                });
                upgradeManager.getSword().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uSword.getLevel()>=0){
                            uSword.setLevel(uSword.getLevel()+1, 3, upgradeManager.getSword());
                            uSword.setDamagemultiplayer(uSword.getLevel());
                        }
                        if(uSword.getLevel() == 1){
                            uBattleAxe.setLevel(0,1,upgradeManager.getBattleAxe());
                            uBattleAxe.setDamagemultiplayer(uBattleAxe.getLevel());
                            upgradeManager.getBattleAxe().setDrawable(images_upgrades, "upgradeIcons_attackBattleAxe");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uSword.returnInformation(uSword, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBattleAxe().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBattleAxe.getLevel()>=0){
                            uBattleAxe.setLevel(uBattleAxe.getLevel()+1, 1, upgradeManager.getBattleAxe());
                            uBattleAxe.setDamagemultiplayer(uBattleAxe.getLevel());
                        }
                        if(uBattleAxe.getLevel() == 1) {
                            uMace.setLevel(0, 3, upgradeManager.getMace());
                            uMace.setDamagemultiplayer(uMace.getLevel());
                            upgradeManager.getMace().setDrawable(images_upgrades, "upgradeIcons_attackMace");
                            uCannon.setLevel(0,3,upgradeManager.getCannon());
                            uCannon.setDamagemultiplayer(uCannon.getLevel());
                            upgradeManager.getCannon().setDrawable(images_upgrades, "upgradeIcons_attackCannon");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBattleAxe.returnInformation(uBattleAxe, 1, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+160, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getMace().addListener(new ClickListener(){

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uMace.getLevel()>=0){
                            uMace.setLevel(uMace.getLevel()+1, 3, upgradeManager.getMace());
                            uMace.setDamagemultiplayer(uMace.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uMace.returnInformation(uMace, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(x);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+64, 0, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        tooltip.exit(event, x,y , pointer, fromActor);

                    }

                });

                upgradeManager.getBow().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBow.getLevel()>=0){
                            uBow.setLevel(uBow.getLevel()+1, 3, upgradeManager.getBow());
                            uBow.setDamagemultiplayer(uBow.getLevel());
                        }
                        if(uBow.getLevel() == 1){
                            uCrossBow.setLevel(0,3,upgradeManager.getCrossbow());
                            uCrossBow.setDamagemultiplayer(uCrossBow.getLevel());
                            upgradeManager.getCrossbow().setDrawable(images_upgrades, "upgradeIcons_attackCrossbow");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBow.returnInformation(uBow, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getCrossbow().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uCrossBow.getLevel()>=0){
                            uCrossBow.setLevel(uCrossBow.getLevel()+1, 3, upgradeManager.getCrossbow());
                            uCrossBow.setDamagemultiplayer(uCrossBow.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uCrossBow.returnInformation(uCrossBow, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getCannon().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uCannon.getLevel()>=0){
                            uCannon.setLevel(uCannon.getLevel()+1, 3, upgradeManager.getCannon());
                            uCannon.setDamagemultiplayer(uCannon.getLevel());
                        }
                        if(uCannon.getLevel() == 1) {
                            uCannonBall.setLevel(0, 3, upgradeManager.getCannonBall());
                            uCannonBall.setDamagemultiplayer(uCannonBall.getLevel());
                            upgradeManager.getCannonBall().setDrawable(images_upgrades, "upgradeIcons_attackCannonBall");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uCannon.returnInformation(uCannon, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+160, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getCannonBall().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uCannonBall.getLevel()>=0){
                            uCannonBall.setLevel(uCannonBall.getLevel()+1, 3, upgradeManager.getCannonBall());
                            uCannonBall.setDamagemultiplayer(uCannonBall.getLevel());
                        }
                        if(uCannonBall.getLevel() == 1) {
                            uBetterCannon.setLevel(0, 3, upgradeManager.getBetterCannon());
                            uBetterCannon.setDamagemultiplayer(uBetterCannon.getLevel());
                            upgradeManager.getBetterCannon().setDrawable(images_upgrades, "upgradeIcons_attackBetterCannon");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uCannonBall.returnInformation(uCannonBall, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(x);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+64, 0, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x,y , pointer, fromActor);
                    }
                });
                upgradeManager.getBetterCannon().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBetterCannon.getLevel()>=0){
                            uBetterCannon.setLevel(uBetterCannon.getLevel()+1, 3, upgradeManager.getBetterCannon());
                            uBetterCannon.setDamagemultiplayer(uBetterCannon.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBetterCannon.returnInformation(uBetterCannon, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(x);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2+32), 0, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        tooltip.exit(event, x,y , pointer, fromActor);

                    }
                });
                upgradeManager.getSceptre().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uSceptre.getLevel()>=0){
                            uSceptre.setLevel(uSceptre.getLevel()+1, 3, upgradeManager.getSceptre());
                            uSceptre.setDamagemultiplayer(uSceptre.getLevel());
                        }
                        if(uSceptre.getLevel() == 1) {
                            uBook.setLevel(0, 3, upgradeManager.getBook());
                            uBook.setDamagemultiplayer(uBook.getLevel());
                            upgradeManager.getBook().setDrawable(images_upgrades, "upgradeIcons_attackBook");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uSceptre.returnInformation(uSceptre, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(upgradeDialog.getWidth());
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBook().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBook.getLevel()>=0){
                            uBook.setLevel(uBook.getLevel()+1, 3, upgradeManager.getBook());
                            uBook.setDamagemultiplayer(uBook.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBook.returnInformation(uBook, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getGear().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        uGear.setLevel(uGear.getLevel()+1, 3, upgradeManager.getGear());
                        uGear.setDamagemultiplayer(uGear.getLevel());
                        if(uGear.getLevel() == 1){
                            uSonar.setLevel(0, 3, upgradeManager.getSonar());
                            uSonar.setDamagemultiplayer(uSonar.getLevel());
                            upgradeManager.getSonar().setDrawable(images_upgrades, "upgradeIcons_attackSonar");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uGear.returnInformation(uGear, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -72, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getSonar().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uSonar.getLevel()>=0){
                            uSonar.setLevel(uSonar.getLevel()+1, 3, upgradeManager.getSonar());
                            uSonar.setDamagemultiplayer(uSonar.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uSonar.returnInformation(uSonar, 3, labelStyle);
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getHealth().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        uHealth.setLevel(uHealth.getLevel()+1, 3, upgradeManager.getHealth());
                        uHealth.setDamagemultiplayer(uHealth.getLevel());
                        if(uHealth.getLevel() == 1){
                            uBetterCannon.setLevel(0, 3, upgradeManager.getBetterHealth());
                            uBetterHealth.setDamagemultiplayer(uBetterHealth.getLevel());
                            upgradeManager.getBetterHealth().setDrawable(images_upgrades, "upgradeIcons_healthBetterHealth");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uHealth.returnInformation(uHealth, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -72, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBetterHealth().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBetterHealth.getLevel()>=0){
                            uBetterHealth.setLevel(uBetterHealth.getLevel()+1, 3, upgradeManager.getBetterHealth());
                            uBetterHealth.setDamagemultiplayer(uBetterHealth.getLevel());
                        }
                        if(uBetterHealth.getLevel() == 1) {
                            uRegeneration.setLevel(0, 3, upgradeManager.getRegeneration());
                            uRegeneration.setDamagemultiplayer(uRegeneration.getLevel());
                            upgradeManager.getRegeneration().setDrawable(images_upgrades, "upgradeIcons_healthRegen");

                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBetterHealth.returnInformation(uBetterHealth, 3, labelStyle);
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getRegeneration().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uRegeneration.getLevel()>=0){
                            uRegeneration.setLevel(uRegeneration.getLevel()+1, 3, upgradeManager.getRegeneration());
                            uRegeneration.setDamagemultiplayer(uRegeneration.getLevel());
                        }
                        if(uRegeneration.getLevel() == 1){
                            uShield.setLevel(0,3,upgradeManager.getShield());
                            uShield.setDamagemultiplayer(uShield.getLevel());
                            upgradeManager.getShield().setDrawable(images_upgrades, "upgradeIcons_healthShield");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uRegeneration.returnInformation(uRegeneration, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getShield().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uShield.getLevel()>=0){
                            uShield.setLevel(uShield.getLevel()+1, 3, upgradeManager.getShield());
                            uShield.setDamagemultiplayer(uShield.getLevel());
                        }
                        if(uShield.getLevel() == 1){
                            uBetterBetterHealth.setLevel(0,1,upgradeManager.getBetterBetterHealth());
                            uBetterBetterHealth.setDamagemultiplayer(uBetterBetterHealth.getLevel());
                            upgradeManager.getBetterBetterHealth().setDrawable(images_upgrades, "upgradeIcons_healthBetterBetterHealth");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uShield.returnInformation(uShield, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBetterBetterHealth().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBetterBetterHealth.getLevel()>=0){
                            uBetterBetterHealth.setLevel(uBetterBetterHealth.getLevel()+1, 3, upgradeManager.getBetterBetterHealth());
                            uBetterBetterHealth.setDamagemultiplayer(uBetterBetterHealth.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBetterBetterHealth.returnInformation(uBetterBetterHealth, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getGold().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        uGold.setLevel(uGold.getLevel()+1, 3, upgradeManager.getGold());
                        uGold.setDamagemultiplayer(uGold.getLevel());
                        if(uGold.getLevel() == 1){
                            uBetterGold.setLevel(0, 3, upgradeManager.getBetterGold());
                            uBetterGold.setDamagemultiplayer(uBetterGold.getLevel());
                            upgradeManager.getBetterGold().setDrawable(images_upgrades, "upgradeIcons_incomeBetterGold");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uGold.returnInformation(uGold, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -72, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBetterGold().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBetterGold.getLevel()>=0){
                            uBetterGold.setLevel(uBetterGold.getLevel()+1, 1, upgradeManager.getBetterGold());
                            uBetterGold.setDamagemultiplayer(uBetterGold.getLevel());
                        }
                        if(uBetterGold.getLevel() == 1) {
                            uDiamonds.setLevel(0, 3, upgradeManager.getDiamonds());
                            uDiamonds.setDamagemultiplayer(uDiamonds.getLevel());
                            upgradeManager.getDiamonds().setDrawable(images_upgrades, "upgradeIcons_incomeDiamonds");
                            uDiscount10.setLevel(0,3,upgradeManager.getDiscount10());
                            uDiscount10.setDamagemultiplayer(uDiscount10.getLevel());
                            upgradeManager.getDiscount10().setDrawable(images_upgrades, "upgradeIcons_incomeDiscountTen");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBetterGold.returnInformation(uBetterGold, 1, labelStyle);
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getDiamonds().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uDiamonds.getLevel()>=0){
                            uDiamonds.setLevel(uDiamonds.getLevel()+1, 3, upgradeManager.getDiamonds());
                            uDiamonds.setDamagemultiplayer(uDiamonds.getLevel());
                        }
                        if(uDiamonds.getLevel() == 1){
                            uBetterDiamonds.setLevel(0,3,upgradeManager.getBetterDiamonds());
                            uDiamonds.setDamagemultiplayer(uDiamonds.getLevel());
                            upgradeManager.getBetterDiamonds().setDrawable(images_upgrades, "upgradeIcons_incomeBetterDiamonds");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uDiamonds.returnInformation(uDiamonds, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBetterDiamonds().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBetterDiamonds.getLevel()>=0){
                            uBetterDiamonds.setLevel(uBetterDiamonds.getLevel()+1, 3, upgradeManager.getBetterDiamonds());
                            uBetterDiamonds.setDamagemultiplayer(uBetterDiamonds.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBetterDiamonds.returnInformation(uBetterDiamonds, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getDiscount10().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uDiscount10.getLevel()>=0){
                            uDiscount10.setLevel(uDiscount10.getLevel()+1, 1, upgradeManager.getDiscount10());
                            uDiscount10.setDamagemultiplayer(uDiscount10.getLevel());
                        }
                        if(uDiscount10.getLevel() == 1) {
                            uDiscount20.setLevel(0, 1, upgradeManager.getDiscount20());
                            uDiscount20.setDamagemultiplayer(uDiscount20.getLevel());
                            upgradeManager.getDagger().setDrawable(images_upgrades, "upgradeIcons_incomeDiscountTwenty");
                            uUpgrade.setLevel(0,1,upgradeManager.getUpgrade());
                            uUpgrade.setDamagemultiplayer(uUpgrade.getLevel());
                            upgradeManager.getUpgrade().setDrawable(images_upgrades, "upgradeIcons_incomeUpgrade");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uDiscount10.returnInformation(uDiscount10, 1, labelStyle);
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y+10, pointer, fromActor);

                    }
                });
                upgradeManager.getDiscount20().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uDiscount20.getLevel()>=0){
                            uDiscount20.setLevel(uDiscount20.getLevel()+1, 1, upgradeManager.getDiscount20());
                            uDiscount20.setDamagemultiplayer(uDiscount20.getLevel());
                        }
                        if(uDiscount20.getLevel() == 1){
                            uDiscount30.setLevel(0,1,upgradeManager.getDiscount30());
                            uDiscount30.setDamagemultiplayer(uDiscount30.getLevel());
                            upgradeManager.getDiscount30().setDrawable(images_upgrades, "upgradeIcons_incomeDiscountThirty");
                            }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uDiscount20.returnInformation(uDiscount20, 1, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y+10, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getDiscount30().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uDiscount30.getLevel()>=0){
                            uDiscount30.setLevel(uDiscount30.getLevel()+1, 1, upgradeManager.getDiscount30());
                            uDiscount30.setDamagemultiplayer(uDiscount30.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uDiscount30.returnInformation(uDiscount30, 1, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y+10, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getUpgrade().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uUpgrade.getLevel()>=0){
                            uUpgrade.setLevel(uUpgrade.getLevel()+1, 1, upgradeManager.getUpgrade());
                            uUpgrade.setDamagemultiplayer(uUpgrade.getLevel());
                        }
                        if(uUpgrade.getLevel() == 1) {
                            uHammer.setLevel(0, 3, upgradeManager.getHammer());
                            uHammer.setDamagemultiplayer(uHammer.getLevel());
                            upgradeManager.getHammer().setDrawable(images_upgrades, "upgradeIcons_incomeHammer");
                         }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uUpgrade.returnInformation(uUpgrade, 1, labelStyle);
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getHammer().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uHammer.getLevel()>=0){
                            uHammer.setLevel(uHammer.getLevel()+1, 3, upgradeManager.getHammer());
                            uHammer.setDamagemultiplayer(uHammer.getLevel());
                        }
                        if(uHammer.getLevel() == 1){
                            uBetterUpgrade.setLevel(0,1,upgradeManager.getBetterUpgrade());
                            uBetterUpgrade.setDamagemultiplayer(uBetterUpgrade.getLevel());
                            upgradeManager.getBetterUpgrade().setDrawable(images_upgrades, "upgradeIcons_incomeBetterUpgrade");}
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uHammer.returnInformation(uHammer, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBetterUpgrade().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBetterUpgrade.getLevel()>=0){
                            uBetterUpgrade.setLevel(uBetterUpgrade.getLevel()+1, 1, upgradeManager.getBetterUpgrade());
                            uBetterUpgrade.setDamagemultiplayer(uBetterUpgrade.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uBetterUpgrade.returnInformation(uBetterUpgrade, 1, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getLuck().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uLuck.getLevel()>=0){
                            uLuck.setLevel(uLuck.getLevel()+1, 3, upgradeManager.getLuck());
                            uLuck.setDamagemultiplayer(uLuck.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        information = uLuck.returnInformation(uLuck, 3, labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
 */