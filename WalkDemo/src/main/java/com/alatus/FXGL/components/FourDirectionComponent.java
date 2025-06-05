package com.alatus.FXGL.components;

import com.alatus.FXGL.constEnum.Dir;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

public class FourDirectionComponent extends Component {
    private AnimationChannel acUp, acDown, acLeft, acRight;
    private static final int INIT_SPEED = 150;
    private double speed;
    private Dir dir;

    private String imageName;
    private AnimatedTexture animationTexture;
    private boolean stopFlag = false;
    public FourDirectionComponent(String imageName) {
        this.imageName = imageName;
        acUp = createAnimationChannel(12,15);
        acDown = createAnimationChannel(0,3);
        acLeft = createAnimationChannel(4,7);
        acRight = createAnimationChannel(8,11);
        animationTexture = new AnimatedTexture(acDown);
        dir = Dir.DOWN;
    }
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(animationTexture);
        entity.getBoundingBoxComponent().addHitBox(
                new HitBox(
                        BoundingShape.box(128/4.0,192/4.0)
                )
        );
    }
    private AnimationChannel createAnimationChannel(int x, int y) {
        return new AnimationChannel(FXGL.image(imageName),
                4,128/4,192/4,
                Duration.seconds(0.75),x,y);
    }
    public void moveDirection(Dir dir){
        this.dir = dir;
        switch (dir){
            case UP, LEFT:
                speed = -INIT_SPEED;
                break;
            case DOWN, RIGHT:
                speed = INIT_SPEED;
                break;
        }
    }
    @Override
    public void onUpdate(double tpf) {
        if(dir == Dir.UP || dir == Dir.DOWN){
            entity.translateY(speed * tpf);
            entity.translateX(0);
        }
        else{
            entity.translateY(0);
            entity.translateX(speed * tpf);
        }
        switch (dir){
            case UP:
                animationTexture.loopNoOverride(acUp);
                if(stopFlag){
                    animationTexture.loopAnimationChannel(acUp);
                    stopFlag = false;
                }
                break;
            case DOWN:
                animationTexture.loopNoOverride(acDown);
                if(stopFlag){
                    animationTexture.loopAnimationChannel(acDown);
                    stopFlag = false;
                }
                break;
            case LEFT:
                animationTexture.loopNoOverride(acLeft);
                if(stopFlag){
                    animationTexture.loopAnimationChannel(acLeft);
                    stopFlag = false;
                }
                break;
            case RIGHT:
                animationTexture.loopNoOverride(acRight);
                if(stopFlag){
                    animationTexture.loopAnimationChannel(acRight);
                    stopFlag = false;
                }
                break;
        }
        speed = (int)(speed * 0.9);
        if (Math.abs(speed) < 1) {
            speed = 0;
            animationTexture.stop();
            stopFlag = true;
        }
    }
}
