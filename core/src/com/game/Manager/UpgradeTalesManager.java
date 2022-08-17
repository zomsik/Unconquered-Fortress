package com.game.Manager;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class UpgradeTalesManager {
    private Skin images_upgrade;
    private String upgradeName;
    private int priceDiamonds;
    private boolean isLock, isBought;
    private Image image;
    public UpgradeTalesManager(){

    }
    public void setUpgradeTale(Skin images_upgrade,  String upgradeName, boolean isBought, boolean isLock){
        if(isBought && !isLock){
            image = new Image(images_upgrade, "upgradeIcons_bought");
        }
        else if(isLock && !isBought){
            image = new Image(images_upgrade, "upgradeIcons_lock");
        }else{
            image = new Image(images_upgrade, upgradeName);
        }

    }
    public Image returnUpgradeTale(){
        return image;
    }

    public int getPriceDiamonds() {
        return priceDiamonds;
    }

    public void setPriceDiamonds(int priceDiamonds) {
        this.priceDiamonds = priceDiamonds;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought, UpgradeTalesManager tale) {
        tale.setUpgradeName("upgradeIcons_lock");
        setUpgradeTale(tale.getImages_upgrade(), tale.getUpgradeName(), true, isLock);
        isBought = bought;
    }

    public Skin getImages_upgrade() {
        return images_upgrade;
    }

    public String getUpgradeName() {
        return upgradeName;
    }

    public Image getImage() {
        return image;
    }

    public void setImages_upgrade(Skin images_upgrade) {
        this.images_upgrade = images_upgrade;
    }

    public void setUpgradeName(String upgradeName) {
        this.upgradeName = upgradeName;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
