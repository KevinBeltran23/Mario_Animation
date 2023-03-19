import processing.core.PImage;

import java.util.List;

public abstract class Actioned extends Entity{

    private final double animationPeriod;


    public Actioned(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod(){
        return animationPeriod;
    }

    public void scheduleActions(WorldModel world, ImageStore imageStore, EventScheduler eventScheduler) {
        eventScheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        eventScheduler.scheduleEvent(this, this.createAnimationAction(0), this.getAnimationPeriod());
    }
}
