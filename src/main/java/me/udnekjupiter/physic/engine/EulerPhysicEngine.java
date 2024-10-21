package me.udnekjupiter.physic.engine;

import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.controller.Controller;

public class EulerPhysicEngine extends PhysicEngine3d{
    @Override
    public void initialize() {
        Console.getInstance().addListener(this);
        Controller.getInstance().addListener(this);

    }
}
