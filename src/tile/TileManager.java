package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class TileManager {

  GamePanel gp;
  public Tile[] tile;
  public int[][] mapTileNum;

  public TileManager(GamePanel gp) {
    this.gp = gp;
    tile = new Tile[50];
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

    getTileImage();
    loadMap("res/maps/worldV4.txt");
  }

  public void getTileImage() {

    setup(0, "000", false);
    setup(1, "001", false);
    setup(2, "002", false);
    setup(3, "003", false);
    setup(4, "004", false);
    setup(5, "005", false);
    setup(6, "006", false);
    setup(7, "007", false);
    setup(8, "008", false);
    setup(9, "009", false);
    setup(10, "010", false);
    setup(11, "011", false);
    setup(12, "012", false);
    setup(13, "013", false);
    setup(14, "014", false);
    setup(15, "015", false);
    setup(16, "016", true);
    setup(17, "017", false);
    setup(18, "018", true);
    setup(19, "019", true);
    setup(20, "020", true);
    setup(21, "021", true);
    setup(22, "022", true);
    setup(23, "023", true);
    setup(24, "024", true);
    setup(25, "025", true);
    setup(26, "026", true);
    setup(27, "027", true);
    setup(28, "028", true);
    setup(29, "029", true);
    setup(30, "030", true);
    setup(31, "031", true);
    setup(32, "032", true);
    setup(33, "033", false);
    setup(34, "034", false);
    setup(35, "035", true);
    setup(36, "036", false);
    setup(37, "037", false);


  }
  public void setup(int index, String imageName, boolean collision) {
    UtilityTool uTool = new UtilityTool();

    try {
      tile[index] = new Tile();
      tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
      tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
      tile[index].collision = collision;

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

        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
      }

      worldCol++;

      if (worldCol == gp.maxWorldCol) {
        worldCol = 0;
        worldRow++;
      }
    }


  }
}
