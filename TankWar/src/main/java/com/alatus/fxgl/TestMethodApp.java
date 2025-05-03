package com.alatus.fxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;

import java.util.Map;

public class TestMethodApp extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
//        初始化游戏的设置
        System.out.println("initSettings"+Thread.currentThread().getName());
//        宽度高度,版本图标,菜单等
        gameSettings.setMainMenuEnabled(true);
    }

    public TestMethodApp() {
        System.out.println("TestMethodApp"+Thread.currentThread().getName());
        System.out.println("盖亚");
    }

    @Override
    protected void onPreInit() {
        System.out.println("onPreInit"+Thread.currentThread().getName());
//        游戏的预处理
        System.out.println("游戏预处理");
        super.onPreInit();
    }

    @Override
    protected void initInput() {
//        设置输入,对用户操作的监听
        System.out.println("initInput"+Thread.currentThread().getName());
        super.initInput();
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        System.out.println("initGameVars"+Thread.currentThread().getName());
//        设置游戏的变量,可以游戏其他的生命周期随时访问
        super.initGameVars(vars);
    }

    @Override
    protected void initGame() {
        System.out.println("initGame"+Thread.currentThread().getName());
//        初始化游戏,创建人物啊,地图啊,设置音量啊
        System.out.println("游戏初始化了");
    }

    @Override
    protected void initPhysics() {
        System.out.println("initPhysics"+Thread.currentThread().getName());
//        初始化物理设置,比如碰撞的检测等
        super.initPhysics();
    }

    @Override
    protected void initUI() {
        System.out.println("initUI"+Thread.currentThread().getName());
//        初始化界面上的组件
        printTimer = FXGL.newLocalTimer();
        super.initUI();
    }

    private LocalTimer printTimer;
    @Override
    protected void onUpdate(double tpf) {
        if(printTimer.elapsed(Duration.seconds(5))){
            System.out.println("五秒钟过去了");
            System.out.println("onUpdate"+Thread.currentThread().getName());
            printTimer.capture();
        }
//        游戏每帧刷新调用
        super.onUpdate(tpf);
    }

    public static void main(String[] args) {
        System.out.println("main"+Thread.currentThread().getName());
        System.out.println("启动");
        launch(args);
    }
}
