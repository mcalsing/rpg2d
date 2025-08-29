package main;

import java.awt.*;

public class EventHandler {

  GamePanel gp;

  EventReact[][][] eventReact;

  boolean canTouchEvent = true;
  int previousEventX, previousEventY;

  public EventHandler(GamePanel gp) {
    this.gp = gp;

    eventReact = new EventReact[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

    int map = 0;
    int col = 0;
    int row = 0;

    while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {

      eventReact[map][col][row] = new EventReact();
      eventReact[map][col][row].x = 0; // 23
      eventReact[map][col][row].y = 0; // 23
      eventReact[map][col][row].width = 48; // 2
      eventReact[map][col][row].height = 48; // 2
      eventReact[map][col][row].eventReactDefaultX = eventReact[map][col][row].x;
      eventReact[map][col][row].eventReactDefaultY = eventReact[map][col][row].y;

      col++;
      if (col == gp.maxWorldCol) {
        col = 0;
        row++;
        if (row == gp.maxWorldRow) {
          row = 0;
          map++;
        }
      }
    }
  }

  public void checkEvent() {

    // Check if the player is more than 1 tile away from the last event
    int xDistante = Math.abs(gp.player.worldX - previousEventX);
    int yDistante = Math.abs(gp.player.worldY - previousEventY);
    int distance = Math.max(xDistante, yDistante);

    if (distance > gp.tileSize) {
      canTouchEvent = true;
    }

    if (canTouchEvent) {
      if (hit (0,10,39, "any")) {
        teleport(1, 9, 41);
      } else if (hit(1, 9, 41, "any")) teleport(0, 10, 39);
    }
  }

  public boolean hit(int map, int col, int row, String reqDirection) {

    boolean hit = false;

    if(map == gp.currentMap) {

      gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
      gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
      eventReact[map][col][row].x = col * gp.tileSize + eventReact[map][col][row].x;
      eventReact[map][col][row].y = row * gp.tileSize + eventReact[map][col][row].y;

      if(gp.player.solidArea.intersects(eventReact[map][col][row]) && eventReact[map][col][row].eventDone == false) {
        if(gp.player.directions.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
          hit = true;

          previousEventX = gp.player.worldX;
          previousEventY = gp.player.worldY;
        }
      }

      gp.player.solidArea.x = gp.player.solidAreaDefaultX;
      gp.player.solidArea.y = gp.player.solidAreaDefaultY;
      eventReact[map][col][row].x = eventReact[map][col][row].eventReactDefaultX;
      eventReact[map][col][row].y = eventReact[map][col][row].eventReactDefaultY;
    }

    return hit;
  }

  public void teleport(int map, int col, int row) {
    gp.currentMap = map;
    gp.player.worldX = gp.tileSize * col;
    gp.player.worldY = gp.tileSize * row;
    previousEventX = gp.player.worldX;
    previousEventY = gp.player.worldY;
    canTouchEvent = false;
  }
}
