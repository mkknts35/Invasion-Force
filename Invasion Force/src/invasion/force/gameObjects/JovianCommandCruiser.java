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

import invasion.force.settings.Configs;
import invasion.force.board.*;

/**
 * The Jovian Command Cruiser which can move changing its direction randomly
 * every turn and saps the ship's power.
 * @author Laurens van Wingerden, Vitalii Egorchatov
 */
public class JovianCommandCruiser 
	extends JovianWarship{

	/**
	 * REQUIRES: The Jovian's current sector and the ship as a target.
	 * MODIFIES: This.
	 * EFFECTS: Creates an instance of Jovian Command Cruiser and puts it into a sector;
	 * sets the cruiser's label and initial speed; passes the ship as a target.
	 * @param sector The current Jovian Command Cruiser's sector.
	 * @param ship The reference to the ship.
	 */
	public JovianCommandCruiser(Sector sector, Ship ship) {
		super(sector, ship);
		label = Configs.JCC;
	}
	
	/**
	 * PURPOSE: This has been hit by other SpaceObject so it destroys itself 
	 * if the object is an Antimatter Pod and object's bumped() called so that they can 
	 * deal with this particular impact;
	 * EFFECTS: Destroys the command cruiser only by an Antimatter Pod.
	 * @param object The object that collided with this.
	 */
	@Override
	public void bump(SpaceObject object) {
		object.bumped(this);
		if(object instanceof AntimatterPod){
			selfDestruct();
		}
	}
	
	/**
	 * PURPOSE: Nothing happens because this.move() doesn't allow to bump into anything.
	 * @param object The object that collided with this.
	 */
	@Override
	public void bumped(SpaceObject object) {
		
	}	
}

