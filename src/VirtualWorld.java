//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import processing.core.PApplet;
import processing.core.PImage;

public final class VirtualWorld extends PApplet {
    private static String[] ARGS;
    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int VIEW_COLS = 20;
    private static final int VIEW_ROWS = 15;
    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 8421504;
    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.1;
    private String loadFile = "world.sav";
    private long startTimeMillis = 0L;
    private double timeScale = 1.0;
    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    public VirtualWorld() {
    }

    public void settings() {
        this.size(640, 480);
    }

    public void setup() {
        this.parseCommandLine(ARGS);
        this.loadImages("imagelist");
        this.loadWorld(this.loadFile, this.imageStore);
        this.view = new WorldView(15, 20, this, this.world, 32, 32);
        this.scheduler = new EventScheduler();
        this.startTimeMillis = System.currentTimeMillis();
        this.scheduleActions(this.world, this.scheduler, this.imageStore);
    }

    public void draw() {
        double appTime = (double)(System.currentTimeMillis() - this.startTimeMillis) * 0.001;
        double frameTime = (appTime - this.scheduler.getCurrentTime()) / this.timeScale;
        this.update(frameTime, this.scheduler);
        this.view.drawViewport();
    }

    public void mousePressed(){
        Point pressed = this.mouseToPoint();
        System.out.println("Monkey Time! " + pressed.x + ", " + pressed.y);
        for (Entity entity : this.world.getEntities()){
            if (entity.getPosition().nearby(pressed) && entity instanceof Tree){
                (entity).setID("bananaTime");
            }
        }
        List<Point> points = pressed.getNearby(this.view);
        System.out.println(points.size());
        for (Point point : points){
            this.world.setBackgroundCell(point, new Background("wood", imageStore.getImageList("wood")));
        }

        if (!world.isOccupied(pressed)){
            //Plant sapling = pressed.createSapling(WorldModel.getSaplingKey(), imageStore.getImageList(WorldModel.getSaplingKey()), 0);
            //sapling.addEntity(world);
            //sapling.scheduleActions(world, imageStore, scheduler);
            Donkey_Kong donkeyKong = pressed.createDonkeyKong("donkeyKong", 0.720, 0.180, 4, imageStore.getImageList("donkeyKong"));
            donkeyKong.addEntity(world);
            donkeyKong.scheduleActions(world, imageStore, scheduler);


        }
    }

    /*

    public void mousePressed() {
        Point pressed = this.mouseToPoint();
        System.out.println("CLICK! " + pressed.x + ", " + pressed.y);
        Optional<Entity> entityOptional = this.world.getOccupant(pressed);
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            PrintStream var10000 = System.out;
            String var10001 = entity.getId();
            if (entity instanceof Sapling sapling) {
                var10000.println(var10001 + ": " + "Sapling : " + sapling.getHealth());
            }
            if (entity instanceof Banana_Sapling bananaSapling) {
                var10000.println(var10001 + ": " + "Banana Sapling : " + bananaSapling.getHealth());
            }
            else if (entity instanceof Tree tree) {
                var10000.println(var10001 + ": " + "Tree : " + tree.getHealth());
            }
            else if (entity instanceof Banana_Tree bananaTree) {
                var10000.println(var10001 + ": " + "Banana Tree : " + bananaTree.getHealth());
            }
            else if (entity instanceof Fairy) {
                var10000.println(var10001 + ": " + "Fairy");
            }
            else if (entity instanceof Dude_Not_Full) {
                var10000.println(var10001 + ": " + "Dude_Not_Full");
            }
            else if (entity instanceof Dude_Full) {
                var10000.println(var10001 + ": " + "Dude_Full");
            }
            else if (entity instanceof Stump) {
                var10000.println(var10001 + ": " + "Stump");
            }
            else if (entity instanceof Banana_Stump) {
                var10000.println(var10001 + ": " + "Banana Stump");
            }
            else if (entity instanceof House) {
                var10000.println(var10001 + ": " + "House");
            }
            else{
                var10000.println(var10001 + ": " + "Obstacle");
            }
        }
    }

     */


    public void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Actioned) {
                ((Actioned) entity).scheduleActions(world, imageStore, scheduler);
            }
        }
    }

    public Point mouseToPoint() {
        return this.view.getViewport().viewportToWorld(this.mouseX / 32, this.mouseY / 32);
    }

    public void keyPressed() {
        if (this.key == '\uffff') {
            int dx = 0;
            int dy = 0;
            switch (this.keyCode) {
                case 37:
                    --dx;
                    break;
                case 38:
                    --dy;
                    break;
                case 39:
                    ++dx;
                    break;
                case 40:
                    ++dy;
            }

            this.view.getViewport().shiftView(dx, dy, this.view);
        }

    }

    public Background createDefaultBackground(ImageStore imageStore) {
        return new Background("background_default", imageStore.getImageList("background_default"));
    }

    public PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, 1);
        img.loadPixels();
        Arrays.fill(img.pixels, color);
        img.updatePixels();
        return img;
    }

    public void loadImages(String filename) {
        this.imageStore = new ImageStore(this.createImageColored(32, 32, 8421504));

        try {
            Scanner in = new Scanner(new File(filename));
            this.imageStore.loadImages(in, this);
        } catch (FileNotFoundException var3) {
            System.err.println(var3.getMessage());
        }

    }

    public void loadWorld(String file, ImageStore imageStore) {
        this.world = new WorldModel();

        try {
            Scanner in = new Scanner(new File(file));
            this.world.load(in, imageStore, this.createDefaultBackground(imageStore));
        } catch (FileNotFoundException var5) {
            Scanner in = new Scanner(file);
            this.world.load(in, imageStore, this.createDefaultBackground(imageStore));
        }
    }

    public void parseCommandLine(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG -> timeScale = Math.min(FAST_SCALE, timeScale);
                case FASTER_FLAG -> timeScale = Math.min(FASTER_SCALE, timeScale);
                case FASTEST_FLAG -> timeScale = Math.min(FASTEST_SCALE, timeScale);
                default -> loadFile = arg;
            }
        }
    }

    public static void main(String[] args) {
        ARGS = args;
        PApplet.main(VirtualWorld.class, new String[0]);
    }

    public static List<String> headlessMain(String[] args, double lifetime) {
        ARGS = args;
        VirtualWorld virtualWorld = new VirtualWorld();
        virtualWorld.setup();
        virtualWorld.update(lifetime, virtualWorld.scheduler);
        return virtualWorld.world.log();
    }

    void update(double frameTime, EventScheduler eventScheduler) {
        eventScheduler.updateOnTime(frameTime);
    }
}

