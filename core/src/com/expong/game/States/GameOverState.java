package com.expong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
public class GameOverState extends State {
    // State -def variables
    private Sound sound2;
    private boolean justIn;
    private Table table;
    private int  hscore;
    public Stage stage;
    public MyTextButton buttonPlay,buttonMenu;
    public boolean buttonLocker;

    protected GameOverState(GameStateManager gsm, int score) {
        //State init.
        super(gsm);
        Gdx.input.setCatchBackKey(true);
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);

        table = new Table(ExtremePong.SKIN);
        table.setBounds(0,0,ExtremePong.SCREEN_WIDTH,ExtremePong.SCREEN_HEIGHT);
        table.add("GAME OVER","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).colspan(3).spaceBottom(50);
        table.row();


        hscore = ExtremePong.PREFS.getInteger("highScore");
        if (score > hscore) {
            ExtremePong.PREFS.putInteger("highScore", score);
            ExtremePong.PREFS.flush();
            table.add("HIGH SCORE: ","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).colspan(2);
            table.add(Integer.toString(score),"currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
        }
        else
        {
            table.add("HIGH SCORE: ","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).colspan(2);
            table.add(Integer.toString(hscore),"currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
        }


        table.row();
        table.add("NEW SCORE: ","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).height(150).colspan(2);
        table.add(Integer.toString(score),"currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).height(150);
        table.row();


        stage = new Stage(new StretchViewport(ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT));

        buttonMenu = new MyTextButton("Menu",cam.position.x-ExtremePong.SCREEN_WIDTH/2+100,cam.position.y-ExtremePong.SCREEN_HEIGHT/2+250, MyTextButton.Type.BIG);
        table.add(buttonMenu.getButton());
        buttonPlay = new MyTextButton("Try again",cam.position.x+ExtremePong.SCREEN_WIDTH/2-100,cam.position.y-ExtremePong.SCREEN_HEIGHT/2+250,MyTextButton.Type.BIG);
        table.add(buttonPlay.getButton()).colspan(2);

        stage.addActor(table);

        buttonLocker = false;

        if(ExtremePong.PREFS.getBoolean("isSound"))
        {
            sound2 = Gdx.audio.newSound(Gdx.files.internal("sound2.wav"));
            justIn = true;
        }
        else justIn = false;
        Gdx.input.vibrate(100);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleImput() {
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
        buttonMenu.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker) {
                    gsm.pop();
                    gsm.push(new MenuState(gsm));
                    buttonLocker = true;
                }
            }
        });
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            gsm.pop();
            gsm.push(new MenuState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleImput();
        if(justIn){
            justIn = false;
            sound2.play(0.5f);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        if(ExtremePong.PREFS.getBoolean("isSound"))sound2.dispose();
        buttonMenu.dispose();
        buttonPlay.dispose();
    }

    @Override
    public void pause() {

    }
}

