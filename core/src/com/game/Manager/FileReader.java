package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

public class FileReader {
    private String resolutionValue;
    private int volumeValue;


    private String languageValue;
    public FileReader(){

    }

    public String getResolutionValue() {return resolutionValue;}

    public int getVolumeValue() {
        return volumeValue;
    }

    public String getLanguageValue() {
        return languageValue;
    }

    public void downloadSettings(){
        String jsonPath = "save/settings.json";
        JsonReader json = new JsonReader();
        JsonValue base  = json.parse(Gdx.files.internal(jsonPath));

        resolutionValue = base.getString("resolution");
        volumeValue = Integer.valueOf(base.getString("volume"));
        languageValue = (base.getString("language"));

    }

    public void setSettings(String resolution, int volume, String language){
        String jsonPath = "save/settings.json";
        User_settings user_settings = new User_settings();
        FileHandle file = Gdx.files.local(jsonPath);
        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        user_settings.resolution = resolution;
        user_settings.volume = volume;
        user_settings.language = language;
        String txt = json.toJson(user_settings);
        file.writeString(json.prettyPrint(txt), false);
    }

}
