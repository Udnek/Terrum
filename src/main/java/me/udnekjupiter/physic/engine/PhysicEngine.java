package me.udnekjupiter.physic.engine;

import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Tickable;

public interface PhysicEngine extends Initializable, Tickable {
    double GRAVITATIONAL_ACCELERATION = -9.80665;
    double MAX_VELOCITY = 500;
    double MAX_FORCE = 50000;
    double MAX_DEPTH = 0.1;     //Жесткое ограничение, отражающее максимальную глубину на которую один хитбокс может погрузиться в другой
}
