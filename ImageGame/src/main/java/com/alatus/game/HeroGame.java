package com.alatus.game;

import com.alatus.game.component.HeroComponent;
import com.alatus.game.factory.ImageEntityFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.ViewComponent;
import javafx.scene.paint.Color;
import static com.almasb.fxgl.dsl.FXGL.*;

public class HeroGame extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(new ImageEntityFactory());
        Entity entity = spawn("HeroWithWing", new SpawnData(0, 100));
        ViewComponent viewComponent = entity.getViewComponent();
        String[] strings = {"data/BlueWing.kv", "data/GoldenWing.kv"};
        HeroComponent heroComponent = entity.getComponent(HeroComponent.class);
        viewComponent.addOnClickHandler(event->{
            heroComponent.setWing(FXGLMath.random(strings).get());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
