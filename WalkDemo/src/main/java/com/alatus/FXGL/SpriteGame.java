package com.alatus.FXGL;

import com.alatus.FXGL.components.WarriorComponent;
import com.alatus.FXGL.constEnum.Dir;
import com.alatus.FXGL.factory.SpriteFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import kotlin.Unit;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SpriteGame extends GameApplication {
    private WarriorComponent warriorComponent;
    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SpriteFactory());
        getGameScene().setBackgroundColor(Color.GRAY);
        Entity warrior = spawn("Warrior", 100, 100);
        warriorComponent = warrior.getComponent(WarriorComponent.class);
    }
    @Override
    protected void initInput() {
        onKey(KeyCode.UP,()->{
            warriorComponent.moveDirection(Dir.UP);
            return Unit.INSTANCE;
        });
        onKey(KeyCode.DOWN,()->{
            warriorComponent.moveDirection(Dir.DOWN);
            return Unit.INSTANCE;
        });
        onKey(KeyCode.LEFT,()->{
            warriorComponent.moveDirection(Dir.LEFT);
            return Unit.INSTANCE;
        });
        onKey(KeyCode.RIGHT,()->{
            warriorComponent.moveDirection(Dir.RIGHT);
            return Unit.INSTANCE;
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
