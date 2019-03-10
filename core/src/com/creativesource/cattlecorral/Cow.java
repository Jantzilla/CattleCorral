package com.creativesource.cattlecorral;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Cow extends Animal {
    public Cow(PlayScreen screen, Animation up, Animation left, Animation down, Animation right, Viewport viewport, float worldWidth, ArrayList<TiledMapTileLayer> tiledMapTileLayers, int startPosition) {
        super(screen, up, left, down, right, viewport, worldWidth, tiledMapTileLayers, startPosition);
    }

    @Override
    public boolean isCellBlocked(float x, float y) {
        for(TiledMapTileLayer tiledMapTileLayer : tiledMapTileLayers) {
            if(tiledMapTileLayer.isVisible()) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) (x / tiledMapTileLayer.getTileWidth()), (int) (y / tiledMapTileLayer.getTileHeight()));
                if(!getCorraled(cell)) {
                    if (active && cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("cow")) {
                        screen.points += 20;
                        if(screen.prefs.hasSound())
                            screen.cow.play(1.0f);
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
