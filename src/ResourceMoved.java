import processing.core.PImage;

import java.util.List;

public abstract class ResourceMoved extends Moved
{
    private final int resourceLimit;
    private int resourceCount;


    public ResourceMoved(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
        if (horiz == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof Stump)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
            if (vert == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof Stump)) {
                newPos = this.getPosition();
            }
        }
        return newPos;
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
