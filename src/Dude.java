import processing.core.PImage;

import java.util.List;

public abstract class Dude extends Moved
{
    private final int resourceLimit;
    private int resourceCount;


    public Dude(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit(){
        return this.resourceLimit;
    }

    public double getResourceCount(){
        return this.resourceCount;
    }

    public void setResourceCount(int num){
        this.resourceCount += num;
    }

    abstract public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action);

}
