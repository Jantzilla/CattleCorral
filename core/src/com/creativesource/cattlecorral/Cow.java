package com.creativesource.cattlecorral;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Cow extends Sprite {

    TiledMapTileLayer tiledMapTileLayer;
    float worldWidth, animationTime = 0;
    Animation up,left,down,right;
    Viewport viewport;

    public Cow(Animation up, Animation left, Animation down, Animation right, Viewport viewport, float worldWidth, TiledMapTileLayer tiledMapTileLayer) {
        super((TextureRegion) up.getKeyFrame(0));
        this.viewport = viewport;
        this.worldWidth = worldWidth;
        this.tiledMapTileLayer = tiledMapTileLayer;
        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        init();
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void init() {
        setX(worldWidth / 2 - (getWidth() / 2));
        setY(Constants.COW_HEIGHT);
    }

    public void update(float delta) {
        animationTime += delta;

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            if(!isCellBlocked(getX() + (getWidth() / 2),getY() + (getY() / 2))) {
                setX(getX() - delta * Constants.MOVEMENT_SPEED);
                setRegion((TextureRegion) left.getKeyFrame(animationTime));
            }
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if(!isCellBlocked(getX() + (getWidth() / 1.9f),getY() + (getY() / 2))) {
                setX(getX() + delta * Constants.MOVEMENT_SPEED);
                setRegion((TextureRegion) right.getKeyFrame(animationTime));
            }
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            if(!isCellBlocked(getX() + (getWidth() / 2),getY() + getHeight() / 2.9f)) {
                setY(getY() + delta * Constants.MOVEMENT_SPEED);
                setRegion((TextureRegion) up.getKeyFrame(animationTime));
            }
        } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            if(!isCellBlocked(getX() + (getWidth() / 2),getY() + getHeight() / 3)) {
                setY(getY() - delta * Constants.MOVEMENT_SPEED);
                setRegion((TextureRegion) down.getKeyFrame(animationTime));
            }
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