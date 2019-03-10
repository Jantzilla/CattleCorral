package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

public class StartScreen extends InputAdapter implements Screen {

    CattleCorral game;

    Sprite sprite;
    SpriteBatch batch;
    FitViewport viewport;

    BitmapFont font;
    Prefs prefs;
    int score, randomDirection, animalSize, animalElevation, directionMultiplier, animalSpeed;
    Skin skin;
    TextButton playButton;
    Stage stage;
    Button soundButton;
    Label title, topScore;
    float animationTime;
    Animation<TextureRegion> anim;
    Texture grassTexture;

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

        Pixmap levels_background = new Pixmap(Gdx.files.internal("start_background_grass.png"));
        Pixmap pixmapNew = new Pixmap((int) viewport.getWorldWidth(), (int) viewport.getWorldHeight(), levels_background.getFormat());
        pixmapNew.drawPixmap(levels_background,
                0, 0, levels_background.getWidth(), levels_background.getHeight(),
                0, 0, pixmapNew.getWidth(), pixmapNew.getHeight()
        );
        grassTexture = new Texture(pixmapNew);

        font = new BitmapFont();
        font.getData().setScale(Constants.START_LABEL_SCALE);
        font.setColor(Constants.BACKGROUND_COLOR);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        Pixmap pixmap = new Pixmap(Gdx.files.internal("label_background.png"));
        Texture texture = new Texture(pixmap);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        title = new Label("Cattle Corral", skin, "title");
        title.getStyle().background = new Image(texture).getDrawable();
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

        createAnimal();
    }

    @Override
    public void render(float delta) {
        animationTime += delta;

        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Pixmap original = new Pixmap(Gdx.files.internal("signs.png"));
        Pixmap ropeImage = new Pixmap(800, 200, original.getFormat());
        ropeImage.drawPixmap(original,
                0, 0, original.getWidth(), original.getHeight(),
                0, 0, ropeImage.getWidth(), ropeImage.getHeight()
        );
        Texture texture = new Texture(ropeImage);

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(grassTexture, 0, 0);
        batch.draw(texture, viewport.getWorldWidth() / 2 - 400, viewport.getWorldHeight() - 120);

        animateAnimal();

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

    public void createAnimal() {
        sprite = null;

        String direction;
        float startLocation;
        int randomAnimal = new Random().nextInt(3);
        randomDirection = new Random().nextInt(2);
        int randomDepth = new Random().nextInt(3);

        if(randomDirection == 0) {
            direction = "left";
            startLocation = viewport.getWorldWidth();
            directionMultiplier = -1;
        } else {
            direction = "right";
            startLocation = - 600;
            directionMultiplier = 1;
        }

        switch (randomDepth) {
            case 0:
                animalSize = 300;
                animalElevation = 575;
                animalSpeed = 10;
                break;
            case 1:
                animalSize = 500;
                animalElevation = 250;
                animalSpeed = 30;
                break;
            default:
                animalSize = 800;
                animalElevation = - 300;
                animalSpeed = 40;
        }

        animationTime = 0;
        anim = new Animation<TextureRegion>(0.05f, game.textureAtlases.get(randomAnimal).findRegions(direction));
        anim.setPlayMode(Animation.PlayMode.LOOP);
        sprite = new Sprite(game.textureAtlases.get(randomAnimal).findRegion(direction));
        sprite.setX(startLocation);

    }

    public void animateAnimal() {

        sprite.setRegion((TextureRegion) anim.getKeyFrame(animationTime));
        sprite.setX(sprite.getX() + directionMultiplier * animationTime * animalSpeed);
        batch.draw(sprite,sprite.getX(),animalElevation, animalSize, animalSize);

        if((randomDirection == 0 && sprite.getX() < - 600) || (randomDirection == 1 && sprite.getX() > viewport.getWorldWidth())) {
            createAnimal();
        }

    }
}