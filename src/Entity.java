//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.List;
import java.util.Optional;
import processing.core.PImage;

public abstract class Entity {

    public Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final String id;

    public Entity(String id, Point position, List<PImage> images, double actionPeriod) {
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.id = id;
    }

    public Action createAnimationAction(int repeatCount) {
        return new Animation(this, repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    public String log() {
        return this.getId().isEmpty() ? null : String.format("%s %d %d %d", this.getId(), this.getPosition().x, this.getPosition().y, this.getImageIndex());
    }

    public void addEntity(WorldModel worldModel) {
        if (worldModel.withinBounds(this.getPosition())) {
            worldModel.setOccupancyCell(this.getPosition(), this);
            worldModel.getEntities().add(this);
        }
    }

    public void removeEntity(EventScheduler scheduler, WorldModel worldModel) {
        scheduler.unscheduleAllEvents(this);
        worldModel.removeEntityAt(this.getPosition());
    }

    public void moveEntity(WorldModel world, EventScheduler scheduler, Point pos) {
        Point oldPos = this.getPosition();
        if (world.withinBounds(pos) && !pos.equals(oldPos)) {
            world.setOccupancyCell(oldPos, null);
            Optional<Entity> occupant = world.getOccupant(pos);
            occupant.ifPresent((target) -> {
                target.removeEntity(scheduler, world);
            });
            world.setOccupancyCell(pos, this);
            this.setPosition(pos);
        }
    }

    public void tryAddEntity(WorldModel worldModel) {
        if (worldModel.isOccupied(this.getPosition())) {
            throw new IllegalArgumentException("position occupied");
        } else {
            this.addEntity(worldModel);
        }
    }

    public String getId(){
        return this.id;
    }

    public Point getPosition(){
        return this.position;
    }

    public List<PImage> getImages(){
        return this.images;
    }

    public int getImageIndex(){
        return this.imageIndex;
    }

    public void nextImage(){
        this.imageIndex += 1;
    }

    public double getActionPeriod() {
        return actionPeriod;
    }

    public void setPosition(Point pos){
        this.position = pos;
    }

}
