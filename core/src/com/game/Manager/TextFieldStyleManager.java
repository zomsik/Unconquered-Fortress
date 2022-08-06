package com.game.Manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class TextFieldStyleManager {
    public TextFieldStyleManager(){

    }

    public void setTextFieldStyle(TextField.TextFieldStyle textFieldStyle, Skin images, BitmapFont font, String name, Color color){
        textFieldStyle.background = images.getDrawable(name);
        textFieldStyle.font = font;
        textFieldStyle.fontColor = color;
    }

    public TextField.TextFieldStyle returnTextFieldStyle(TextField.TextFieldStyle textFieldStyle){
        return textFieldStyle;
    }
}
