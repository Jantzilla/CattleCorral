package com.creativesource.cattlecorral;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public abstract class Animal extends Sprite {

    int startPosition;
    ArrayList<TiledMapTileLayer> tiledMapTileLayers;
    float worldWidth, animationTime = 0;
    Animation up,left,down,right;
    Viewport viewport;
    String lastDirection = "";
    abstract boolean isCellBlocked(float x, float y);

    public Animal(Animation up, Animation left, Animation down, Animation right, Viewport viewport, float worldWidth, ArrayList<TiledMapTileLayer> tiledMapTileLayers, int startPosition) {
        super((TextureRegion) up.getKeyFrame(0));
        this.viewport = viewport;
        this.worldWidth = worldWidth;
        this.tiledMapTileLayers = tiledMapTileLayers;
        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        this.startPosition = startPosition;
        init();
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void init() {
        setX(worldWidth / 2 - (getWidth() / 2));
        setY(- startPosition);
    }

    public void update(Batch batch, float delta) {
        draw(batch);
        animationTime += delta;

        if((lastDirection.equals("") || lastDirection.equals("up")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + 77)) {
            setY(getY() + delta * Constants.MOVEMENT_SPEED);
            setRegion((TextureRegion) up.getKeyFrame(animationTime));
            lastDirection = "up";

        } else if((lastDirection.equals("") || lastDirection.equals("left")) && !isCellBlocked(getX() + 20,getY() + (getHeight() / 2))) {
            setX(getX() - delta * Constants.MOVEMENT_SPEED);
            setRegion((TextureRegion) left.getKeyFrame(animationTime));
            lastDirection = "left";

        } else if((lastDirection.equals("") || lastDirection.equals("up")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + 77)) {
            setY(getY() + delta * Constants.MOVEMENT_SPEED);
            setRegion((TextureRegion) up.getKeyFrame(animationTime));
            lastDirection = "up";

        } else if((lastDirection.equals("") || lastDirection.equals("right")) && !isCellBlocked(getX() + (getWidth() - 20),getY() + (getHeight() / 2))) {
            setX(getX() + delta * Constants.MOVEMENT_SPEED);
            setRegion((TextureRegion) right.getKeyFrame(animationTime));
            lastDirection = "right";

        } else if((lastDirection.equals("") || lastDirection.equals("down")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + (getHeight() - 77))) {
            setY(getY() - delta * Constants.MOVEMENT_SPEED);
            setRegion((TextureRegion) down.getKeyFrame(animationTime));
            lastDirection = "down";

        }
    }
}