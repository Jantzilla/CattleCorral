package com.creativesource.cattlecorral.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.creativesource.cattlecorral.screen.PlayScreen;

import java.util.ArrayList;

public class Sheep extends Animal {
    public Sheep(PlayScreen screen, Animation up, Animation left, Animation down, Animation right, float worldWidth, ArrayList<TiledMapTileLayer> tiledMapTileLayers, int startPosition) {
        super(screen, up, left, down, right, worldWidth, tiledMapTileLayers, startPosition);
    }

    @Override
    boolean isCellBlocked(float x, float y) {
        for(TiledMapTileLayer tiledMapTileLayer : tiledMapTileLayers) {
            if(tiledMapTileLayer.isVisible()) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) (x / tiledMapTileLayer.getTileWidth()), (int) (y / tiledMapTileLayer.getTileHeight()));
                if(!getCorraled(cell)) {
                    if (active && cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("sheep")) {
                        screen.points += 20;
                        if(screen.prefs.hasSound())
                            screen.sheep.play(1.0f);
                    }
                }
                if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")) {
                    lastDirection = "";
                    return true;

                } else if (isCorraled && cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("gate")) {
                    randomDirection = 4;
                    wanderDuration = 40;
                }
            }
        }
        return false;
    }
}
