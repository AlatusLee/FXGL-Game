package com.alatus.fxgl;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class D3Test extends Application {
    private static final double MOVEMENT_SPEED = 2.0;
    private static final double ROTATION_SPEED = 2.0;

    private Group root;
    private Group tank;
    private Camera camera;
    private PerspectiveCamera perspectiveCamera;

    private boolean wPressed, sPressed, aPressed, dPressed;
    private boolean leftPressed, rightPressed;

    @Override
    public void start(Stage primaryStage) {
        initScene(primaryStage);
        initTank();
        initCamera();
        initKeyboardControls();
        startGameLoop();
    }

    private void initScene(Stage stage) {
        root = new Group();
        Scene scene = new Scene(root, 1280, 720, true);
        scene.setFill(Color.SKYBLUE);

        // 设置3D场景
        scene.setCamera(perspectiveCamera = new PerspectiveCamera(true));
        perspectiveCamera.setNearClip(0.1);
        perspectiveCamera.setFarClip(10000.0);
        perspectiveCamera.setTranslateZ(-500);

        stage.setScene(scene);
        stage.setTitle("3D Tank Demo");
        stage.show();
    }

    private void initTank() {
        tank = new Group();

        // 坦克主体
        Box body = new Box(100, 50, 200);
        body.setMaterial(new PhongMaterial(Color.GREEN));

        // 炮塔
        Box turret = new Box(60, 40, 60);
        turret.setTranslateY(-20);
        turret.setMaterial(new PhongMaterial(Color.DARKGREEN));

        // 炮管
        Box gun = new Box(20, 20, 150);
        gun.setTranslateZ(75);  // 将炮管向前延伸
        gun.setMaterial(new PhongMaterial(Color.GRAY));

        tank.getChildren().addAll(body, turret, gun);
        root.getChildren().add(tank);
    }

    private void initCamera() {
        // 第三人称相机
        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-300);
        camera.setTranslateY(-150);
        camera.setRotationAxis(Rotate.Y_AXIS);
        camera.setRotate(180);

        // 将相机绑定到坦克
        tank.getChildren().add(camera);
    }

    private void initKeyboardControls() {
        Scene scene = root.getScene();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> wPressed = true;
                case S -> sPressed = true;
                case A -> aPressed = true;
                case D -> dPressed = true;
                case LEFT -> leftPressed = true;
                case RIGHT -> rightPressed = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W -> wPressed = false;
                case S -> sPressed = false;
                case A -> aPressed = false;
                case D -> dPressed = false;
                case LEFT -> leftPressed = false;
                case RIGHT -> rightPressed = false;
            }
        });
    }

    private void startGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                handleMovement();
                handleRotation();
                updateCamera();
            }
        }.start();
    }

    private void handleMovement() {
        if (wPressed) {
            tank.setTranslateZ(tank.getTranslateZ() + MOVEMENT_SPEED);
        }
        if (sPressed) {
            tank.setTranslateZ(tank.getTranslateZ() - MOVEMENT_SPEED);
        }
    }

    private void handleRotation() {
        if (leftPressed) {
            tank.setRotate(tank.getRotate() + ROTATION_SPEED);
        }
        if (rightPressed) {
            tank.setRotate(tank.getRotate() - ROTATION_SPEED);
        }
    }

    private void updateCamera() {
        // 保持相机跟随坦克
        camera.setTranslateX(tank.getTranslateX());
        camera.setTranslateZ(tank.getTranslateZ() - 300);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
