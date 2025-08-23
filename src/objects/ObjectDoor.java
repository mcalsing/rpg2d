package objects;

import javax.imageio.ImageIO;
import java.util.Objects;

public class ObjectDoor extends SuperObject {

  public ObjectDoor() {
    name = "Door";

    try {
      image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    collision = true;
  }
}
