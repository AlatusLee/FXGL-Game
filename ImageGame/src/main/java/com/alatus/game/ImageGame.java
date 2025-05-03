package com.alatus.game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import static com.almasb.fxgl.dsl.FXGL.*;

public class ImageGame extends GameApplication {
    @Override
    protected void onPreInit() {
//        预处理方法
        getAssetLoader().loadImage("Tank.png");
//        针对只使用一次的图片,帮他作为Image创建出来,这样方法中有效,出去就没了
//        new Image(getAssetLoader().loadImage("Tank.png"))
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new ImageEntityFactory());
        spawn("Nahida",new SpawnData(50,350));
        spawn("Car",new SpawnData(100,100));
        spawn("FatCar",new SpawnData(350,350));
        spawn("Boom",new SpawnData(300,100));
        spawn("Warrior",new SpawnData(300,300));
        Entity tank = spawn("Tank",new SpawnData(400,100));
//        for (int i = 0; i < 5; i++) {
//            spawn("Tank", FXGLMath.randomPoint(
//                    new Rectangle2D(0,0,500,500)
//            ));
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
