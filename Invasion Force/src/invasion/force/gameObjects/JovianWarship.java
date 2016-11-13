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
import invasion.force.settings.Configs;
import invasion.force.board.*;

/**
 * The abstract super class for all types of Jovian
 *
 * @author Laurens van Wingerden, Vitalii Egorchatov
 */
public abstract class JovianWarship
        extends SpaceObject
        implements Movable {

    /**
     * The Jovian's random roaming generator.
     */
    private int DELTA;
    protected Random random;
    /**
     * The ship is the target of this Jovian.
     */
    private Ship ship;

    /**
     * REQUIRES: The Jovian's current sector and the ship as a target. MODIFIES:
     * This. EFFECTS: Creates an instance of Jovian and puts it into a sector;
     * sets the Jovian's initial speed; passes the ship as a target.
     *
     * @param sector The current Jovian's sector.
     * @param ship The reference to the ship.
     */
    public JovianWarship(Sector sector, Ship ship) {
        super(sector);
        random = new Random();
        DELTA = Configs.JOVIAN_DELTA;
        setSpeed(new int[]{Configs.NEUTRAL, 1});
        this.ship = ship;
    }

    /**
     * EFFECTS: Moves the Jovian and saps the ship's power.
     *
     * @throws CriticalPowerException
     */
    @Override
    public void action() throws CriticalPowerException {
        if (DELTA == 0) {
            DELTA = Configs.JOVIAN_DELTA;
            move();
            ship.sapPower();
        } else {
            DELTA--;
        }
    }

    /**
     * MODIFIES: This. The Jovian's current sector and direction. EFFECTS:
     * Changes the Jovian's direction randomly every turn; puts the Jovian into
     * the next sector if it's empty.
     */
    @Override
    public void move() {

        sector.setInhabitant(null);
        Sector nextSector = null;
        int direction = 0;
        do {
            do {
                direction = 1 + random.nextInt(9);
            } while (direction == 5);
            nextSector = quadrant.getNext(sector, direction);
        } while (nextSector == null || nextSector.getInhabitant() != null);
        setSpeed(new int[]{direction, 1});
        setSector(nextSector);
    }

    /**
     * REQUIRES: The necessary velocity in the form of an array, in which the
     * first item is direction and the second is speed. MODIFIES: This:
     * Velocity. EFFECTS: Changes its direction or/and speed.
     *
     * @param vel The necessary Jovian's velocity
     */
    public void setSpeed(int[] vel) {
        velocity[0] = vel[0];
        velocity[1] = vel[1];
    }
}
