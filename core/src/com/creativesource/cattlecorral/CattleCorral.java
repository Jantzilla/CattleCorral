package com.creativesource.cattlecorral;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CattleCorral extends ApplicationAdapter {
//	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	Texture img;
	private float[] vertices;

	@Override
	public void create () {
//		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		vertices = new float[]{315,0,315,100,50,100,50,400,550,400,550,50,800,50,800,5,500,5,500,350,100,350,100,150,365,150,365,0};
//		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.polyline(vertices);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(325,5,15, 30);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
//		batch.dispose();
//		img.dispose();
		shapeRenderer.dispose();
	}
}
