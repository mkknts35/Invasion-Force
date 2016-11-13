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
import invasion.force.board.Sector;

/**
 * The Triton Missile weapon which can move straight and whether bump an object
 * or get out of bounds.
 *
 * @author Laurens van Wingerden, Vitalii Egorchatov
 */
public class TritonMissile
        extends Weapon {

    /**
     * REQUIRES: The ship's current sector and the direction of the shoot.
     * MODIFIES: This. EFFECTS: Creates an instance of a missile and puts it
     * into a sector next to the ship depending on the direction of the shot;
     * sets the missile's label and initial direction and speed.
     *
     * @param sector The current ship's sector.
     * @param direction The direction of the shot.
     */
    public TritonMissile(Sector sector, int direction) {
        super(sector, direction);
        label = Configs.TRT_MSSL;
    }

    /**
     * MODIFIES: This. The missile's current sector or destroy the missile if it
     * bumped something. EFFECTS: Checks what is in the next sector; destroys
     * the missile if it has reached the quadrant border; puts the missile into
     * the next sector if it's empty; bumps the object in the next sector if the
     * next sector contains any.
     */
    public void move() {
        Sector nextSector = quadrant.getNext(sector, velocity[0]);
        if (nextSector == null) {
            selfDestruct();
        } else {
            SpaceObject object = nextSector.getInhabitant();
            if (object != null) {
                object.bump(this);
            } else {
                setSector(nextSector);
            }
        }
    }

    /**
     * PURPOSE: This has been hit by other SpaceObject so it destroys itself and
     * object's bumped() called so that they can deal with this particular
     * impact EFFECTS: Destroys the missile.
     *
     * @param object The object that collided with this.
     */
    @Override
    public void bump(SpaceObject object) {
        object.bumped(this);
        selfDestruct();
    }

    /**
     * PURPOSE: A feedback message from the object this has collided with.
     * EFFECTS: Destroys the missile;
     *
     * @param object The object that collided with this.
     */
    @Override
    public void bumped(SpaceObject object) {
        selfDestruct();
    }

}
