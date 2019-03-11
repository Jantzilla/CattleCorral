package com.creativesource.cattlecorral.entity;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.creativesource.cattlecorral.screen.PlayScreen;

import java.util.ArrayList;
import java.util.Random;


public abstract class Animal extends Sprite {

    PlayScreen screen;
    private int startPosition;
    int wanderDuration, randomDirection;
    ArrayList<TiledMapTileLayer> tiledMapTileLayers;
    private float worldWidth, animationTime = 0;
    private Animation up,left,down,right;
    String lastDirection = "";
    public boolean isCorraled;
    public boolean active = true;

    abstract boolean isCellBlocked(float x, float y);

    Animal(PlayScreen screen, Animation up, Animation left, Animation down, Animation right, float worldWidth, ArrayList<TiledMapTileLayer> tiledMapTileLayers, int startPosition) {
        super((TextureRegion) up.getKeyFrame(0));
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

    private void init() {
        setY(screen.worldHeight / 2 - (getHeight() / 2));
        setX(+ (worldWidth + startPosition));
    }

    public void update(Batch batch, float delta, float speed) {
        draw(batch);
        animationTime += delta;

        if(isCorraled) {
            wander(delta);
            return;
        }

        if((lastDirection.equals("") || lastDirection.equals("left")) && !isCellBlocked(getX() + 40,getY() + (getHeight() / 2))) {
            setX(getX() - delta * speed);
            setRegion((TextureRegion) left.getKeyFrame(animationTime));
            lastDirection = "left";

        } else if((lastDirection.equals("") || lastDirection.equals("down")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + (getHeight() - 120))) {
            setY(getY() - delta * speed);
            setRegion((TextureRegion) down.getKeyFrame(animationTime));
            lastDirection = "down";

        } else if((lastDirection.equals("") || lastDirection.equals("left")) && !isCellBlocked(getX() + 40,getY() + (getHeight() / 2))) {
            setX(getX() - delta * speed);
            setRegion((TextureRegion) left.getKeyFrame(animationTime));
            lastDirection = "left";

        } else if((lastDirection.equals("") || lastDirection.equals("up")) && !isCellBlocked(getX() + (getWidth() / 2),getY() + 120)) {
            setY(getY() + delta * speed);
            setRegion((TextureRegion) up.getKeyFrame(animationTime));
            lastDirection = "up";

        } else if((lastDirection.equals("") || lastDirection.equals("right")) && !isCellBlocked(getX() + (getWidth() - 40),getY() + (getHeight() / 2))) {
            setX(getX() + delta * speed);
            setRegion((TextureRegion) right.getKeyFrame(animationTime));
            lastDirection = "right";

            if(getX() > worldWidth) {
                setX(getX() - delta * speed);
                setRegion((TextureRegion) left.getKeyFrame(animationTime));
                lastDirection = "left";
            }

        }
    }

    boolean getCorraled(TiledMapTileLayer.Cell cell) {
        if(!isCorraled && cell != null && cell.getTile() != null && (cell.getTile().getProperties().containsKey("cow")
                || cell.getTile() != null && cell.getTile().getProperties().containsKey("pig")
                || cell.getTile() != null && cell.getTile().getProperties().containsKey("sheep"))) {
            isCorraled = true;
            return false;
        }
        return true;
    }

    private void wander(float delta) {
        Random r = new Random();
        int least = 40;
        int most = 200;

        if(wanderDuration == 0) {
            randomDirection = new Random().nextInt(5);
            wanderDuration = r.nextInt(most-least) + least;
        }

        if(randomDirection == 1 && !isCellBlocked(getX() + (getWidth() / 2),getY() + 120)) {
            setY(getY() + delta * 20);
            setRegion((TextureRegion) up.getKeyFrame(animationTime));

        } else if(randomDirection == 4 && !isCellBlocked(getX() + (getWidth() / 2),getY() + (getHeight() - 120))) {
            setY(getY() - delta * 20);
            setRegion((TextureRegion) down.getKeyFrame(animationTime));

        } else if(randomDirection == 2 && !isCellBlocked(getX() + 40,getY() + (getHeight() / 2))) {
            setX(getX() - delta * 20);
            setRegion((TextureRegion) left.getKeyFrame(animationTime));

        } else if(randomDirection == 3 && !isCellBlocked(getX() + (getWidth() - 40),getY() + (getHeight() / 2))) {
            setX(getX() + delta * 20);
            setRegion((TextureRegion) right.getKeyFrame(animationTime));

        }

        wanderDuration--;
    }
}