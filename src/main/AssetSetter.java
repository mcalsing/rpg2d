package main;

import entity.NPC_OldMan;
import monster.Orc;
import objects.ObjectChest;
import objects.ObjectDoor;
import objects.ObjectKey;

public class AssetSetter {
  GamePanel gp;

  public AssetSetter(GamePanel gp) {
    this.gp = gp;
  }

  public void setObjects() {
    int mapNum = 0;

    //Key
    gp.obj[mapNum][0] = new ObjectKey(gp);
    gp.obj[mapNum][0].worldX = gp.tileSize * 23;
    gp.obj[mapNum][0].worldY = gp.tileSize * 12;

    //Door
    gp.obj[mapNum][1] = new ObjectDoor(gp);
    gp.obj[mapNum][1].worldX = gp.tileSize * 12;
    gp.obj[mapNum][1].worldY = gp.tileSize * 12;

    gp.obj[mapNum][3] = new ObjectDoor(gp);
    gp.obj[mapNum][3].worldX = gp.tileSize * 21;
    gp.obj[mapNum][3].worldY = gp.tileSize * 22;

    gp.obj[mapNum][4] = new ObjectDoor(gp);
    gp.obj[mapNum][4].worldX = gp.tileSize * 23;
    gp.obj[mapNum][4].worldY = gp.tileSize * 25;

    //Chest
    gp.obj[mapNum][2] = new ObjectChest(gp);
    gp.obj[mapNum][2].worldX = gp.tileSize * 12;
    gp.obj[mapNum][2].worldY = gp.tileSize * 8;

  }

  public void setNPC() {
    int mapNum = 0;

    gp.npc[mapNum][0] = new NPC_OldMan(gp);
    gp.npc[mapNum][0].worldX = gp.tileSize * 24;
    gp.npc[mapNum][0].worldY = gp.tileSize * 15;
  }

  public void setMonster() {
    int mapNum = 0;

    gp.monster[mapNum][0] = new Orc(gp);
    gp.monster[mapNum][0].worldX = gp.tileSize * 23;
    gp.monster[mapNum][0].worldY = gp.tileSize * 36;

    gp.monster[mapNum][1] = new Orc(gp);
    gp.monster[mapNum][1].worldX = gp.tileSize * 23;
    gp.monster[mapNum][1].worldY = gp.tileSize * 39;
  }
}
