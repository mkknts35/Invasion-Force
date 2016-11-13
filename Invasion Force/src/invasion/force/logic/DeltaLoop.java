/*
 * Copyright (C) 2016 Michael Koonts, Sukhenko Artur, Laurens van Wingerden, 
 * Vitalii Egorchatov, Mandip Sangha, David Lamothe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package invasion.force.logic;

import java.util.*;

/*
 * AUTHOR: DAVID LAMOTHE, MANDIP SANGHA
 * OVERVIEW: RUNS GAMES LOOP
 */

public class DeltaLoop{
	private static final int FPS = 3;
	private Timer timer;
	private GameEngine game;
	private boolean isRunning;
	private GameLoop gameLoop;
	
	/*
	 * REQUIRES: New Game Object
	 * MODIFIES: isRunning, gameLoop, game, timer
	 * EFFECTS: Initializes game object and set isRunning true and starts 
	 * gameloop 
	 */
	DeltaLoop(GameEngine newGame){
		game = newGame;
		isRunning = true;
		timer = new Timer();
		gameLoop = new GameLoop();
		timer.schedule(gameLoop, 0, 1000 / FPS);
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES: NONE
	 * EFFECTS: calls game objects update and
	 * if running is false then stop timer 
	 */
	private class GameLoop extends TimerTask{
		public void run()
		{
			game.update();
	        if (!isRunning)
	        {
	            timer.cancel();
	        }
		}
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES: isRunning
	 * EFFECTS: set isRunning to false
	 */
	public void stopRunning() {
		isRunning = false;
	}
}

	

