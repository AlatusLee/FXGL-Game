package com.alatus;

import static com.almasb.fxgl.dsl.FXGL.*;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class ChatFactory implements EntityFactory {
    @Spawns("bg")
    public Entity newBg(SpawnData data){
        return entityBuilder(data)
                .view(texture("bg.JPG"))
                .build();
    }
}
