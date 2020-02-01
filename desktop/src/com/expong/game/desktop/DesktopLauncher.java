package com.expong.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.expong.game.BanerAdHandler;
import com.expong.game.ExtremePong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = ExtremePong.SCREEN_WIDTH;
		config.height = ExtremePong.SCREEN_HEIGHT;
		new LwjglApplication(new ExtremePong(new BanerAdHandler() {
			@Override
			public void showAds(int show) {

			}
		}), config);
	}
}
