package objects;

import javax.imageio.ImageIO;
import java.util.Objects;

public class ObjectKey extends SuperObject {

  public ObjectKey() {
    name = "Key";

    try {
      image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
