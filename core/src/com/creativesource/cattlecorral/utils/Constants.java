package com.creativesource.cattlecorral.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Constants {
    public static final Color BACKGROUND_COLOR = Color.FOREST;

    private static final float MOVEMENT_SPEED_1 = 35.0f;
    private static final float MOVEMENT_SPEED_2 = 40.0f;
    private static final float MOVEMENT_SPEED_3 = 45.0f;
    private static final float MOVEMENT_SPEED_4 = 50.0f;
    private static final float MOVEMENT_SPEED_5 = 70.0f;
    private static final float MOVEMENT_SPEED_6 = 75.0f;
    private static final float MOVEMENT_SPEED_7 = 80.0f;
    private static final float MOVEMENT_SPEED_8 = 85.0f;
    private static final float MOVEMENT_SPEED_9 = 90.0f;
    private static final float MOVEMENT_SPEED_10 = 100.0f;

    private static final String LEVEL_1_LABEL = "1";
    private static final String LEVEL_2_LABEL = "2";
    private static final String LEVEL_3_LABEL = "3";
    private static final String LEVEL_4_LABEL = "4";
    private static final String LEVEL_5_LABEL = "5";
    private static final String LEVEL_6_LABEL = "6";
    private static final String LEVEL_7_LABEL = "7";
    private static final String LEVEL_8_LABEL = "8";
    private static final String LEVEL_9_LABEL = "9";
    private static final String LEVEL_10_LABEL = "10";

    private static final float SPAWN_RATE_1 = 18;
    private static final float SPAWN_RATE_2 = 20;
    private static final float SPAWN_RATE_3 = 22;
    private static final float SPAWN_RATE_4 = 24;
    private static final float SPAWN_RATE_5 = 28;
    private static final float SPAWN_RATE_6 = 32;
    private static final float SPAWN_RATE_7 = 36;
    private static final float SPAWN_RATE_8 = 40;
    private static final float SPAWN_RATE_9 = 45;
    private static final float SPAWN_RATE_10 = 50;

    public static final float GAME_SPAN_1 = 85;
    public static final float GAME_SPAN_2 = 150;

    public static final float SINGLE_SCORE = 20;

    public static final float START_LABEL_SCALE = 1.5f;

    public static final Rectangle GATE_ONE = new Rectangle(600, 250, 100, 100);
    public static final Rectangle GATE_TWO = new Rectangle(175, 250, 100, 100);
    public static final Rectangle GATE_THREE = new Rectangle(400, 265, 100, 100);

    public static final String SCORE_LABEL = "Score";
    public static final int GAME_PAUSED = 0;
    public static final int GAME_RESUMED = 1;
    public static final int GAME_COMPLETE = 2;

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

        public float spawnRate;
        public float speed;
        public String label;

        Level(float spawnRate, float speed, String label) {
            this.spawnRate = spawnRate;
            this.label = label;
            this.speed = speed;
        }
    }

}