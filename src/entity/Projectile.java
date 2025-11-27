package entity;

import main.GamePanel;

public class Projectile extends Entity {

	Entity user;
	
	public Projectile(GamePanel gamePanel) {
		super(gamePanel);
	
	}

	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife; // Reset life
	}
	
	public void update() {
		
		// Player shoots - check it hits a monster
		if (user == gamePanel.getPlayer()) {
			int monsterIndex = gamePanel.getCollisionChecker().checkEntity(this, gamePanel.getMonster());
			if (monsterIndex != 999) {
				gamePanel.getPlayer().damageMonster(monsterIndex, attack);
				generateParticle(user.projectile, gamePanel.getMonster()[gamePanel.currentMap][monsterIndex]);
				alive = false;
			}
		}
		// Monster shoots - check it hits the player
		else {
			boolean contactPlayer = gamePanel.getCollisionChecker().checkPlayer(this);
			if (!gamePanel.getPlayer().invincible && contactPlayer) {
				damagePlayer(attack);
				generateParticle(user.projectile, gamePanel.getPlayer());
				alive = false;
			}
		}
		
		switch(direction) {
			case "up": 		worldY -= speed; break;
			case "down": 	worldY += speed; break;
			case "left": 	worldX -= speed; break;
			case "right": 	worldX += speed; break;
		}
		
		// Projectile loses life over time
		life--;
		if (life <= 0) {
			alive = false;
		}
		
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
}
