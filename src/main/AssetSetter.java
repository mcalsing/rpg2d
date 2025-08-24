package main;

import entity.NPC_OldMan;
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
    gp.obj[0] = new ObjectKey(gp);
    gp.obj[0].worldX = 24 * gp.tileSize;
    gp.obj[0].worldY = 13 * gp.tileSize;

    //Door
    gp.obj[1] = new ObjectDoor(gp);
    gp.obj[1].worldX = 13 * gp.tileSize;
    gp.obj[1].worldY = 13 * gp.tileSize;

    //Chest
    gp.obj[2] = new ObjectChest(gp);
    gp.obj[2].worldX = 13 * gp.tileSize;
    gp.obj[2].worldY = 9 * gp.tileSize;

  }

  public void setNPC() {

    gp.npc[0] = new NPC_OldMan(gp);
    gp.npc[0].worldX = 24 * gp.tileSize;
    gp.npc[0].worldY = 15 * gp.tileSize;
  }
}
