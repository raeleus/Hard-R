package com.ray3k.template.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.dongbat.jbump.Collisions;
import com.ray3k.template.*;
import com.ray3k.template.screens.*;

import static com.ray3k.template.JamGame.*;

public class DecalEntity extends Entity {
    AtlasSprite atlasSprite;
    
    public DecalEntity(String name, float x, float y) {
        TextureAtlas textureAtlas = assetManager.get("textures/textures.atlas");
        var region = textureAtlas.findRegion(name);
        atlasSprite = new AtlasSprite(region);
        atlasSprite.setPosition(x - region.getRegionWidth() / 2, y - region.getRegionHeight() / 2);
        
    }
    
    @Override
    public void create() {
    
    }
    
    @Override
    public void actBefore(float delta) {
    
    }
    
    @Override
    public void act(float delta) {
    
    }
    
    @Override
    public void draw(float delta) {
        atlasSprite.draw(batch);
    }
    
    @Override
    public void destroy() {
    
    }
    
    @Override
    public void collision(Collisions collisions) {
    
    }
}
