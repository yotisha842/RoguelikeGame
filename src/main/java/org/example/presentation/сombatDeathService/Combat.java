package org.example.presentation.сombatDeathService;

import org.example.domain.entity.character.Enemy;
import org.example.domain.entity.character.Player;

import java.util.Random;

public class Combat {

    public void enemyAttack(Player player, Enemy enemy, String[] logs) {
        if (enemy.GetSymbol() == 'O') {
            if (enemy.ogrStrike) {
                enemy.ogrStrike = !enemy.ogrStrike;
                logs[1] = "Ogr missed";
                return;
            } else {
                enemy.ogrStrike = !enemy.ogrStrike;
            }
        }

        if (!enemy.isHit(player) && enemy.GetSymbol() != 'O') {
            logs[1] = "Enemy miss!";
            return;
        }

        if (enemy.GetSymbol() == 's') {
            Random random = new Random();
            if (random.nextInt(100) < 30) {
                player.setSleeping(true);
                logs[1] = "Snake puts player to sleep!";
            }
        }

        int damage;
        if (enemy.GetSymbol() == 'v') {
            double coefficient = 1.0 + (enemy.getLevelIndex() / 10.0);
            player.setMaxHp(player.getMaxHp() - (int)(2.0 * coefficient));
            if (player.getHp() > player.getMaxHp()) player.setHp(player.getMaxHp());
        } else {
            damage = enemy.calculateDamage();
            logs[1] = "Enemy gives damage " + damage;
            player.setHp(player.getHp() - damage);
        }
    }

    public void playerAttack(Player player, Enemy enemy, String[] logs) {
        if (!enemy.firstVampireStrike && enemy.getSymbol() == 'v') {
            logs[0] = "First miss to vampire";
            enemy.firstVampireStrike = true;
            return;
        }

        if (player.isHit()) {
            int damage = player.calculateDamage();
            logs[0] = "Player give damage " + damage;
            enemy.hp -= damage;
        } else {
            logs[0] = "Player missed!";
        }
    }
}
