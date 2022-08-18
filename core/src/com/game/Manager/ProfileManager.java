package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import org.json.JSONObject;

import java.util.Random;

public class ProfileManager {

    public static Table createProfileTable(JSONObject save, BitmapFont font, LanguageManager languageManager, int x, String icon){

        Table t = new Table();
        t.setBounds(x, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        t.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(new FileHandle("assets/profile_banner.png")))));

        TextField.TextFieldStyle tFS = new TextField.TextFieldStyle();
        TextFieldStyleManager tSM = new TextFieldStyleManager();
        tSM.setTextFieldStyle(tFS, new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack")), font, "empty_background", Color.WHITE);

        TextField tDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "difficulty_field") + languageManager.getValue(languageManager.getLanguage(), save.getString("difficulty")), tSM.returnTextFieldStyle(tFS));
        tDifficulty.setAlignment(Align.left);
        TextField tFinishedMaps = new TextField(languageManager.getValue(languageManager.getLanguage(), "finishedmaps_field") + save.getInt("finishedMaps"), tSM.returnTextFieldStyle(tFS));
        tFinishedMaps.setAlignment(Align.left);
        TextField tWave = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field") + save.getInt("wave"), tSM.returnTextFieldStyle(tFS));
        tWave.setAlignment(Align.left);
        TextField tGold = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field") + save.getInt("gold"), tSM.returnTextFieldStyle(tFS));
        tGold.setAlignment(Align.left);
        TextField tDiamonds = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field") + save.getInt("diamonds"), tSM.returnTextFieldStyle(tFS));
        tDiamonds.setAlignment(Align.left);
        t.add(new Image(new Texture(new FileHandle(icon)))).width(t.getHeight()/20).height(t.getHeight()/20).align(Align.right).padTop(16).padRight(8);
        t.row();
        t.add(tDifficulty).width(t.getWidth()/10*9).height(t.getHeight()/10).padLeft(t.getWidth()/10*2);
        t.row();
        t.add(tFinishedMaps).width(t.getWidth()/10*9).height(t.getHeight()/10).padLeft(t.getWidth()/10*2);
        t.row();
        t.add(tWave).width(t.getWidth()/10*9).height(t.getHeight()/10).padLeft(t.getWidth()/10*2);
        t.row();
        t.add(tGold).width(t.getWidth()/10*9).height(t.getHeight()/10).padLeft(t.getWidth()/10*2);
        t.row();
        t.add(tDiamonds).width(t.getWidth()/10*9).height(t.getHeight()/10).padBottom(t.getHeight()/2-t.getHeight()/10).padLeft(t.getWidth()/10*2);
        //t.debug();
        t.setTouchable(Touchable.enabled);


        return t;
    }

    public static Table createEmptyProfileTable(BitmapFont font, int x){

        Table t = new Table();
        t.setBounds(x, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        t.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(new FileHandle("assets/profile_banner.png")))));

        ButtonStyleManager bSM = new ButtonStyleManager();
        TextButton.TextButtonStyle tBS = new TextButton.TextButtonStyle();
        bSM.setTextButtonStyle(tBS, new Skin(new TextureAtlas("assets/buttons/buttons_profile.pack")), font, "new_profile_button_up", "new_profile_button_down");
        TextButton tB = new TextButton(null, bSM.returnTextButtonStyle(tBS));

        t.add(tB).height(t.getHeight()/4).width(t.getHeight()/4).padBottom(t.getHeight()/4);

        return t;
    }

    public static JSONObject createEmptySave(String difficulty, int chosenProfile){

        //        //wygenerowanie seeda
        final ThreadLocal<Random> RANDOM_THREAD_LOCAL = ThreadLocal.withInitial(Random::new);
        Random random = RANDOM_THREAD_LOCAL.get();

        JSONObject j = new JSONObject();


        j.put("seed", random.nextInt(1,100));
        j.put("profileNumber", chosenProfile);
        j.put("difficulty", difficulty);
        j.put("finishedMaps",0);
        j.put("wave",0);
        j.put("gold",0);
        j.put("diamonds",0);

        return j;

    }


}
