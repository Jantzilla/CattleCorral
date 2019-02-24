package com.creativesource.cattlecorral;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Cow {

    Vector2 position;

    Viewport viewport;

    public Cow(Viewport viewport) {
        this.viewport = viewport;
        init();
    }

    public void init() {
        position = new Vector2(viewport.getWorldWidth() / 2, Constants.COW_HEIGHT);
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            position.x -= delta * Constants.MOVEMENT_SPEED;
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            position.x += delta * Constants.MOVEMENT_SPEED;
        }

        position.x += -delta * Constants.MOVEMENT_SPEED;

        ensureInBounds();
    }

    private void ensureInBounds() {
        if (position.x - Constants.COW_WIDTH < 0) {
            position.x = Constants.COW_WIDTH;
        }
        if (position.x + Constants.COW_WIDTH > viewport.getWorldWidth()) {
            position.x = viewport.getWorldWidth() - Constants.COW_WIDTH;
        }
    }

    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.BLACK);
        renderer.set(ShapeType.Filled);
        renderer.rect(position.x, position.y, Constants.COW_WIDTH, Constants.COW_HEIGHT);
    }
}