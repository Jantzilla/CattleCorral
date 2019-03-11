package com.creativesource.cattlecorral.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.creativesource.cattlecorral.entity.Animal;
import com.creativesource.cattlecorral.main.CattleCorral;
import com.creativesource.cattlecorral.utils.Constants;
import com.creativesource.cattlecorral.utils.Constants.Level;
import com.creativesource.cattlecorral.entity.Cow;
import com.creativesource.cattlecorral.entity.Pig;
import com.creativesource.cattlecorral.utils.Prefs;
import com.creativesource.cattlecorral.entity.Sheep;

import static com.creativesource.cattlecorral.utils.Constants.GAME_PAUSED;
import static com.creativesource.cattlecorral.utils.Constants.GAME_RESUMED;
import static com.creativesource.cattlecorral.utils.Constants.GAME_COMPLETE;
import static com.creativesource.cattlecorral.utils.Constants.SINGLE_SCORE;

import java.util.ArrayList;
import java.util.Collections;

public class PlayScreen extends InputAdapter implements Screen {
    private Level level;
    private CattleCorral game;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private StretchViewport stretchViewport, hudViewport;
    private OrthographicCamera camera;
    private float worldWidth;
    public float worldHeight;
    private float gameSpan = Constants.GAME_SPAN_1;
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private int topScore;
    public int points;
    private int gameStatus = 1;
    private ArrayList<Integer> integers = new ArrayList<Integer>();
    private Stage stage;
    private Image semiTL;
    private Table table;
    private TextButton resume;
    private ClickListener clickListener, resumeClickListener;
    public Prefs prefs;
    public Sound cow;
    public Sound sheep;
    public Sound pig;
    private Label completeLabel;
    private Label summaryLabel;
    private Label scoreLabel;

    public PlayScreen (CattleCorral game, Level level) {
        this.game = game;
        this.level = level;

        if(level.speed > 50)
            gameSpan = Constants.GAME_SPAN_2;
    }

    @Override
    public void dispose () {
        tiledMapRenderer.dispose();
        tiledMap.dispose();
    }

    @Override
    public void show() {
        float tileWidth=32,tileHeight=32;
        float mapWidth=30,mapHeight=20;

        worldWidth=tileWidth*mapWidth;
        worldHeight=tileHeight*mapHeight;

        camera = new OrthographicCamera();
        stretchViewport =new StretchViewport(worldWidth,worldHeight,camera);
        hudViewport = new StretchViewport(worldWidth,worldHeight,camera);
        camera.update();
        tiledMap = new TmxMapLoader().load("map/new_map.tmx");
        ArrayList<TiledMapTileLayer> tiledMapTileLayers = new ArrayList<TiledMapTileLayer>();
        for(MapLayer layer : tiledMap.getLayers()) {
            tiledMapTileLayers.add((TiledMapTileLayer) layer);
        }

        tiledMap.getLayers().get(1).setVisible(false);
        tiledMap.getLayers().get(5).setVisible(false);
        tiledMap.getLayers().get(2).setVisible(false);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        for(int i = 0; i < gameSpan; i++) {
            integers.add(i);
        }

        Collections.shuffle(integers);

        for(int i = 0; i < game.textureAtlases.size(); i++) {
            Animation<TextureRegion> up, left, down, right;
            up = new Animation<TextureRegion>(0.05f, game.textureAtlases.get(i).findRegions("up"));
            up.setPlayMode(Animation.PlayMode.LOOP);
            left = new Animation<TextureRegion>(0.05f, game.textureAtlases.get(i).findRegions("left"));
            left.setPlayMode(Animation.PlayMode.LOOP);
            down = new Animation<TextureRegion>(0.05f, game.textureAtlases.get(i).findRegions("down"));
            down.setPlayMode(Animation.PlayMode.LOOP);
            right = new Animation<TextureRegion>(0.05f, game.textureAtlases.get(i).findRegions("right"));
            right.setPlayMode(Animation.PlayMode.LOOP);

            for (int o = 0; o < level.spawnRate; o++) {
                switch (i) {
                    case 0:
                        animals.add(new Cow(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75 * integers.get(o)));
                        if(o == 0) {
                            Cow firstCow = new Cow(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75);
                            firstCow.active = false;
                            firstCow.setX(650);
                            firstCow.setY(50);
                            animals.add(firstCow);
                        }
                        break;
                    case 1:
                        animals.add(new Pig(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75 * integers.get((int) (o + gameSpan / 3))));
                        if(o == 0) {
                            Pig firstPig = new Pig(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75);
                            firstPig.active = false;
                            firstPig.setX(450);
                            firstPig.setY(450);
                            animals.add(firstPig);
                        }
                        break;
                    case 2:
                        animals.add(new Sheep(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75 * integers.get((int) (o + gameSpan / 1.5))));
                        if(o == 0) {
                            Sheep firstSheep = new Sheep(this, up, left, down, right, stretchViewport, worldWidth, tiledMapTileLayers, 75);
                            firstSheep.active = false;
                            firstSheep.setX(200);
                            firstSheep.setY(50);
                            animals.add(firstSheep);
                        }
                }
            }
        }

        stage=new Stage(stretchViewport);
        stage.getCamera().position.set(worldWidth/2,worldHeight/2,0);

        table = new Table();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Label levelLabel = new Label("Level " + level.label, skin, "title-plain");
        levelLabel.setFontScale(2);
        levelLabel.setSize(200,60);
        levelLabel.setAlignment(Align.center);
        levelLabel.setPosition(0, stretchViewport.getWorldHeight() - (levelLabel.getHeight() + 10));

        stage.addActor(levelLabel);

        scoreLabel = new Label("", skin, "title-plain");
        scoreLabel.setSize(200,200);
        scoreLabel.setFontScale(1.5f);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setPosition(stretchViewport.getWorldWidth() * 0.81f - scoreLabel.getWidth() / 2, stretchViewport.getWorldHeight() - (scoreLabel.getHeight() + 25));

        stage.addActor(scoreLabel);

        Button soundButton = new Button(skin, "sound");
        soundButton.setSize(65,70);
        soundButton.setPosition(stretchViewport.getWorldWidth() - (soundButton.getWidth() + 15),15);

        stage.addActor(soundButton);

        completeLabel = new Label("Well Done!", skin, "title-plain");
        completeLabel.setSize(1200,120);
        completeLabel.setFontScale(3);
        completeLabel.setAlignment(Align.center);
        completeLabel.setPosition(stretchViewport.getWorldWidth() / 2 - completeLabel.getWidth() / 2, (float) (stretchViewport.getWorldHeight() / 1.2 - (completeLabel.getHeight() / 2)));

        summaryLabel = new Label("You scored: ", skin, "title-plain");
        summaryLabel.setSize(1200,200);
        summaryLabel.setFontScale(1.5f);
        summaryLabel.setAlignment(Align.center);
        summaryLabel.setPosition(stretchViewport.getWorldWidth() / 2 - summaryLabel.getWidth() / 2, (float) (stretchViewport.getWorldHeight() / 6.5 - (summaryLabel.getHeight() / 2)));


        resume = new TextButton("Resume", skin);
        resume.getStyle().downFontColor.set(Color.WHITE);
        resume.getLabel().setFontScale(2);
        TextButton retry = new TextButton("Retry", skin);
        retry.getStyle().downFontColor.set(Color.WHITE);
        retry.getLabel().setFontScale(2);
        TextButton exit = new TextButton("Exit", skin);
        exit.getStyle().downFontColor.set(Color.WHITE);
        exit.getLabel().setFontScale(2);

        resumeClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                resume();
            }
        };

        clickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.values()[level.ordinal() + 1]);
            }
        };

        retry.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                prefs.setScore(points);
                game.showPlayScreen(level);
            }
        });

        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                prefs.setScore(points);
                game.showStartScreen();
            }
        });

        table.add(resume).size(150,80).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(retry).size(150,80).fillX().uniformX();
        table.row();
        table.add(exit).size(150,80).fillX().uniformX();

        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture texture1=new Texture(pixmap);
        pixmap.dispose();

        semiTL=new Image(texture1);
        semiTL.setSize(worldWidth,worldHeight);
        semiTL.getColor().a=.8f;

        table.setPosition(worldWidth / 2 - table.getWidth() / 2,worldHeight / 2 - (table.getHeight() / 2));

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        prefs = new Prefs();

        cow = Gdx.audio.newSound(Gdx.files.internal("audio/cow.wav"));
        sheep = Gdx.audio.newSound(Gdx.files.internal("audio/sheep.mp3"));
        pig = Gdx.audio.newSound(Gdx.files.internal("audio/pig.wav"));

        if(prefs.hasSound())
            soundButton.setChecked(true);

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
            case GAME_COMPLETE:
                delta = 0;
                gameCompleted();
                break;
        }
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        tiledMapRenderer.getBatch().begin();

        int totalCorraled = 0;

        for (int i = 0; i < animals.size(); i++) {
            if(animals.get(i).active && animals.get(i).isCorraled)
                totalCorraled++;
            if(animals.get(i).getX() < - 200) {
                animals.remove(i);
                break;
            }
            animals.get(i).update(tiledMapRenderer.getBatch(), delta, level.speed);
        }

        if(animals.size() == 3 || totalCorraled == animals.size() - 3) {
            gameStatus = GAME_COMPLETE;
        }

        topScore = Math.max(topScore, points);

        scoreLabel.setText(Constants.SCORE_LABEL + "\n" + points);

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
    }

    @Override
    public void pause() {
        gameStatus = GAME_PAUSED;
        stage.addActor(semiTL);
        stage.addActor(table);
        resume.addListener(resumeClickListener);
    }

    @Override
    public void resume() {
        gameStatus = GAME_RESUMED;
        if(semiTL != null) {
            semiTL.remove();
            table.remove();
            resume.removeListener(resumeClickListener);
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
            if (tiledMap.getLayers().get(4).isVisible()) {
                tiledMap.getLayers().get(4).setVisible(false);
                tiledMap.getLayers().get(1).setVisible(true);
            } else {
                tiledMap.getLayers().get(4).setVisible(true);
                tiledMap.getLayers().get(1).setVisible(false);
            }
        } else if(Constants.GATE_THREE.contains(new Vector2(touch.x,touch.y))) {
            resume();
            if (tiledMap.getLayers().get(6).isVisible()) {
                tiledMap.getLayers().get(6).setVisible(false);
                tiledMap.getLayers().get(5).setVisible(true);
            } else {
                tiledMap.getLayers().get(6).setVisible(true);
                tiledMap.getLayers().get(5).setVisible(false);
            }
        } else if(Constants.GATE_TWO.contains(new Vector2(touch.x,touch.y))) {
            resume();
            if (tiledMap.getLayers().get(7).isVisible()) {
                tiledMap.getLayers().get(7).setVisible(false);
                tiledMap.getLayers().get(2).setVisible(true);
            } else {
                tiledMap.getLayers().get(7).setVisible(true);
                tiledMap.getLayers().get(2).setVisible(false);
            }
        } else
            if(gameStatus == GAME_PAUSED)
                resume();
            else
                pause();
        return true;
    }

    private void gameCompleted() {
        prefs.setScore(points);
        int animalsMissed = (int)((level.spawnRate * 3 * SINGLE_SCORE - points) / 20);
        if(animalsMissed == 1)
            summaryLabel.setText("You missed " + animalsMissed + " animal.");
        else
            summaryLabel.setText("You missed " + animalsMissed + " animals.");

        if(points >= (level.spawnRate * 3 * SINGLE_SCORE) && level.ordinal() < 9) {
            completeLabel.setText("Well Done!");
            completeLabel.setColor(Color.GOLD);
            resume.setText("Next");
            resume.addListener(clickListener);
            stage.addActor(semiTL);
            stage.addActor(table);
        } else {
            completeLabel.setText("Almost...");
            table.removeActor(resume);
            stage.addActor(semiTL);
            stage.addActor(table);
            stage.addActor(summaryLabel);
        }
        stage.addActor(completeLabel);
    }
}
