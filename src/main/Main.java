package main;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {

    // Configurações de exibição da tela
    JFrame window = new JFrame();
    window.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
    window.setResizable(false);
    window.setTitle("RPG 2D");

    GamePanel gamePanel = new GamePanel();
    window.add(gamePanel);

    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);

    gamePanel.startGameThread();
  }
}