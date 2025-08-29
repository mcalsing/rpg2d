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
  public Entity[][] obj = new Entity[maxMap][20];
  public Entity[][] npc = new Entity[maxMap][10];
  public Entity[][] monster = new Entity[maxMap][20];
  ArrayList<Entity> entityList = new ArrayList<>();

  // Estado do jogo
  public int gameState;
  public final int titleState = 0;
  public final int playState = 1;
  public final int pauseState = 2;
  public final int dialogState = 3;
  public final int characterState = 4;
  public final int battleState = 5;
  public final int battleTransitionState = 6;

  // Variáveis para batalha
  public Entity battleMonster = null;
  public int previousMap = 0;
  public int previousPlayerX = 0;
  public int previousPlayerY = 0;

  // Sistema de batalha
  public Battle battle;

  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);

    this.battle = new Battle(this);
    gameState = playState;
  }

  public void setupGame() {
    aSetter.setObjects();
    aSetter.setNPC();
    aSetter.setMonster();

    // Garantir que começa no estado de jogo
    gameState = playState;

//    System.out.println("Monstros no mapa 0:");
//    for (int i = 0; i < monster[0].length; i++) {
//      if (monster[0][i] != null) {
//        System.out.println("Índice " + i + ": " + monster[0][i].getClass().getSimpleName());
//      }
//    }
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
    // Metodo de jogo
    if (gameState == playState) {
      player.update();

      for (int i = 0; i < monster[currentMap].length; i++) {
        if (monster[currentMap][i] != null) {
          monster[currentMap][i].update();
        }
      }
    }

    if (gameState == battleState) {
      // Lógica de batalha
      updateBattle();
    }
  }

  public void startBattle(Entity monster) {
    // Salvar estado atual
    previousMap = currentMap;
    previousPlayerX = player.worldX;
    previousPlayerY = player.worldY;
    battleMonster = monster;

    // Iniciar batalha
    battle.startBattle(player, monster);

    // Mudar para estado de batalha
    gameState = battleState;

    // Teleportar para mapa de batalha
    //eHandler.teleport(3, 8, 11);
  }

  public void endBattle(boolean playerWon) {
    if (playerWon) {
      // Remover monstro derrotado do mapa original
      removeMonsterFromMap(battleMonster);
      System.out.println("Você venceu a batalha!");
    }

    // Usar o eventHandler para voltar ao mapa anterior
    eHandler.returnFromBattle(previousMap, previousPlayerX, previousPlayerY);


    // Voltar ao jogo normal
    gameState = playState;
    battleMonster = null;
  }

  private void removeMonsterFromMap(Entity monster) {
    for (int i = 0; i < this.monster[previousMap].length; i++) {
      if (this.monster[previousMap][i] == monster) {
        this.monster[previousMap][i] = null;
        break;
      }
    }
  }

  private void updateBattle() {
    // Implementar lógica de batalha por turnos aqui
    // Por enquanto uma batalha simples
//    if (keyH.enterPressed) {
//      // Simular vitória do jogador
//      endBattle(true);
//      keyH.enterPressed = false;
//    }

    // Lógica de entrada para batalha por turnos
    if (keyH.enterPressed) {
      battle.processTurn("ATTACK");
      keyH.enterPressed = false;
    }

    if (keyH.defendPressed) {
      battle.processTurn("DEFEND");
      keyH.defendPressed = false;
    }

    if (keyH.escapePressed) {
      battle.processTurn("FLEE");
      keyH.escapePressed = false;
    }

    if (keyH.magicPressed) {
      battle.processTurn("MAGIC");
      keyH.magicPressed = false;
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    if (gameState == playState) {

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

        g2.drawString("WorldX: " + player.worldX, x, 400);
        g2.drawString("WorldY: " + player.worldY, x, 420);
        g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, 440);
        g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, 460);
        g2.drawString("Draw Time: " + passed, x, 480);
      }
    }
    if (gameState == battleState) {
      // Desenhar tela de batalha
      drawBattleScreen(g2);
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

  private void drawBattleScreen(Graphics2D g2) {
    // Fundo de batalha
    g2.setColor(new Color(0, 0, 100)); // Azul escuro
    g2.fillRect(0, 0, screenWidth, screenHeight);

    // Desenhar o monstro
    if (battleMonster != null) {
      int monsterX = screenWidth/2 + 2 * tileSize;
      int monsterY = screenHeight/6;

      g2.drawImage(battleMonster.down1, monsterX, monsterY, tileSize*2, tileSize*2, null);
    }

    // Desenhar o jogador
    int playerX = screenWidth/5;
    int playerY = screenHeight/2;
    g2.drawImage(player.up1, playerX, playerY, tileSize*2, tileSize*2, null);

    // Interface de batalha
    g2.setColor(new Color(255, 255, 255, 200));
    g2.fillRect(0, screenHeight - 100, screenWidth, 100);

    g2.setColor(Color.BLACK);
    g2.setFont(new Font("Arial", Font.BOLD, 16));

    if (battleMonster != null) {
      g2.drawString("Você encontrou: " + battleMonster.name, 20, screenHeight - 80);
      g2.drawString("HP: " + battleMonster.hp + "/" + battleMonster.maxHp, 20, screenHeight - 60);
    }

    g2.drawString("Pressione ENTER para atacar", 20, screenHeight - 40);
    g2.drawString("ESC para fugir", 20, screenHeight - 20);

    // Barra de HP do jogador
    g2.setColor(Color.RED);
    g2.fillRect(screenWidth - 150, screenHeight - 80, 100, 15);
    g2.setColor(Color.GREEN);
    g2.fillRect(screenWidth - 150, screenHeight - 80,
            (int)(100 * ((double)player.hp / player.maxHp)), 15);

    g2.setColor(Color.BLACK);
    g2.drawString("HP: " + player.hp + "/" + player.maxHp, screenWidth - 150, screenHeight - 60);
  }
}
