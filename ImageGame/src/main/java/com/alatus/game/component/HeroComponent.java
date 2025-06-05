package com.alatus.game.component;

import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class HeroComponent extends Component {
    private AnimatedTexture heroAnimationTexture = null;
    @Override
    public void onAdded() {
        AnimationChannel heroAnimationChannel = new AnimationChannel(FXGL.image("Hero.png"), Duration.seconds(1.0),9);
        heroAnimationTexture = new AnimatedTexture(heroAnimationChannel);
        heroAnimationTexture.loop();
        entity.getViewComponent().addChild(heroAnimationTexture);
    }
    public void setWing(String dataName){
        PropertyMap propertyMap = FXGL.getAssetLoader().loadPropertyMap(dataName);
        String image = propertyMap.getString("image");
        Integer numFrames = propertyMap.getInt("numFrames");
        Double duration = propertyMap.getDouble("duration");
        Integer tx = propertyMap.getInt("tx");
        Integer ty = propertyMap.getInt("ty");
        AnimationChannel animationChannel = new AnimationChannel(FXGL.image(image), Duration.seconds(duration),numFrames);
        AnimatedTexture animatedTexture = new AnimatedTexture(animationChannel);
        animatedTexture.setTranslateX(tx);
        animatedTexture.setTranslateY(ty);
        animatedTexture.loop();
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(animatedTexture);
        entity.getViewComponent().addChild(heroAnimationTexture);
    }
}