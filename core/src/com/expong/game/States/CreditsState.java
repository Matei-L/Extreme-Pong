package com.expong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.expong.game.ExtremePong;
import com.expong.game.Sprites.MyTextButton;

/**
 * Created by lipan on 8/16/2016.
 */
public class CreditsState extends State {
    // Credits -def variables
    private Stage stage;
    private Table table;
    private MyTextButton buttonBack;
    private boolean buttonLocker;
    private Label label,label1;

    protected CreditsState(GameStateManager gsm) {
        super(gsm);
        //State's init
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);
        Gdx.input.setCatchBackKey(true);
        //Credits init.
        buttonBack = new MyTextButton("Back", MyTextButton.Type.NORMAL);
        table = new Table(ExtremePong.SKIN);
        stage = new Stage(new StretchViewport(ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT));
        table.setFillParent(true);
        label1 =  new Label("Thanks to:\n\n",ExtremePong.SKIN,
                "currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
        label1.setAlignment(Align.center);
        label1.setFontScale(0.75f);
        label = new Label("soundbible.com\nfreeiconspng.com\n1001freedownloads.com\nLibGDX",ExtremePong.SKIN,
                "currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
        label.setAlignment(Align.center);
        label.setFontScale(0.5f);
        table.pad(15);
        table.add(buttonBack.getButton()).top().left().row();
        table.add(label1).center().spaceTop(175f).row();
        table.add(label).center().expand().top().row();
        stage.addActor(table);

        buttonLocker = false;

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void handleImput() {
        //check if we leave the state
        buttonBack.getButton().addListener(new ChangeListener() {
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
    }

    @Override
    public void pause() {

    }
}

