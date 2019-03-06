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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class StartScreen extends InputAdapter implements Screen {

    CattleCorral game;

    SpriteBatch batch;
    FitViewport viewport;

    BitmapFont font, titleFont, scoreFont;
    Prefs prefs;
    int score;
    Skin skin;
    TextButton playButton;
    Stage stage;
    Button soundButton;

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

        titleFont = new BitmapFont();
        titleFont.getData().setScale(Constants.TITLE_LABEL_SCALE);
        titleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        font = new BitmapFont();
        font.getData().setScale(Constants.START_LABEL_SCALE);
        font.setColor(Constants.BACKGROUND_COLOR);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        scoreFont = new BitmapFont();
        scoreFont.getData().setScale(Constants.SCORE_LABEL_SCALE);
        scoreFont.setColor(Color.BLACK);
        scoreFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        playButton = new TextButton("Play", skin);
        playButton.setSize(300,160);
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

        stage = new Stage(viewport);
        stage.addActor(playButton);
        stage.addActor(soundButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        final GlyphLayout gameTitle = new GlyphLayout(font, "Cattle Corral");
        titleFont.draw(batch, "Cattle Corral", viewport.getWorldWidth() / 2 , (float) (viewport.getWorldHeight() / 1.2  + gameTitle.height / 2), 0, Align.center, false);

        final GlyphLayout topScore = new GlyphLayout(font, "TOP SCORE: ");
        scoreFont.draw(batch, "TOP SCORE: " + score, viewport.getWorldWidth() / 2 , (float) (viewport.getWorldHeight() / 1.7  + topScore.height / 2), 0, Align.center, false);

        batch.end();

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