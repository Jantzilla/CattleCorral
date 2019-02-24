package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.creativesource.cattlecorral.Constants.Difficulty;

public class PlayScreen extends InputAdapter implements Screen {
    Difficulty difficulty;
    CattleCorral game;
    ShapeRenderer shapeRenderer;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    ExtendViewport extendViewport;
    OrthographicCamera camera;
    float worldWidth,worldHeight;
    Cow cow;

    public PlayScreen (CattleCorral game, Difficulty difficulty) {
        this.game = game;
        this.difficulty = difficulty;
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        tiledMapRenderer.dispose();
        tiledMap.dispose();
    }

    @Override
    public void show() {
        float tileWidth=32,tileHeight=32;
        float mapWidth=20,mapHeight=20;

        worldWidth=tileWidth*mapWidth;
        worldHeight=tileHeight*mapHeight;

        camera = new OrthographicCamera();
        extendViewport =new ExtendViewport(worldWidth,worldHeight,camera);
        camera.update();
        tiledMap = new TmxMapLoader().load("cattle_corral_test_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        shapeRenderer = new ShapeRenderer();
        cow = new Cow(extendViewport, worldWidth);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        cow.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        shapeRenderer.begin(ShapeType.Filled);
        cow.render(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width,height,false);
        extendViewport.getCamera().position.set(worldWidth/2,worldHeight/2,0);
        extendViewport.getCamera().update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
