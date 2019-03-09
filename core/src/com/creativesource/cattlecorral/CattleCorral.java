package com.creativesource.cattlecorral;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.creativesource.cattlecorral.Constants.Level;

import java.util.ArrayList;


public class CattleCorral extends Game {

	Music intro, play;
	Prefs prefs;
    ArrayList<TextureAtlas> textureAtlases = new ArrayList<TextureAtlas>();

    @Override
	public void create() {
        prefs = new Prefs();

        if(prefs.hasSound())
        	playMusic();

        textureAtlases.add(new TextureAtlas("cow.pack"));
        textureAtlases.add(new TextureAtlas("pig.pack"));
        textureAtlases.add(new TextureAtlas("sheep.pack"));

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

	public void stopMusio() {
	    intro.stop();
	    play.stop();

	    intro.dispose();
	    play.dispose();
    }
}