package main;

import entity.Entity;
import entity.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Battle {
  private final GamePanel gp;
  private final ArrayList<Entity> turnOrder;
  private int currentTurn;
  private boolean inBattle;
  private Entity player;
  private Entity monster;

  public Battle(GamePanel gp) {
    this.gp = gp;
    this.turnOrder = new ArrayList<>();
    this.inBattle = false;
  }

  public void startBattle(Entity player, Entity monster) {
    this.player = player;
    this.monster = monster;
    this.inBattle = true;
    this.currentTurn = 0;

    // Determine turn order
    determineTurnOrder();

    System.out.println("Battle started: " + player.name + " vs " + monster.name);
    System.out.println("Turn order: ");
    for (Entity e : turnOrder) {
      System.out.println("- " + e.name);
    }
  }

  private void determineTurnOrder() {
    turnOrder.clear();
    turnOrder.add(player);
    turnOrder.add(monster);

    // Order by agility (if you have this attribute)
    Collections.sort(turnOrder, Comparator.comparingInt(e -> e.agility));
    Collections.reverse(turnOrder); // Higher agility first
  }

  public void processTurn(String action) {
    if (!inBattle) return;

    Entity attacker = turnOrder.get(currentTurn);

    if (attacker == player) {
      switch (action) {
        case "ATTACK": attack(player, monster); break;
        case "DEFEND": defend(attacker); break;
        case "FLEE":
          if (flee(attacker)) {
            gp.endBattle(false);
            return;
          }
          break;
        case "MAGIC": useMagic(player, monster); break;
      }
    } else {

      // É sorteado um numero de 1 a 3 para ser a escolha do ataque do monstro
      Random random = new Random();
      int i = random.nextInt(3)+1;

      if (i == 1) attack(monster, player);
      if (i == 2) attack(monster, player);
      if (i == 3) defend(attacker);
      //if (i == 4) useMagic(monster, player);
    }

    // Check if battle ended
    if (player.hp <= 0) {
      //System.out.println("You were defeated!");
      gp.endBattle(false);
      return;
    }

    if (monster.hp <= 0) {
      //System.out.println("You won the battle!");
      gp.endBattle(true);
      return;
    }

    // Move to next turn
    currentTurn = (currentTurn + 1) % turnOrder.size();
  }

  private void attack(Entity attacker, Entity target) {
    int damage = calculateDamage(attacker, target);
    target.hp -= damage;

    System.out.println(attacker.name + " attacks " + target.name + " causing " + damage + " damage!");
    System.out.println(target.name + " HP: " + target.hp + "/" + target.maxHp);
  }

  private int calculateDamage(Entity attacker, Entity target) {
    int baseDamage = attacker.strength - (target.defense / 2);
    int variation = (int)(baseDamage * 0.2); // 20% variation
    int finalDamage = baseDamage + (int)(Math.random() * variation * 2) - variation;

    return Math.max(1, finalDamage); // Minimum 1 damage
  }

  private void defend(Entity entity) {
    entity.defense += 5; // Temporarily increase defense
    System.out.println(entity.name + " defended! Defense increased.");
  }

  private boolean flee(Entity entity) {
    int fleeChance = 50; // 50% base chance to flee

    if (entity == player) {
      if (Math.random() * 100 < fleeChance) {
        System.out.println("You fled from battle!");
        return true;
      } else {
        System.out.println("Failed to flee!");
        return false;
      }
    }
    return false;
  }

  private void useMagic(Entity attacker, Entity target) {
    // Implement magic system
    int manaCost = 10;

    if (attacker.mana >= manaCost) {
      attacker.mana -= manaCost;
      int damage = calculateDamage(attacker, target) * 2; // Double damage
      target.hp -= damage;

      System.out.println(attacker.name + " uses magic on " + target.name + " causing " + damage + " damage!");
    }
    // Se usar ataque magico sem mana, perde o turmo
//    else {
//      System.out.println("Not enough mana!");
//      attack(attacker, target); // Attack normally
//    }
  }

  public Entity getPlayer() { return player; }
  public Entity getMonster() { return monster; }
  public Entity getCurrentTurn() { return turnOrder.get(currentTurn); }
  public boolean isInBattle() { return inBattle; }
}