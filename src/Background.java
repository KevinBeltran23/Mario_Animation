//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.List;
import processing.core.PImage;

public final class Background {
    private final String id;
    private final List<PImage> images;
    private int imageIndex;

    public List<PImage> getImages() {
        return this.images;
    }

    public int getImageIndex() {
        return this.imageIndex;
    }

    public Background(String id, List<PImage> images) {
        this.imageIndex = imageIndex;
        this.id = id;
        this.images = images;
    }
}

