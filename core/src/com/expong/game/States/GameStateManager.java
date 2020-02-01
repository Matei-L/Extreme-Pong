package com.expong.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by lipan on 6/20/2016.
 */
public class GameStateManager {
    //it's a stack of States
    private Stack<State> States;
    public GameStateManager(){
        States = new Stack<State>();
    }

    public void push(State state){States.push(state);}

    public void pop(){States.pop().dispose();}

    public void update(float dt){States.peek().update(dt);}

    public boolean empty(){return States.empty();}
    public State peek(){return States.peek();}

    public void render(SpriteBatch sb){
        States.peek().render(sb);
    }

}

