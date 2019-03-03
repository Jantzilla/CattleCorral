package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.creativesource.cattlecorral.Constants.Level;
import static com.creativesource.cattlecorral.Constants.GAME_PAUSED;
import static com.creativesource.cattlecorral.Constants.GAME_RESUMED;

import java.util.ArrayList;
import java.util.Collections;

public class PlayScreen extends InputAdapter implements Screen {
    Level level;
    CattleCorral game;
    ShapeRenderer shapeRenderer;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    TextureAtlas textureAtlas;
    StretchViewport stretchViewport;
    OrthographicCamera camera;
    float worldWidth,worldHeight, gameSpan = Constants.GAME_SPAN_1;
    ArrayList<Animal> animals = new ArrayList<Animal>();
    ArrayList<TiledMapTileLayer> tiledMapTileLayers;
    ArrayList<TextureAtlas> textureAtlases = new ArrayList<TextureAtlas>();
    int topScore, points, gameStatus = 1;
    ArrayList<Integer> integers = new ArrayList<Integer>();
    ScreenViewport hudViewport;
    BitmapFont font;

    public PlayScreen (CattleCorral game, Level level) {
        this.game = game;
        this.level = level;

        if(level.speed > 50)
            gameSpan = Constants.GAME_SPAN_2;
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
        stretchViewport =new StretchViewport(worldWidth,worldHeight,camera);
        hudViewport = new ScreenViewport();
        font = new BitmapFont();
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

        for(int i = 0; i < gameSpan; i++) {
            integers.add(i);
        }

        Collections.shuffle(integers);

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

            for (int o = 0; o < level.spawnRate; o++) {
                switch (i) {
                    case 0:
                        animals.add(new Cow(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75 * integers.get(o)));
                        break;
                    case 1:
                        animals.add(new Pig(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75 * integers.get((int) (o + gameSpan / 3))));
                        break;
                    case 2:
                        animals.add(new Sheep(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75 * integers.get((int) (o + gameSpan / 1.5))));
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
        switch (gameStatus) {
            case GAME_PAUSED:
                delta = 0;
                break;
        }
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        tiledMapRenderer.getBatch().begin();
        for (int i = 0; i < animals.size(); i++) {
            if(animals.get(i).getY() > worldWidth) {
                animals.remove(i);
                break;
            }
            animals.get(i).update(tiledMapRenderer.getBatch(), delta, level.speed);
        }

        topScore = Math.max(topScore, points);

        final String leftHudText = "Level " + level.label;
        final String rightHudText = Constants.SCORE_LABEL + points + "\n" + Constants.TOP_SCORE_LABEL + topScore;

        font.draw(tiledMapRenderer.getBatch(), leftHudText, Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN);
        font.draw(tiledMapRenderer.getBatch(), rightHudText, hudViewport.getWorldWidth() - Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        tiledMapRenderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        stretchViewport.update(width,height,false);
        stretchViewport.getCamera().position.set(worldWidth/2,worldHeight/2,0);
        stretchViewport.getCamera().update();
        hudViewport.update(width, height, true);
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
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
        Vector3 touch = new Vector3(screenX,screenY, 0);
        camera.unproject(touch);
        if(Constants.GATE_ONE.contains(new Vector2(touch.x,touch.y))) {
            gameStatus = GAME_RESUMED;
            if (tiledMap.getLayers().get(2).isVisible()) {
                tiledMap.getLayers().get(2).setVisible(false);
                tiledMap.getLayers().get(7).setVisible(true);
            } else {
                tiledMap.getLayers().get(2).setVisible(true);
                tiledMap.getLayers().get(7).setVisible(false);
            }
        } else if(Constants.GATE_THREE.contains(new Vector2(touch.x,touch.y))) {
            gameStatus = GAME_RESUMED;
            if (tiledMap.getLayers().get(1).isVisible()) {
                tiledMap.getLayers().get(1).setVisible(false);
                tiledMap.getLayers().get(5).setVisible(true);
            } else {
                tiledMap.getLayers().get(1).setVisible(true);
                tiledMap.getLayers().get(5).setVisible(false);
            }
        } else if(Constants.GATE_TWO.contains(new Vector2(touch.x,touch.y))) {
            gameStatus = GAME_RESUMED;
            if (tiledMap.getLayers().get(3).isVisible()) {
                tiledMap.getLayers().get(3).setVisible(false);
                tiledMap.getLayers().get(6).setVisible(true);
            } else {
                tiledMap.getLayers().get(3).setVisible(true);
                tiledMap.getLayers().get(6).setVisible(false);
            }
        } else
            if(gameStatus == GAME_PAUSED)
                gameStatus = GAME_RESUMED;
            else
                gameStatus = GAME_PAUSED;
        return true;
    }
}
