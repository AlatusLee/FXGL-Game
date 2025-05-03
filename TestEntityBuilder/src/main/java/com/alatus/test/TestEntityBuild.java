package com.alatus.test;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TestEntityBuild implements EntityFactory {
    @Spawns("rect,square")
    public Entity newRect(SpawnData data){
        int w = data.get("w");
        int h = (Integer)data.get("h");
        Color color = data.get("color");
        return FXGL.entityBuilder(data)
                .view(new Rectangle(w,h, color))
                .build();
    }
    @Spawns("enemy")
    public Entity newEnemy(SpawnData data){
        return FXGL.entityBuilder(data)
                .build();
    }
}
