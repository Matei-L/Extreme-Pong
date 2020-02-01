package com.expong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.expong.game.ExtremePong;
import com.expong.game.Sprites.MyTextButton;

/**
 * Created by lipan on 7/7/2016.
 */
public class PauseState extends State {
    // State -def variables
    private Table table;
    public MyTextButton buttonPlay,buttonMenu;
    public Stage stage;
    public boolean buttonLocker;

    protected PauseState(GameStateManager gsm) {
        //State init.
        super(gsm);
        Gdx.input.setCatchBackKey(true);
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);

        table = new Table(ExtremePong.SKIN);
        table.setBounds(0,0,ExtremePong.SCREEN_WIDTH,ExtremePong.SCREEN_HEIGHT);
        table.add("PAUSE","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).colspan(2).spaceBottom(200);
        table.row();
        stage = new Stage(new StretchViewport(ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT));
        buttonMenu = new MyTextButton("Menu", MyTextButton.Type.BIG);
        table.add(buttonMenu.getButton()).fill().spaceRight(50);
        buttonPlay = new MyTextButton("Continue", MyTextButton.Type.BIG);
        table.add(buttonPlay.getButton()).fill();
        stage.addActor(table);

        buttonLocker = false;
    }

    @Override
    protected void handleImput() {
        Gdx.input.setInputProcessor(stage);
        buttonPlay.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker) {
                    gsm.pop();
                    buttonLocker = true;
                }
            }
        });
        buttonMenu.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker) {
                    gsm.pop();
                    gsm.pop();
                    gsm.push(new MenuState(gsm));
                    buttonLocker = true;
                }
            }
        });
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            gsm.pop();
        }

    }

    @Override
    public void update(float dt) {
        handleImput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        buttonMenu.dispose();
        buttonPlay.dispose();
    }

    @Override
    public void pause() {

    }
}

