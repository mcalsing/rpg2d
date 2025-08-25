package objects;

import entity.Entity;
import main.GamePanel;

public class ObjectDoor extends Entity {

  public ObjectDoor(GamePanel gp) {
    super(gp);

    name = "Door";
    down1 = setup("/objects/door");
    collision = true;

    //solidArea.x = 0
  }
}
