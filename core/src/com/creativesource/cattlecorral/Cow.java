package com.creativesource.cattlecorral;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Cow extends Sprite {

    float worldWidth;
    Vector2 position;

    Viewport viewport;

    public Cow(Sprite sprite, Viewport viewport, float worldWidth) {
        super(sprite);
        this.viewport = viewport;
        this.worldWidth = worldWidth;
        init();
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void init() {
        setX(worldWidth / 2);
        setY(Constants.COW_HEIGHT);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            setX(getX() - delta * Constants.MOVEMENT_SPEED);
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            setX(getX() + delta * Constants.MOVEMENT_SPEED);
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            setY(getY() + delta * Constants.MOVEMENT_SPEED);
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            setY(getY() - delta * Constants.MOVEMENT_SPEED);
        }

        ensureInBounds();
    }

    private void ensureInBounds() {
        if (position.x - Constants.COW_WIDTH < 0) {
            position.x = Constants.COW_WIDTH;
        }
        if (position.x + Constants.COW_WIDTH > worldWidth) {
            position.x = worldWidth - Constants.COW_WIDTH;
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.BLACK);
        renderer.set(ShapeType.Filled);
        renderer.rect(position.x, position.y, Constants.COW_WIDTH, Constants.COW_HEIGHT);
    }
}