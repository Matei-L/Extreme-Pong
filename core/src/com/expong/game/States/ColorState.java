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
 * Created by lipan on 8/10/2016.
 */
public class ColorState extends State {
    // State -def variables
    private Stage stage;
    private Table table;
    private MyTextButton buttonBack,buttonBG,buttonBALL,buttonPADD,buttonLay;
    private int colk;
    private boolean buttonLocker;

    protected ColorState(GameStateManager gsm,int colk) {
        //State init.
        super(gsm);
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);
        Gdx.input.setCatchBackKey(true);

        this.colk = colk;
        buttonLocker = false;

        stage = new Stage(new StretchViewport(ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT));
        table = new Table(ExtremePong.SKIN);
        table.setFillParent(true);
        table.padBottom(100);
        buttonBack = new MyTextButton("Back",50,ExtremePong.SCREEN_HEIGHT-50, MyTextButton.Type.BIG);

        if(colk != ExtremePong.PREFS.getInteger("currentBgColor"))buttonBALL = new MyTextButton("Ball",MyTextButton.Type.BIG);
        if(colk != ExtremePong.PREFS.getInteger("currentBgColor"))buttonLay = new MyTextButton("Layout",MyTextButton.Type.BIG);
        if(colk != ExtremePong.PREFS.getInteger("currentLayColor")&&
                colk != ExtremePong.PREFS.getInteger("currentPaddColor")&&colk != ExtremePong.PREFS.getInteger("currentBallColor")) buttonBG = new MyTextButton("Background",MyTextButton.Type.BIG);
        if(colk != ExtremePong.PREFS.getInteger("currentBgColor"))buttonPADD = new MyTextButton("Paddle",MyTextButton.Type.BIG);;


        if(colk != ExtremePong.PREFS.getInteger("currentLayColor")&&
                colk != ExtremePong.PREFS.getInteger("currentPaddColor")&&colk != ExtremePong.PREFS.getInteger("currentBallColor")) {
            table.add(buttonBack.getButton()).left().row();
            table.add("Choose","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).spaceBottom(50f).center();
            table.row();
            table.add(buttonBG.getButton()).center().expand().row();
        }
        else{
            table.add(buttonBack.getButton()).expand().left().row();
            table.add("Choose","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).spaceBottom(50f).expand().center();
            table.row();
        }


        if(colk != ExtremePong.PREFS.getInteger("currentBgColor"))table.add(buttonLay.getButton()).center().expand().row();
        if(colk != ExtremePong.PREFS.getInteger("currentBgColor"))table.add(buttonBALL.getButton()).center().expand().row();
        if(colk != ExtremePong.PREFS.getInteger("currentBgColor"))table.add(buttonPADD.getButton()).center().expand().row();


        stage.addActor(table);
    }

    @Override
    protected void handleImput() {
        Gdx.input.setInputProcessor(null);
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            gsm.pop();
            gsm.push(new PrizeState(gsm));
        }
        Gdx.input.setInputProcessor(stage);
        buttonBack.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker) {
                    gsm.pop();
                    gsm.push(new PrizeState(gsm));
                    buttonLocker = true;
                }
            }
        });
        if(colk != ExtremePong.PREFS.getInteger("currentBgColor")) {
            buttonBALL.getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!buttonLocker) {
                        ExtremePong.PREFS.putInteger("currentBallColor", colk);
                        ExtremePong.PREFS.flush();
                        gsm.pop();
                        gsm.push(new PrizeState(gsm));
                        buttonLocker = true;
                    }
                }
            });
            buttonLay.getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(!buttonLocker) {
                        ExtremePong.PREFS.putInteger("currentLayColor", colk);
                        ExtremePong.PREFS.flush();
                        gsm.pop();
                        gsm.push(new PrizeState(gsm));
                        buttonLocker = true;
                    }
                }
            });
            buttonPADD.getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(!buttonLocker) {
                        ExtremePong.PREFS.putInteger("currentPaddColor", colk);
                        ExtremePong.PREFS.flush();
                        gsm.pop();
                        gsm.push(new PrizeState(gsm));
                        buttonLocker = true;
                    }
                }
            });
        }
        if(colk != ExtremePong.PREFS.getInteger("currentLayColor")&&
                colk != ExtremePong.PREFS.getInteger("currentPaddColor")&&colk != ExtremePong.PREFS.getInteger("currentBallColor")) {
            buttonBG.getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!buttonLocker) {
                        ExtremePong.PREFS.putInteger("currentBgColor", colk);
                        ExtremePong.PREFS.flush();
                        gsm.pop();
                        gsm.push(new PrizeState(gsm));
                        buttonLocker = true;
                    }
                }
            });
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
        buttonBack.dispose();
        if(colk != ExtremePong.PREFS.getInteger("currentLayColor")&&
                colk != ExtremePong.PREFS.getInteger("currentPaddColor")&&colk != ExtremePong.PREFS.getInteger("currentBallColor")) {
            buttonBG.dispose();
        }
        if(colk != ExtremePong.PREFS.getInteger("currentBgColor"))
        {
            buttonPADD.dispose();
            buttonBALL.dispose();
            buttonLay.dispose();
        }
    }

    @Override
    public void pause() {

    }
}

