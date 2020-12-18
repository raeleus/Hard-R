package com.ray3k.template.entities;

import com.ray3k.template.*;

import static com.ray3k.template.Resources.*;

public class WizardEntity extends EnemyEntity {
    @Override
    public void create() {
        super.create();
        setSkeletonData(spine_wizard, spine_wizardAnimationData);
        animationState.setAnimation(0, WizardAnimation.animation, true);
        setCollisionBox( skeletonBounds.getMinX() - x, skeletonBounds.getMinY() - y, skeletonBounds.getWidth(), skeletonBounds.getHeight(), collisionFilter);
        speed = 100;
        health = 300;
    }
}
