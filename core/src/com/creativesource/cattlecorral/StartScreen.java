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
import com.creativesource.cattlecorral.Constants.Difficulty;

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
        renderer.rect(Constants.LEVEL_1[0], Constants.LEVEL_1[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_2[0], Constants.LEVEL_2[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_3[0], Constants.LEVEL_3[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_4[0], Constants.LEVEL_4[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_5[0], Constants.LEVEL_5[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_6[0], Constants.LEVEL_6[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_7[0], Constants.LEVEL_7[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_8[0], Constants.LEVEL_8[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_9[0], Constants.LEVEL_9[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);
        renderer.rect(Constants.LEVEL_10[0], Constants.LEVEL_10[1], Constants.BUTTON_SIZE, Constants.BUTTON_SIZE);

        renderer.end();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        final GlyphLayout levelLabel1 = new GlyphLayout(font, Constants.LEVEL_1_LABEL);
        font.draw(batch, Constants.LEVEL_1_LABEL, Constants.LEVEL_1[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_1[1] + Constants.BUTTON_SIZE / 2 + levelLabel1.height / 2, 0, Align.right, false);

        final GlyphLayout levelLabel2 = new GlyphLayout(font, Constants.MEDIUM_LABEL);
        font.draw(batch, Constants.LEVEL_2_LABEL, Constants.LEVEL_2[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_2[1] + Constants.BUTTON_SIZE / 2 + levelLabel2.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel3 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_3_LABEL, Constants.LEVEL_3[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_3[1] + Constants.BUTTON_SIZE / 2 + levelLabel3.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel4 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_4_LABEL, Constants.LEVEL_4[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_4[1] + Constants.BUTTON_SIZE / 2 + levelLabel4.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel5 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_5_LABEL, Constants.LEVEL_5[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_5[1] + Constants.BUTTON_SIZE / 2 + levelLabel5.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel6 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_6_LABEL, Constants.LEVEL_6[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_6[1] + Constants.BUTTON_SIZE / 2 + levelLabel6.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel7 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_7_LABEL, Constants.LEVEL_7[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_7[1] + Constants.BUTTON_SIZE / 2 + levelLabel7.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel8 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_8_LABEL, Constants.LEVEL_8[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_8[1] + Constants.BUTTON_SIZE / 2 + levelLabel8.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel9 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_9_LABEL, Constants.LEVEL_9[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_9[1] + Constants.BUTTON_SIZE / 2 + levelLabel9.height / 2, 0, Align.center, false);

        final GlyphLayout levelLabel10 = new GlyphLayout(font, Constants.HARD_LABEL);
        font.draw(batch, Constants.LEVEL_10_LABEL, Constants.LEVEL_10[0] + Constants.BUTTON_SIZE / 2, Constants.LEVEL_10[1] + Constants.BUTTON_SIZE / 2 + levelLabel10.height / 2, 0, Align.center, false);


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

        if (worldTouch.dst(Constants.EASY_CENTER) < Constants.START_BUBBLE_RADIUS) {
            game.showPlayScreen(Difficulty.EASY);
        }

        if (worldTouch.dst(Constants.MEDIUM_CENTER) < Constants.START_BUBBLE_RADIUS) {
            game.showPlayScreen(Difficulty.MEDIUM);
        }

        if (worldTouch.dst(Constants.HARD_CENTER) < Constants.START_BUBBLE_RADIUS) {
            game.showPlayScreen(Difficulty.HARD);
        }

        return true;
    }
}