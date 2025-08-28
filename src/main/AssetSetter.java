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
    gp.obj[0].worldX = gp.tileSize * 23;
    gp.obj[0].worldY = gp.tileSize * 12;

    //Door
    gp.obj[1] = new ObjectDoor(gp);
    gp.obj[1].worldX = gp.tileSize * 12;
    gp.obj[1].worldY = gp.tileSize * 12;

    gp.obj[3] = new ObjectDoor(gp);
    gp.obj[3].worldX = gp.tileSize * 21;
    gp.obj[3].worldY = gp.tileSize * 22;

    gp.obj[4] = new ObjectDoor(gp);
    gp.obj[4].worldX = gp.tileSize * 23;
    gp.obj[4].worldY = gp.tileSize * 25;

    //Chest
    gp.obj[2] = new ObjectChest(gp);
    gp.obj[2].worldX = gp.tileSize * 12;
    gp.obj[2].worldY = gp.tileSize * 8;

  }

  public void setNPC() {

    gp.npc[0] = new NPC_OldMan(gp);
    gp.npc[0].worldX = gp.tileSize * 24;
    gp.npc[0].worldY = gp.tileSize * 15;
  }
}
