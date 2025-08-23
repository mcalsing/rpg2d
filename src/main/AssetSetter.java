package main;

import objects.ObjectChest;
import objects.ObjectDoor;
import objects.ObjectKey;

public class AssetSetter {
  GamePanel gp;

  public AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  public void setObjects() {
    //Key
    gp.obj[0] = new ObjectKey();
    gp.obj[0].worldX = 24 * gp.tileSize;
    gp.obj[0].worldY = 13 * gp.tileSize;

    //Door
    gp.obj[1] = new ObjectDoor();
    gp.obj[1].worldX = 13 * gp.tileSize;
    gp.obj[1].worldY = 13 * gp.tileSize;

    //Chest
    gp.obj[2] = new ObjectChest();
    gp.obj[2].worldX = 13 * gp.tileSize;
    gp.obj[2].worldY = 9 * gp.tileSize;

  }
}
