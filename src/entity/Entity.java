package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
  public int worldX, worldY;
  public int speed;
  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  public String directions = "down";
  public int spriteCounter = 0;
  public int spriteNum = 1;

  //npc
  public int actionLockCounter = 0;


  //Atributos do objeto
  public BufferedImage image1, image2, image3;
  public String name;
  public boolean collision = false;
  public int hp = 20;
  public int maxHp = 20;
  public int mana = 50;
  public int maxMana = 50;
  public int strength = 10;
  public int defense = 5;
  public int agility = 10;
  public int xpReward = 20;

  public String getName() {
    return name;
  }

  GamePanel gp;

  //Colisão do personagem
  public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collisionOn = false;

  public Entity(GamePanel gp) {
    this.gp = gp;
  }

  public void setAction() {
    //vai usar o metodo setAction da classe npc_oldman
  }

  public void update() {
    setAction();

    collisionOn = false;
    gp.cChecker.checkTile(this);
    gp.cChecker.checkObject(this, false);
    //gp.cChecker.checkEntity(this, gp.npc);
    //gp.cChecker.checkEntity(this, gp.monster);
    gp.cChecker.checkPlayer(this);

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
  }

  public void draw(Graphics2D g2) {

    BufferedImage image = null;
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
       worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
       worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
       worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

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

  public BufferedImage setup(String imagePath) {

    UtilityTool uTool = new UtilityTool();
    BufferedImage image = null;

    try {
      image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
      image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return image;
  }

}
