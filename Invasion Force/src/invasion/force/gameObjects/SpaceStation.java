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
 * The Space Station is a stationary space object.
 *
 * @author Laurens van Wingerden, Vitalii Egorchatov
 */
public class SpaceStation
        extends SpaceObject {

    /**
     * REQUIRES: The Space Station's current sector. MODIFIES: This. EFFECTS:
     * Creates an instance of Space Station and puts it into a sector; sets the
     * station's label.
     *
     * @param sector The current station's sector.
     */
    public SpaceStation(Sector sector) {
        super(sector);
        label = Configs.SSTN;
    }

    /**
     * EFFECTS: Does nothing since the Space Station is an stationary object.
     */
    @Override
    public void action() {

    }

    /**
     * PURPOSE: This has been hit by other SpaceObject so it can reload the ship
     * if the object is Ship; it destroys itself if the object is an Antimatter
     * Pod and object's bumped() called so that they can deal with this
     * particular impact; EFFECTS: Reloads the ship or destroys the Space
     * Station only by an Antimatter Pod.
     *
     * @param object The object that collided with this.
     */
    @Override
    public void bump(SpaceObject object) {
        object.bumped(this);
        if (object instanceof Ship) {
            ((Ship) object).reload();
        } else if (object instanceof AntimatterPod) {
            selfDestruct();
        }
    }

    /**
     * PURPOSE: Nothing happens because the Space Station doesn't move.
     *
     * @param object The object that collided with this.
     */
    @Override
    public void bumped(SpaceObject object) {

    }
}
