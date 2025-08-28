package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Player extends Entity {

  KeyHandler keyH;
  public final int screenX;
  public final int screenY;
  int standCounter = 0;

  int  hasKey = 0;

  // Movimentação por grid
  boolean moving = false;
  int pixelCounter = 0;

  public Player(GamePanel gp, KeyHandler keyH) {
    super(gp);
    this.keyH = keyH;

    screenX = gp.screenWidth/2 - (gp.tileSize/2);
    screenY = gp.screenHeight/2 - (gp.tileSize/2);

    // Area de colisão do jogador 32x32 ao invés de 48x48
    solidArea = new Rectangle();
    solidArea.x = 1;
    solidArea.y = 1;
    solidAreaDefaultX = solidArea.x;
    solidAreaDefaultY = solidArea.y;
    solidArea.width = 46;
    solidArea.height = 46;

    setDefaultValues();
    getPlayerImage();
  }

  public void setDefaultValues() {
    worldX = gp.tileSize * 23;
    worldY = gp.tileSize * 21;
    speed = 4;
    directions = "down";
  }

  public void getPlayerImage() {
    up1 = setup("/player/boy_up_1");
    up2 = setup("/player/boy_up_2");
    down1 = setup("/player/boy_down_1");
    down2 = setup("/player/boy_down_2");
    left1 = setup("/player/boy_left_1");
    left2 = setup("/player/boy_left_2");
    right1 = setup("/player/boy_right_1");
    right2 = setup("/player/boy_right_2");
  }

  public void update() {

    // Movimentação baseada por tile/grid/sqm
    if (moving == false) {

      // Movimentação normal
      if (keyH.upPressed  || keyH.downPressed || keyH.leftPressed || keyH.rightPressed ) {

        if (keyH.upPressed) {
          directions = "up";
        } else if (keyH.downPressed) {
          directions = "down";
        } else if (keyH.leftPressed) {
          directions = "left";
        } else if (keyH.rightPressed) {
          directions = "right";
        }

        moving = true;

        // Check tile collision
        collisionOn = false;
        gp.cChecker.checkTile(this);
      } else {
        standCounter++;
        if (standCounter == 20) {
          spriteNum = 1;
          standCounter = 0;
        }
      }
    }

    // Check object collision
    int objIndex =  gp.cChecker.checkObject(this, true);
    pickUpObject(objIndex);

    // Check NPC collision
    int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
    interactNPC(npcIndex);

    // Check monster collision
    int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);

    if (moving) {

      // If collision is false, player can move
      if (!collisionOn) {
        switch (directions) {
          case "up":    worldY -= speed; break;
          case "down":  worldY += speed; break;
          case "left":  worldX -= speed; break;
          case "right": worldX += speed; break;
        }
      }

      //Sprite Changer. A cada 13 frames troca de sprite
      spriteCounter++;
      if (spriteCounter > 12) {
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      }

      pixelCounter += speed;

      if (pixelCounter == 48) {
        moving = false;
        pixelCounter = 0;
      }
    }
  }

  public void pickUpObject(int i) {

    if (i != 999) {
      String objectName = gp.obj[i].name;

      switch (objectName) {
        case "Key":
          hasKey++;
          gp.obj[i] = null;
          System.out.println("Key: " +hasKey);
          break;
        case "Door":
          if (hasKey > 0) {
            gp.obj[i] = null;
            hasKey--;
          }
          System.out.println("Key: " +hasKey);
          break;
      }
    }
  }

  public void interactNPC(int i) {
    if (i != 999) {
      //System.out.println("You are hitting a npc");
    }
  }

  public void draw(Graphics2D g2) {
    BufferedImage image = null;
    switch(directions) {
      case "up":
        if (spriteNum == 1) image = up1;
        if (spriteNum == 2) image = up2;
        break;
      case "down":
        if (spriteNum == 1) image = down1;
        if (spriteNum == 2) image = down2;
        break;
      case "left":
        if (spriteNum == 1) image = left1;
        if (spriteNum == 2) image = left2;
        break;
      case "right":
        if (spriteNum == 1) image = right1;
        if (spriteNum == 2) image = right2;
        break;
    }
    g2.drawImage(image, screenX, screenY, null);

  }
}
