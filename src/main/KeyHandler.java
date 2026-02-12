package main;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

	private GamePanel gamePanel;
	
	public boolean upPressed = false;
    public boolean downPressed = false;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    public boolean enterPressed = false;
    public boolean shootKeyPressed = false;
    public boolean spacePressed = false;
    
    public boolean showDebugText = false;
    public boolean godModeOn = false;

    public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
    
    public void handleKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        
        // TITLE STATE
        if (gamePanel.gameState == gamePanel.titleState) {
        	titleState(code);
        }
        // PLAY STATE
        else if (gamePanel.gameState == gamePanel.playState) {
        	playState(code);
        }
        // PAUSE STATE
		else if (gamePanel.gameState == gamePanel.pauseState) {
			pauseState(code);
		}
        // DIALOGUE STATE
		else if (gamePanel.gameState == gamePanel.dialogueState || gamePanel.gameState == gamePanel.cutsceneState) {
			dialogueState(code);
		}
        // CHARACTER STATE
 		else if (gamePanel.gameState == gamePanel.characterState) {
 			characterState(code);
 		}
        // OPTIONS STATE
  		else if (gamePanel.gameState == gamePanel.optionsState) {
  			optionsState(code);
  		}
        // GAME OVER STATE
  		else if (gamePanel.gameState == gamePanel.gameOverState) {
  			gameOverState(code);
  		}
        // TRADE STATE
  		else if (gamePanel.gameState == gamePanel.tradeState) {
  			tradeState(code);
  		}
        // MAP SCREEN STATE
  		else if (gamePanel.gameState == gamePanel.mapState) {
  			mapState(code);
  		}
        
        // FULL SCREEN TOGGLE
        if (code == KeyCode.F11) {
			gamePanel.toggleFullscreen();
		}
        
        // DEBUG
        if (code == KeyCode.T) { 
			showDebugText = !showDebugText;
		}
        
        // GOD MODE
        if (code == KeyCode.G) { 
        	godModeOn = !godModeOn;
		}
    }

    private void titleState(KeyCode code) {
    	if (code == KeyCode.W || code == KeyCode.UP) {
    		gamePanel.getUI().commandNum--;
    		if (gamePanel.getUI().commandNum < 0) {
				gamePanel.getUI().commandNum = 2;
			}
    	}
        if (code == KeyCode.S || code == KeyCode.DOWN) {
        	gamePanel.getUI().commandNum++;
        	if (gamePanel.getUI().commandNum > 2) {
				gamePanel.getUI().commandNum = 0;
			}
        }
        if (code == KeyCode.ENTER) {
        	switch (gamePanel.getUI().commandNum) {
        	case 0: // NEW GAME
        		gamePanel.gameState = gamePanel.playState;
        		break;
        	case 1: // LOAD GAME
        		gamePanel.getSaveLoad().load();
        		gamePanel.gameState = gamePanel.playState;
        		break;
        	case 2: // QUIT GAME
        		System.exit(0);
        		break;
        	}
        }
    }
    
	private void playState(KeyCode code) {
		if (code == KeyCode.W || code == KeyCode.UP) {
    		upPressed = true;
    	}
        if (code == KeyCode.S || code == KeyCode.DOWN) {
            downPressed = true;
        }
        if (code == KeyCode.A || code == KeyCode.LEFT) {
            leftPressed = true;
        }
        if (code == KeyCode.D || code == KeyCode.RIGHT) {
            rightPressed = true;
        }
        if (code == KeyCode.P) {
        	gamePanel.gameState = gamePanel.pauseState;
        }
        if (code == KeyCode.C) {
        	gamePanel.gameState = gamePanel.characterState;
        }
        if (code == KeyCode.ENTER) {
        	enterPressed = true;
        }
        if (code == KeyCode.F) {
        	shootKeyPressed = true;
        }
        if (code == KeyCode.ESCAPE) {
        	gamePanel.gameState = gamePanel.optionsState;
        }
        if (code == KeyCode.M) {
        	gamePanel.gameState = gamePanel.mapState;
        }
        if (code == KeyCode.X) {
        	gamePanel.getMap().miniMapOn = !gamePanel.getMap().miniMapOn;
        }
        if (code == KeyCode.SPACE) {
        	spacePressed = true;
        }
	}
	
	private void pauseState(KeyCode code) {
		if (code == KeyCode.P) {
        	gamePanel.gameState = gamePanel.playState;
        }
	}
	
	private void dialogueState(KeyCode code) {
		if (code == KeyCode.ENTER) {
			enterPressed = true;
		}
	}
	
	private void characterState(KeyCode code) {
		if (code == KeyCode.C || code == KeyCode.ESCAPE) {
			gamePanel.gameState = gamePanel.playState;
		}
		if (code == KeyCode.ENTER) {
			gamePanel.getPlayer().selectItem();
		}
		playerInventory(code);
	}
	
	private void optionsState(KeyCode code) {
		if (code == KeyCode.ESCAPE) {
			gamePanel.getConfig().saveConfig();
			gamePanel.gameState = gamePanel.playState;
		}
		if (code == KeyCode.ENTER) {
			enterPressed = true;
		}
		if (code == KeyCode.W || code == KeyCode.UP) {
			if (gamePanel.getUI().commandNum > 0) {
				gamePanel.getUI().commandNum--;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.S || code == KeyCode.DOWN) {
			if (gamePanel.getUI().commandNum < 4) {
				gamePanel.getUI().commandNum++;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.A || code == KeyCode.LEFT) {
			// Music volume
			if (gamePanel.getUI().commandNum == 1 && gamePanel.getMusic().volume > 0.2f) {
				gamePanel.stopMusic(); // Need to stop music before changing volume
				gamePanel.setMusicVolume(gamePanel.getMusic().volume - 0.2f);
				gamePanel.playMusic(0);
				gamePanel.playSoundEffect(9); // cursor sound
			}
			// Sound volume
			if (gamePanel.getUI().commandNum == 2 && gamePanel.getSoundEffect().volume > 0.2f) {
				gamePanel.setSoundVolume(gamePanel.getSoundEffect().volume - 0.2f);
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.D || code == KeyCode.RIGHT) {
			// Music volume
			if (gamePanel.getUI().commandNum == 1 && gamePanel.getMusic().volume < 1.0f) {
				gamePanel.stopMusic(); // Need to stop music before changing volume
				gamePanel.setMusicVolume(gamePanel.getMusic().volume + 0.2f);
				gamePanel.playMusic(0);
				gamePanel.playSoundEffect(9); // cursor sound
			}
			// Sound volume
			if (gamePanel.getUI().commandNum == 2 && gamePanel.getSoundEffect().volume < 1.0f) {
				gamePanel.setSoundVolume(gamePanel.getSoundEffect().volume + 0.2f);
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
	}
	
	private void gameOverState(KeyCode code) {
		if (code == KeyCode.ENTER) {
			if (gamePanel.getUI().commandNum == 0) {
				gamePanel.gameState = gamePanel.playState;
				gamePanel.resetGame(false);
			}
			else if (gamePanel.getUI().commandNum == 1) {
				gamePanel.gameState = gamePanel.titleState;
				gamePanel.resetGame(true);
			}
		}
		if (code == KeyCode.W || code == KeyCode.UP) {
			if (gamePanel.getUI().commandNum > 0) {
				gamePanel.getUI().commandNum--;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.S || code == KeyCode.DOWN) {
			if (gamePanel.getUI().commandNum < 1) {
				gamePanel.getUI().commandNum++;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
	}
	
	private void tradeState(KeyCode code) {
		if (code == KeyCode.ENTER) {
			enterPressed = true;
		}
		
		// Trade option selection
		if (gamePanel.getUI().subState == 0) {
			if (code == KeyCode.W || code == KeyCode.UP) {
				if (gamePanel.getUI().commandNum > 0) {
					gamePanel.getUI().commandNum--;
					gamePanel.playSoundEffect(9); // cursor sound
				}
			}
			if (code == KeyCode.S || code == KeyCode.DOWN) {
				if (gamePanel.getUI().commandNum < 2) {
					gamePanel.getUI().commandNum++;
					gamePanel.playSoundEffect(9); // cursor sound
				}
			}
		}
		
		// Buy selection
		if (gamePanel.getUI().subState == 1) {
			npcInventory(code);
			if (code == KeyCode.ESCAPE) {
				gamePanel.getUI().subState = 0;
				gamePanel.getUI().commandNum = 0;
			}
		}
		
		// Sell selection
		if (gamePanel.getUI().subState == 2) {
			playerInventory(code);
			if (code == KeyCode.ESCAPE) {
				gamePanel.getUI().subState = 0;
				gamePanel.getUI().commandNum = 0;
			}
		}
	}
	
	public void playerInventory(KeyCode code) {
		if (code == KeyCode.W || code == KeyCode.UP) {
			if (gamePanel.getUI().playerSlotRow > 0) {
				gamePanel.getUI().playerSlotRow--;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.S || code == KeyCode.DOWN) {
			if (gamePanel.getUI().playerSlotRow < 3) {
				gamePanel.getUI().playerSlotRow++;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.A || code == KeyCode.LEFT) {
			if (gamePanel.getUI().playerSlotCol > 0) {
				gamePanel.getUI().playerSlotCol--;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.D || code == KeyCode.RIGHT) {
			if (gamePanel.getUI().playerSlotCol < 3) {
				gamePanel.getUI().playerSlotCol++;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
	}
	
	public void npcInventory(KeyCode code) {
		if (code == KeyCode.W || code == KeyCode.UP) {
			if (gamePanel.getUI().npcSlotRow > 0) {
				gamePanel.getUI().npcSlotRow--;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.S || code == KeyCode.DOWN) {
			if (gamePanel.getUI().npcSlotRow < 3) {
				gamePanel.getUI().npcSlotRow++;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.A || code == KeyCode.LEFT) {
			if (gamePanel.getUI().npcSlotCol > 0) {
				gamePanel.getUI().npcSlotCol--;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
		if (code == KeyCode.D || code == KeyCode.RIGHT) {
			if (gamePanel.getUI().npcSlotCol < 3) {
				gamePanel.getUI().npcSlotCol++;
				gamePanel.playSoundEffect(9); // cursor sound
			}
		}
	}
	
	private void mapState(KeyCode code) {
		if (code == KeyCode.M || code == KeyCode.ESCAPE) {
			gamePanel.gameState = gamePanel.playState;
		}
	}
	
    public void handleKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();

        if (code == KeyCode.W || code == KeyCode.UP) {
            upPressed = false;
        }
        if (code == KeyCode.S || code == KeyCode.DOWN) {
            downPressed = false;
        }
        if (code == KeyCode.A || code == KeyCode.LEFT) {
            leftPressed = false;
        }
        if (code == KeyCode.D || code == KeyCode.RIGHT) {
            rightPressed = false;
        }
        if (code == KeyCode.F) {
            shootKeyPressed = false;
        }
        if (code == KeyCode.ENTER) {
           	enterPressed = false;
        }
        if (code == KeyCode.SPACE) {
           	spacePressed = false;
        }
    }
	
	public boolean isKeyPressed() {
		return upPressed || downPressed || leftPressed || rightPressed;
	}
}
