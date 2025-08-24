package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

  public NPC_OldMan(GamePanel gp) {
    super(gp);

    directions = "down";
    speed = 1;

    getImage();
  }

  public void getImage() {
    up1 = setup("/npc/oldman_up_1");
    up2 = setup("/npc/oldman_up_2");
    down1 = setup("/npc/oldman_down_1");
    down2 = setup("/npc/oldman_down_2");
    left1 = setup("/npc/oldman_left_1");
    left2 = setup("/npc/oldman_left_2");
    right1 = setup("/npc/oldman_right_1");
    right2 = setup("/npc/oldman_right_2");
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
