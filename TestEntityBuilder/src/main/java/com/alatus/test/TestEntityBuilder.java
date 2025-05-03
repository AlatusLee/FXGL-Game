package com.alatus.test;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.paint.Color;

public class TestEntityBuilder extends GameApplication {
    private enum Type{
        Rect,GameBox
    }
    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
//        老方法
//        Entity build = FXGL.entityBuilder().build();
//        FXGL.getGameWorld().addEntity(build);
//        FXGL.entityBuilder(new SpawnData(100,50))
//                .buildAndAttach();
        getGameWorld().addEntityFactory(new TestEntityBuild());
//        getGameWorld().spawn("rect",new SpawnData(50,80));
////        创建实体并且添加到游戏世界
//        spawn("rect",new SpawnData(80,100));
////        create方法只会创造实体,不会添加到游戏世界
//        Entity rect = getGameWorld().create("rect", new SpawnData(280, 280));
//        getGameWorld().addEntity(rect);

        Entity entity = spawn("square", new SpawnData(400, 400)
                .put("w", 60).put("h", 60).put("color", Color.YELLOW));
        entity.setType(Type.GameBox);
        System.out.println(entity.getObject("color").toString());
        System.out.println(entity.getInt("w"));
        PropertyMap properties = entity.getProperties();
        properties.keys().forEach(key -> {
            System.out.println(properties.getValue(key).toString());
        });
        System.out.println(entity.getType());
        System.out.println(properties.getValue("type").toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
