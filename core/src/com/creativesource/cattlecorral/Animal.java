package com.creativesource.cattlecorral;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;


public abstract class Animal extends Sprite {

    public PlayScreen screen;
    int startPosition, wanderDuration, randomDirection;
    ArrayList<TiledMapTileLayer> tiledMapTileLayers;
    float worldWidth, animationTime = 0;
    Animation up,left,down,right;
    Viewport viewport;
    String lastDirection = "";
    boolean isCorraled;

    abstract boolean isCellBlocked(float x, float y);

    public Animal(PlayScreen screen, Animation up, Animation left, Animation down, Animation right, Viewport viewport, float worldWidth, ArrayList<TiledMapTileLayer> tiledMapTileLayers, int startPosition) {
        super((TextureRegion) up.getKeyFrame(0));
        this.viewport = viewport;
        this.worldWidth = worldWidth;
        this.tiledMapTileLayers = tiledMapTileLayers;
        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        this.startPosition = startPosition;
        this.screen = screen;
        init();
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void init() {
        setX(worldWidth / 2 - (getWidth() / 2));
        setY(- startPosition);
    }

    public void update(Batch batch, float delta, float speed) {
        draw(batch);
        animationTime += delta;

        if(isCorraled) {
            wander(delta);
            return;
        }

        if((lastDirection.equals("") || lastDirection.equals("up")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + 77)) {
            setY(getY() + delta * speed);
            setRegion((TextureRegion) up.getKeyFrame(animationTime));
            lastDirection = "up";

        } else if((lastDirection.equals("") || lastDirection.equals("left")) && !isCellBlocked(getX() + 20,getY() + (getHeight() / 2))) {
            setX(getX() - delta * speed);
            setRegion((TextureRegion) left.getKeyFrame(animationTime));
            lastDirection = "left";

        } else if((lastDirection.equals("") || lastDirection.equals("up")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + 77)) {
            setY(getY() + delta * speed);
            setRegion((TextureRegion) up.getKeyFrame(animationTime));
            lastDirection = "up";

        } else if((lastDirection.equals("") || lastDirection.equals("right")) && !isCellBlocked(getX() + (getWidth() - 20),getY() + (getHeight() / 2))) {
            setX(getX() + delta * speed);
            setRegion((TextureRegion) right.getKeyFrame(animationTime));
            lastDirection = "right";

        } else if((lastDirection.equals("") || lastDirection.equals("down")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + (getHeight() - 77))) {
            setY(getY() - delta * speed);
            setRegion((TextureRegion) down.getKeyFrame(animationTime));
            lastDirection = "down";

            if(getY() < 0) {
                setY(getY() + delta * speed);
                setRegion((TextureRegion) up.getKeyFrame(animationTime));
                lastDirection = "up";
            }

        }
    }

    public boolean getCorraled(TiledMapTileLayer.Cell cell) {
        if(!isCorraled && cell != null && cell.getTile() != null && (cell.getTile().getProperties().containsKey("cow")
        || cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("pig")
        || cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("sheep"))) {
            isCorraled = true;
            return false;
        }
        return true;
    }

    public void wander(float delta) {
        Random r = new Random();
        int least = 40;
        int most = 200;

        if(wanderDuration == 0) {
            randomDirection = new Random().nextInt(5);
            wanderDuration = r.nextInt(most-least) + least;
        }

        if(randomDirection == 1 && !isCellBlocked(getX() + (getWidth() / 2),getY() + 77)) {
            setY(getY() + delta * 20);
            setRegion((TextureRegion) up.getKeyFrame(animationTime));

        } else if(randomDirection == 4 && !isCellBlocked(getX() + (getWidth() / 2),getY() + (getHeight() - 77))) {
            setY(getY() - delta * 20);
            setRegion((TextureRegion) down.getKeyFrame(animationTime));

        } else if(randomDirection == 2 && !isCellBlocked(getX() + 20,getY() + (getHeight() / 2))) {
            setX(getX() - delta * 20);
            setRegion((TextureRegion) left.getKeyFrame(animationTime));

        } else if(randomDirection == 3 && !isCellBlocked(getX() + (getWidth() - 20),getY() + (getHeight() / 2))) {
            setX(getX() + delta * 20);
            setRegion((TextureRegion) right.getKeyFrame(animationTime));

        }

        wanderDuration--;
    }
}