package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class StartScreen extends InputAdapter implements Screen {

    CattleCorral game;

    SpriteBatch batch;
    FitViewport viewport;

    BitmapFont font;
    Prefs prefs;
    int score;
    Skin skin;
    TextButton playButton;
    Stage stage;
    Button soundButton;
    Label title, topScore;

    public StartScreen(CattleCorral game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        prefs = new Prefs();
        score = prefs.getTopScore();

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
        font.getData().setScale(Constants.START_LABEL_SCALE);
        font.setColor(Constants.BACKGROUND_COLOR);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        title = new Label("Cattle Corral", skin, "title");
        title.setSize(1300,250);
        title.setFontScale(8);
        title.setAlignment(Align.center);
        title.setPosition(viewport.getWorldWidth() / 2 - title.getWidth() / 2, (float) (viewport.getWorldHeight() / 1.2 - (title.getHeight() / 2)));

        topScore = new Label("TOP SCORE: " + score, skin, "optional");
        topScore.setSize(800,100);
        topScore.setFontScale(6);
        topScore.setAlignment(Align.center);
        topScore.setPosition(viewport.getWorldWidth() / 2 - topScore.getWidth() / 2, (float) (viewport.getWorldHeight() / 1.7 - (topScore.getHeight() / 2)));

        playButton = new TextButton("Play", skin, "round");
        playButton.getStyle().downFontColor.set(Color.WHITE);
        playButton.setSize(325,185);
        playButton.getLabel().setFontScale(5);
        playButton.setPosition(viewport.getWorldWidth() / 2 - playButton.getWidth() / 2,viewport.getWorldHeight() / 3 - (playButton.getHeight() / 2));

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showLevelScreen();
            }
        });

        soundButton = new Button(skin,"sound");
        soundButton.setSize(120,120);
        soundButton.setPosition(viewport.getWorldWidth() - (soundButton.getWidth() + 25),25);

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(prefs.hasSound()) {
                    prefs.setSound(false);
                    game.stopMusio();
                } else {
                    prefs.setSound(true);
                    game.playMusic();
                }
            }
        });

        if(prefs.hasSound()) {
            soundButton.setChecked(true);
        }

        stage = new Stage(viewport);
        stage.addActor(title);
        stage.addActor(topScore);
        stage.addActor(playButton);
        stage.addActor(soundButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void dispose() {

    }
}