import processing.core.PImage;

import java.util.List;

public abstract class Banana extends Health
{
    public Banana(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod, health, healthLimit);
    }
}
