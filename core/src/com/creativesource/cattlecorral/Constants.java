package com.creativesource.cattlecorral;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;


public class Constants {
    public static final float WORLD_SIZE = 10.0f;
    public static final Color BACKGROUND_COLOR = Color.FOREST;

    public static final float COW_HEIGHT = 30;
    public static final float COW_WIDTH = 15;

    public static final float MOVEMENT_SPEED = 40.0f;

    public static final float HUD_FONT_REFERENCE_SCREEN_SIZE = 480.0f;
    public static final float HUD_MARGIN = 20.0f;

    public static final String EASY_LABEL = "Easy";
    public static final String MEDIUM_LABEL = "Medium";
    public static final String HARD_LABEL = "Hard";
    public static final String LEVEL_1_LABEL = "1";
    public static final String LEVEL_2_LABEL = "2";
    public static final String LEVEL_3_LABEL = "3";
    public static final String LEVEL_4_LABEL = "4";
    public static final String LEVEL_5_LABEL = "5";
    public static final String LEVEL_6_LABEL = "6";
    public static final String LEVEL_7_LABEL = "7";
    public static final String LEVEL_8_LABEL = "8";
    public static final String LEVEL_9_LABEL = "9";
    public static final String LEVEL_10_LABEL = "10";

    public static final float SPAWN_RATE_1 = 18;
    public static final float SPAWN_RATE_2 = 20;
    public static final float SPAWN_RATE_3 = 22;
    public static final float SPAWN_RATE_4 = 24;
    public static final float SPAWN_RATE_5 = 28;
    public static final float SPAWN_RATE_6 = 32;
    public static final float SPAWN_RATE_7 = 36;
    public static final float SPAWN_RATE_8 = 40;
    public static final float SPAWN_RATE_9 = 45;
    public static final float SPAWN_RATE_10 = 50;

    public static final Color EASY_COLOR = new Color(0.192f, 0.949f, 0.490f, 1);
    public static final Color MEDIUM_COLOR = new Color(0.043f, 0.694f, 0.301f, 1);
    public static final Color HARD_COLOR = new Color(0.027f, 0.392f, 0.172f, 1);

    public static final float START_WORLD_SIZE = 480.0f;
    public static final float START_BUBBLE_RADIUS = START_WORLD_SIZE / 9;
    public static final float START_LABEL_SCALE = 1.5f;
    public static final float BUTTON_SIZE = 50;
    private static float BUTTON_SPACE = 25;
    private static float BUTTON_ARRAY_WIDTH = 325;
    public static final float[] LEVEL_1 = {(START_WORLD_SIZE - BUTTON_ARRAY_WIDTH) / 2, START_WORLD_SIZE / 2};
    public static final float[] LEVEL_2 = {LEVEL_1[0] + BUTTON_SIZE + BUTTON_SPACE, START_WORLD_SIZE / 2};
    public static final float[] LEVEL_3 = {LEVEL_2[0] + BUTTON_SIZE + BUTTON_SPACE, START_WORLD_SIZE / 2};
    public static final float[] LEVEL_4 = {LEVEL_3[0] + BUTTON_SIZE + BUTTON_SPACE, START_WORLD_SIZE / 2};
    public static final float[] LEVEL_5 = {LEVEL_4[0] + BUTTON_SIZE + BUTTON_SPACE, START_WORLD_SIZE / 2};
    public static final float[] LEVEL_6 = {LEVEL_1[0], LEVEL_5[1] - BUTTON_SIZE - BUTTON_SPACE};
    public static final float[] LEVEL_7 = {LEVEL_6[0] + BUTTON_SIZE + BUTTON_SPACE, LEVEL_5[1] - BUTTON_SIZE - BUTTON_SPACE};
    public static final float[] LEVEL_8 = {LEVEL_7[0] + BUTTON_SIZE + BUTTON_SPACE, LEVEL_5[1] - BUTTON_SIZE - BUTTON_SPACE};
    public static final float[] LEVEL_9 = {LEVEL_8[0] + BUTTON_SIZE + BUTTON_SPACE, LEVEL_5[1] - BUTTON_SIZE - BUTTON_SPACE};
    public static final float[] LEVEL_10 = {LEVEL_9[0] + BUTTON_SIZE + BUTTON_SPACE, LEVEL_5[1] - BUTTON_SIZE - BUTTON_SPACE};

    public static final Vector2 EASY_CENTER = new Vector2(START_WORLD_SIZE / 4, START_WORLD_SIZE / 2);
    public static final Vector2 MEDIUM_CENTER = new Vector2(START_WORLD_SIZE / 2, START_WORLD_SIZE / 2);
    public static final Vector2 HARD_CENTER = new Vector2(START_WORLD_SIZE * 3 / 4, START_WORLD_SIZE / 2);
    public static final String START_LABEL = "Difficulty: ";
    public static final String SCORE_LABEL = "Score: ";
    public static final String TOP_SCORE_LABEL = "Top Score: ";
    public static final int GAME_PAUSED = 0;
    public static final int GAME_RESUMED = 1;

    public enum Level {
        ONE(SPAWN_RATE_1, MOVEMENT_SPEED_1, LEVEL_1_LABEL),
        TWO(SPAWN_RATE_2, MOVEMENT_SPEED_2, LEVEL_2_LABEL),
        THREE(SPAWN_RATE_3, MOVEMENT_SPEED_3, LEVEL_3_LABEL),
        FOUR(SPAWN_RATE_4, MOVEMENT_SPEED_4, LEVEL_4_LABEL),
        FIVE(SPAWN_RATE_5, MOVEMENT_SPEED_5, LEVEL_5_LABEL),
        SIX(SPAWN_RATE_6, MOVEMENT_SPEED_6, LEVEL_6_LABEL),
        SEVEN(SPAWN_RATE_7, MOVEMENT_SPEED_7, LEVEL_7_LABEL),
        EIGHT(SPAWN_RATE_8, MOVEMENT_SPEED_8, LEVEL_8_LABEL),
        NINE(SPAWN_RATE_9, MOVEMENT_SPEED_9, LEVEL_9_LABEL),
        TEN(SPAWN_RATE_10, MOVEMENT_SPEED_10, LEVEL_10_LABEL);

        float spawnRate, speed;
        String label;

        Level(float spawnRate, float speed, String label) {
            this.spawnRate = spawnRate;
            this.label = label;
            this.speed = speed;
        }
    }

}