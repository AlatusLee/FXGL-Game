package com.alatus.fxgl;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class HelloWorldApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
//        设置名称
        gameSettings.setTitle("Hello World");
//        设置版本号
        gameSettings.setVersion("1.0");
//        设置高度
        gameSettings.setHeight(385);
//        设置宽度
        gameSettings.setWidth(500);
    }

    public static void main(String[] args) {
        launch(HelloWorldApp.class, args);
    }
}
