package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class StartScreen extends InputAdapter implements Screen {

    CattleCorral game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    FitViewport viewport;

    BitmapFont font;

    public StartScreen(CattleCorral game) {
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();

        viewport = new FitViewport(Constants.START_WORLD_SIZE, Constants.START_WORLD_SIZE);
        Gdx.input.setInputProcessor(this);

        font = new BitmapFont();
        font.getData().setScale(Constants.START_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(viewport.getCamera().combined);

        renderer.begin(ShapeType.Filled);

        renderer.setColor(Constants.EASY_COLOR);
        renderer.rect(viewport.getWorldWidth() / 2 - Constants.PLAY_BUTTON_SIZE / 2, viewport.getWorldHeight() / 2 - Constants.PLAY_BUTTON_SIZE / 2, Constants.PLAY_BUTTON_SIZE, Constants.PLAY_BUTTON_SIZE);

        renderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        final GlyphLayout levelLabel1 = new GlyphLayout(font, "Play");
        font.draw(batch, "Play", viewport.getWorldWidth() / 2 , viewport.getWorldHeight() / 2  + levelLabel1.height / 2, 0, Align.center, false);

        batch.end();
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
        renderer.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));

        if((worldTouch.x > viewport.getWorldWidth() / 2 - Constants.PLAY_BUTTON_SIZE / 2 && worldTouch.x < viewport.getWorldWidth() / 2 + Constants.PLAY_BUTTON_SIZE / 2)
                && ((worldTouch.y > viewport.getWorldHeight() / 2 - Constants.PLAY_BUTTON_SIZE / 2 && worldTouch.y < viewport.getWorldHeight() / 2 + Constants.PLAY_BUTTON_SIZE / 2))) {
            game.showLevelScreen();

        }

        return true;
    }
}