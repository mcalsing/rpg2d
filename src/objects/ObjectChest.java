package objects;

import javax.imageio.ImageIO;
import java.util.Objects;

public class ObjectChest extends SuperObject {

  public ObjectChest() {
    name = "Chest";

    try {
      image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
