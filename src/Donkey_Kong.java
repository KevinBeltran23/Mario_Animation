import processing.core.PImage;

import java.util.List;

public class Donkey_Kong extends Actioned
{
    public Donkey_Kong(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    public void execute(WorldModel world, ImageStore imageStore, EventScheduler scheduler, Action action){
    }



}
