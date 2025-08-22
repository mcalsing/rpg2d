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
    mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

    getTileImage();
    loadMap("res/maps/map01.txt");
  }

  public void getTileImage() {
    try {
      tile[0] = new Tile();
      tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

      tile[1] = new Tile();
      tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));

      tile[2] = new Tile();
      tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));

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

      while ((line = br.readLine())!=null && row<gp.maxScreenRow) {
        //System.out.println("linea :"+row+"-"+line);

        String[] numbers = line.split(" ");

        for (col=0;col<gp.maxScreenCol;col++) {
          mapTileNum[col][row]=Integer.parseInt(numbers[col]);
        }
        row++;
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void draw(Graphics g2) {

    int col = 0;
    int row = 0;
    int x = 0;
    int y = 0;

    while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

      int tileNum = mapTileNum[col][row];

      g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
      col++;
      x += gp.tileSize;

      if (col == gp.maxScreenCol) {
        col = 0;
        x = 0;
        row++;
        y += gp.tileSize;
      }
    }


  }
}
