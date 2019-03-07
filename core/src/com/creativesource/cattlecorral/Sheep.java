package com.creativesource.cattlecorral;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Sheep extends Animal {
    public Sheep(PlayScreen screen, Animation up, Animation left, Animation down, Animation right, Viewport viewport, float worldWidth, ArrayList<TiledMapTileLayer> tiledMapTileLayers, int startPosition) {
        super(screen, up, left, down, right, viewport, worldWidth, tiledMapTileLayers, startPosition);
    }

    @Override
    boolean isCellBlocked(float x, float y) {
        for(TiledMapTileLayer tiledMapTileLayer : tiledMapTileLayers) {
            if(tiledMapTileLayer.isVisible()) {
                TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) (x / tiledMapTileLayer.getTileWidth()), (int) (y / tiledMapTileLayer.getTileHeight()));
                if(!getCorraled(cell)) {
                    if (cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("sheep")) {
                        screen.points += 20;
                        screen.sheep.play(1.0f);
                    }
                }
                if(cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked")) {
                    lastDirection = "";
                    return true;
                }
            }
        }
        return false;
    }
}
