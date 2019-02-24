package com.creativesource.cattlecorral;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class Constants {
    public static final float WORLD_SIZE = 10.0f;
    public static final Color BACKGROUND_COLOR = Color.FOREST;

    public static final float COW_HEIGHT = 30;
    public static final float COW_WIDTH = 15;

    public static final float MOVEMENT_SPEED = 20.0f;

    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;
    public static final float HUD_MARGIN = 20.0f;

    public static final String EASY_LABEL = "Easy";
    public static final String MEDIUM_LABEL = "Medium";
    public static final String HARD_LABEL = "Hard";

    public static final float EASY_SPAWNS_PER_SECOND = 5;
    public static final float MEDIUM_SPAWNS_PER_SECOND = 15;
    public static final float HARD_SPAWNS_PER_SECOND = 25;

    public static final Color EASY_COLOR = new Color(0.192f, 0.949f, 0.490f, 1);
    public static final Color MEDIUM_COLOR = new Color(0.043f, 0.694f, 0.301f, 1);
    public static final Color HARD_COLOR = new Color(0.027f, 0.392f, 0.172f, 1);

    public static final float START_WORLD_SIZE = 480.0f;
    public static final float START_BUBBLE_RADIUS = START_WORLD_SIZE / 9;
    public static final float START_LABEL_SCALE = 1.5f;

    public static final Vector2 EASY_CENTER = new Vector2(START_WORLD_SIZE / 4, START_WORLD_SIZE / 2);
    public static final Vector2 MEDIUM_CENTER = new Vector2(START_WORLD_SIZE / 2, START_WORLD_SIZE / 2);
    public static final Vector2 HARD_CENTER = new Vector2(START_WORLD_SIZE * 3 / 4, START_WORLD_SIZE / 2);
    public static final String START_LABEL = "Difficulty: ";
    public static final String SCORE_LABEL = "Score: ";
    public static final String TOP_SCORE_LABEL = "Top Score: ";

    public enum Difficulty {
        EASY(EASY_SPAWNS_PER_SECOND, EASY_LABEL),
        MEDIUM(MEDIUM_SPAWNS_PER_SECOND, MEDIUM_LABEL),
        HARD(HARD_SPAWNS_PER_SECOND, HARD_LABEL);

        float spawnRate;
        String label;

        Difficulty(float spawnRate, String label) {
            this.spawnRate = spawnRate;
            this.label = label;
        }
    }

}