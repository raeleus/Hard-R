package com.ray3k.template.entities;

import static com.ray3k.template.Resources.*;

public class ScooterEntity extends EnemyEntity {
    @Override
    public void create() {
        super.create();
        setSkeletonData(spine_wizardScooter, spine_wizardScooterAnimationData);
        animationState.setAnimation(0, WizardScooterAnimation.animation, true);
        setCollisionBox( skeletonBounds.getMinX() - x, skeletonBounds.getMinY() - y, skeletonBounds.getWidth(), skeletonBounds.getHeight(), collisionFilter);
        speed = 400;
        health = 50;
    }
}
