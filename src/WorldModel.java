//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import processing.core.PImage;

public final class WorldModel {
    private int numRows;
    private int numCols;
    private Background[][] background;
    private Entity[][] occupancy;
    private Set<Entity> entities;

    public WorldModel() {
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public Set<Entity> getEntities() {
        return this.entities;
    }

    public static String getTreeKey() {
        return "tree";
    }

    public static String getSaplingKey() {
        return "sapling";
    }

    public static String getStumpKey() {
        return "stump";
    }

    public static String getBananaTreeKey(){return "bananaTree";}

    public static String getBananaSaplingKey(){return "bananaSapling";}

    public static String getBananaStumpKey(){return "bananaStump";}

    public Optional<Entity> findNearest(Point pos, List<Class> entityKind) {
        List<Entity> ofType = new LinkedList();

        for (Class kind : entityKind) {

            for (Entity entity : this.entities) {
                if (entity instanceof Sapling && kind == Sapling.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Banana_Sapling && kind == Banana_Sapling.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Tree && kind == Tree.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Banana_Tree && kind == Banana_Sapling.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Fairy && kind == Fairy.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Dude_Not_Full && kind == Dude_Not_Full.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Dude_Full && kind == Dude_Full.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Stump && kind == Stump.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Banana_Stump && kind == Banana_Stump.class) {
                    ofType.add(entity);
                }
                if (entity instanceof House && kind == House.class) {
                    ofType.add(entity);
                }
                if (entity instanceof Obstacle && kind == Obstacle.class) {
                    ofType.add(entity);
                }
            }
        }

        return pos.nearestEntity(ofType);
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.y][pos.x] = background;
    }

    public Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }

    public void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }

    public Optional<Entity> getOccupant(Point pos) {
        return isOccupied(pos) ? Optional.of(this.getOccupancyCell(pos)) : Optional.empty();
    }

    public Optional<PImage> getBackgroundImage(Point pos) {
        return withinBounds(pos) ? Optional.of(getCurrentImageBackground(this.getBackgroundCell(pos))) : Optional.empty();
    }

    public void load(Scanner saveFile, ImageStore imageStore, Background defaultBackground) {
        this.parseSaveFile(saveFile, imageStore, defaultBackground);
        if (this.background == null) {
            this.background = new Background[this.numRows][this.numCols];
            Background[][] var4 = this.background;
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Background[] row = var4[var6];
                Arrays.fill(row, defaultBackground);
            }
        }

        if (this.occupancy == null) {
            this.occupancy = new Entity[this.numRows][this.numCols];
            this.entities = new HashSet();
        }

    }

    public void parseStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 0) {
            Entity entity = pt.createStump(id, imageStore.getImageList("stump"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "stump", 0));
        }
    }

    public void parseBananaStump(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 0) {
            Entity entity = pt.createBananaStump(id, imageStore.getImageList("bananaStump"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "bananaStump", 0));
        }
    }

    public void parseObstacle(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 1) {
            Entity entity = pt.createObstacle(id, Double.parseDouble(properties[0]), imageStore.getImageList("obstacle"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "obstacle", 1));
        }
    }

    public void parseTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 3) {
            Entity entity = pt.createTree(id, Double.parseDouble(properties[1]), Double.parseDouble(properties[0]), Integer.parseInt(properties[2]), imageStore.getImageList("tree"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "tree", 3));
        }
    }

    public void parseBananaTree(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 3) {
            Entity entity = pt.createBananaTree(id, Double.parseDouble(properties[1]), Double.parseDouble(properties[0]), Integer.parseInt(properties[2]), imageStore.getImageList("bananaTree"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "bananaTree", 3));
        }
    }

    public void parseFairy(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 2) {
            Entity entity = pt.createFairy(id, Double.parseDouble(properties[1]), Double.parseDouble(properties[0]), imageStore.getImageList("fairy"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "fairy", 2));
        }
    }

    public void parseDude(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 3) {
            Entity entity = pt.createDudeNotFull(id, Double.parseDouble(properties[0]), Double.parseDouble(properties[1]), Integer.parseInt(properties[2]), imageStore.getImageList("dude"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "dude", 3));
        }
    }

    public void parseSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 1) {
            int health = Integer.parseInt(properties[0]);
            Entity entity = pt.createSapling(id, imageStore.getImageList("sapling"), health);
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "sapling", 1));
        }
    }

    public void parseBananaSapling(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 1) {
            int health = Integer.parseInt(properties[0]);
            Entity entity = pt.createBananaSapling(id, imageStore.getImageList("bananaSapling"), health);
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "bananaSapling", 1));
        }
    }

    public void parseSaveFile(Scanner saveFile, ImageStore imageStore, Background defaultBackground) {
        String lastHeader = "";
        int headerLine = 0;
        int lineCounter = 0;

        while(saveFile.hasNextLine()) {
            ++lineCounter;
            String line = saveFile.nextLine().strip();
            if (line.endsWith(":")) {
                headerLine = lineCounter;
                lastHeader = line;
                switch (line) {
                    case "Backgrounds:":
                        this.background = new Background[this.numRows][this.numCols];
                        break;
                    case "Entities:":
                        this.occupancy = new Entity[this.numRows][this.numCols];
                        this.entities = new HashSet();
                }
            } else {
                switch (lastHeader) {
                    case "Rows:":
                        this.numRows = Integer.parseInt(line);
                        break;
                    case "Cols:":
                        this.numCols = Integer.parseInt(line);
                        break;
                    case "Backgrounds:":
                        this.parseBackgroundRow(line, lineCounter - headerLine - 1, imageStore);
                        break;
                    case "Entities:":
                        this.parseEntity(line, imageStore);
                }
            }
        }

    }

    public void parseEntity(String line, ImageStore imageStore) {
        String[] properties = line.split(" ", 5);
        if (properties.length >= 4) {
            String key = properties[0];
            String id = properties[1];
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            properties = properties.length == 4 ? new String[0] : properties[4].split(" ");
            switch (key) {
                case "obstacle":
                    this.parseObstacle(properties, pt, id, imageStore);
                    break;
                case "dude":
                    this.parseDude(properties, pt, id, imageStore);
                    break;
                case "fairy":
                    this.parseFairy(properties, pt, id, imageStore);
                    break;
                case "house":
                    this.parseHouse(properties, pt, id, imageStore);
                    break;
                case "tree":
                    this.parseTree(properties, pt, id, imageStore);
                    break;
                case "bananaTree":
                    this.parseBananaTree(properties, pt, id, imageStore);
                    break;
                case "sapling":
                    this.parseSapling(properties, pt, id, imageStore);
                    break;
                case "bananaSapling":
                    this.parseBananaSapling(properties, pt, id, imageStore);
                    break;
                case "stump":
                    this.parseStump(properties, pt, id, imageStore);
                    break;
                case "bananaStump":
                    this.parseBananaStump(properties, pt, id, imageStore);
                    break;
                default:
                    throw new IllegalArgumentException("Entity key is unknown");
            }

        } else {
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public void parseBackgroundRow(String line, int row, ImageStore imageStore) {
        String[] cells = line.split(" ");
        if (row < this.numRows) {
            int rows = Math.min(cells.length, this.numCols);

            for(int col = 0; col < rows; ++col) {
                this.background[row][col] = new Background(cells[col], imageStore.getImageList(cells[col]));
            }
        }

    }

    public void parseHouse(String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == 0) {
            Entity entity = pt.createHouse(id, imageStore.getImageList("house"));
            entity.tryAddEntity(this);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", "house", 0));
        }
    }

    public void removeEntityAt(Point point) {
        if (withinBounds(point) && this.getOccupancyCell(point) != null) {
            Entity entity = this.getOccupancyCell(point);
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(point, (Entity)null);
        }

    }

    public List<String> log() {
        List<String> list = new ArrayList();
        Iterator var2 = this.entities.iterator();

        while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            String log = entity.log();
            if (log != null) {
                list.add(log);
            }
        }

        return list;
    }

    public PImage getCurrentImageBackground(Object object) {
        if (object instanceof Background background) {
            return background.getImages().get(background.getImageIndex());
        } else {
            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", object));
        }
    }

    public boolean isOccupied(Point point) {
        return withinBounds(point) && getOccupancyCell(point) != null;
    }

    public boolean withinBounds(Point point) {
        return point.y >= 0 && point.y < getNumRows() && point.x >= 0 && point.x < getNumCols();
    }
}

