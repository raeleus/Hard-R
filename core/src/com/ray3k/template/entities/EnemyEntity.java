package com.ray3k.template.entities;

import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.*;
import com.ray3k.template.*;

import static com.ray3k.template.Resources.*;
import static com.ray3k.template.entities.PlayerEntity.*;

public class EnemyEntity extends Entity {
    EnemyCollisionFilter collisionFilter = new EnemyCollisionFilter();
    final float GRAVITY = -5000;
    final float RANGE = 500;
    final float Y_RANGE = 200;
    float health = 100;
    float speed = 300;
    
    @Override
    public void create() {
        setSkeletonData(spine_wizardMotorcycle, spine_wizardMotorcycleAnimationData);
        animationState.setAnimation(0, WizardMotorcycleAnimation.stand, true);
        setCollisionBox( skeletonBounds.getMinX() - x, skeletonBounds.getMinY() - y, skeletonBounds.getWidth(), skeletonBounds.getHeight(), collisionFilter);
        gravityY = GRAVITY;
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
        if (Utils.pointDistance(player.x, player.y, x, y) < RANGE) {
            if (player.x < x) {
                deltaX = -speed;
            } else {
                deltaX = speed;
            }
        } else {
            deltaX = 0;
        }
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
            if (collision.other.userData instanceof WallEntity) {
                if (collision.normal.y == 1) {
                    deltaY = 0;
                }
            }
        }
    }
    
    public static class EnemyCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof WallEntity) return Response.slide;
            return null;
        }
    }
}
