package com.ray3k.template;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;

public class Resources {
    public static Skin skin_skin;

    public static SkeletonData spine_breadtron;

    public static AnimationStateData spine_breadtronAnimationData;

    public static SkeletonData spine_player;

    public static AnimationStateData spine_playerAnimationData;

    public static SkeletonData spine_star;

    public static AnimationStateData spine_starAnimationData;

    public static SkeletonData spine_wizardMotorcycle;

    public static AnimationStateData spine_wizardMotorcycleAnimationData;

    public static SkeletonData spine_wizardScooter;

    public static AnimationStateData spine_wizardScooterAnimationData;

    public static SkeletonData spine_wizard;

    public static AnimationStateData spine_wizardAnimationData;

    public static TextureAtlas textures_textures;

    public static Sound sfx_attack;

    public static Sound sfx_attack2;

    public static Sound sfx_click;

    public static Sound sfx_death;

    public static Sound sfx_hit;

    public static Sound sfx_jump;

    public static Sound sfx_kill;

    public static Sound sfx_win;

    public static Music bgm_audioSample;

    public static Music bgm_menu;

    public static void loadResources(AssetManager assetManager) {
        skin_skin = assetManager.get("skin/skin.json");
        spine_breadtron = assetManager.get("spine/breadtron.json");
        spine_breadtronAnimationData = assetManager.get("spine/breadtron.json-animation");
        BreadtronAnimation.stand = spine_breadtron.findAnimation("stand");
        BreadtronAnimation.walk = spine_breadtron.findAnimation("walk");
        spine_player = assetManager.get("spine/player.json");
        spine_playerAnimationData = assetManager.get("spine/player.json-animation");
        PlayerAnimation.attack = spine_player.findAnimation("attack");
        PlayerAnimation.stand = spine_player.findAnimation("stand");
        PlayerAnimation.walk = spine_player.findAnimation("walk");
        spine_star = assetManager.get("spine/star.json");
        spine_starAnimationData = assetManager.get("spine/star.json-animation");
        StarAnimation.animation = spine_star.findAnimation("animation");
        spine_wizardMotorcycle = assetManager.get("spine/wizard-motorcycle.json");
        spine_wizardMotorcycleAnimationData = assetManager.get("spine/wizard-motorcycle.json-animation");
        WizardMotorcycleAnimation.attack = spine_wizardMotorcycle.findAnimation("attack");
        WizardMotorcycleAnimation.stand = spine_wizardMotorcycle.findAnimation("stand");
        spine_wizardScooter = assetManager.get("spine/wizard-scooter.json");
        spine_wizardScooterAnimationData = assetManager.get("spine/wizard-scooter.json-animation");
        WizardScooterAnimation.animation = spine_wizardScooter.findAnimation("animation");
        spine_wizard = assetManager.get("spine/wizard.json");
        spine_wizardAnimationData = assetManager.get("spine/wizard.json-animation");
        WizardAnimation.animation = spine_wizard.findAnimation("animation");
        textures_textures = assetManager.get("textures/textures.atlas");
        sfx_attack = assetManager.get("sfx/attack.mp3");
        sfx_attack2 = assetManager.get("sfx/attack2.mp3");
        sfx_click = assetManager.get("sfx/click.mp3");
        sfx_death = assetManager.get("sfx/death.mp3");
        sfx_hit = assetManager.get("sfx/hit.mp3");
        sfx_jump = assetManager.get("sfx/jump.mp3");
        sfx_kill = assetManager.get("sfx/kill.mp3");
        sfx_win = assetManager.get("sfx/win.mp3");
        bgm_audioSample = assetManager.get("bgm/audio-sample.mp3");
        bgm_menu = assetManager.get("bgm/menu.mp3");
    }

    public static class BreadtronAnimation {
        public static Animation stand;

        public static Animation walk;
    }

    public static class PlayerAnimation {
        public static Animation attack;

        public static Animation stand;

        public static Animation walk;
    }

    public static class StarAnimation {
        public static Animation animation;
    }

    public static class WizardMotorcycleAnimation {
        public static Animation attack;

        public static Animation stand;
    }

    public static class WizardScooterAnimation {
        public static Animation animation;
    }

    public static class WizardAnimation {
        public static Animation animation;
    }
}
