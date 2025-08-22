package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class TileManager {

  GamePanel gp;
  Tile[] tile;
  int[][] mapTileNum;

  public TileManager(GamePanel gp) {
    this.gp = gp;
    tile = new Tile[10];
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

    getTileImage();
    loadMap("res/maps/world01.txt");
  }

  public void getTileImage() {
    try {
      tile[0] = new Tile();
      tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

      tile[1] = new Tile();
      tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));

      tile[2] = new Tile();
      tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));

      tile[3] = new Tile();
      tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

      tile[4] = new Tile();
      tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));

      tile[5] = new Tile();
      tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void loadMap(String filePath) {
    try {
      //String map01 = "res/maps/map01.txt";
      FileReader is = new FileReader(filePath);
      BufferedReader br = new BufferedReader(is);

      int col = 0;
      int row = 0;
      String line;

      while ((line = br.readLine())!=null && row<gp.maxWorldRow) {
        //System.out.println("linea :"+row+"-"+line);

        String[] numbers = line.split(" ");

        for (col=0; col<gp.maxWorldCol; col++) {
          mapTileNum[col][row]=Integer.parseInt(numbers[col]);
        }
        row++;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void draw(Graphics g2) {

    int worldCol = 0;
    int worldRow = 0;

    while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

      int tileNum = mapTileNum[worldCol][worldRow];

      int worldX = worldCol * gp.tileSize;
      int worldY = worldRow * gp.tileSize;
      int screenX = worldX - gp.player.worldX + gp.player.screenX;
      int screenY = worldY - gp.player.worldY + gp.player.screenY;

      if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
         worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)  {

        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
      }

      worldCol++;

      if (worldCol == gp.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }


  }
}
