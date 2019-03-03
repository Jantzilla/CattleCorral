package com.creativesource.cattlecorral;

import com.badlogic.gdx.Game;
import com.creativesource.cattlecorral.Constants.Level;


public class CattleCorral extends Game {

	@Override
	public void create() {
        showStartScreen();
	}

	public void showStartScreen() {
		setScreen(new StartScreen(this));
	}

	public void showLevelScreen() {
		setScreen(new LevelScreen(this));
	}

	public void showPlayScreen(Level level) {
		setScreen(new PlayScreen(this, level));
	}
}