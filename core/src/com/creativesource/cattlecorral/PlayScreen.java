package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.creativesource.cattlecorral.Constants.Difficulty;

import java.util.ArrayList;
import java.util.Random;

public class PlayScreen extends InputAdapter implements Screen {
    Difficulty difficulty;
    CattleCorral game;
    ShapeRenderer shapeRenderer;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    TextureAtlas textureAtlas;
    ExtendViewport extendViewport;
    OrthographicCamera camera;
    float worldWidth,worldHeight;
    ArrayList<Animal> animals = new ArrayList<Animal>();
    ArrayList<TiledMapTileLayer> tiledMapTileLayers;
    ArrayList<TextureAtlas> textureAtlases = new ArrayList<TextureAtlas>();

    public PlayScreen (CattleCorral game, Difficulty difficulty) {
        this.game = game;
        this.difficulty = difficulty;
    }

    @Override
    public void dispose () {
        shapeRenderer.dispose();
        tiledMapRenderer.dispose();
        textureAtlas.dispose();
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
        tiledMapTileLayers = new ArrayList<TiledMapTileLayer>();
        for(MapLayer layer : tiledMap.getLayers()) {
            tiledMapTileLayers.add((TiledMapTileLayer) layer);
        }
        tiledMap.getLayers().get(5).setVisible(false);
        tiledMap.getLayers().get(6).setVisible(false);
        tiledMap.getLayers().get(7).setVisible(false);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        textureAtlases.add(new TextureAtlas("cow.pack"));
        textureAtlases.add(new TextureAtlas("pig.pack"));
        textureAtlases.add(new TextureAtlas("sheep.pack"));

        for(int i = 0; i < textureAtlases.size(); i++) {
            Animation<TextureRegion> up, left, down, right;
            up = new Animation<TextureRegion>(0.05f, textureAtlases.get(i).findRegions("up"));
            up.setPlayMode(Animation.PlayMode.LOOP);
            left = new Animation<TextureRegion>(0.05f, textureAtlases.get(i).findRegions("left"));
            left.setPlayMode(Animation.PlayMode.LOOP);
            down = new Animation<TextureRegion>(0.05f, textureAtlases.get(i).findRegions("down"));
            down.setPlayMode(Animation.PlayMode.LOOP);
            right = new Animation<TextureRegion>(0.05f, textureAtlases.get(i).findRegions("right"));
            right.setPlayMode(Animation.PlayMode.LOOP);

            for (int o = 0; o < 10; o++) {
                switch (i) {
                    case 0:
                        animals.add(new Cow(up, left, down, right, extendViewport, worldWidth, tiledMapTileLayers, 80 * new Random().nextInt(100)));
                        break;
                    case 1:
                        animals.add(new Pig(up, left, down, right, extendViewport, worldWidth, tiledMapTileLayers, 80 * new Random().nextInt(100)));
                        break;
                    case 2:
                        animals.add(new Sheep(up, left, down, right, extendViewport, worldWidth, tiledMapTileLayers, 80 * new Random().nextInt(100)));
                }
            }
        }
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        tiledMapRenderer.getBatch().begin();
        for(Animal animal : animals)
            animal.draw(tiledMapRenderer.getBatch());
        tiledMapRenderer.getBatch().end();
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
        if((screenX > 275 && screenX < 350) && (screenY > 275 && screenY < 350)) {
            if (tiledMap.getLayers().get(2).isVisible()) {
                tiledMap.getLayers().get(2).setVisible(false);
                tiledMap.getLayers().get(7).setVisible(true);
            } else {
                tiledMap.getLayers().get(2).setVisible(true);
                tiledMap.getLayers().get(7).setVisible(false);
            }
        } else if((screenX > 275 && screenX < 350) && (screenY > 50 && screenY < 125)) {
            if (tiledMap.getLayers().get(1).isVisible()) {
                tiledMap.getLayers().get(1).setVisible(false);
                tiledMap.getLayers().get(5).setVisible(true);
            } else {
                tiledMap.getLayers().get(1).setVisible(true);
                tiledMap.getLayers().get(5).setVisible(false);
            }
        } else if((screenX > 285 && screenX < 365) && (screenY > 175 && screenY < 250)) {
            if (tiledMap.getLayers().get(3).isVisible()) {
                tiledMap.getLayers().get(3).setVisible(false);
                tiledMap.getLayers().get(6).setVisible(true);
            } else {
                tiledMap.getLayers().get(3).setVisible(true);
                tiledMap.getLayers().get(6).setVisible(false);
            }
        }
        return true;
    }
}
