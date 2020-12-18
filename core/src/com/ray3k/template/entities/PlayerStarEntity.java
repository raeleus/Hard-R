package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.dongbat.jbump.*;
import com.ray3k.template.*;
import com.ray3k.template.entities.PlayerEntity.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.StarAnimation.*;
import static com.ray3k.template.screens.GameScreen.*;

public class PlayerStarEntity extends Entity {
    StarCollisionFilter collisionFilter = new StarCollisionFilter();
    float damage = 50;
    
    @Override
    public void create() {
        setSkeletonData(spine_star, spine_starAnimationData);
        animationState.setAnimation(0, animation, false);
        setCollisionBox( skeletonBounds.getMinX() - x, skeletonBounds.getMinY() - y, skeletonBounds.getWidth(), skeletonBounds.getHeight(), collisionFilter);
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
//        shapeDrawer.setColor(Color.RED);
//        shapeDrawer.setDefaultLineWidth(1f);
//        Rect rect = world.getRect(item);
//        if (rect != null) shapeDrawer.rectangle(rect.x, rect.y, rect.w, rect.h);
    }
    
    @Override
    public void destroy() {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
        for (int i = 0; i < collisions.size(); i++) {
            var collision = collisions.get(i);
            if (collision.other.userData instanceof EnemyEntity) {
                var enemy = (EnemyEntity) collision.other.userData;
                enemy.health -= damage;
                if (enemy.health <= 0) enemy.destroy = true;
                destroy = true;
                sfx_kill.play(sfx);
            } else if (collision.other.userData instanceof WallEntity) {
                destroy = true;
            }
        }
    }
    
    public static class StarCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof Entity) {
                return Response.cross;
            }
            return null;
        }
    }
}
