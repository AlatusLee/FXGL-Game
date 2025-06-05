package com.alatus.FXGL.factory;

import com.alatus.FXGL.components.WarriorComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class SpriteFactory implements EntityFactory {
    @Spawns("Warrior")
    public Entity newWarrior(SpawnData data){
        return FXGL.entityBuilder(data)
//                不支持有物理碰撞实体的组件
                .with(new KeepOnScreenComponent())
                .with(new WarriorComponent())
                .build();
    }
}
