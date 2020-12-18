package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.dongbat.jbump.*;
import com.ray3k.template.*;
import com.ray3k.template.screens.*;

import static com.ray3k.template.Core.*;
import static com.ray3k.template.Core.Binding.*;
import static com.ray3k.template.Resources.*;
import static com.ray3k.template.Resources.PlayerAnimation.*;
import static com.ray3k.template.screens.GameScreen.*;

public class PlayerEntity extends Entity {
    final float PLAYER_SPEED = 500;
    final float JUMP_SPEED = 1500;
    final float STAR_SPEED = 800;
    final float GRAVITY = -5000;
    final float DEATH_Y =  -500;
    final float HURT_SPEED = 3000;
    final float HURT_DIRECTION = 45f;
    float hurtTimer;
    final float HURT_TIME =  .5f;
    boolean grounded = false;
    PlayerCollisionFilter collisionFilter = new PlayerCollisionFilter();
    public static PlayerEntity player;
    
    @Override
    public void create() {
        player = this;
        setSkeletonData(spine_player, spine_playerAnimationData);
        animationState.setAnimation(0, stand, false);
        setCollisionBox( skeletonBounds.getMinX() - x, skeletonBounds.getMinY() - y, skeletonBounds.getWidth(), skeletonBounds.getHeight(), collisionFilter);
        gravityY = GRAVITY;
    }
    
    @Override
    public void actBefore(float delta) {
        //jump
        if (grounded && gameScreen.isBindingJustPressed(UP)) {
            grounded = false;
            deltaY = JUMP_SPEED;
            sfx_jump.play(sfx);
        }
    }
    
    @Override
    public void act(float delta) {
        //animation
        if (gameScreen.isBindingPressed(SHOOT)) {
            if (animationState.getCurrent(0).getAnimation() != attack) {
                animationState.setAnimation(0, attack, false);
                animationState.addAnimation(0, stand, false, 0);
            }
        } else if (gameScreen.isBindingPressed(RIGHT)) {
            if (animationState.getCurrent(0).getAnimation() != walk) animationState.setAnimation(0, walk, true);
            skeleton.getRootBone().setScale(1, 1);
        } else if (gameScreen.isBindingPressed(LEFT)) {
            if (animationState.getCurrent(0).getAnimation() != walk) animationState.setAnimation(0, walk, true);
            skeleton.getRootBone().setScale(-1, 1);
        } else {
            if (animationState.getCurrent(0).getAnimation() == walk) animationState.setAnimation(0, stand, true);
        }
    
        //physics
        if (hurtTimer <= 0) {
            if (gameScreen.isBindingPressed(RIGHT)) {
                deltaX = PLAYER_SPEED;
            } else if (gameScreen.isBindingPressed(LEFT)) {
                deltaX = -PLAYER_SPEED;
            } else {
                deltaX = 0;
            }
        } else {
            hurtTimer -= delta;
        }
        
        //shoot
        if (gameScreen.isBindingJustPressed(SHOOT)) {
            sfx_attack.play(sfx);
            var star = new PlayerStarEntity();
            star.setPosition(x, y + 25);
            entityController.add(star);
            star.setMotion(STAR_SPEED, MathUtils.isEqual(skeleton.getRootBone().getScaleX(), 1f) ? 0 : 180);
        }
        
        //camera
        gameScreen.camera.position.set(x, y, 0);
        
        
        if (y < DEATH_Y) destroy = true;
        grounded = false;
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
        core.transition(new GameScreen());
        sfx_death.play(sfx);
    }
    
    @Override
    public void collision(Collisions collisions) {
        for (int i = 0; i < collisions.size(); i++) {
            var collision = collisions.get(i);
            if (collision.other.userData instanceof WallEntity) {
                if (collision.normal.y == 1) {
                    if (deltaY < 0) deltaY = 0;
                    grounded = true;
                }
            } else if (collision.other.userData instanceof GoalEntity) {
                ((GoalEntity) collision.other.userData).destroy = true;
                levelNum++;
                core.transition(new GameScreen());
                sfx_win.play(sfx);
            } else if (collision.other.userData instanceof  EnemyEntity) {
                if (hurtTimer <= 0) {
                    var enemy = (EnemyEntity) collision.other.userData;
                    if (collision.normal.x == 1) setMotion(HURT_SPEED, HURT_DIRECTION);
                    else setMotion(HURT_SPEED, 180 - HURT_DIRECTION);
                    hurtTimer = HURT_TIME;
                    grounded = false;
                    sfx_hit.play(sfx);
                }
            }
        }
    }
    
    public static class PlayerCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {
            if (other.userData instanceof WallEntity) return Response.slide;
            else if (other.userData instanceof  GoalEntity) return Response.cross;
            else if (other.userData instanceof EnemyEntity) return Response.bounce;
            return null;
        }
    }
}
