package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.dongbat.jbump.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.screens.GameScreen.*;

public class GoalEntity extends Entity {
    @Override
    public void create() {
        setCollisionBox(0, 0, 16, 16, (item, other) -> null);
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
    
    }
    
    @Override
    public void draw(float delta) {
//        var shapeDrawer = gameScreen.shapeDrawer;
//        shapeDrawer.setColor(Color.GREEN);
//        shapeDrawer.setDefaultLineWidth(1f);
//        Rect rect = world.getRect(item);
//        if (rect != null) shapeDrawer.rectangle(rect.x, rect.y, rect.w, rect.h);
    }
    
    @Override
    public void destroy() {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
    
    }
}
