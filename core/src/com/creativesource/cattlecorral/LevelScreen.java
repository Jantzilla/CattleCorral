package com.creativesource.cattlecorral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.creativesource.cattlecorral.Constants.Level;

import static com.creativesource.cattlecorral.Constants.GAME_SPAN_1;
import static com.creativesource.cattlecorral.Constants.SINGLE_SCORE;

public class LevelScreen extends InputAdapter implements Screen {

    CattleCorral game;

    ShapeRenderer renderer;
    SpriteBatch batch;
    StretchViewport viewport;

    BitmapFont font, titleFont;
    Button backButton, soundButton;
    TextButton levelOneButton, levelTwoButton, levelThreeButton, levelFourButton, levelFiveButton
            ,levelSixButton, levelSevenButton, levelEightButton, levelNineButton, levelTenButton;
    Table table, tableBackground;
    Stage stage;
    Texture texture;
    TextureRegion textureRegion;
    TextureRegionDrawable texRegionDrawable;
    int topScore;
    Prefs prefs;

    public LevelScreen(CattleCorral game) {
        this.game = game;
    }

    @Override
    public void show() {
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();
        Gdx.input.setCatchBackKey(true);

        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.input.setInputProcessor(this);

        titleFont = new BitmapFont();
        titleFont.getData().setScale(Constants.TITLE_LABEL_SCALE);
        titleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        font = new BitmapFont();
        font.getData().setScale(Constants.START_LABEL_SCALE);
        font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

        prefs = new Prefs();

        topScore = prefs.getTopScore();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        levelOneButton = new TextButton("1",skin,"round");
        levelOneButton.setSize(250, 150);
        levelOneButton.getLabel().setFontScale(4);
        levelTwoButton = new TextButton("2",skin,"round");
        levelTwoButton.getLabel().setFontScale(4);
        levelThreeButton = new TextButton("3",skin,"round");
        levelThreeButton.getLabel().setFontScale(4);
        levelFourButton = new TextButton("4",skin,"round");
        levelFourButton.getLabel().setFontScale(4);
        levelFiveButton = new TextButton("5",skin,"round");
        levelFiveButton.getLabel().setFontScale(4);
        levelSixButton = new TextButton("6",skin,"round");
        levelSixButton.getLabel().setFontScale(4);
        levelSevenButton = new TextButton("7",skin,"round");
        levelSevenButton.getLabel().setFontScale(4);
        levelEightButton = new TextButton("8",skin,"round");
        levelEightButton.getLabel().setFontScale(4);
        levelNineButton = new TextButton("9",skin,"round");
        levelNineButton.getLabel().setFontScale(4);
        levelTenButton = new TextButton("10",skin,"round");
        levelTenButton.getLabel().setFontScale(4);

        backButton = new Button(skin,"left");
        backButton.setSize(100, 100);
        backButton.setPosition(25,viewport.getWorldHeight() - (backButton.getHeight() + 25));

        soundButton = new Button(skin,"sound");
        soundButton.setSize(120, 120);
        soundButton.setPosition(viewport.getWorldWidth() - (soundButton.getWidth() + 25),25);

        levelOneButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.ONE);
            }
        });
        levelTwoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.TWO);
            }
        });
        levelThreeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.THREE);
            }
        });
        levelFourButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.FOUR);
            }
        });
        levelFiveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.FIVE);
            }
        });
        levelSixButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.SIX);
            }
        });
        levelSevenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.SEVEN);
            }
        });
        levelEightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.EIGHT);
            }
        });
        levelNineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.NINE);
            }
        });
        levelTenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showPlayScreen(Level.TEN);
            }
        });
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.showStartScreen();
            }
        });

        table = new Table();
        tableBackground = new Table();

        for(int i = 0; i < 11; i++) {
            if(i == 5) {
                tableBackground.row();
            } else
                tableBackground.add(new TextButton("",skin,"round")).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        }

        Pixmap pixmap = new Pixmap(Gdx.files.internal("lock.png"));
        Pixmap pixmap100 = new Pixmap(100, 100, pixmap.getFormat());
        pixmap100.drawPixmap(pixmap,
                0, 0, pixmap.getWidth(), pixmap.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture texture = new Texture(pixmap100);
        textureRegion = new TextureRegion(texture);
        texRegionDrawable = new TextureRegionDrawable(textureRegion);

        table.add(levelOneButton).size(250, 150).padRight(10).padBottom(10);
        if(topScore == Level.ONE.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelTwoButton).size(250, 150).padRight(10).padBottom(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        if(topScore == Level.TWO.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelThreeButton).size(250, 150).padRight(10).padBottom(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        if(topScore == Level.THREE.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelFourButton).size(250, 150).padRight(10).padBottom(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        if(topScore == Level.FOUR.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelFiveButton).size(250, 150).padBottom(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        table.row();
        if(topScore == Level.FIVE.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelSixButton).size(250, 150).padRight(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        if(topScore == Level.SIX.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelSevenButton).size(250, 150).padRight(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        if(topScore == Level.SEVEN.spawnRate * GAME_SPAN_1 * SINGLE_SCORE) {
            table.add(levelEightButton).size(250, 150).padRight(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        if(topScore == Level.EIGHT.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelNineButton).size(250, 150).padRight(10);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);
        if(topScore == Level.NINE.spawnRate * 3 * SINGLE_SCORE) {
            table.add(levelTenButton).size(250, 150);
        } else
            table.add(new ImageButton(texRegionDrawable)).size(levelOneButton.getWidth(), levelOneButton.getHeight()).padRight(10).padBottom(10);

        table.setPosition(viewport.getWorldWidth() / 2 - table.getWidth() / 2,viewport.getWorldHeight() / 2 - (table.getHeight() / 2));
        tableBackground.setPosition(viewport.getWorldWidth() / 2 - table.getWidth() / 2,viewport.getWorldHeight() / 2 - (table.getHeight() / 2));

        stage = new Stage(viewport);
        stage.getCamera().position.set(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,0);

        stage.addActor(tableBackground);
        stage.addActor(table);
        stage.addActor(backButton);
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

        final GlyphLayout gameTitle = new GlyphLayout(font, "Levels");
        titleFont.draw(batch, "Levels", viewport.getWorldWidth() / 2 , (float) (viewport.getWorldHeight() / 1.2  + gameTitle.height / 2), 0, Align.center, false);

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
        renderer.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            // Respond to the back button click here
            game.showStartScreen();
            return true;
        }
        return false;
    }
}