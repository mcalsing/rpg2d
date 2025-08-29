package main;

import entity.Entity;

import java.awt.*;
import java.util.Arrays;

public class CollisionChecker {

  GamePanel gp;
  EventHandler eventHandler;

  public CollisionChecker(GamePanel gp, EventHandler eventHandler) {
    this.gp = gp;
    this.eventHandler = eventHandler;
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

        tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;

      case "down":
        entityArea.y   += entity.speed;
        entityLeftCol   = entityArea.x / gp.tileSize;
        entityRightCol  = (entityArea.x + entityArea.width) / gp.tileSize;
        entityBottomRow = (entityArea.y + entityArea.height) / gp.tileSize;

        tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
        tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;

      case "left":
        entityArea.x   -= entity.speed;
        entityLeftCol   = entityArea.x / gp.tileSize;
        entityTopRow    = entityArea.y / gp.tileSize;
        entityBottomRow = (entityArea.y + entityArea.height) / gp.tileSize;

        tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;

      case "right":
        entityArea.x   += entity.speed;
        entityRightCol  = (entityArea.x + entityArea.width) / gp.tileSize;
        entityTopRow    = entityArea.y / gp.tileSize;
        entityBottomRow = (entityArea.y + entityArea.height) / gp.tileSize;

        tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
        tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
          entity.collisionOn = true;
        }
        break;
    }
  }
  public int checkObject(Entity entity, boolean player) {
    int index = 999;

    for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
      if (gp.obj[gp.currentMap][i] != null) {

        // Criar cópia temporária da área sólida da entidade
        Rectangle entityArea = new Rectangle(
          entity.worldX + entity.solidArea.x,
          entity.worldY + entity.solidArea.y,
          entity.solidArea.width,
          entity.solidArea.height
        );

        // Criar cópia temporária da área sólida do objeto
        Rectangle objectArea = new Rectangle(
          gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].solidArea.x,
          gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].solidArea.y,
          gp.obj[gp.currentMap][i].solidArea.width,
          gp.obj[gp.currentMap][i].solidArea.height
        );

        // Checa colisão em cada direção usando apenas a cópia
        switch (entity.directions) {
          case "up":    entityArea.y -= entity.speed; break;
          case "down":  entityArea.y += entity.speed; break;
          case "left":  entityArea.x -= entity.speed; break;
          case "right": entityArea.x += entity.speed; break;
        }

        if (entityArea.intersects(objectArea)) {
          if (gp.obj[gp.currentMap][i].collision) {
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
  public int checkEntity(Entity entity, Entity[][] target) {
    int index = 999;

    for (int i = 0; i < target[gp.currentMap].length; i++) {
      if (target[gp.currentMap][i] != null) {

        // Área temporária da entidade
        Rectangle entityArea = new Rectangle(
          entity.worldX + entity.solidArea.x,
          entity.worldY + entity.solidArea.y,
          entity.solidArea.width,
          entity.solidArea.height
        );

        // Área temporária do target
        Rectangle targetArea = new Rectangle(
          target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x,
          target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y,
          target[gp.currentMap][i].solidArea.width,
          target[gp.currentMap][i].solidArea.height
        );

        switch (entity.directions) {
          case "up":    entityArea.y -= entity.speed; break;
          case "down":  entityArea.y += entity.speed; break;
          case "left":  entityArea.x -= entity.speed; break;
          case "right": entityArea.x += entity.speed; break;
        }

        if (entityArea.intersects(targetArea)) {
          //eventHandler.teleport(3, 8, 11);
          entity.collisionOn = true;
          index = i;
          break;
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
