package com.expong.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.expong.game.ExtremePong;

import java.util.Random;


/**
 * Created by lipan on 6/21/2016.
 */
public class Ball {

    //ball limits
    private static final int DIMENSION = 32;
    private static final int SPEED_LIMIT= 1200;
    //ball-def variables
    private TextureRegion ball;
    private float speed = 625;
    private float rotation;
    private Vector2 position;
    private Vector2 velocity;
    //aid
    private int speedadd;
    private boolean bottomTouch;
    private Random rand;
    //effects
    private Sound sound1;

    public Ball(float x, float y)
    {
        //aid init.
        speedadd = 75;
        bottomTouch = false;
        rand = new Random();
        //ball init.
        if(ExtremePong.PREFS.getInteger("currentBall")==25){
            ball = new TextureRegion(ExtremePong.SKIN.getRegion("ball"+1));
        }
        else{
            ball = new TextureRegion(ExtremePong.SKIN.getRegion("ball"+Integer.toString(ExtremePong.PREFS.getInteger("currentBall"))));
        }
        position = new Vector2(x,y);
        velocity = new Vector2();
        velocity.set(rand.nextInt(4*ExtremePong.SCREEN_WIDTH)-3*ExtremePong.SCREEN_WIDTH/2-x,-y);
        //effects
        if(ExtremePong.PREFS.getBoolean("isSound")){
            sound1 = Gdx.audio.newSound(Gdx.files.internal("sound1.mp3"));
        }
    }
    //Returns 2 for lose, 1 for point and 0 for default
    public int update(float dt,Paddle paddle1,Paddle paddle2)
    {
        //update the position and velocity
        velocity.nor();
        velocity.scl(relativeSpeed());
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        //position control
        if (position.x <= 0) position.x = 0;
        else if(position.x >=ExtremePong.SCREEN_WIDTH-DIMENSION)position.x = ExtremePong.SCREEN_WIDTH - DIMENSION;
        if (position.y <= 0) position.y = 0;
        else if(position.y >=ExtremePong.SCREEN_HEIGHT-50-DIMENSION)position.y = ExtremePong.SCREEN_HEIGHT - 50-DIMENSION;
        // bounce on the x coord.
        if(position.x == 0 || position.x == ExtremePong.SCREEN_WIDTH-DIMENSION) {
            velocity.x = (-1) * velocity.x;
        }
        //top paddle collision
        if(position.y==ExtremePong.SCREEN_HEIGHT-50-DIMENSION){
            if(ExtremePong.PREFS.getBoolean("isSound"))
            {
                sound1.play(0.5f);
            }
            newDirection(paddle2);
            velocity.y = (-1) * velocity.y;
            bottomTouch = false;
        }
        //rotation update
        velocity.scl(1/dt);
        rotation = (rotation +speed*dt/3)%360;
        //check if you lost
        if(position.y==0) {
            return 2;
        }
        //bottom paddle collision
        return bottomColision(paddle1);
    }

    private int bottomColision(Paddle paddle1) {
        //hitboxes init.
        Rectangle rectangle;
        rectangle = new Rectangle(paddle1.getPosition().x, paddle1.getPosition().y, paddle1.getDIMENSIONS().x, paddle1.getDIMENSIONS().y);
        Circle circle;
        if(bottomTouch){
            circle = new Circle(0,0, DIMENSION / 2);
            if(position.y>paddle1.getPosition().y-DIMENSION/2)position.y = paddle1.getPosition().y-DIMENSION/2;
        }
        else circle = new Circle(position.x + DIMENSION / 2, position.y + DIMENSION / 2, DIMENSION / 2);
        //check for collision
        if (Intersector.overlaps(circle, rectangle)) {
            if(position.x<=paddle1.getPosition().x&&position.x+DIMENSION>=paddle1.getPosition().x
                    &&position.y+DIMENSION/2<=paddle1.getPosition().y+paddle1.getDIMENSIONS().y) {
                //left colision
                position.x = paddle1.getPosition().x - DIMENSION;
                if (velocity.x > 0)velocity.x = velocity.x * (-1);
                else velocity.setAngle(200);
                bottomTouch = true;
                if (speed < SPEED_LIMIT) speed = (SPEED_LIMIT + speed) / 2;
            }
            else{
                if(position.x<=paddle1.getPosition().x+paddle1.getDIMENSIONS().x&&position.x+DIMENSION>=paddle1.getPosition().x+paddle1.getDIMENSIONS().x
                        &&position.y+DIMENSION/2<=paddle1.getPosition().y+paddle1.getDIMENSIONS().y){
                    //right colision
                    position.x=paddle1.getPosition().x+paddle1.getDIMENSIONS().x;
                    if(velocity.x<0)velocity.x=velocity.x*(-1);
                    else velocity.setAngle(340);
                    bottomTouch = true;
                    if (speed < SPEED_LIMIT) speed = (SPEED_LIMIT + speed) / 2;
                }
                else{
                    if (position.y <= paddle1.getPosition().y + paddle1.getDIMENSIONS().y && position.y + DIMENSION >= paddle1.getPosition().y + paddle1.getDIMENSIONS().y) {
                        //normal collision
                        //increase speed
                        if (speed < SPEED_LIMIT) speed += speedadd;
                        else
                        {
                            if(speed < 1500)speed+=30.0f/9.3f;
                            else speed = 1500;
                        }
                        //moving the ball to the edge of the paddle
                        velocity.nor();
                        while (Intersector.overlaps(circle, rectangle)) {
                            position.x -= velocity.x;
                            position.y -= velocity.y;
                            circle.setPosition(position.x + DIMENSION / 2, position.y + DIMENSION / 2);
                        }
                        velocity.scl(speed);
                        //bounce on the y coord.
                        velocity.y = (-1) * velocity.y;
                        if (ExtremePong.PREFS.getBoolean("isSound")) {
                            sound1.play(0.5f);
                        }
                        newDirection(paddle1);
                        return 1;
                    }
                    else {
                        //Moving ball under paddle to evite bugs
                        bottomTouch = true;
                        position.y = paddle1.getPosition().y-DIMENSION-1;
                    }
                }
            }
        }
        return 0;
    }
    //Change speed depending on the angle
    private float relativeSpeed()
    {
        float rot = velocity.angle();
        if(rot> 180.0f)rot-=180.0f;
        rot -= 90.0f;
        if(rot<0)rot = -rot;
        float Sscale = (40*rot)/90.0f;
        Sscale +=80;
        return (Sscale*speed)/100.0f;
    }
    //Gives the new direction of the paddle
    private void newDirection(Paddle paddle)
    {
        float point;
        if(paddle.isplayer) {
            point = (position.x + DIMENSION / 2 - paddle.getPosition().x) / paddle.getDIMENSIONS().x;
            point = 1 - point;
            if (position.x + DIMENSION / 2 < paddle.getPosition().x) point = 1.0f;
            else if (position.x + DIMENSION / 2 > paddle.getPosition().x + paddle.getDIMENSIONS().x - DIMENSION)
                point = 0.0f;
        }
        else
        {
            point = rand.nextFloat();
            point = 1 - point;
        }
        velocity.setAngle(30+point*120.0f);
    }

    public static int getDIMENSION() {return DIMENSION;}

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getBall() {
        return ball;
    }

    public float getRotation() {
        return rotation;
    }

    public void dispose(){
        if(ExtremePong.PREFS.getBoolean("isSound"))sound1.dispose();
    }

}

