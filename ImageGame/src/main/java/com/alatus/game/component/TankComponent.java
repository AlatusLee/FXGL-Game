package com.alatus.game.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import static com.almasb.fxgl.dsl.FXGL.texture;

public class TankComponent extends Component {
    private InformComponent informComponent;
//    当组件被添加到实体的时候触发
    @Override
    public void onAdded() {
        ViewComponent viewComponent = entity.getViewComponent();
        viewComponent.addOnClickHandler(event->{
            Texture texture = (Texture) viewComponent.getChildren().get(0);
            texture.dispose();
            viewComponent.clearChildren();
//            entity.getComponent(InformComponent.class).Inform();
//            自动注入?IOC
            informComponent.Inform();
            viewComponent.addChild(texture("Tank.png",100,100)
                    .multiplyColor(FXGLMath.randomColor()));
        });
    }

//    @Override
//    public void onUpdate(double tpf) {
        ////这个方法每帧都会调用,很消耗性能的
//    }
}
