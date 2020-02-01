package com.expong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.expong.game.ExtremePong;
import com.expong.game.MyColors;
import com.expong.game.Sprites.MyPictureButton;
import com.expong.game.Sprites.MyTextButton;

/**
 * Created by lipan on 7/12/2016.
 */
public class PrizeState extends State {
    // State -def variables
    private static final int numButtons = 25,numPaddles = 13;
    private Stage stage;
    private Table table,table2,table3;
    private Array <MyPictureButton> Buttons;
    private Array <MyPictureButton> Paddles;
    private Array <MyPictureButton> MyCol;
    private MyTextButton buttonBack;
    private boolean buttonLocker;
    private ScrollPane scrollPane;
    private Table scrollTable;
    private float rotation;
    private Label label;
    private float sballcolor;

    protected PrizeState(GameStateManager gsm) {
        //State init.
        super(gsm);
        rotation = 90;
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);
        Gdx.input.setCatchBackKey(true);

        stage = new Stage(new StretchViewport(ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT));
        table = new Table(ExtremePong.SKIN);
        buttonBack = new MyTextButton("Back", MyTextButton.Type.NORMAL);
        table3 = new Table(ExtremePong.SKIN);
        table3.setBounds(0,700,480,100);
        table3.pad(15);
        table3.add(buttonBack.getButton()).left().top().expandX();
        label = new Label(Integer.toString(ExtremePong.PREFS.getInteger("POINTS")),ExtremePong.SKIN,"currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")));
        table3.add(label).right().top().expandX();
        stage.addActor(table3);

        table.setBounds(0,0,480,700);
        scrollTable = new Table();
        scrollTable.setBounds(0,0,480,700);

        table.pad(15);
        table.add("BALLS:","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).colspan(4).expand().spaceBottom(50f);
        table.row();

        Buttons = new Array<MyPictureButton>();
        sballcolor=1;

        for(int i=0;i<numButtons;i++){
            if (!ExtremePong.PREFS.contains("ub"+Integer.toString(i))) {
                if(i==0) ExtremePong.PREFS.putBoolean("ub"+Integer.toString(i), true);
                else ExtremePong.PREFS.putBoolean("ub"+Integer.toString(i), false);
                ExtremePong.PREFS.flush();
            }
            if(i+1==numButtons){
                Buttons.add(new MyPictureButton(ExtremePong.SKIN.getDrawable("ball" + Integer.toString(1)), ExtremePong.SKIN.getDrawable("x"), ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")),
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentBallColor")), 0.5f, 13, ExtremePong.PREFS.getBoolean("ub" + Integer.toString(i))));
                table.add(Buttons.get(i).getButton()).width(450).height(112.5f).colspan(4).row();
            }
            else{
                Buttons.add(new MyPictureButton(ExtremePong.SKIN.getDrawable("ball" + Integer.toString(i + 1)), ExtremePong.SKIN.getDrawable("x"), ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")),
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentBallColor")), 0.5f, (i / 4 + 1) * 25, ExtremePong.PREFS.getBoolean("ub" + Integer.toString(i))));
                table.add(Buttons.get(i).getButton()).width(112.5f).height(112.5f);
                if(0==(i+1)%4){
                    table.row();
                }
            }
        }


        table.row();
        table2 = new Table(ExtremePong.SKIN);
        table2.add("PADDLES:","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).colspan(3).expand().spaceBottom(50f);
        table2.row();

        Paddles = new Array<MyPictureButton>();

        for(int i=0;i<numPaddles;i++) {
            if (!ExtremePong.PREFS.contains("up"+Integer.toString(i))) {
                if(i==0)ExtremePong.PREFS.putBoolean("up"+Integer.toString(i), true);
                else ExtremePong.PREFS.putBoolean("up"+Integer.toString(i), false);
                ExtremePong.PREFS.flush();
            }
            if(i+1==numPaddles)
            {
                Paddles.add(new MyPictureButton(ExtremePong.SKIN.getDrawable("pad" + Integer.toString(1)), ExtremePong.SKIN.getDrawable("x"), ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")),
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentPaddColor")), 0.85f, 23, ExtremePong.PREFS.getBoolean("up" + Integer.toString(i))));
                table2.add(Paddles.get(i).getButton()).width(450f).height(150f).colspan(3).row();
            }
            else {
                Paddles.add(new MyPictureButton(ExtremePong.SKIN.getDrawable("pad" + Integer.toString(i + 1)), ExtremePong.SKIN.getDrawable("x"), ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")),
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentPaddColor")), 0.85f, (i / 3 + 1) * 25, ExtremePong.PREFS.getBoolean("up" + Integer.toString(i))));
                table2.add(Paddles.get(i).getButton()).width(150f).height(150f);
                if (0 == (i + 1) % 3) {
                    table2.row();
                }
            }
        }


        table2.row();
        table2.add("COLORS:","currentFont",ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor"))).colspan(3).expand().spaceBottom(50f).spaceTop(50.0f);
        table2.row();

        MyCol = new Array<MyPictureButton>();

        for(int i=0;i<ExtremePong.COLORS.numColors;i++) {
            if (!ExtremePong.PREFS.contains("uc"+Integer.toString(i))) {
                if(i==0||i==1)ExtremePong.PREFS.putBoolean("uc"+Integer.toString(i), true);
                else ExtremePong.PREFS.putBoolean("uc"+Integer.toString(i), false);
                ExtremePong.PREFS.flush();
            }

            MyCol.add(new MyPictureButton(ExtremePong.SKIN.getDrawable("c"), ExtremePong.SKIN.getDrawable("c"),ExtremePong.COLORS.getColor(i+1),ExtremePong.COLORS.getColor(i+1),
                    1.0f,((ExtremePong.PREFS.getInteger("ncu"))/3+1)*25,ExtremePong.PREFS.getBoolean("uc"+Integer.toString(i))));
            table2.add(MyCol.get(i).getButton()).width(150).height(150);
            if (0 == (i + 1) % 3) {
                table2.row();
            }
        }


        table.add(table2).colspan(4).spaceTop(50.0f).padBottom(100.0f);
        scrollPane = new ScrollPane(table);
        scrollPane.setScrollingDisabled(true,false);
        scrollTable.add(scrollPane).expand();
        stage.addActor(scrollTable);

        Gdx.input.setInputProcessor(stage);

        buttonLocker = false;
    }

    @Override
    protected void handleImput() {

        Gdx.input.setInputProcessor(stage);
        if(Gdx.input.justTouched())buttonLocker = false;


        for(int i=0;i<numButtons;i++){
            final int ii = i;
            Buttons.get(i).getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(!buttonLocker) {
                        if(Buttons.get(ii).getIsDisabled()) {
                            Buttons.get(ii).setDisabled(false);
                            label.setText(Integer.toString(ExtremePong.PREFS.getInteger("POINTS")));
                            if(!Buttons.get(ii).getIsDisabled()){
                                ExtremePong.PREFS.putBoolean("ub"+Integer.toString(ii), true);
                                ExtremePong.PREFS.flush();
                            }
                        }
                        if(!Buttons.get(ii).getIsDisabled()) {
                            ExtremePong.PREFS.putInteger("currentBall", ii + 1);
                            ExtremePong.PREFS.flush();
                        }
                        buttonLocker = true;
                    }
                }
            });
        }


        for(int i=0;i<numPaddles;i++){
            final int ii = i;
            Paddles.get(i).getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(!buttonLocker) {
                        if (Paddles.get(ii).getIsDisabled()) {
                            Paddles.get(ii).setDisabled(false);
                            label.setText(Integer.toString(ExtremePong.PREFS.getInteger("POINTS")));
                            if (!Paddles.get(ii).getIsDisabled()){
                                ExtremePong.PREFS.putBoolean("up"+Integer.toString(ii), true);
                                ExtremePong.PREFS.flush();
                            }
                        }
                        if (!Paddles.get(ii).getIsDisabled()){
                            ExtremePong.PREFS.putInteger("currentPadd", ii + 1);
                            ExtremePong.PREFS.flush();
                        }
                        buttonLocker = true;
                    }
                }
            });
        }


        for(int i=0;i<ExtremePong.COLORS.numColors;i++){
            final int ii = i;
            MyCol.get(i).getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(!buttonLocker) {
                        if(MyCol.get(ii).getIsDisabled()) {
                            MyCol.get(ii).setDisabled(false);
                            label.setText(Integer.toString(ExtremePong.PREFS.getInteger("POINTS")));
                            if(!MyCol.get(ii).getIsDisabled()){
                                ExtremePong.PREFS.putBoolean("uc"+Integer.toString(ii), true);
                                ExtremePong.PREFS.putInteger("ncu",ExtremePong.PREFS.getInteger("ncu")+1);
                                ExtremePong.PREFS.flush();
                            }
                        }
                        if(!MyCol.get(ii).getIsDisabled()) {
                            gsm.pop();
                            gsm.push(new ColorState(gsm, ii + 1));
                        }
                        buttonLocker = true;
                    }
                }
            });
        }


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

        scrollPane.act(dt);
        handleImput();

        for(int i=0;i<Buttons.size;i++){
            Buttons.get(i).getButton().getImage().setOrigin(Align.center);
            Buttons.get(i).getButton().getImage().rotateBy(rotation*dt);
            if(i+1 == ExtremePong.PREFS.getInteger("currentBall")){
                Buttons.get(i).getButton().getImage().setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentBallColor")));
            }
            else{
                Buttons.get(i).getButton().getImage().setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentBallColor")).r,
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentBallColor")).g,
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentBallColor")).b,
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentBallColor")).a*0.5f);
            }
            if(ExtremePong.PREFS.getInteger("currentBall")==numButtons){
                sballcolor +=0.25f*dt;
                if((int)sballcolor == ExtremePong.PREFS.getInteger("currentBgColor"))sballcolor++;
                if(sballcolor>33) sballcolor = 1;
                Buttons.get(numButtons-1).getButton().getImage().setColor(ExtremePong.COLORS.getColor((int)sballcolor));
            }
        }

        for(int i=0;i<Paddles.size;i++) {
            Paddles.get(i).getButton().getImage().setOrigin(Align.center);
            if(i+1 == ExtremePong.PREFS.getInteger("currentPadd")){
                Paddles.get(i).getButton().getImage().setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentPaddColor")));
            }
            else{
                Paddles.get(i).getButton().getImage().setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentPaddColor")).r,
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentPaddColor")).g,
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentPaddColor")).b,
                        ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentPaddColor")).a*0.5f);
            }
            if(ExtremePong.PREFS.getInteger("currentPadd")==numPaddles){
                if(ExtremePong.PREFS.getInteger("currentBall")!= numButtons)
                {
                    sballcolor +=0.25f*dt;
                    if((int)sballcolor == ExtremePong.PREFS.getInteger("currentBgColor"))sballcolor++;
                    if(sballcolor>33) sballcolor = 1;
                }
                Paddles.get(numPaddles-1).getButton().getImage().setColor(ExtremePong.COLORS.getColor((int)sballcolor));
            }

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
        buttonBack.dispose();
    }

    @Override
    public void pause() {

    }
}

