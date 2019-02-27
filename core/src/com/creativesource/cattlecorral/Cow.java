package com.creativesource.cattlecorral;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Cow extends Animal {
    public Cow(Animation up, Animation left, Animation down, Animation right, Viewport viewport, float worldWidth, ArrayList<TiledMapTileLayer> tiledMapTileLayers, int startPosition) {
        super(up, left, down, right, viewport, worldWidth, tiledMapTileLayers, startPosition);
    }
}
