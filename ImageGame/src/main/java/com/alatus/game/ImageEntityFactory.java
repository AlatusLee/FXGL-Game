package com.alatus.game;

import com.alatus.game.component.InformComponent;
import com.alatus.game.component.TankComponent;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class ImageEntityFactory implements EntityFactory {
//    mp3,wav,图片等资源引擎会帮我们提前缓存
//    避免我们大量重复且无意义的IO读取操作对设备性能造成影响
//    减少内存占用
    @Spawns("Car")
    public Entity newCar(SpawnData data){
//        一般就是在这里读取图片的时候就设置一波像素大小,节省内存,而且后面也不用缩放那么麻烦
        Texture carLeft = FXGL.texture("car.png");
        Texture carRight = carLeft.copy();
//        翻转-1就是水平镜像翻转,虽然我不懂原理
        carRight.setScaleX(-1);
        carRight.setTranslateX(carLeft.getWidth());
        return FXGL.entityBuilder(data)
                .view(carLeft)
                .neverUpdated()
                .view(carRight)
                .bbox(BoundingShape.box(carLeft.getWidth()*2,carLeft.getHeight()))
                .build();
    }
    @Spawns("FatCar")
    public Entity newFatCar(SpawnData data){
        Texture leftCar = FXGL.texture("fat_car.png");
        Texture rightCar = leftCar.copy();
        rightCar.setScaleX(-1);
        rightCar.setTranslateX(leftCar.getWidth());
        return FXGL.entityBuilder(data)
                .view(leftCar)
                .neverUpdated()
                .view(rightCar)
                .bbox(BoundingShape.box(leftCar.getWidth()*2,leftCar.getHeight()))
                .build();
    }
    @Spawns("Boom")
    public Entity newBoom(SpawnData data){
        AnimationChannel animationChannel = new AnimationChannel(FXGL.image("Boom.png"),
                Duration.seconds(0.5),
                14);
        AnimatedTexture animatedTexture = new AnimatedTexture(animationChannel);
        animatedTexture.loop();
        return FXGL.entityBuilder(data)
                .view(animatedTexture)
                .build();
    }
    @Spawns("Warrior")
    public Entity newWarrior(SpawnData data){
        AnimationChannel animationChannel = new AnimationChannel(FXGL.image("warrior.png"),
                4,32,48,Duration.seconds(0.8),0,3);
        AnimatedTexture animatedTexture = new AnimatedTexture(animationChannel);
        animatedTexture.loop();
        return FXGL.entityBuilder(data)
                .view(animatedTexture)
//                不要自动更新,减少性能损耗,不要轻易使用,动画类型的会受到影响
                .build();
    }
    @Spawns("Tank")
    public Entity newTank(SpawnData data){
        Texture tank = FXGL.texture("Tank.png", 100, 100);
        Texture tank1 = tank.multiplyColor(FXGLMath.randomColor()).brighter();
        return FXGL.entityBuilder(data)
                .view(tank1)
                .neverUpdated()
//                要想实现组件之间的自动注入,必须在TankComponent之前已经存在了这个组件,不然他识别不到
                .with(new InformComponent())
                .with(new TankComponent())
                .build();
    }
    @Spawns("Nahida")
    public Entity newNahida(SpawnData data){
        List<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            imageList.add(FXGL.image(String.format("Nahida/image"+i+".png")));
        }
        AnimationChannel animationChannel = new AnimationChannel(imageList,Duration.seconds(1));
        AnimatedTexture animatedTexture = new AnimatedTexture(animationChannel);
        animatedTexture.loopReverse();
        return FXGL.entityBuilder(data)
                .view(animatedTexture)
                .build();
    }
}
