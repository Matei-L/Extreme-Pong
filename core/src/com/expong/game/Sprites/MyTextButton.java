package com.expong.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.expong.game.ExtremePong;

/**
 * Created by lipan on 6/20/2016.
 */
public class MyTextButton {
    //button-def variables
    public enum Type{BIG,MEDIUM,SMALL,NORMAL};
    public TextButton button;
    public TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;

    public MyTextButton(String name, float x, float y,Type type)
    {
        //button init.
        font = new BitmapFont(Gdx.files.internal(ExtremePong.PREFS.getString("currentFont")));
        if(type == Type.NORMAL) {
            font.getData().setScale(1.0f);
        }
        if(type == Type.MEDIUM) {
            font.getData().setScale(0.50f);
        }
        if(type == Type.BIG) {
            font.getData().setScale(0.75f);
        }
        if(type == Type.SMALL) {
            font.getData().setScale(0.25f);
        }
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        button = new TextButton(name, textButtonStyle);
        button.getLabel().setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));

        button.setPosition(x-button.getWidth()/2,y-button.getHeight()/2);
    }

    public MyTextButton(String name,Type type)
    {
        //button init.
        font = new BitmapFont(Gdx.files.internal(ExtremePong.PREFS.getString("currentFont")));
        if(type == Type.MEDIUM) {
            font.getData().setScale(0.50f);
        }
        if(type == Type.BIG) {
            font.getData().setScale(0.75f);
        }
        if(type == Type.SMALL) {
            font.getData().setScale(0.25f);
        }
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        button = new TextButton(name, textButtonStyle);
        button.getLabel().setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
    }

    public TextButton getButton() {
        return button;
    }

    public void dispose(){
        font.dispose();
    }
}

