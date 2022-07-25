package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;

import java.util.ArrayList;
import java.util.Collections;
public class Resolutions {
    private ArrayList<String> resolutions;
    public Resolutions(){
        resolutions = new ArrayList<>();
        setResolutions(resolutions);
    }

    private ArrayList<String> setResolutions(ArrayList<String> resolutions){
        //Utworzenie tablicy wszystkich możliwych konfiguracji
        Graphics.DisplayMode[] modes = Gdx.graphics.getDisplayModes();
        //Utworzenie zmiennej przechowującej aktualną konfigurację
        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
        //Utworzenie ArrayListy konfiguracji o maxymalnie trzech wartościach (1280x720, 1600x900, 1920x1080)
        for(int i=0; i<modes.length; i++){
            if(modes[i].width == 1280 && modes[i].height == 720 && modes[i].bitsPerPixel == currentMode.bitsPerPixel && modes[i].refreshRate == currentMode.refreshRate)
            {
                //Jeśli aktualna konfiguracja jest konfiguracją domyślną ustaw Fullscreen
                if(modes[i].width != currentMode.width){
                    resolutions.add(modes[i].width + " X " + modes[i].height + " Windowed");
                }else{
                    resolutions.add(modes[i].width + " X " + modes[i].height + " Fullscreen");
                }
            }
            if(modes[i].width == 1600 && modes[i].height == 900 && modes[i].bitsPerPixel == currentMode.bitsPerPixel && modes[i].refreshRate == currentMode.refreshRate)
            {
                //Jeśli aktualna konfiguracja jest konfiguracją domyślną ustaw Fullscreen
                if(modes[i].width != currentMode.width){
                    resolutions.add(modes[i].width + " X " + modes[i].height + " Windowed");
                }else{
                    resolutions.add(modes[i].width + " X " + modes[i].height + " Fullscreen");
                }
            }
            //Jeśli aktualna konfiguracja jest konfiguracją domyślną ustaw Fullscreen, brak obsługi dla większych rozdzielczości, może powinna być i wtedy 1920x1080 w oknie
            if(modes[i].width == currentMode.width && modes[i].height == currentMode.height && modes[i].bitsPerPixel == currentMode.bitsPerPixel && modes[i].refreshRate == currentMode.refreshRate)
            {
                resolutions.add(modes[i].width + " X " + modes[i].height + " Fullscreen");
            }
        }
        //Odwrócenie ArrayListy aby zawsze była pokazywana aktualna rozdzielczość jako pierwsza
        Collections.reverse(resolutions);
        return resolutions;
    }
    public ArrayList<String> getResolutions() {
        return resolutions;
    }
}
