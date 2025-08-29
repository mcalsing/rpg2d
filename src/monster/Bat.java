package monster;

import entity.Entity;
import main.GamePanel;

import java.util.Random;

public class Bat extends Entity {

  public Bat(GamePanel gp) {
    super(gp);

    name = "Bat";
    speed = 1;
    maxHp = 10;
    hp = 10;
    agility = 8;
    strength = 4;

    solidArea.x = 0; // 3
    solidArea.y = 0; // 18
    solidArea.width = 48; //42
    solidArea.height = 48; //30
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;

    getImage();
  }

  public void getImage() {
    up1 = setup("/monster/bat_down_1");
    up2 = setup("/monster/bat_down_2");
    down1 = setup("/monster/bat_down_1");
    down2 = setup("/monster/bat_down_2");
    left1 = setup("/monster/bat_down_1");
    left2 = setup("/monster/bat_down_2");
    right1 = setup("/monster/bat_down_1");
    right2 = setup("/monster/bat_down_2");
  }

  public void setAction() {

    actionLockCounter++;

    if(actionLockCounter == 120) {

      Random random = new Random();
      int i = random.nextInt(100)+1;

      if (i <= 25) directions = "up";
      if (i > 25 && i <= 50) directions = "down";
      if (i > 50 && i <= 75) directions = "left";
      if (i > 75) directions = "right";

      actionLockCounter = 0;
    }
  }
}