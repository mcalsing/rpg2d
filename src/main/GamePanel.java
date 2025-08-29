package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

  // Screen Settings
  final int originalTilesSize = 16; // 16x16 tile
  final int scale = 3;
  
  public final int tileSize = originalTilesSize * scale; //48x48
  public final int maxScreenCol = 16;
  public final int maxScreenRow = 12;
  public final int screenWidth = tileSize * maxScreenCol; // 768px
  public final int screenHeight = tileSize * maxScreenRow; // 576px

  // World Settings
  public final int maxWorldCol = 50;
  public final int maxWorldRow = 50;
  public final int maxMap = 10;
  public int currentMap = 0;

  //FPS
  int FPS = 60;

  // System
  TileManager tileM = new TileManager(this);
  KeyHandler keyH = new KeyHandler();

  Thread gameThread;
  public AssetSetter aSetter = new AssetSetter(this);
  public EventHandler eHandler = new EventHandler(this);
  public CollisionChecker cChecker = new CollisionChecker(this, eHandler); //AQUIIII

  // Entity and objects
  public Player player = new Player(this,keyH);
  public Entity obj[][] = new Entity[maxMap][10];
  public Entity npc[][] = new Entity[maxMap][10];
  public Entity monster[][] = new Entity[maxMap][20];
  ArrayList<Entity> entityList = new ArrayList<>();

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.BLACK);
    // Game Rendering performance
    this.setDoubleBuffered(true);

    //Movimentação do personagem
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void setupGame() {
    aSetter.setObjects();
    aSetter.setNPC();
    aSetter.setMonster();
  }

  //
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
    //Player
    player.update();

    //NPC
    for (int i = 0; i < npc[1].length; i++) {
      if (npc[currentMap][i] != null) {
        npc[currentMap][i].update();
      }
    }

    //Monster
    for (int i = 0; i < monster[1].length; i++) {
      if (monster[currentMap][i] != null) {
        monster[currentMap][i].update();
      }
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    //Debug
    long drawStart = 0;
    if (keyH.showDebugText) drawStart = System.nanoTime();

    //Tile
    tileM.draw(g2);

    //Add entities to the list
    entityList.add(player);

    //NPC
    for (int i = 0; i < npc[1].length; i++) {
      if (npc[currentMap][i] != null) {
        entityList.add(npc[currentMap][i]);
      }
    }

    //Object
    for (int i = 0; i < obj[1].length; i++) {
      if (obj[currentMap][i] != null) {
        entityList.add(obj[currentMap][i]);
      }
    }

    //Monster
    for (int i = 0; i < monster[1].length; i++) {
      if (monster[currentMap][i] != null) {
        entityList.add(monster[currentMap][i]);
      }
    }

    // Sort
    Collections.sort(entityList, new Comparator<Entity>() {
      @Override
      public int compare(Entity e1, Entity e2) {

        int result = Integer.compare(e1.worldY, e2.worldX);
        return result;
      }
    });

    // Draw Entities
    for(int i = 0; i < entityList.size(); i++) {
      entityList.get(i).draw(g2);
    }

    // Empty entity list
    for(int i = 0; i < entityList.size(); i++) {
      entityList.remove(i);
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
}











