package objects;

import entity.Entity;
import main.GamePanel;

public class ObjectKey extends Entity {


  public ObjectKey(GamePanel gp) {
    super(gp);

    name = "Key";
    down1 = setup("/objects/key");

    // ao inves de "down1..." no corações de vida, colocar as 3 linhas abaixo
    //image = setup("/objects/chest.png");
    //image1 = setup("/objects/chest.png");
    //image2 = setup("/objects/chest.png");
  }
}
