import processing.core.PImage;

import java.util.List;

public abstract class Health extends Actioned
{

    private int health;
    private int healthLimit;

    public Health(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod,int health, int healthLimit) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.health = health;
        this.healthLimit = healthLimit;
    }

    public int getHealth() {
        return this.health;
    }

    public void adjustHealth(int num){
        this.health += num;
    }

    public void setHealth(int num){this.health = num;}

    public int getHealthLimit(){
        return this.healthLimit;
    }

    public void setHealthLimit(int num){this.healthLimit = num;}

    public boolean transformHealthEntity(WorldModel world, EventScheduler scheduler, ImageStore imageStore, Action action) {
        return this.transform(world, scheduler, imageStore);
    }
    public abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);

    public abstract void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action);
}
