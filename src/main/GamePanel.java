package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

  // Configurações de tela
  final int originalTileSize = 16; // 16x16 tile padrão
  final int scale = 3;
  public final int tileSize = originalTileSize * scale; // 48x48
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; // 768 px
  public final int screenHeight = tileSize * maxScreenRow; // 576 px

  // Configurações do mundo
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;
  public final int maxMap = 10;
  public int currentMap = 0;

  // FPS
  int FPS = 60;

  // Sistema
  TileManager tileM = new TileManager(this);
  KeyHandler keyH = new KeyHandler();
  public EventHandler eHandler = new EventHandler(this);
  public CollisionChecker cChecker = new CollisionChecker(this, eHandler);
  public AssetSetter aSetter = new AssetSetter(this);
  Thread gameThread;

  // Entidades
  public Player player = new Player(this, keyH);
  public Entity obj[][] = new Entity[maxMap][20];
  public Entity npc[][] = new Entity[maxMap][10];
  public Entity monster[][] = new Entity[maxMap][20];
  ArrayList<Entity> entityList = new ArrayList<>();

  // Estado do jogo
  public int gameState;
  public final int titleState = 0;
  public final int playState = 1;
  public final int pauseState = 2;
  public final int dialogState = 3;
  public final int characterState = 4;

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void setupGame() {
    aSetter.setObjects();
    aSetter.setNPC();
    aSetter.setMonster();

    System.out.println("Monstros no mapa 0:");
    for (int i = 0; i < monster[0].length; i++) {
      if (monster[0][i] != null) {
        System.out.println("Índice " + i + ": " + monster[0][i].getClass().getSimpleName());
      }
    }

  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000.0 / FPS; // 0.01666 segundos
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;

    while (gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
      }
    }
  }

  public void update() {
    player.update();

    for (int i = 0; i < monster[currentMap].length; i++) {
      if (monster[currentMap][i] != null) {
        monster[currentMap][i].update();
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    //debug
    long drawStart = 0;
    if (keyH.showDebugText) drawStart = System.nanoTime();

    tileM.draw(g2);

    // Entidades organizadas
    buildEntityList();
    Collections.sort(entityList, Comparator.comparingInt(e -> e.worldY));

    for (Entity entity : entityList) {
      entity.draw(g2);
    }

    //Debug
    if (keyH.showDebugText) {
      long drawEnd = System.nanoTime();
      long passed = drawEnd - drawStart;

      g2.setColor(Color.white);
      int x = 10;
      //int y = 400;
      int lineHeight = 20;

      g2.drawString("WorldX: " + player.worldX, x, 400);
      g2.drawString("WorldY: " + player.worldY, x, 420);
      g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, 440);
      g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, 460);
      g2.drawString("Draw Time: " + passed, x, 480);
    }

    g2.dispose();
  }

  private void buildEntityList() {
    entityList.clear();
    entityList.add(player);

    for (Entity e : npc[currentMap]) if (e != null) entityList.add(e);
    for (Entity e : obj[currentMap]) if (e != null) entityList.add(e);
    for (Entity e : monster[currentMap]) if (e != null) entityList.add(e);
  }
}
