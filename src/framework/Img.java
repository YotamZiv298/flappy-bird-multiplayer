package framework;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Img {

    private static final HashMap<String, Image> _cache = new HashMap<>();

    public static Image loadImage(String path) {
        if (_cache.containsKey(path))
            return _cache.get(path);

        Image image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!_cache.containsKey(path))
            _cache.put(path, image);

        return image;
    }

}
