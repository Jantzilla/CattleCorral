package com.creativesource.cattlecorral;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Cow extends Sprite {

    TiledMapTileLayer tiledMapTileLayer;
    float worldWidth;

    Viewport viewport;

    public Cow(Sprite sprite, Viewport viewport, float worldWidth, TiledMapTileLayer tiledMapTileLayer) {
        super(sprite);
        this.viewport = viewport;
        this.worldWidth = worldWidth;
        this.tiledMapTileLayer = tiledMapTileLayer;
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
            if(!isCellBlocked(getX(),getY()))
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
        if (getX() - Constants.COW_WIDTH < 0) {
            setX(Constants.COW_WIDTH);
        }
        if (getX() + Constants.COW_WIDTH > worldWidth) {
            setX(worldWidth - Constants.COW_WIDTH);
        }
    }

    private boolean isCellBlocked(float x, float y) {
        Cell cell = tiledMapTileLayer.getCell((int) (x / tiledMapTileLayer.getTileWidth()),(int) (y / tiledMapTileLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }
}