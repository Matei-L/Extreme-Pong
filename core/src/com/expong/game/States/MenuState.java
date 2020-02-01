package com.expong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.expong.game.ExtremePong;
import com.expong.game.Sprites.MyTextButton;

/**
 * Created by lipan on 6/20/2016.
 */
public class MenuState extends State{
    // State -def variables
    public Stage stage;
    public Table table;
    public MyTextButton buttonPlay,buttonMUTE,buttonPrizes,buttonCredits;
    public boolean buttonLocker;

    public MenuState(GameStateManager gsm) {
        //State init.
        super(gsm);
        Gdx.input.setCatchBackKey(true);
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);

        stage = new Stage(new StretchViewport(ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT));
        table = new Table(ExtremePong.SKIN);
        table.setBounds(0,0,480,800);
        table.pad(15);

        buttonCredits = new MyTextButton("Credits",50,750,MyTextButton.Type.SMALL);
        // table.setSize(ExtremePong.SCREEN_WIDTH,ExtremePong.SCREEN_HEIGHT);

        table.add("PONG","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).spaceBottom(50);
        table.row();
        buttonPlay = new MyTextButton("Play", MyTextButton.Type.BIG);
        table.add(buttonPlay.getButton()).height(150).fill();
        table.row();
        buttonPrizes = new MyTextButton("Prizes", MyTextButton.Type.BIG);
        table.add(buttonPrizes.getButton()).height(150).fill();
        table.row();

        if(ExtremePong.PREFS.getBoolean("isSound")){
            buttonMUTE = new MyTextButton("MUTE", MyTextButton.Type.BIG);
        }
        else{
            buttonMUTE = new MyTextButton("UNMUTE", MyTextButton.Type.BIG);
        }

        table.add(buttonMUTE.getButton()).height(150).fill();
        table.row();

        buttonCredits.getButton().setWidth(100);
        buttonCredits.getButton().setHeight(50);

        stage.addActor(buttonCredits.getButton());
        stage.addActor(table);

        buttonLocker = false;

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    protected void handleImput() {

        Gdx.input.setInputProcessor(null);
        if(Gdx.input.justTouched())buttonLocker = false;
        if(Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            buttonLocker = true;
            Gdx.app.exit();
        }
        Gdx.input.setInputProcessor(stage);

        buttonPlay.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker) {
                    gsm.pop();
                    gsm.push(new PlayState(gsm));
                    buttonLocker = true;
                }
            }
        });
        buttonPrizes.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker){
                    gsm.pop();
                    gsm.push(new PrizeState(gsm));
                    buttonLocker = true;
                }
            }
        });
        buttonMUTE.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker){
                    if(ExtremePong.PREFS.getBoolean("isSound"))
                    {
                        ExtremePong.PREFS.putBoolean("isSound",false);
                        buttonMUTE.getButton().setText("UNMUTE");
                        ExtremePong.PREFS.flush();
                    }
                    else
                    {
                        ExtremePong.PREFS.putBoolean("isSound",true);
                        buttonMUTE.getButton().setText("MUTE");
                        ExtremePong.PREFS.flush();
                    }
                    buttonLocker = true;
                }
            }
        });

        buttonCredits.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker){
                    gsm.pop();
                    gsm.push(new CreditsState(gsm));
                    buttonLocker = true;
                }
            }
        });

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
        buttonCredits.dispose();
        buttonMUTE.dispose();
        buttonPlay.dispose();
        buttonPrizes.dispose();
        stage.dispose();
    }

    @Override
    public void pause() {

    }
}

