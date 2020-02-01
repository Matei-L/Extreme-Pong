package com.expong.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.expong.game.ExtremePong;
import com.expong.game.MyColors;

/**
 * Created by lipan on 8/18/2016.
 */
public class IntroState extends State {

    private Texture texture1, texture2;
    private int time;
    private BitmapFont font;

    public IntroState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);

        time = 0;
        texture1 = new Texture("MLGAMES2.png");
        texture2 = new Texture("MLGAMES1.png");
        font = ExtremePong.SKIN.getFont("currentFont");
        font.setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
    }

    @Override
    protected void handleImput() {

    }

    @Override
    public void update(float dt) {
        handleImput();
        if(time<45)
        {
            time++;
        }
        else{
            gsm.pop();
            gsm.push(new MenuState(gsm));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        ExtremePong.COLORS.setColor(ExtremePong.PREFS.getInteger("currentLayColor"),sb);
        sb.draw(texture1,0,200);
        ExtremePong.COLORS.setColor(2,sb);
        sb.draw(texture2,0,200);
        font.draw(sb,"ML GAMES",120,180);
        sb.end();
    }

    @Override
    public void dispose() {
        texture1.dispose();
        texture2.dispose();
    }

    @Override
    public void pause() {

    }
}

