package com.example.okyas;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    Vector2 position = new Vector2();
    Animation anim;
    boolean passOn;

    public Enemy(float x, float y, Animation anim){
        this.position.x = x;
        this.position.y = y;
        this.anim = anim;

    }
}
