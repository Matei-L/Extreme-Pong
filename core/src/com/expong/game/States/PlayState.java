package com.expong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.expong.game.ExtremePong;
import com.expong.game.Sprites.Ball;
import com.expong.game.Sprites.MyTextButton;
import com.expong.game.Sprites.Paddle;

/**
 * Created by lipan on 6/20/2016.
 */
public class PlayState extends State {
    // State -def variables
    public Ball ball;
    public Paddle paddle1,paddle2;
    private BitmapFont font;
    private GlyphLayout layout;
    private GlyphLayout layout2;
    private int score;
    private boolean isplaying;
    public Stage stage;
    public MyTextButton buttonPause;
    public boolean buttonLocker;
    private float sballcolor;

    public PlayState(GameStateManager gsm) {
        //State init.
        super(gsm);
        Gdx.input.setCatchBackKey(true);
        cam.setToOrtho(false, ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT);

        paddle1 = new Paddle(cam.position.x-64,150,true);
        paddle2 = new Paddle(cam.position.x-64,ExtremePong.SCREEN_HEIGHT-50,false);
        ball = new Ball(cam.position.x-16,cam.position.y-16);
        sballcolor = 1;

        font = new BitmapFont(Gdx.files.internal(ExtremePong.PREFS.getString("currentFont")));
        font.setColor(ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")).r,
                ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")).g,
                ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")).b,
                ExtremePong.COLORS.getColor(ExtremePong.PREFS.getInteger("currentLayColor")).a/3);
        font.getData().setScale(1.0f);
        layout2 = new GlyphLayout(font, "SLIDE HERE");
        font.getData().setScale(2.50f);
        layout = new GlyphLayout(font, "0");

        score = 0;
        isplaying = false;

        stage = new Stage(new StretchViewport(ExtremePong.SCREEN_WIDTH, ExtremePong.SCREEN_HEIGHT));
        buttonPause = new MyTextButton("Pause",cam.position.x-ExtremePong.SCREEN_WIDTH/2+60,cam.position.y+ExtremePong.SCREEN_HEIGHT/2-90, MyTextButton.Type.MEDIUM);
        stage.addActor(buttonPause.getButton());

        buttonLocker = false;
    }
    @Override
    protected void handleImput() {
        Gdx.input.setInputProcessor(null);
        if(isplaying){
            if(Gdx.input.isTouched()){

                Vector3 touch;
                touch = new Vector3();
                cam.unproject(touch.set(Gdx.input.getX(),Gdx.input.getY(), 0));
                paddle1.update(touch.x);
            }
        }
        else
        {
            if(Gdx.input.justTouched()) {
                isplaying = true;
                font.getData().setScale(2.5f);
                if(buttonLocker)layout.setText(font, Integer.toString(score));
                else layout.setText(font,"0");
                buttonLocker = false;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            isplaying = false;
            gsm.push(new PauseState(gsm));
            buttonLocker = true;
        }

        Gdx.input.setInputProcessor(stage);
        if(buttonPause.getButton().isPressed())isplaying = false;
        buttonPause.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(!buttonLocker){
                    isplaying = false;
                    gsm.push(new PauseState(gsm));
                    buttonLocker = true;
                }
            }
        });

    }

    @Override
    public void update(float dt) {
        handleImput();
        if(isplaying) {
            paddle2.update(ball.getPosition().x);
            int point = ball.update(dt, paddle1, paddle2);
            if (point == 1) {
                score++;
                ExtremePong.PREFS.putInteger("POINTS",ExtremePong.PREFS.getInteger("POINTS")+1);
                ExtremePong.PREFS.flush();
                layout.setText(font, Integer.toString(score));
            } else if (point == 2) {
                ExtremePong.PREFS.putInteger("addN",ExtremePong.PREFS.getInteger("addN")+1);
                ExtremePong.PREFS.flush();
                gsm.pop();
                gsm.push(new GameOverState(gsm,score));
            }
        }
        if(ExtremePong.PREFS.getInteger("currentBall")==25||ExtremePong.PREFS.getInteger("currentPadd")==13){
            sballcolor +=4*dt;
            if((int)sballcolor == ExtremePong.PREFS.getInteger("currentBgColor"))sballcolor++;
            if(sballcolor>33) sballcolor = 1;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        stage.draw();
        sb.begin();

        if(!isplaying)
        {
            font.getData().setScale(1.0f);
            layout.setText(font,"Tap to play");
            font.draw(sb,layout2,cam.position.x-layout2.width/2,50+layout2.height);
        }
        font.draw(sb,layout,cam.position.x-layout.width/2,cam.position.y+layout.height);
        if(ExtremePong.PREFS.getInteger("currentBall")==25)
        {
            ExtremePong.COLORS.setColor((int)sballcolor,sb);
        }else
        {
            ExtremePong.COLORS.setColor(ExtremePong.PREFS.getInteger("currentBallColor"),sb);
        }
        sb.draw(ball.getBall(),ball.getPosition().x,ball.getPosition().y,ball.getDIMENSION()/2,ball.getDIMENSION()/2, ball.getDIMENSION(),ball.getDIMENSION(),1,1,ball.getRotation());

        if(ExtremePong.PREFS.getInteger("currentPadd")==13)
        {
            ExtremePong.COLORS.setColor((int)sballcolor,sb);
        }else
        {
            ExtremePong.COLORS.setColor(ExtremePong.PREFS.getInteger("currentPaddColor"),sb);
        }
        sb.draw(paddle1.getPaddle(),paddle1.getPosition().x,paddle1.getPosition().y,paddle1.getDIMENSIONS().x,paddle1.getDIMENSIONS().y);
        sb.draw(paddle2.getPaddle(),paddle2.getPosition().x,paddle2.getPosition().y,paddle2.getDIMENSIONS().x,paddle2.getDIMENSIONS().y);

        ExtremePong.COLORS.setColor(0,sb);

        sb.end();
    }
    @Override
    public void dispose() {
        ball.dispose();
        font.dispose();
        stage.dispose();
        buttonPause.dispose();
    }

    @Override
    public void pause() {
        if(isplaying) {
            isplaying = false;
            gsm.push(new PauseState(gsm));
            buttonLocker = true;
        }
    }
}

