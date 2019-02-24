package com.creativesource.cattlecorral;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class CattleCorral extends ApplicationAdapter implements InputProcessor {
//	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	ExtendViewport extendViewport;
	OrthographicCamera camera;
	float worldWidth,worldHeight;
	Texture img;

	@Override
	public void create () {
		float tileWidth=32,tileHeight=32;
		float mapWidth=20,mapHeight=20;

		worldWidth=tileWidth*mapWidth;
		worldHeight=tileHeight*mapHeight;

//		batch = new SpriteBatch();
        camera = new OrthographicCamera();
		extendViewport =new ExtendViewport(worldWidth,worldHeight,camera);
        camera.update();
        tiledMap = new TmxMapLoader().load("cattle_corral_test_map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		shapeRenderer = new ShapeRenderer();
//		img = new Texture("badlogic.jpg");
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(332,5,15, 30);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
		shapeRenderer.dispose();
		tiledMapRenderer.dispose();
		tiledMap.dispose();
	}

	@Override
	public void resize(int width, int height) {
		extendViewport.update(width,height,false);
		extendViewport.getCamera().position.set(worldWidth/2,worldHeight/2,0);
		extendViewport.getCamera().update();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
