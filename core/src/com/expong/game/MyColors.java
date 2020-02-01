package com.expong.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lipan on 8/10/2016.
 */
public class MyColors {
    //number of possible colors in game
    public int numColors;

    public MyColors(){
        numColors = 33;
    }
    //gets the i color
    public Color getColor(int i){
        switch (i){
            case 0: return  Color.CLEAR;
            case 1: return  Color.BLACK;
            case 2: return  Color.WHITE;
            case 3: return  Color.LIGHT_GRAY;
            case 4: return  Color.GRAY;
            case 5: return  Color.DARK_GRAY;
            case 6: return  Color.BLUE;
            case 7: return  Color.NAVY;
            case 8: return  Color.ROYAL;
            case 9: return  Color.SLATE;
            case 10: return  Color.SKY;
            case 11: return  Color.CYAN;
            case 12: return  Color.TEAL;
            case 13: return  Color.GREEN;
            case 14: return  Color.CHARTREUSE;
            case 15: return  Color.LIGHT_GRAY;
            case 16: return  Color.FOREST;
            case 17: return  Color.OLIVE;
            case 18: return  Color.YELLOW;
            case 19: return  Color.GOLD;
            case 20: return  Color.GOLDENROD;
            case 21: return  Color.ORANGE;
            case 22: return  Color.BROWN;
            case 23: return  Color.TAN;
            case 24: return  Color.FIREBRICK;
            case 25: return  Color.RED;
            case 26: return  Color.SCARLET;
            case 27: return  Color.CORAL;
            case 28: return  Color.SALMON;
            case 29: return  Color.PINK;
            case 30: return  Color.MAGENTA;
            case 31: return  Color.PURPLE;
            case 32: return  Color.VIOLET;
            case 33: return Color.MAROON;
        }
        return Color.CLEAR;
    }
    //sets the given spriteBatch at the i color
    public void setColor(int i, SpriteBatch sb){
        sb.setColor(getColor(i));
    }
}

