package com.expong.game.Sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.expong.game.ExtremePong;
/**
 * Created by lipan on 6/20/2016.
 */
public class MyPictureButton {

    //button-def variables
    public ImageTextButton button;
    public ImageTextButton.ImageTextButtonStyle imageButtonStyle;
    public boolean islocked;
    private Drawable image;
    private int price;
    private float scale;

    public MyPictureButton(Drawable texture, Drawable lockedtexture,Color color,Color color2,float scale,int price,boolean locker)
    {
        //button init.
        imageButtonStyle = new ImageTextButton.ImageTextButtonStyle();
        imageButtonStyle.down = lockedtexture;
        imageButtonStyle.up= lockedtexture;
        imageButtonStyle.font = ExtremePong.SKIN.getFont("currentFont");
        islocked = !locker;
        this.scale = scale;
        this.price = price;
        image = texture;

        if(islocked){
            if(price == 13||price == 23) {
                button = new ImageTextButton("? ? ?", imageButtonStyle);
            }
            else {
                button = new ImageTextButton(Integer.toString(price), imageButtonStyle);
            }
            button.getLabel().setVisible(true);
            button.getImage().setVisible(false);
        }
        else {
            button = new ImageTextButton("",imageButtonStyle);
            button.getLabel().setVisible(false);
            button.getStyle().imageUp = image;
            button.getStyle().imageDown = image;
            button.getImage().setScale(scale);
        }

        button.getLabel().setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
        button.setColor(color);
        button.getImage().setColor(color2);
        button.getLabel().setOrigin(Align.center);
    }

    public ImageTextButton getButton() {
        return button;
    }

    public void setDisabled(boolean isdisabeld){
        //if false, put button in the not locked state
        if(price == 13)
        {
            boolean ok =true;
            for(int i =0;i<24&&ok;i++)
                if(!ExtremePong.PREFS.getBoolean("ub"+Integer.toString(i)))ok=false;
            if(ok){
                islocked = isdisabeld;
                button.getImage().setVisible(true);
                button.getLabel().setVisible(false);
                button.getStyle().imageUp = image;
                button.getStyle().imageDown = image;
                button.getImage().setScale(scale);
                button.getLabel().setText("");
            }
        }
        else
        if(price == 23)
        {
            if(ExtremePong.PREFS.getInteger("highScore")>=50){
                islocked = isdisabeld;
                button.getImage().setVisible(true);
                button.getLabel().setVisible(false);
                button.getStyle().imageUp = image;
                button.getStyle().imageDown = image;
                button.getImage().setScale(scale);
                button.getLabel().setText("");
            }
        }
        else
        if(price <= ExtremePong.PREFS.getInteger("POINTS")&&islocked) {
            islocked = isdisabeld;
            button.getImage().setVisible(true);
            ExtremePong.PREFS.putInteger("POINTS",ExtremePong.PREFS.getInteger("POINTS")-price);
            ExtremePong.PREFS.flush();
            button.getLabel().setVisible(false);
            button.getStyle().imageUp = image;
            button.getStyle().imageDown = image;
            button.getImage().setScale(scale);
            button.getLabel().setText("");
        }
    }

    public boolean getIsDisabled(){return islocked;}
}

