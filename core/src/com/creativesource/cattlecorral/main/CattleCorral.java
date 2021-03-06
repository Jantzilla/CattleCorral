package com.creativesource.cattlecorral.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.creativesource.cattlecorral.utils.Constants.Level;
import com.creativesource.cattlecorral.utils.Prefs;
import com.creativesource.cattlecorral.screen.LevelScreen;
import com.creativesource.cattlecorral.screen.PlayScreen;
import com.creativesource.cattlecorral.screen.StartScreen;

import java.util.ArrayList;


public class CattleCorral extends Game {

	private Music intro, play;
	public ArrayList<TextureAtlas> textureAtlases = new ArrayList<TextureAtlas>();

    @Override
	public void create() {
		Prefs prefs = new Prefs();

        if(prefs.hasSound())
        	playMusic();

        textureAtlases.add(new TextureAtlas("pack/cow.pack"));
        textureAtlases.add(new TextureAtlas("pack/pig.pack"));
        textureAtlases.add(new TextureAtlas("pack/sheep.pack"));

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
		intro = Gdx.audio.newMusic(Gdx.files.internal("audio/intro.wav"));
		play = Gdx.audio.newMusic(Gdx.files.internal("audio/play.wav"));

		intro.play();

		intro.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				play.setLooping(true);
				play.play();
			}
		});
	}

    @Override
    public void dispose() {
        super.dispose();
        intro.dispose();
        play.dispose();
        for(TextureAtlas atlas : textureAtlases)
            atlas.dispose();
    }

    public void stopMusic() {
	    intro.stop();
	    play.stop();

	    intro.dispose();
	    play.dispose();
    }
}