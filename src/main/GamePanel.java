package main;

import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

  // Screen Settings
  final int originalTilesSize = 16; // 16x16 tile
  final int scale = 3;
  
  public final int tileSize = originalTilesSize * scale; //48x48
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; // 768px
  public final int screenHeight = tileSize * maxScreenRow; // 576px

  int FPS = 60;

  TileManager tileM = new TileManager(this);
  KeyHandler keyH = new KeyHandler();
  Thread gameThread;
  Player player = new Player(this,keyH);

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    // Game Rendering performance
    this.setDoubleBuffered(true);

    //movimentação do personagem
    this.addKeyListener(keyH);
    this.setFocusable(true);

  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  //Função Loop
  @Override
  public void run() {
    double drawInterval = (double) 1000000000/FPS; // 0.01666 seconds
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    while (gameThread != null) {

      currentTime = System.nanoTime();
      timer += (currentTime - lastTime);
      delta += (currentTime - lastTime) / drawInterval;
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
        drawCount++;

      }
      if (timer >= 1000000000) {
        System.out.println("FPS: " +drawCount);
        drawCount = 0;
        timer = 0;
      }
    }
  }

  public void update() {
    player.update();

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    tileM.draw(g2);

    player.draw(g2);

    g2.dispose();
  }
}











