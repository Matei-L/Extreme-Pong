package com.expong.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.expong.game.ExtremePong;

/**
 * Created by lipan on 6/22/2016.
 */
public class Paddle {
    //paddle-def variables
    private static final Vector2 DIMENSIONS = new Vector2(128,16);
    private TextureRegion paddle;
    public boolean isplayer;
    private Vector2 position;

    public Paddle(float x, float y, boolean isplayer){
        //paddle init.
        this.isplayer = isplayer;
        if(ExtremePong.PREFS.getInteger("currentPadd")==13){
            paddle = new TextureRegion(ExtremePong.SKIN.getRegion("pad"+1));
        }
        else {
            paddle = new TextureRegion(ExtremePong.SKIN.getRegion("pad" + Integer.toString(ExtremePong.PREFS.getInteger("currentPadd"))));
        }
        position = new Vector2(x,y);
        if(!isplayer)
        {
            paddle.flip(true,true);
        }
    }
    public void update(float newposition){
        //position update
        if(!isplayer)newposition+=DIMENSIONS.y/2;
        if(newposition> ExtremePong.SCREEN_WIDTH-64)
            position.x = ExtremePong.SCREEN_WIDTH-128;
        else if(newposition<64)
            position.x = 0;
        else  position.x = newposition-64;
    }
    public static Vector2 getDIMENSIONS() {
        return DIMENSIONS;
    }

    public TextureRegion getPaddle() {
        return paddle;
    }

    public Vector2 getPosition() {
        return position;
    }
}

