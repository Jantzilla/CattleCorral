package com.creativesource.cattlecorral;

import com.badlogic.gdx.Game;
import com.creativesource.cattlecorral.Constants.Difficulty;


public class CattleCorral extends Game {

	@Override
	public void create() {
		showDifficultyScreen();
	}

	public void showDifficultyScreen() {
		setScreen(new StartScreen(this));
	}

	public void showIciclesScreen(Difficulty difficulty) {
		setScreen(new PlayScreen(this, difficulty));
	}
}