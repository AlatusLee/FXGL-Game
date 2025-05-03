package com.alatus.fxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.localization.Language;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.Map;

public class TankWarApp extends GameApplication {
    private Entity tankEntity;
    private boolean isMoving;
    private Direction dir = Direction.RIGHT;
    private LocalTimer shootTimer;
    private Duration duration = Duration.seconds(0.25);
    private static Point2D UP = new Point2D(0,-1);
    private static Point2D DOWN = new Point2D(0,1);
    private static Point2D LEFT = new Point2D(-1,0);
    private static Point2D RIGHT = new Point2D(1,0);
    private static String SCORE = "score";
    private static String NAME = "name";
    private static String HIT_POINT = "hitPoint";
//    assets目录下,levels保存地图管卡的文件,music保存背景音乐(mp3结尾),sounds保存音效(wav结尾),textures保存图片资源(png结尾)
//    ui防止界面相关的资源,ui/fonts字体文件,ui/cursors光标图像,ui/css放置css文件
//    properties放置配置信息,dialogues放置对话图文件,text放置文本信息
    private enum Direction{
        UP,DOWN,LEFT,RIGHT
    }
    private enum GameType{
        TANK,ENEMY,BULLET
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setMainMenuEnabled(true);
        gameSettings.getSupportedLanguages().add(Language.CHINESE);
        gameSettings.setDefaultLanguage(Language.CHINESE);
        gameSettings.setWidth(1920);
        gameSettings.setHeight(1080);
        gameSettings.setTitle("坦克大战");
        gameSettings.setVersion("1.0");
//        assets是所有资源的父目录
//        textures是专门用于存储图片的目录
        gameSettings.setAppIcon("tab.PNG");
    }

//    重写方法,获取用户的输入操作
    @Override
    protected void initInput() {
//        方向移动
        FXGL.getInput().addAction(new UserAction("向上") {
//            这个是当按下的时候,一直按着也算触发
            @Override
            protected void onAction() {
                if(isMoving){
                    return;
                }
                isMoving = true;
                dir = Direction.UP;
                tankEntity.translateY(-5);
                tankEntity.setRotation(0);
            }
        }, KeyCode.UP);
        FXGL.getInput().addAction(new UserAction("向下") {
            //            这个是当按下的时候,一直按着也算触发
            @Override
            protected void onAction() {
                if(isMoving){
                    return;
                }
                isMoving = true;
                dir = Direction.DOWN;
                tankEntity.translateY(5);
                tankEntity.setRotation(180);
            }
        }, KeyCode.DOWN);
        FXGL.getInput().addAction(new UserAction("向左") {
            //            这个是当按下的时候,一直按着也算触发
            @Override
            protected void onAction() {
                if(isMoving){
                    return;
                }
                isMoving = true;
                dir = Direction.LEFT;
                tankEntity.translateX(-5);
                tankEntity.setRotation(270);
            }
        }, KeyCode.LEFT);
        FXGL.getInput().addAction(new UserAction("向右") {
            //            这个是当按下的时候,一直按着也算触发
            @Override
            protected void onAction() {
                if(isMoving){
                    return;
                }
                isMoving = true;
                dir = Direction.RIGHT;
                tankEntity.translateX(5);
                tankEntity.setRotation(90);
            }
        }, KeyCode.RIGHT);
//        发射子弹
        FXGL.getInput().addAction(new UserAction("发射子弹") {
            @Override
            protected void onAction() {
//                我们的坦克是否存活
                if(!tankEntity.isActive()){
                    return;
                }
//                判断发射的时间间隔是否大于0.25秒,如果大于或者等于就发射
                if(!shootTimer.elapsed(duration)){
                    return;
                }
                shootTimer.capture();
                FXGL.play("fire.wav");
                Point2D p = null;
                if(dir == Direction.UP){
                    p = UP;
                }
                else if(dir == Direction.DOWN){
                    p = DOWN;
                }
                else if(dir == Direction.LEFT){
                    p = LEFT;
                }
                else if(dir == Direction.RIGHT){
                    p = RIGHT;
                }
//                创建子弹
                Entity bullet = FXGL.entityBuilder().at(tankEntity.getCenter().getX()-10,tankEntity.getCenter().getY()-10)
                        .type(GameType.BULLET)
                        .collidable()
//                        视图配上实体
                        .viewWithBBox(new Rectangle(20,20))
//                        子弹组件
                        .with(new ProjectileComponent(p,600))
//                        超越边界自动移除
                        .with(new OffscreenCleanComponent())
                        .buildAndAttach();
            }
        },KeyCode.SPACE);
    }

    @Override
    protected void initGame() {
//        FXGL.geti()获取数值
//        FXGL.getip()获取可观测的属性
        FXGL.getip(SCORE).addListener((obs, old, newV)->{
            if(newV.intValue() >= 10){
                FXGL.getNotificationService().pushNotification("指挥官,我们已摧毁敌方目标");
            }
        });
        shootTimer = FXGL.newLocalTimer();
//        Canvas canvas = new Canvas(100,100);
//        GraphicsContext g2D = canvas.getGraphicsContext2D();
//        g2D.setFill(Color.web("#ffec03"));
//        g2D.fillRect(0,0,80,30);
//        g2D.setFill(Color.web("#cebc17"));
//        g2D.fillRect(15,30,50,40);
//        g2D.setFill(Color.web("#ffec03"));
//        g2D.fillRect(0,70,80,30);
//        g2D.setFill(Color.web("#ffec03"));
//        g2D.fillRect(40,40,60,20);

//        游戏里面的对象Entity实体,是一个类似spring的组件对象
//        这里创建了这个实体,并且把我们的视图传递给他了

//        tankEntity = FXGL.entityBuilder()
////                这里的view就是我们创建的canvas,他只是一个外观,但是在游戏世界它没有实体
//                .view(canvas)
////                bbox决定了游戏实体的真实大小
////                比如视图是一朵花,但是碰撞体积的模型我们不可能真的给他一个花的模型,而是会选择一个近似的图像作为模型,如圆形,矩形
//                .bbox(BoundingShape.box(100,100))
////                因为在实际开发中,常常一个实体上会有多个视图
//                .view(new Text("我是一个玩家"))
//                .build();

//        但是假如我们的view大小和游戏体积是一样的,就可以直接用viewWithBBox
        Texture texture = FXGL.texture("Tank.png",100,100);
        tankEntity = FXGL.entityBuilder()
                .type(GameType.TANK)
                .collidable()
//                这里的view就是我们创建的canvas,他只是一个外观,但是在游戏世界它没有实体
//                bbox决定了游戏实体的真实大小
//                比如视图是一朵花,但是碰撞体积的模型我们不可能真的给他一个花的模型,而是会选择一个近似的图像作为模型,如圆形,矩形
                .viewWithBBox(texture)
//                因为在实际开发中,常常一个实体上会有多个视图
                .build();
//        设置坦克旋转的中心点
        tankEntity.setRotationOrigin(new Point2D(50,50));
        tankEntity.setOnNotActive(()->{
            FXGL.getNotificationService().pushNotification("我们失败了!指挥官大人");
        });
//        接下来我们要把它添加到游戏里面去
//        先获取游戏的世界,再添加这个实体
        FXGL.getGameWorld().addEntity(tankEntity);
        createEnemy();
    }

    @Override
    protected void onPreInit() {
//        预加载资源
//        设置游戏的初始化音量
        FXGL.getSettings().setGlobalMusicVolume(0.3);
        FXGL.getSettings().setGlobalSoundVolume(0.5);
        FXGL.loopBGM("music.mp3");
    }

    private void createEnemy() {
        FXGL.entityBuilder()
                .type(GameType.ENEMY)
                .at(FXGLMath.random(60,1920-60),FXGLMath.random(60,1080-60))
                .viewWithBBox(new Rectangle(60,60,Color.RED))
//                .with(new CollidableComponent())
//                可以简写为
                .collidable()
                .buildAndAttach();
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.TANK,GameType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity tank, Entity enemy) {
                // 获取双方中心点（在移除前获取位置）
                Point2D enemyCenter = enemy.getCenter();
                Point2D tankCenter = tank.getCenter();

                // 创建通用动画参数
                Duration animationDuration = Duration.seconds(0.35);
                double finalScale = 10;

                FXGL.play("strike.wav");

                // 敌人爆炸动画（蓝色）
                Circle enemyExplosion = new Circle(10, Color.BLUE);
                FXGL.entityBuilder()
                        .view(enemyExplosion)
                        .at(enemyCenter)
                        .with(new ExpireCleanComponent(animationDuration))
                        .buildAndAttach();

                ScaleTransition enemyScale = new ScaleTransition(animationDuration, enemyExplosion);
                enemyScale.setToX(finalScale);
                enemyScale.setToY(finalScale);

                FadeTransition enemyFade = new FadeTransition(animationDuration, enemyExplosion);
                enemyFade.setToValue(0);

                new ParallelTransition(enemyScale, enemyFade).play();

                // 坦克爆炸动画（红色）
                Circle tankExplosion = new Circle(10, Color.RED);
                FXGL.entityBuilder()
                        .view(tankExplosion)
                        .at(tankCenter)
                        .with(new ExpireCleanComponent(animationDuration))
                        .buildAndAttach();

                ScaleTransition tankScale = new ScaleTransition(animationDuration, tankExplosion);
                tankScale.setToX(finalScale);
                tankScale.setToY(finalScale);

                FadeTransition tankFade = new FadeTransition(animationDuration, tankExplosion);
                tankFade.setToValue(0);

                new ParallelTransition(tankScale, tankFade).play();

                // 移除实体（在动画开始后立即移除）
                enemy.removeFromWorld();
                tank.removeFromWorld();

                createEnemy();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.BULLET,GameType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy) {
//                int score = FXGL.geti(SCORE)+10;
//                FXGL.set(SCORE,score);
                FXGL.inc(SCORE,10);
                bullet.removeFromWorld();
                Point2D center = enemy.getCenter();
                enemy.removeFromWorld();
                Circle circle = new Circle(10,Color.RED);
                Duration duration = Duration.seconds(0.35);
                FXGL.play("strike.wav");
                Entity boom = FXGL.entityBuilder()
//                        超时自动清理组件
                        .with(new ExpireCleanComponent(duration))
                        .at(center)
                        .view(circle).buildAndAttach();
                ScaleTransition st = new ScaleTransition(duration,circle);
                st.setToX(10);
                st.setToY(10);
                FadeTransition ft = new FadeTransition(duration,circle);
                ft.setToValue(0);
                ParallelTransition pt = new ParallelTransition(st,ft);
//                设置结束事件
                pt.setOnFinished(event -> boom.removeFromWorld());
                pt.play();
                createEnemy();
            }
        });
    }

    @Override
    protected void initUI() {
//        Text text = FXGL.addVarText(SCORE,20,40);
//        text.setFill(Color.BLUE);
//        text.fontProperty().unbind();
//        text.setFont(Font.font(40));
        Text text = FXGL.getUIFactoryService().newText(FXGL.getip(SCORE).asString("当前得分:%d"));
        text.fontProperty().unbind();
        text.setLayoutX(20);
        text.setLayoutY(40);
        text.setFill(Color.BLUE);
        text.setFont(Font.font(40));
        FXGL.addUINode(text);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put(SCORE,0);
//        除了支持int类型,还支持double类型,Object类型,long类型,boolean类型,String类型,以及自定义类型
        vars.put(HIT_POINT,10.0);
//        全局游戏变量自带类型推断,不要填null,会导致游戏出错
        vars.put(NAME,"旧约");
    }

    //    重写update方法
//    这个方法在游戏的每一帧都会调用一次
    @Override
    protected void onUpdate(double tpf) {
//        tpf就是帧率
        isMoving = false;
    }
    public static void main(String[] args) {
        launch(args);
    }
}