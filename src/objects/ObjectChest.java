package objects;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class ObjectChest extends Entity {

  public ObjectChest(GamePanel gp) {
    super(gp);

    name = "Chest";
    down1 = setup("/objects/chest");
  }
}
