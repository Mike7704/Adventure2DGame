package main;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler {

	private GamePanel gamePanel;
	
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    public boolean enterPressed = false;
    
    public boolean showDebugText = false;

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
		else if (gamePanel.gameState == gamePanel.dialogueState) {
			dialogueState(code);
		}
        // CHARACTER STATE
 		else if (gamePanel.gameState == gamePanel.characterState) {
 			characterState(code);
 		}
        
        // DEBUG
        if (code == KeyCode.T) { 
			showDebugText = !showDebugText;
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
	}
	
	private void pauseState(KeyCode code) {
		if (code == KeyCode.P) {
        	gamePanel.gameState = gamePanel.playState;
        }
	}
	
	private void dialogueState(KeyCode code) {
		if (code == KeyCode.ENTER) {
			gamePanel.gameState = gamePanel.playState;
		}
	}
	
	private void characterState(KeyCode code) {
		if (code == KeyCode.C) {
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
    }

	public boolean isUpPressed() {
		return upPressed;
	}
	
	public boolean isDownPressed() {
		return downPressed;
	}
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public boolean isKeyPressed() {
		return upPressed || downPressed || leftPressed || rightPressed;
	}
}
