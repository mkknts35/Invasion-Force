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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import invasion.force.board.*;
import invasion.force.gui.GamePanels;
import invasion.force.gameObjects.*;
import java.awt.Color;
import static invasion.force.settings.Configs.*;

/*
 * NOTE: Round() Method taken from http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
 */

/*
 * AUTHOR: DAVID LAMOTHE, MANDIP SANGHA
 * OVERVIEW: RUNS THE GAMES
 */

public class GameEngine {
	
	public GamePanels panels;
	public Ship ship;
	public Quadrant quad;
	public DeltaLoop deltaLoop;
	private int starTime;
	private int criticalCounter;
	private int destructCounter;
	private boolean selfDestructActive;
	
	private ArrayList<SpaceObject> quadObjects;
	private ArrayList<Weapon> weaponObjects;
	
	/*
	 * REQUIRES: new GamePanels
	 * MODIFIES: criticalCounter,destructCounter,selfDestructActive,starTime,
	 * panels,quad,ship,quadObjects,weaponObjects,deltaLoop
	 * EFFECTS: Initializes game engine variables
	 */
	public GameEngine(GamePanels newPanel) {
            criticalCounter = INITCRITICALTIMER;
            destructCounter = INITDESTRUCTTIMER;
            selfDestructActive = false;
            starTime = INITTIME;
            panels = newPanel;
            quad = Space.getInstance().getQuadrant(1, 1);
            ship = new Ship(quad.getSector(new Position(0,0)));
            Space.getInstance().initPopulation(ship);
            quad.populate();
            quadObjects = ship.getQuadrant().getGeneratedObjects();
            weaponObjects = ship.getQuadrant().getWeaponList();
            deltaLoop = new DeltaLoop(this);
            populateSidePanel();
            update();
	}
	
	/*
	 * REQUIRES: speed, direction
	 * MODIFIES: NONE
	 * EFFECTS: pass speed and direction to ship object
	 */
	public void setVelocity(int speed, int direction) {
            int[] velocity;
            velocity = new int[2];
            velocity[SPEED] = speed;
            velocity[DIRECTION] = direction;
            ship.setSpeed(velocity);
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES: panel
	 * EFFECTS: populates the panel's labels that show ship
	 * inventory with the values from ship  
	 */
	private void populateSidePanel() {
            for(int i = 0; i < TOTAL_POWERS; i++) {
                panels.powerLabels[i].setText(String.valueOf(ship.getPower(i)));
            }
            panels.powerAvailLabel.setText(String.valueOf(round(ship.getUnusedPower(),2)) + "%");
            panels.totalPowerLabel.setText(String.valueOf(round(ship.getPower(),2)) + "%");
            panels.antimatterPodsLabel.setText(String.valueOf(ship.getNumAntimatterPods()));
            panels.tritonMislsLabel.setText(String.valueOf(ship.getNumTrtMissiles()));
            panels.quadrantLabel.setText(String.valueOf(quad.getPosition().getRow()) + "-" + String.valueOf(quad.getPosition().getCol()));
            panels.sectorLabel.setText(String.valueOf(ship.getSector().getPosition().getRow()) + "-" + String.valueOf(ship.getSector().getPosition().getCol()));
            panels.joviansLeftLabel.setText(String.valueOf(Space.getInstance().TotalJovian));
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  panel
	 * EFFECTS: updates GUI and calls action for all game objects
	 */
	public void update() {	
            Position pos = new Position(0,0);
            if(quad != ship.getQuadrant()) {
                quad = ship.getQuadrant();
                quadObjects = ship.getQuadrant().getGeneratedObjects();
                weaponObjects = ship.getQuadrant().getWeaponList();
                panels.quadrantLabel.setText(String.valueOf(quad.getPosition().getRow()) + "-" + String.valueOf(quad.getPosition().getCol()));
            }

            clearBoard();
            for(int i= 0; i < quadObjects.size(); i++){
                pos = quadObjects.get(i).getPosition();
                panels.grid[pos.getCol()][pos.getRow()].setText(quadObjects.get(i).getLabel());
                if(quadObjects.get(i) instanceof Ship){
                    panels.grid[pos.getCol()][pos.getRow()].setForeground(Color.GREEN);
                }else if(quadObjects.get(i) instanceof Star){
                    panels.grid[pos.getCol()][pos.getRow()].setForeground(Color.ORANGE);
                }
                
                if(quadObjects.get(i) instanceof SpaceStation) panels.grid[pos.getCol()][pos.getRow()].setForeground(Color.BLUE);
                try {
                    quadObjects.get(i).action();
                } catch (CriticalPowerException e){
                    panels.invalidCommandLabel.setText("Captian the power levels are going critical");
                }
            }

            for(int i= 0; i < weaponObjects.size(); i++){
                if(weaponObjects.get(i) instanceof Maser) {
                    for (Sector sector : ((Maser)weaponObjects.get(i)).getTail()){
                        panels.grid[sector.getPosition().getCol()][sector.getPosition().getRow()].setText(weaponObjects.get(i).getLabel());
                    }
                    weaponObjects.get(i).action();
                    if(quadObjects.get(i) instanceof Maser) panels.grid[pos.getCol()][pos.getRow()].setForeground(Color.CYAN);
                } else if(weaponObjects.get(i) instanceof AntimatterPod) {
                    weaponObjects.get(i).action();
                    pos = weaponObjects.get(i).getPosition();
                    panels.grid[pos.getCol()][pos.getRow()].setText(weaponObjects.get(i).getLabel());
                }else {
                    pos = weaponObjects.get(i).getPosition();
                    panels.grid[pos.getCol()][pos.getRow()].setText(weaponObjects.get(i).getLabel());
                    weaponObjects.get(i).action();
                }
            }

            if(ship.getUnusedPower() < MIN_TOTAL_POWER) {
                criticalCounter--;
                panels.invalidCommandLabel.setText("Critical power: Self destruct in " + criticalCounter);
            } else {
                criticalCounter = INITCRITICALTIMER;
            }

            if(selfDestructActive) {
                destructCounter--;
                panels.invalidCommandLabel.setText("Self destruct in " + destructCounter);
            }



            //SHIP ACTION
            ship.action();


            updateResource();
            panels.sectorLabel.setText(String.valueOf(ship.getSector().getPosition().getRow()) + "-" + String.valueOf(ship.getSector().getPosition().getCol()));
            isActiveSR(ship.getPower(SRSENSOR) > MIN_SYSTEM_POWER);
            isActiveLR(ship.getPower(LRSENSOR) > MIN_SYSTEM_POWER);
            panels.powerAvailLabel.setText(String.valueOf(round(ship.getUnusedPower(),2)) + "%");
            panels.totalPowerLabel.setText(String.valueOf(round(ship.getPower(),2)) + "%");
            panels.starTimeLabel.setText(String.valueOf(starTime));
            panels.joviansLeftLabel.setText(String.valueOf(Space.getInstance().TotalJovian));
            starTime += TIMEINCREMENT;
            updateCondition();

            if(criticalCounter == CRITICALMASS || destructCounter == SELFDESTRUCTMAX || ship.getPower() == 0) {
                endGame();
            }

            if(Space.getInstance().TotalJovian == 0) {
                winGame();
            }
	}
	
	/*
	 * REQUIRES: type, value
	 * MODIFIES: panels
	 * EFFECTS: pass type and value to ship and set the labels that they use
	 * in panel
	 */
	public void setPower(int type, double value){

            try{
                ship.adjustPower(type,value);
            }catch (CriticalPowerException e){
                panels.invalidCommandLabel.setText("Captian the power levels are going critical");
            }
            panels.powerLabels[type].setText(String.valueOf(round(ship.getPower(type),2)));
            panels.powerAvailLabel.setText(String.valueOf(round(ship.getUnusedPower(),2)) + "%");
            panels.totalPowerLabel.setText(String.valueOf(round(ship.getPower(),2)) + "%");
	}

	/*
	 * REQUIRES: NONE
	 * MODIFIES: selfDestructActive, panels
	 * EFFECTS: Actives and deactives ship self destruct by toggling selfDestructActive
	 * and set destructCounter to INITDESTRUCTTIMER if selfDestructActive false
	 */
	public void selfDestruct(){
            if(selfDestructActive) {
                selfDestructActive = false;
                panels.invalidCommandLabel.setText("Self destruct de-actived");
            } else {
                selfDestructActive=true;
                destructCounter=INITDESTRUCTTIMER;
                panels.invalidCommandLabel.setText("Self destruct active");
            }
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  panels
	 * EFFECTS: set panel's invalidCommandLabel
	 */
	public void invalidCommand()
	{
            panels.invalidCommandLabel.setText("Captain your an idiot");
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  panels
	 * EFFECTS: set panel's invalidCommandLabel to ""
	 */
	public void clearInvalidCommand()
	{
		panels.invalidCommandLabel.setText("");
	}
	
	/*
	 * REQUIRES: type, direction
	 * MODIFIES:  NONE
	 * EFFECTS: pass type and direction to ship
	 */
	public void shootWeapon(int type, int direction) {
		ship.shootWeapon(type, direction);
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  NONE
	 * EFFECTS: call ship's detonateAntiPod
	 */
	public void explodePod() {
		ship.detonateAntiPod();
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  NONE
	 * EFFECTS: calls deltaloop to stop timer and ship selfdestruct and
	 * a pop window to say you lost
	 */
	private void endGame() {
		deltaLoop.stopRunning();
		ship.selfDestruct();
		JOptionPane.showMessageDialog(null,"You have lost the game");
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  NONE
	 * EFFECTS: calls deltaloop to stop timer and
	 * a pop window to say you won
	 */
	private void winGame() {
            deltaLoop.stopRunning();
            JOptionPane.showMessageDialog(null,"You have won the game!");
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  panels
	 * EFFECTS: clear panels grid to the empty label
	 */
	private void clearBoard(){
            for(int i = 0; i < QUADRANT_SIZE; i++) {
                for(int j = 0; j < QUADRANT_SIZE; j++) {
                    panels.grid[i][j].setText(EMPTY);
                    panels.grid[i][j].setForeground(Color.WHITE);
                }
            }
	}
	
	/*
	 * REQUIRES: isActive
	 * MODIFIES:  panel
	 * EFFECTS: set panel's grid visiblty to isActive
	 */
	private void isActiveSR(boolean isActive) {
		for(int i = 0; i < QUADRANT_SIZE; i++) {
			for(int j = 0; j < QUADRANT_SIZE; j++) {
					panels.grid[i][j].setVisible(isActive);
			}
		}
	}

	/*
	 * REQUIRES: isActive
	 * MODIFIES:  panel
	 * EFFECTS: set panel's lRSensorLabel visiblty to isActive
	 */
	private void isActiveLR(boolean isActive) {
		for(int i = 0; i < LRARRSIZE; i++) {
			panels.lRSensorLabel[i].setVisible(isActive);
		}
	}

	/*
	 * REQUIRES: NONE
	 * MODIFIES:  panel
	 * EFFECTS: set panel's resource labels to right values from ship
	 */
	private void updateResource()
	{
		panels.antimatterPodsLabel.setText(String.valueOf(ship.getNumAntimatterPods()));
		panels.tritonMislsLabel.setText(String.valueOf(ship.getNumTrtMissiles()));
	}
	
	/*
	 * REQUIRES: NONE
	 * MODIFIES:  panels
	 * EFFECTS: set panel's condition labels to right values from ship condition
	 */
	private void updateCondition()
	{
		for (int i = CONDITIONLEVELAMOUNT; i >= 0; i--){
			if (ship.getPower() >= CONDITIONLEVELS[i]){
				panels.conditionLabel.setText(CONDITIONSTRINGVALUES[i]);
				panels.conditionLabel.setForeground(CONDITIONCOLOR[i]);
			}
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
