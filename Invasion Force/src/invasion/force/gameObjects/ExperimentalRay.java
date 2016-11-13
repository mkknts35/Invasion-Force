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
package invasion.force.gameObjects;


import java.util.Random;

/**
* The Experimental Ray weapon which has 50% chance to damage the ship more than enemies
* and vice versa.
* @author Laurens van Wingerden, Vitalii Egorchatov
*/
public class ExperimentalRay 
	extends Weapon{
	
	/**
	 * The ExperimentalRay's random damage chance generator.
	 */
	private Random random;
	
	private Ship ship;
	
	/**
	 * MODIFIES: This.
	 * EFFECTS: Creates an instance of an Experimental Ray; 
	 * initializes the random damage chance generator.
	 * @param sector The current ship's sector.
	 * @param direction The direction of the shoot.
	 */
	public ExperimentalRay(Ship ship){
		random = new Random();
		this.ship = ship;
	}
	
	/**
	 * EFFECTS: Calculates random 50/50 chance to damage the ship more than enemies
	 * and vice versa; in the first case, the ship loses 50% of the total power;
	 * in the second case, all the Jovians in the quadrant are destroyed.
	 */
	public void detonate(){	
		//If it's 0, the ship gets hurt
		if(random.nextInt(2) == 0){
			//ship
		} 
		//If it's 1, all the Jovians get killed
		else {
//			for(SpaceObject object : getCurQuadrant().getGeneratedObjects()){
//				if(object instanceof JovianWarship){
//					
//				}
//			}
		}
	}
	
	public void move(){
		
	}
	
	/**
	 * PURPOSE: Does nothing because it doesn't move.
	 * @param object The object that collided with this.
	 */
	@Override
	public void bump(SpaceObject object) {

	}
	
	/**
	 * PURPOSE: Does nothing because it has no position.
	 * @param object The object that collided with this.
	 */
	@Override
	public void bumped(SpaceObject object) {
		
	}
}
