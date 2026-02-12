package main;

import entity.Entity;
import entity.FakePlayer;
import javafx.scene.canvas.GraphicsContext;
import monster.MON_SkeletonLord;
import object.OBJ_Door_Iron;

public class CutsceneManager {

	private GamePanel gamePanel;
	public int sceneNum;
	public int scenePhase;
	
	// Scene number
	public final int NA = 0;
	public final int skeletonLord = 1;
	
	public CutsceneManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void draw(GraphicsContext gc) {		
		switch(sceneNum) {
			case skeletonLord: skeletonLordScene(); break;
		}
	}
	
	public void skeletonLordScene() {
		if (scenePhase == 0) {
			gamePanel.bossBattleOn = true;
			
			// Shut the iron door
			for (int i = 0; i < gamePanel.getObject()[1].length; i++) {
				if (gamePanel.getObject()[gamePanel.currentMap][i] == null) {
					gamePanel.getObject()[gamePanel.currentMap][i] = new OBJ_Door_Iron(gamePanel);
					gamePanel.getObject()[gamePanel.currentMap][i].worldX = gamePanel.tileSize * 25;
					gamePanel.getObject()[gamePanel.currentMap][i].worldY = gamePanel.tileSize * 28;
					gamePanel.getObject()[gamePanel.currentMap][i].temp = true;
					gamePanel.playSoundEffect(21);
					break;
				}
			}
			
			// Add a fake player for the cutscene
			gamePanel.getPlayer().visible = false;
			for (int i = 0; i < gamePanel.getNPC()[1].length; i++) {
				if (gamePanel.getNPC()[gamePanel.currentMap][i] == null) {
					gamePanel.getNPC()[gamePanel.currentMap][i] = new FakePlayer(gamePanel);
					gamePanel.getNPC()[gamePanel.currentMap][i].worldX = gamePanel.getPlayer().worldX;
					gamePanel.getNPC()[gamePanel.currentMap][i].worldY = gamePanel.getPlayer().worldY;
					gamePanel.getNPC()[gamePanel.currentMap][i].direction = gamePanel.getPlayer().direction;
					break;
				}
			}
			
			scenePhase++;
		}
		
		if (scenePhase == 1) {
			gamePanel.getPlayer().worldY -= 2;
			
			if (gamePanel.getPlayer().worldY < gamePanel.tileSize * 16) {
				scenePhase++;
			}
		}
		
		if (scenePhase == 2) {
			// Get the boss to wake up
			for (int i = 0; i < gamePanel.getMonster()[1].length; i++) {
				if (gamePanel.getMonster()[gamePanel.currentMap][i] != null && gamePanel.getMonster()[gamePanel.currentMap][i].name.equals(MON_SkeletonLord.monName)) {
					gamePanel.getMonster()[gamePanel.currentMap][i].sleep = false;
					gamePanel.getUI().npc = gamePanel.getMonster()[gamePanel.currentMap][i];
					scenePhase++;
					break;
				}
			}
		}
		
		if (scenePhase == 3) {
			// Boss dialogue
			gamePanel.getUI().drawDialogueScreen();
		}
		
		if (scenePhase == 4) {						
			// Remove the fake player and return to the player
			for (int i = 0; i < gamePanel.getNPC()[1].length; i++) {
				if (gamePanel.getNPC()[gamePanel.currentMap][i] != null && gamePanel.getNPC()[gamePanel.currentMap][i].name.equals(FakePlayer.npcName)) {
					gamePanel.getPlayer().worldX = gamePanel.getNPC()[gamePanel.currentMap][i].worldX;
					gamePanel.getPlayer().worldY = gamePanel.getNPC()[gamePanel.currentMap][i].worldY;
					gamePanel.getPlayer().direction = gamePanel.getNPC()[gamePanel.currentMap][i].direction;
					
					gamePanel.getNPC()[gamePanel.currentMap][i] = null;
					break;
				}
			}
			
			gamePanel.getPlayer().visible = true;
			
			// Reset scene
			sceneNum = NA;
			scenePhase = 0;
			gamePanel.gameState = gamePanel.playState;
			gamePanel.stopMusic();
			gamePanel.playMusic(22);
		}
	}
}
