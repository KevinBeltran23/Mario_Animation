import processing.core.PImage;

import java.util.List;

public abstract class Animate extends Actioned
{
    public Animate(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler) {
        eventScheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }
}
