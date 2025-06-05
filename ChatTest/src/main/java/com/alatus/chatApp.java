package com.alatus;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.cutscene.Cutscene;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class chatApp extends GameApplication {
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(816);
        gameSettings.setHeight(624);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new ChatFactory());
        spawn("bg");
        runOnce(()->{
            List<String> lines = getAssetLoader().loadText("chat.txt");
            Cutscene cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene);
        }, Duration.ONE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
