//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.*;

import processing.core.PImage;

public final class Point {
    private static final int SAPLING_HEALTH_LIMIT = 5;
    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.0;
    public static final List<String> PATH_KEYS = new ArrayList(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right", "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));
    public static final Random rand = new Random();

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max - min);
    }

    public static double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    static int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;
        return deltaX * deltaX + deltaY * deltaY;
    }

    public Optional<Entity> nearestEntity(List<Entity> entities) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), this);
            Iterator var4 = entities.iterator();

            while(var4.hasNext()) {
                Entity other = (Entity)var4.next();
                int otherDistance = distanceSquared(other.getPosition(), this);
                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public House createHouse(String id, List<PImage> images) {
        return new House(id, this, images, 0.0);
    }

    public Obstacle createObstacle(String id, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, this, images, 0.0, animationPeriod);
    }

    public Tree createTree(String id, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Tree(id, this, images, actionPeriod, animationPeriod, health, -1);
    }

    public Banana_Tree createBananaTree(String id, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Banana_Tree(id, this, images, actionPeriod, animationPeriod, health, 0);
    }

    public Sapling createSapling(String id, List<PImage> images, int health) {
        return new Sapling(id, this, images, 1.0, 1.0, 0, 5);
    }

    public Banana_Sapling createBananaSapling(String id, List<PImage> images, int health) {
        return new Banana_Sapling(id, this, images, 1.0, 1.0, 0, 5);
    }

    public Stump createStump(String id, List<PImage> images) {
        return new Stump(id, this, images, 0.0);
    }

    public Banana_Stump createBananaStump(String id, List<PImage> images) {
        return new Banana_Stump(id, this, images, 0.0);
    }

    public Dude_Full createDudeFull(String id, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Dude_Full(id, this, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public Dude_Not_Full createDudeNotFull(String id, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Dude_Not_Full(id, this, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public Fairy createFairy(String id, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Fairy(id, this, images, actionPeriod, animationPeriod);
    }

    public boolean adjacent(Point p2) {
        return this.x == p2.x && Math.abs(this.y - p2.y) == 1 || this.y == p2.y && Math.abs(this.x - p2.x) == 1;
    }

    public boolean nearby(Point p){
        return (Math.abs(this.x - p.x) + Math.abs(this.y - p.y)) <= 2 ;
    }

    public List<Point> getNearby(WorldView worldView){
        List<Point> points = new ArrayList<>();
        for(int row = 0; row < worldView.getViewport().getNumRows(); ++row) {
            for(int col = 0; col < worldView.getViewport().getNumCols(); ++col) {
                Point worldPoint = worldView.getViewport().viewportToWorld(col, row);
                if (worldPoint.nearby(this)){
                    points.add(worldPoint);
                }
            }
        }
        return points;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point)other).x == this.x && ((Point)other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + this.x;
        result = result * 31 + this.y;
        return result;
    }
}
