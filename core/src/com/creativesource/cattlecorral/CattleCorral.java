package com.creativesource.cattlecorral;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.creativesource.cattlecorral.Constants.Level;


public class CattleCorral extends Game {

	Music intro, play;

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

	public void playMusic() {
		intro = Gdx.audio.newMusic(Gdx.files.internal("intro.wav"));
		play = Gdx.audio.newMusic(Gdx.files.internal("play.wav"));

		intro.play();

		intro.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				play.setLooping(true);
				play.play();
			}
		});
	}
}