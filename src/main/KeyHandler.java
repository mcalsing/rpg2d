package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

  public boolean upPressed, downPressed, leftPressed, rightPressed;
  public boolean attackPressed = false;
  public boolean defendPressed = false;
  public boolean magicPressed = false;
  public boolean escapePressed = false;

  //Debug
  boolean showDebugText = false;

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_UP) upPressed = true;
    if (code == KeyEvent.VK_DOWN) downPressed = true;
    if (code == KeyEvent.VK_LEFT) leftPressed = true;
    if (code == KeyEvent.VK_RIGHT) rightPressed = true;

    // Teclas de batalha
    if (code == KeyEvent.VK_R) magicPressed = true;
    if (code == KeyEvent.VK_E) attackPressed = true;
    if (code == KeyEvent.VK_W) defendPressed = true;
    if (code == KeyEvent.VK_Q) escapePressed = true;

    //Debug
    if (code == KeyEvent.VK_T) {
      if (!showDebugText) {
        showDebugText = true;
      } else if (showDebugText) {
        showDebugText = false;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_UP) upPressed = false;
    if (code == KeyEvent.VK_DOWN) downPressed = false;
    if (code == KeyEvent.VK_LEFT) leftPressed = false;
    if (code == KeyEvent.VK_RIGHT) rightPressed = false;

    // Teclas de batalha
    if (code == KeyEvent.VK_R) magicPressed = false;
    if (code == KeyEvent.VK_E) attackPressed = false;
    if (code == KeyEvent.VK_W) defendPressed = false;
    if (code == KeyEvent.VK_Q) escapePressed = false;
  }
}
