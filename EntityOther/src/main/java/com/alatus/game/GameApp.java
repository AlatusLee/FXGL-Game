package com.alatus.game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.*;
import javafx.scene.paint.Color;

import java.util.Optional;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        Entity entity = spawn("rect", new SpawnData(0, 0).put("w", 100).put("h", 100).put("color", Color.RED));
        Entity entity1 = spawn("rect", new SpawnData(100, 100).put("w", 100).put("h", 100).put("color", Color.BLUE));
//        计算的是entity的坐标距离
        System.out.println(entity.distance(entity1));
//        计算的是碰撞体积或是模型距离,如果存在交集或者包含,那么就是0
//        如果没有bbox可供计算,那么就还是计算坐标距离
        System.out.println(entity.distanceBBox(entity1));
        Entity entity2 = getGameWorld().create("rect", new SpawnData(100, 0).put("w", 100).put("h", 100).put("color", Color.GREEN));
        entity2.setOnActive(()->{
            System.out.println("E3启动!");
        });
        entity2.setOnNotActive(()->{
            System.out.println("E3死了!");
        });
        System.out.println(entity2.isActive());
        getGameWorld().addEntity(entity2);
//        create是已经生成到了显卡的Buffer，但还没翻转到显示器。pawn是直接执行两步
        System.out.println(entity2.isActive());
        getGameWorld().removeEntity(entity2);
        entity.getComponents().forEach(comp->{
//            四大核心组件是,bbox碰撞实体,类型Type,transform位移,view外观
            System.out.println(comp.toString());
        });
//        四大核心组件都有直接访问的方法
        ViewComponent viewComponent = entity.getViewComponent();
        BoundingBoxComponent boundingBoxComponent = entity.getBoundingBoxComponent();
//        位移这玩意我们主要用于做移动
        TransformComponent transformComponent = entity.getTransformComponent();
//        主要是我们平时都直接translate位移了,这个组件用的比较少
        entity.translate(100,100);
        TypeComponent typeComponent = entity.getTypeComponent();
//        虽然我们在创建这个实体时,没有指明具体的Type,但是type是FXGL实体的四大核心组件,所以为null
        boolean b = entity.hasComponent(TypeComponent.class);
        System.out.println(b);
//        可以重复添加,但是会报警告!!!
        entity.addComponent(new TypeComponent());
//        已经有的组件,不要重复添加!!!
//        四大核心组件是不给移除的,也会警告
        entity.removeComponent(TypeComponent.class);
        entity.addComponent(new HealthIntComponent(10));
        System.out.println(entity.removeComponent(HealthIntComponent.class));
//        获取组件,组件可能不存在
        Optional<IDComponent> componentOptional = entity.getComponentOptional(IDComponent.class);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
