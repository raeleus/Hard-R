package com.ray3k.template.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.ray3k.template.Core.*;
import com.ray3k.template.*;
import com.ray3k.template.OgmoReader.*;
import com.ray3k.template.entities.*;
import com.ray3k.template.screens.DialogPause.*;
import com.ray3k.template.vfx.*;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static com.ray3k.template.Core.*;

public class GameScreen extends JamScreen {
    public static GameScreen gameScreen;
    public static final Color BG_COLOR = new Color();
    public Stage stage;
    public ShapeDrawer shapeDrawer;
    public boolean paused;
    private ChainVfxEffect vfxEffect;
    private Label fpsLabel;
    public static int levelNum = 1;
    
    @Override
    public void show() {
        super.show();
    
        gameScreen = this;
        vfxEffect = new GlitchEffect();
//        vfxManager.addEffect(vfxEffect);
        BG_COLOR.set(Color.PINK);
    
        paused = false;
    
        stage = new Stage(new ScreenViewport(), batch);
        
        var root = new Table();
        root.setFillParent(true);
        root.align(Align.bottomLeft);
        root.pad(10);
        stage.addActor(root);
        
        fpsLabel = new Label("test", skin);
        root.add(fpsLabel);
        
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (!paused && keycode == Keys.ESCAPE) {
                    paused = true;
                
                    DialogPause dialogPause = new DialogPause(GameScreen.this);
                    dialogPause.show(stage);
                    dialogPause.addListener(new PauseListener() {
                        @Override
                        public void resume() {
                            paused = false;
                        }
                    
                        @Override
                        public void quit() {
                            core.transition(new MenuScreen());
                        }
                    });
                }
                return super.keyDown(event, keycode);
            }
        });
    
        shapeDrawer = new ShapeDrawer(batch, skin.getRegion("white"));
        shapeDrawer.setPixelSize(.5f);
    
        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    
        camera = new OrthographicCamera();
        viewport = new FitViewport(1024, 576, camera);
    
        entityController.clear();
        
        var reader = new OgmoReader();
        reader.addListener(new OgmoListener() {
            @Override
            public void level(String ogmoVersion, int width, int height, int offsetX, int offsetY,
                              ObjectMap<String, OgmoValue> valuesMap) {
        
            }
    
            @Override
            public void layer(String name, int gridCellWidth, int gridCellHeight, int offsetX, int offsetY) {
        
            }
    
            @Override
            public void entity(String name, int id, int x, int y, int width, int height, boolean flippedX,
                               boolean flippedY, int originX, int originY, int rotation, Array<EntityNode> nodes,
                               ObjectMap<String, OgmoValue> valuesMap) {
                switch (name) {
                    case "wall":
                        var wall = new WallEntity(x, y - height, width, height);
                        entityController.add(wall);
                        break;
                    case "player":
                        var player = new PlayerEntity();
                        player.setPosition(x, y);
                        entityController.add(player);
                        break;
                    case "wizard-motorcycle":
                        var enemy = new EnemyEntity();
                        enemy.setPosition(x, y);
                        entityController.add(enemy);
                        break;
                    case "wizard":
                        var wizard = new WizardEntity();
                        wizard.setPosition(x, y);
                        entityController.add(wizard);
                        break;
                    case "wizard-scooter":
                        var scooter = new ScooterEntity();
                        scooter.setPosition(x, y);
                        entityController.add(scooter);
                        break;
                    case "goal-entity":
                        var goal = new GoalEntity();
                        goal.setPosition(x, y);
                        entityController.add(goal);
                        break;
                }
            }
    
            @Override
            public void grid(int col, int row, int x, int y, int width, int height, int id) {
        
            }
    
            @Override
            public void decal(int centerX, int centerY, float scaleX, float scaleY, int rotation, String texture,
                              String folder) {
                var decal = new DecalEntity(folder + "/" + Utils.fileName(texture), centerX, centerY);
                entityController.add(decal);
            }
    
            @Override
            public void tile(String tileSet, int col, int row, int x, int y, int id) {
        
            }
    
            @Override
            public void tile(String tileSet, int col, int row, int x, int y, int tileX, int tileY) {
        
            }
    
            @Override
            public void layerComplete() {
        
            }
    
            @Override
            public void levelComplete() {
        
            }
        });
        var file = Gdx.files.internal("levels/level" + levelNum + ".json");
        if (file.exists()) reader.readFile(file);
        else core.transition(new MenuScreen());
    }
    
    @Override
    public void act(float delta) {
        if (!paused) {
            entityController.act(delta);
            vfxManager.update(delta);
        }
        stage.act(delta);
    
        if (isBindingJustPressed(Binding.LEFT)) {
            System.out.println("left");
        }
        if (isBindingJustPressed(Binding.UP)) {
            System.out.println("up");
        }
        
        fpsLabel.setText(Gdx.graphics.getFramesPerSecond());
    }
    
    @Override
    public void draw(float delta) {
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        vfxManager.cleanUpBuffers();
        vfxManager.beginInputCapture();
        Gdx.gl.glClearColor(BG_COLOR.r, BG_COLOR.g, BG_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        entityController.draw(paused ? 0 : delta);
        batch.end();
        vfxManager.endInputCapture();
        vfxManager.applyEffects();
        vfxManager.renderToScreen();
    
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        if (width + height != 0) {
            vfxManager.resize(width, height);
            viewport.update(width, height);
        
            stage.getViewport().update(width, height, true);
        }
    }
    
    @Override
    public void dispose() {
        vfxEffect.dispose();
    }
    
    @Override
    public void hide() {
        super.hide();
        vfxManager.removeAllEffects();
        vfxEffect.dispose();
        entityController.dispose();
    }
}
