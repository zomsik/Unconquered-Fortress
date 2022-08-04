package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class FileReader {
    private String resolutionValue;
    private int volumeValue;


    private String languageValue;
    public FileReader(){
        printResolution();
    }

    public String getResolutionValue() {
        return resolutionValue;
    }

    public int getVolumeValue() {
        return volumeValue;
    }

    public String getLanguageValue() {
        return languageValue;
    }

    public void printResolution(){
        String jsonPath = "save/settings.json";
        JsonReader json = new JsonReader();
        JsonValue base  = json.parse(Gdx.files.internal(jsonPath));
        for(JsonValue component : base.get("user_settings"))
        {
            //System.out.println(component.get("resolution").getString("x"));
            //System.out.println(component.get("resolution").getString("y"));
            //System.out.println(component.get("resolution").getString("mode"));
            resolutionValue = (component.get("resolution").getString("x") + " X " + component.get("resolution").getString("y") + component.get("resolution").getString("mode"));
            volumeValue = Integer.valueOf(component.getString("volume"));
            languageValue = (component.getString("language"));
        }
    }
}
