package objects;

import entity.Entity;
import main.GamePanel;

public class ObjectDoor extends Entity {

  public ObjectDoor(GamePanel gp) {
    super(gp);

    name = "Door";
    down1 = setup("/objects/door");
    collision = true;

    solidArea.x = 0;
    solidArea.y = 0; //16 ou 0
    solidArea.width = 48;
    solidArea.height = 48; //32 ou 48
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
  }
}
