//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import processing.core.PApplet;
import processing.core.PImage;

public final class ImageStore {
    private static final int KEYED_IMAGE_MIN = 5;
    private static final int COLOR_MASK = 16777215;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;
    private final Map<String, List<PImage>> images = new HashMap();
    private final List<PImage> defaultImages = new LinkedList();

    public ImageStore(PImage defaultImage) {
        this.defaultImages.add(defaultImage);
    }

    public List<PImage> getImageList(String key) {
        return (List)this.images.getOrDefault(key, this.defaultImages);
    }

    public void loadImages(Scanner in, PApplet screen) {
        for(int lineNumber = 0; in.hasNextLine(); ++lineNumber) {
            try {
                this.processImageLine(this.images, in.nextLine(), screen);
            } catch (NumberFormatException var5) {
                System.out.printf("Image format error on line %d\n", lineNumber);
            }
        }

    }

    public List<PImage> getImages(Map<String, List<PImage>> images, String key) {
        return (List)images.computeIfAbsent(key, (k) -> {
            return new LinkedList();
        });
    }

    public void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = this.getImages(images, key);
                imgs.add(img);
                if (attrs.length >= 5) {
                    int r = Integer.parseInt(attrs[2]);
                    int g = Integer.parseInt(attrs[3]);
                    int b = Integer.parseInt(attrs[4]);
                    this.setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }

    }

    public void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & 16777215;
        img.format = 2;
        img.loadPixels();

        for(int i = 0; i < img.pixels.length; ++i) {
            if ((img.pixels[i] & 16777215) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }

        img.updatePixels();
    }
}

