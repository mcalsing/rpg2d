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
  public final int worldWidth = tileSize * maxWorldCol;
  public final int worldHeight = tileSize * maxWorldRow;

  //FPS
  int FPS = 60;

  TileManager tileM = new TileManager(this);
  KeyHandler keyH = new KeyHandler();
  Thread gameThread;

  public CollisionChecker cChecker = new CollisionChecker(this);
  public AssetSetter aSetter = new AssetSetter(this);

  // Entity and objects
  public Player player = new Player(this,keyH);
  public Entity obj[] = new Entity[10];
  public Entity npc[] = new Entity[10];
  public Entity monster[] = new Entity[20];
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
    for (int i = 0; i < npc.length; i++) {
      if (npc[i] != null) {
        npc[i].update();
      }
    }

    //Monster
    for (int i = 0; i < monster.length; i++) {
      if (monster[i] != null) {
        monster[i].update();
      }
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;

    //Debug
    long drawStart = 0;
    if (keyH.checkDrawTime) drawStart = System.nanoTime();

    //Tile
    tileM.draw(g2);

    //Add entities to the list
    entityList.add(player);

    //NPC
    for (int i = 0; i < npc.length; i++) {
      if (npc[i] != null) {
        entityList.add(npc[i]);
      }
    }

    //Object
    for (int i = 0; i < obj.length; i++) {
      if (obj[i] != null) {
        entityList.add(obj[i]);
      }
    }

    //Monster
    for (int i = 0; i < monster.length; i++) {
      if (monster[i] != null) {
        entityList.add(monster[i]);
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
    if (keyH.checkDrawTime) {
      long drawEnd = System.nanoTime();
      long passed = drawEnd - drawStart;
      g2.setColor(Color.white);
      g2.drawString("Draw Time: " + passed, 10, 400);
      System.out.println("Draw Time: " +passed);
    }

    g2.dispose();
  }
}











