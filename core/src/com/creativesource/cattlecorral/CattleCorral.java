package com.creativesource.cattlecorral;

import com.badlogic.gdx.Game;
import com.creativesource.cattlecorral.Constants.Difficulty;


public class CattleCorral extends Game {

	@Override
	public void create() {
        showStartScreen();
	}

	public void showStartScreen() {
		setScreen(new StartScreen(this));
	}

	public void showPlayScreen(Difficulty difficulty) {
		setScreen(new PlayScreen(this, difficulty));
	}
}