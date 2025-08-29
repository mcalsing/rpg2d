package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

  public boolean upPressed, downPressed, leftPressed, rightPressed;
  public boolean enterPressed = false;
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

    if (code == KeyEvent.VK_W) upPressed = true;
    if (code == KeyEvent.VK_S) downPressed = true;
    if (code == KeyEvent.VK_A) leftPressed = true;
    if (code == KeyEvent.VK_D) rightPressed = true;

    // Teclas de batalha
    if (code == KeyEvent.VK_ENTER) enterPressed = true;
    if (code == KeyEvent.VK_D) defendPressed = true;
    if (code == KeyEvent.VK_M) magicPressed = true;
    if (code == KeyEvent.VK_ESCAPE) escapePressed = true;

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

    if (code == KeyEvent.VK_W) upPressed = false;
    if (code == KeyEvent.VK_S) downPressed = false;
    if (code == KeyEvent.VK_A) leftPressed = false;
    if (code == KeyEvent.VK_D) rightPressed = false;

    // Teclas de batalha
    if (code == KeyEvent.VK_ENTER) enterPressed = false;
    if (code == KeyEvent.VK_D) defendPressed = false;
    if (code == KeyEvent.VK_M) magicPressed = false;
    if (code == KeyEvent.VK_ESCAPE) escapePressed = false;
  }
}
