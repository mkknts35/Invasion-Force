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

import invasion.force.board.Sector;
import java.util.ArrayList;
import invasion.force.settings.Configs;

/**
 * The Maser weapon which can move straight leaving its tail and whether hit an
 * object or or get out of bounds.
 *
 * @author Laurens van Wingerden, Vitalii Egorchatov
 */
public class Maser
        extends Weapon {

    /**
     * The Maser's tail
     */
    private ArrayList<Sector> sectors = null;

    /**
     * The flag to indicate whether the maser hit another object.
     */
    protected Boolean hit = false;

    /**
     * REQUIRES: The ship's current sector and the direction of the shot.
     * MODIFIES: This. EFFECTS: Creates an instance of a maser and puts it into
     * a sector next to the ship depending on the direction of the shoot; sets
     * the maser's label and initial direction and speed; initializes the
     * maser's tail.
     *
     * @param sector The current ship's sector.
     * @param direction The direction of the shoot.
     */
    public Maser(Sector sector, int direction) {
        super(sector, direction);
        label = Configs.MSR;
        sectors = new ArrayList<Sector>();
    }

    /**
     * MODIFIES: This: The maser's current sector and tail, and flag of hitting.
     * EFFECTS: Checks what is in the next sector; puts the maser into the next
     * sector if it's empty; bumps the object in the next sector if the next
     * sector contains any; removes one piece of the maser's tail if the maser
     * has bumped or reached the quadrant border; destroys the maser if its tail
     * is completely removed.
     */
    @Override
    public void move() {
        Sector tail = null;
        if (hit) {
            tail = getNext();
            if (tail == null) {
                selfDestruct();
            } else {
                sector.setInhabitant(null);
            }
            return;
        }
        sectors.add(sector);
        Sector nextSector = quadrant.getNext(sector, velocity[0]);
        if (nextSector == null) {
            hit = true;
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
     * MODIFIES: This: The maser's tail. EFFECTS: Returns the first sector with
     * the maser's tail and removes it from the tail if the tail still exists or
     * null otherwise.
     *
     * @return The first sector with the maser's tail.
     */
    private Sector getNext() {
        if (sectors.size() == 0) {
            return null;
        }
        Sector sector = sectors.get(0);
        sectors.remove(0);
        return sector;
    }

    /**
     * PURPOSE: This has been hit by other SpaceObject so it sets 'hit' to true
     * so that it can start removing its tail and object's bumped() called so
     * that they can deal with this particular impact MODIFIES: This: hit.
     * EFFECTS: Starts destroying the maser starting with its tail.
     *
     * @param object The object that collided with this.
     */
    @Override
    public void bump(SpaceObject object) {
        object.bumped(this);
        hit = true;
    }

    /**
     * PURPOSE: A feedback message from the object this has collided with.
     * MODIFIES: This: hit. EFFECTS: Starts destroying the maser starting with
     * its tail.
     *
     * @param object The object that collided with this.
     */
    @Override
    public void bumped(SpaceObject object) {
        hit = true;
    }

    public ArrayList<Sector> getTail() {
        return sectors;
    }
}
