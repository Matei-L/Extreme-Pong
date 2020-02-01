package com.expong.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by lipan on 6/20/2016.
 */
public abstract class State {
    //it's the base class of the states in game
    protected OrthographicCamera cam;
    protected Vector2 mouse;
    protected GameStateManager gsm;

    protected  State(GameStateManager gsm) {
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector2();
    }

    protected abstract void handleImput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
    public abstract void pause();
}

