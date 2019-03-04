package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.creativesource.cattlecorral.Constants.Level;
import static com.creativesource.cattlecorral.Constants.GAME_PAUSED;
import static com.creativesource.cattlecorral.Constants.GAME_RESUMED;
import static com.creativesource.cattlecorral.Constants.GAME_COMPLETE;

import java.util.ArrayList;
import java.util.Collections;

public class PlayScreen extends InputAdapter implements Screen {
    Level level;
    CattleCorral game;
    ShapeRenderer shapeRenderer;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    TextureAtlas textureAtlas;
    StretchViewport stretchViewport, hudViewport;
    OrthographicCamera camera;
    float worldWidth,worldHeight, gameSpan = Constants.GAME_SPAN_1;
    ArrayList<Animal> animals = new ArrayList<Animal>();
    ArrayList<TiledMapTileLayer> tiledMapTileLayers;
    ArrayList<TextureAtlas> textureAtlases = new ArrayList<TextureAtlas>();
    int topScore, totalCorraled, points, gameStatus = 1;
    ArrayList<Integer> integers = new ArrayList<Integer>();
    BitmapFont font;
    Stage stage;
    Image semiTL;
    Table table;
    TextButton resume;

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
        hudViewport = new StretchViewport(worldWidth,worldHeight,camera);
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

        stage=new Stage(stretchViewport);
        stage.getCamera().position.set(worldWidth/2,worldHeight/2,0);

        table = new Table();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        resume = new TextButton("Resume", skin);
        TextButton retry = new TextButton("Retry", skin);
        TextButton exit = new TextButton("Exit", skin);

        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resume();
            }
        });

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(level);
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showStartScreen();
            }
        });

        table.add(resume).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(retry).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture texture1=new Texture(pixmap);
        pixmap.dispose();

        semiTL=new Image(texture1);
        semiTL.setSize(worldWidth,worldHeight);
        semiTL.getColor().a=.8f;

        table.setPosition(worldWidth / 2 - table.getWidth() / 2,worldHeight / 2 - (table.getHeight() / 2));

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

        totalCorraled = 0;

        for (int i = 0; i < animals.size(); i++) {
            if(animals.get(i).isCorraled)
                totalCorraled++;
            if(animals.get(i).getY() > worldWidth) {
                animals.remove(i);
                break;
            }
            animals.get(i).update(tiledMapRenderer.getBatch(), delta, level.speed);
        }

        if(animals.size() == 0 || totalCorraled == animals.size()) {
            gameStatus = GAME_COMPLETE;
        }

        topScore = Math.max(topScore, points);

        final String levelText = "Level " + level.label;
        final String hudText = levelText + "\n" + Constants.SCORE_LABEL + points;

        font.draw(tiledMapRenderer.getBatch(), hudText, hudViewport.getWorldWidth() - Constants.HUD_MARGIN, hudViewport.getWorldHeight() - Constants.HUD_MARGIN,
                0, Align.right, false);

        tiledMapRenderer.getBatch().end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stretchViewport.update(width,height,false);
        stage.getViewport().update(width, height, true);
        stage.getCamera().position.set(worldWidth/2,worldHeight/2,0);
        hudViewport.update(width, height, false);
        hudViewport.getCamera().position.set(worldWidth/2,worldHeight/2,0);
        hudViewport.getCamera().update();
        stretchViewport.getCamera().position.set(worldWidth/2,worldHeight/2,0);
        stretchViewport.getCamera().update();
        font.getData().setScale(Math.min(width, height) / Constants.HUD_FONT_REFERENCE_SCREEN_SIZE);
    }

    @Override
    public void pause() {
        gameStatus = GAME_PAUSED;
        Gdx.input.setInputProcessor(stage);
        stage.addActor(semiTL);
        stage.addActor(table);
    }

    @Override
    public void resume() {
        gameStatus = GAME_RESUMED;
        Gdx.input.setInputProcessor(this);
        if(semiTL != null) {
            semiTL.remove();
            table.remove();
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touch = new Vector3(screenX,screenY, 0);
        camera.unproject(touch);

        if(Constants.GATE_ONE.contains(new Vector2(touch.x,touch.y))) {
            resume();
            if (tiledMap.getLayers().get(2).isVisible()) {
                tiledMap.getLayers().get(2).setVisible(false);
                tiledMap.getLayers().get(7).setVisible(true);
            } else {
                tiledMap.getLayers().get(2).setVisible(true);
                tiledMap.getLayers().get(7).setVisible(false);
            }
        } else if(Constants.GATE_THREE.contains(new Vector2(touch.x,touch.y))) {
            resume();
            if (tiledMap.getLayers().get(1).isVisible()) {
                tiledMap.getLayers().get(1).setVisible(false);
                tiledMap.getLayers().get(5).setVisible(true);
            } else {
                tiledMap.getLayers().get(1).setVisible(true);
                tiledMap.getLayers().get(5).setVisible(false);
            }
        } else if(Constants.GATE_TWO.contains(new Vector2(touch.x,touch.y))) {
            resume();
            if (tiledMap.getLayers().get(3).isVisible()) {
                tiledMap.getLayers().get(3).setVisible(false);
                tiledMap.getLayers().get(6).setVisible(true);
            } else {
                tiledMap.getLayers().get(3).setVisible(true);
                tiledMap.getLayers().get(6).setVisible(false);
            }
        } else
            if(gameStatus == GAME_PAUSED)
                resume();
            else
                pause();
        return true;
    }
}
