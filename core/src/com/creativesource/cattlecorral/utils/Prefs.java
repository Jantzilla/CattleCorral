package com.creativesource.cattlecorral.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {

    private Preferences pref ;
    private boolean hasSound;
    private int topScore;

    public Prefs(){
        pref = Gdx.app.getPreferences("Preferences");
        hasSound = pref.getBoolean("hasSound",true);
        topScore = pref.getInteger("topScore",0);

    }

    public void setSound(boolean hasSound){
        this.hasSound=hasSound;
        pref.putBoolean("hasSound",hasSound);
        pref.flush();
    }

    public boolean hasSound(){
        return hasSound;
    }


    public void setScore(int topScore){
        if(topScore > getTopScore()) {
            this.topScore = topScore;
            pref.putInteger("topScore", topScore);
            pref.flush();
        }
    }

    public int getTopScore(){
        return topScore;
    }
}
