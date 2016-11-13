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

import invasion.force.board.*;

/**
 * The abstract super class for all types of weapon
 * @author Laurens van Wingerden, Vitalii Egorchatov
 */
public abstract class Weapon 
	extends SpaceObject
	implements Movable{
	
	int[] velocity = new int[2];
	
	/**
	 * MODIFIES: This.
	 * EFFECTS: Creates an instance of weapon without any sector.
	 */
	public Weapon(){		
		super(null);
	}
	
	/**
	 * REQUIRES: The ship's current sector and the direction of the shot.
	 * MODIFIES: This.
	 * EFFECTS: Creates an instance of weapon and puts it into a sector
	 * next to the ship depending on the direction of the shoot;
	 * sets the weapon's initial direction and speed.
	 * @param sector The current ship's sector.
	 * @param direction The direction of the shot.
	 */
	public Weapon(Sector sector, int direction){
		
		super(Space.getInstance().
				getQuadrant(sector.getQuadPosition()).
				getNext(sector, direction));
		setSpeed(new int[]{direction, 1});
	}
	
	/**
	 * REQUIRES: The necessary velocity in the form of an array, 
	 * in which the first item is direction and the second is speed.
	 * MODIFIES: This: Velocity.
	 * EFFECTS: Changes its direction or/and speed.
	 * @param vel The necessary weapons's velocity 
	 */
	public void setSpeed(int[] vel){
		this.velocity[0] = vel[0];
		this.velocity[1] = vel[1];
	}
	
	/**
	 * EFFECTS: Moves the weapon.
	 */
	@Override
	public void action(){
		this.move();
	}
}
