import processing.core.PImage;

import java.util.List;

public abstract class Plant extends Actioned
{
    private int health;
    private final int healthLimit;

    public Plant(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod,int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int num){
        this.health += num;
    }

    public int getHealthLimit(){
        return this.healthLimit;
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore, Action action) {
        return this.transform(world, scheduler, imageStore);
    }
    public abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public abstract void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action);
}
