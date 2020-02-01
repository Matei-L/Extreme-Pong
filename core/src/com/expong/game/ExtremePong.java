package com.expong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.expong.game.States.GameStateManager;
import com.expong.game.States.IntroState;
import com.expong.game.States.PlayState;

public class ExtremePong extends ApplicationAdapter {
	// Game Screen
	public static final int SCREEN_WIDTH = 480;
	public static final int SCREEN_HEIGHT = 800;
	// Game -def variables
	public static Preferences PREFS;
	public static Skin SKIN;
	public static final String TITLE="Extreme Pong";
	public static MyColors COLORS;
	private GameStateManager gsm;
	private SpriteBatch sb;

	BanerAdHandler handler;

	public ExtremePong(BanerAdHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public void create () {
		//Game init.

		PREFS = Gdx.app.getPreferences("ExPong");
		if (!PREFS.contains("POINTS")) {
			PREFS.putInteger("POINTS",0);
			PREFS.flush();
		}
		if (!PREFS.contains("ncu")) {
			PREFS.putInteger("ncu",0);
			PREFS.flush();
		}
		if (!PREFS.contains("speedRatio")) {
			PREFS.putInteger("speedRatio", 50);
			PREFS.flush();
		}
		if (!PREFS.contains("highScore")) {
			PREFS.putInteger("highScore", 0);
			PREFS.flush();
		}
		if (!PREFS.contains("currentBall")) {
			PREFS.putInteger("currentBall", 1);
			PREFS.flush();
		}
		if (!PREFS.contains("currentPadd")) {
			PREFS.putInteger("currentPadd", 1);
			PREFS.flush();
		}
		if (!PREFS.contains("currentFont")) {
			PREFS.putString("currentFont","font.fnt");
			PREFS.flush();
		}
		if (!PREFS.contains("currentMusic")) {
			PREFS.putString("currentMusic","");
			PREFS.flush();
		}
		if (!PREFS.contains("isSound")) {
			PREFS.putBoolean("isSound",true);
			PREFS.flush();
		}
		if (!PREFS.contains("currentPaddColor")) {
			PREFS.putInteger("currentPaddColor", 2);
			PREFS.flush();
		}
		if (!PREFS.contains("currentBallColor")) {
			PREFS.putInteger("currentBallColor", 2);
			PREFS.flush();
		}
		if (!PREFS.contains("currentLayColor")) {
			PREFS.putInteger("currentLayColor", 2);
			PREFS.flush();
		}
		if (!PREFS.contains("currentBgColor")) {
			PREFS.putInteger("currentBgColor", 1);
			PREFS.flush();
		}
		if (!PREFS.contains("addN")) {
			PREFS.putInteger("addN", 0);
			PREFS.flush();
		}

		COLORS =new MyColors();

		SKIN = new Skin(new TextureAtlas(Gdx.files.internal("pack.pack")));
		SKIN.add("currentFont",new BitmapFont(Gdx.files.internal(ExtremePong.PREFS.getString("currentFont"))),BitmapFont.class);

		sb = new SpriteBatch();

		gsm = new GameStateManager();
		gsm.push(new IntroState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(COLORS.getColor(PREFS.getInteger("currentBgColor")).r,
				COLORS.getColor(PREFS.getInteger("currentBgColor")).g,
				COLORS.getColor(PREFS.getInteger("currentBgColor")).b,
				COLORS.getColor(PREFS.getInteger("currentBgColor")).a);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(gsm.peek().getClass()!= PlayState.class)
		{
			handler.showAds(1);
		}
		else {
			handler.showAds(0);
		}
		if(PREFS.getInteger("addN")==5&& gsm.peek().getClass()!= PlayState.class) {
			handler.showAds(2);
		}

		gsm.update(Gdx.graphics.getDeltaTime());

		gsm.render(sb);
	}

	@Override
	public void pause() {
		super.pause();
		gsm.peek().pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
		SKIN.dispose();
		sb.dispose();
	}
}

