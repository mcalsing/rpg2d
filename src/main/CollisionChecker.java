package main;

import entity.Entity;

import java.awt.*;

public class CollisionChecker {

  GamePanel gp;

  public CollisionChecker(GamePanel gp) {
    this.gp = gp;
  }

  public void checkTile(Entity entity) {

    // Criar cópia da área sólida da entidade
    Rectangle entityArea = new Rectangle(
      entity.worldX + entity.solidArea.x,
      entity.worldY + entity.solidArea.y,
      entity.solidArea.width,
      entity.solidArea.height
    );

    int entityLeftCol, entityRightCol, entityTopRow, entityBottomRow;
    int tileNum1, tileNum2;

    switch (entity.directions) {
      case "up":
        entityArea.y  -= entity.speed;
        entityLeftCol  = entityArea.x / gp.tileSize;
        entityRightCol = (entityArea.x + entityArea.width) / gp.tileSize;
        entityTopRow   = entityArea.y / gp.tileSize;

        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;

      case "down":
        entityArea.y   += entity.speed;
        entityLeftCol   = entityArea.x / gp.tileSize;
        entityRightCol  = (entityArea.x + entityArea.width) / gp.tileSize;
        entityBottomRow = (entityArea.y + entityArea.height) / gp.tileSize;

        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;

      case "left":
        entityArea.x   -= entity.speed;
        entityLeftCol   = entityArea.x / gp.tileSize;
        entityTopRow    = entityArea.y / gp.tileSize;
        entityBottomRow = (entityArea.y + entityArea.height) / gp.tileSize;

        tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;

      case "right":
        entityArea.x   += entity.speed;
        entityRightCol  = (entityArea.x + entityArea.width) / gp.tileSize;
        entityTopRow    = entityArea.y / gp.tileSize;
        entityBottomRow = (entityArea.y + entityArea.height) / gp.tileSize;

        tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;
    }
  }
  public int checkObject(Entity entity, boolean player) {
    int index = 999;

    for (int i = 0; i < gp.obj.length; i++) {
      if (gp.obj[i] != null) {

        // Criar cópia temporária da área sólida da entidade
        Rectangle entityArea = new Rectangle(
          entity.worldX + entity.solidArea.x,
          entity.worldY + entity.solidArea.y,
          entity.solidArea.width,
          entity.solidArea.height
        );

        // Criar cópia temporária da área sólida do objeto
        Rectangle objectArea = new Rectangle(
          gp.obj[i].worldX + gp.obj[i].solidArea.x,
          gp.obj[i].worldY + gp.obj[i].solidArea.y,
          gp.obj[i].solidArea.width,
          gp.obj[i].solidArea.height
        );

        // Checa colisão em cada direção usando apenas a cópia
        switch (entity.directions) {
          case "up":    entityArea.y -= entity.speed; break;
          case "down":  entityArea.y += entity.speed; break;
          case "left":  entityArea.x -= entity.speed; break;
          case "right": entityArea.x += entity.speed; break;
        }

        if (entityArea.intersects(objectArea)) {
          if (gp.obj[i].collision) {
            entity.collisionOn = true;
          }
          if (player) {
            index = i;
          }
        }
      }
    }
    return index;
  }

  //Colisao do jogador com NPC ou monstro
  public int checkEntity(Entity entity, Entity[] target) {
    int index = 999;

    for (int i = 0; i < target.length; i++) {
      if (target[i] != null) {

        // Área temporária da entidade
        Rectangle entityArea = new Rectangle(
          entity.worldX + entity.solidArea.x,
          entity.worldY + entity.solidArea.y,
          entity.solidArea.width,
          entity.solidArea.height
        );

        // Área temporária do target
        Rectangle targetArea = new Rectangle(
          target[i].worldX + target[i].solidArea.x,
          target[i].worldY + target[i].solidArea.y,
          target[i].solidArea.width,
          target[i].solidArea.height
        );

        switch (entity.directions) {
          case "up":    entityArea.y -= entity.speed; break;
          case "down":  entityArea.y += entity.speed; break;
          case "left":  entityArea.x -= entity.speed; break;
          case "right": entityArea.x += entity.speed; break;
        }

        if (entityArea.intersects(targetArea)) {
          //if (targetArea != entity) {

          //}
          entity.collisionOn = true;
          index = i;
        }
      }
    }
    return index;
  }

  //Colisao do npc com o jogador
  public void checkPlayer(Entity entity) {
    // Área temporária da entidade
    Rectangle entityArea = new Rectangle(
      entity.worldX + entity.solidArea.x,
      entity.worldY + entity.solidArea.y,
      entity.solidArea.width,
      entity.solidArea.height
    );

    // Área temporária do player
    Rectangle playerArea = new Rectangle(
      gp.player.worldX + gp.player.solidArea.x,
      gp.player.worldY + gp.player.solidArea.y,
      gp.player.solidArea.width,
      gp.player.solidArea.height
    );

    switch (entity.directions) {
      case "up":    entityArea.y -= entity.speed; break;
      case "down":  entityArea.y += entity.speed; break;
      case "left":  entityArea.x -= entity.speed; break;
      case "right": entityArea.x += entity.speed; break;
    }

    if (entityArea.intersects(playerArea)) {
      entity.collisionOn = true;
    }
  }
}
