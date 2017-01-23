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

import static invasion.force.settings.Configs.*;
import java.util.ArrayList;
import invasion.force.settings.Configs;
import invasion.force.board.Quadrant;
import invasion.force.board.Sector;
import invasion.force.board.Space;

/**
 * Overview: This class provides a SpaceObject that the player can move around
 * and use to shoot at other SpaceObjects. This provides an interface for the
 * game logic to convert user input into the corresponding behavior.
 *
 * @author Michael Koonts
 *
 */
public class Ship extends SpaceObject implements Movable {

    private static final int MAGNITUDE = 0;
    public static final int DIRECTION = 1;
    private final int[] STOP = {0, Configs.NEUTRAL};
    private final PowerSystem systems;

    /**
     * REQUIRES: 
     *      @param sec - see super class for requirements 
     * MODIFIES: the game state 
     * EFFECTS: Creates a SpaceObject which the player will be in control of
     */
    public Ship(Sector sec) {
        super(sec);
        systems = new PowerSystem(this);
        label = Configs.SHIP;

    }

    /**
     * REQUIRES: 
     * @param type - be a valid weapon type *see settings.Configs
     * @param direction - be a valid direction *see settings.Configs MODIFIES:
     * game state EFFECTS: shoots a weapon of the given type in the given
     * direction A typical call would look like shootWeapon(MASER, NW); using
     * the class settings.Configs to value to MASER and NW if there is
     * sufficient power the weapon system will shoot
     */
    public void shootWeapon(int type, int direction) {
        if (Configs.MASER == type) {
            ((MaserCannon) systems.getSystem(type)).arm(direction);
        } else {
            ((Launchers) systems.getSystem(LAUNCHER)).loadtube(type, direction);
        }
    }

    /**
     * REQUIRES: @param type - be a valid engine type *see settings.Configs
     *
     * @param velocity[MAGNITUDE] >= 0
     * @param velocity[DIRECTION] - be a valid direction *see settings.Configs
     * MODIFIES: this, game state EFFECTS: if the given engine has enough power
     * it will be activated and the ship will begin moving on the given vector.
     * Note: To stop the ship's current movement: @param type - the active
     * engine
     * @param velocity[MAGNITUDE] = 0
     * @param velocity[DIRECTION] = Configs.NEUTRAL
     */
    private void setEngineState(int type, int[] velocity) {
        if (systems.getSystem(type).getPower() > MIN_SYSTEM_POWER) {
            ((Engine) systems.getSystem(type)).setActive(velocity);
            //Check and see if the other engine is on, and if it is turn it off
            if (Configs.ION == type && ((Engine) systems.getSystem(Configs.HYPER)).getActive()) {
                ((Engine) systems.getSystem(Configs.HYPER)).setActive(new int[]{0, Configs.NEUTRAL});
            } else if (Configs.HYPER == type && ((Engine) systems.getSystem(Configs.ION)).getActive()) {
                ((Engine) systems.getSystem(Configs.ION)).setActive(new int[]{0, Configs.NEUTRAL});
            }
        }
    }

    /**
     * REQUIRES: nothing MODIFIES: this EFFECTS: restores the number of missiles
     * and antimatter pods to maximum capacity and replenishes the power system
     */
    public void reload() {
        ((Launchers) systems.getSystem(LAUNCHER)).reload();
        systems.recharge(MAX_POWER);
    }

    /**
     * REQUIRES: nothing MODIFIES: this EFFECTS: reduces the amount of available
     * power. The amount of power drained depends on the amount of power
     * directed to the shields and the constant amount declared in
     * settings.Configs
     *
     * @throws CriticalPowerException
     *
     */
    public void sapPower() throws CriticalPowerException {
        ((Sheilds) systems.getSystem(Configs.SHIELD)).sap();
    }

    /**
     * REQUIRES: @param system - be a valid system id *see settings.Configs
     *
     * @param level - be a value such that the net power drain will be less than
     * the power available MODIFIES: this EFFECTS: changes the amount of power
     * available to the given system
     * @throws CriticalPowerException
     */
    public void adjustPower(int system, double level) throws CriticalPowerException {
        this.systems.setSystemLevel(system, level);
    }

    /**
     * REQUIRES: @param system - be a valid system id *see settings.Configs
     * MODIFIES: nothing EFFECTS: returns the amount of power available to the
     * given system
     */
    public double getPower(int system) {
        return this.systems.getSystemPower(system);
    }

    /**
     * REQUIRES: nothing MODIFIES: nothing EFFECTS: returns the total amount of
     * power available to all systems
     */
    public double getPower() {
        return this.systems.getPowerAvailable();
    }

    /**
     * REQUIRES: nothing MODIFIES: nothing EFFECTS: returns the amount of power
     * not distributed to a system
     */
    public double getUnusedPower() {
        return this.systems.getPowerAvailable() - this.systems.getPowerConsumed();
    }

    /**
     * REQUIRES: nothing MODIFIES: nothing EFFECTS: @return a pointer to the
     * ships current sector
     */
    public Sector getSector() {
        return sector;
    }

    public int getNumAntimatterPods() {
        return ((Launchers) this.systems.getSystem(Configs.LAUNCHER)).getAntiPods();
    }

    public void detonateAntiPod() {
        ((Launchers) this.systems.getSystem(Configs.LAUNCHER)).detonatePod();
    }

    public int getNumTrtMissiles() {
        return ((Launchers) this.systems.getSystem(Configs.LAUNCHER)).getMissilesLeft();
    }

    public double getPowerConsumed() {
        return this.systems.getPowerConsumed();
    }

    /**
     * REQUIRES: nothing MODIFIES: game state EFFECTS: implements all player
     * commands currently queued up This will have different results depending
     * on what the player has asked the ship to do ie. move, shoot, etc.
     *
     * @throws CollissionException
     */
    @Override
    public void move() {
        systems.cycle();
    }

    /**
     * REQUIRES: @param velocity[MAGNITUDE] >= 0
     *
     * @param velocity[DIRECTION] - be a valid direction *see settings.Configs
     * MODIFIES: this EFFECTS: The next time Ship.move() is called on this it
     * will begin moving on the given vector Note: To stop the ship's current
     * movement: @param type - the active engine
     * @param velocity[MAGNITUDE] = 0
     * @param velocity[DIRECTION] = Configs.NEUTRAL
     */
    @Override
    public void setSpeed(int[] velocity) {
        if (velocity[MAGNITUDE] < Configs.MAX_ION) {
            this.setEngineState(ION, velocity);
        } else {
            this.setEngineState(HYPER, velocity);
        }
    }

    @Override
    public void action() {
        this.move();
    }

    /**
     * Overview: This class provides an abstraction to the ship that
     * encapsulates everything that requires power in order to function. It
     * provides a simple set of methods that allow the ship to function
     * intuitively. If the system requested to act doesn't have sufficient power
     * it simply does nothing. If the normal progression of the game causes the
     * power system to become overloaded (the total power usage by all systems
     * exceeds the power available)an exception is thrown
     */
    private class PowerSystem {

        private double powerAvailable;
        private double powerConsumed;
        private ArrayList<ShipSystem> systems;
        public Ship ship;

        /**
         * REQUIRES: @param ship - a pointer to the ship that this is a part of
         * MODIFIES: the state of @param ship EFFECTS: Initializes the ships
         * power system with the default values in settings.Configs
         */
        public PowerSystem(Ship ship) {
            this.powerAvailable = MAX_POWER;
            this.powerConsumed = 0.0;
            this.ship = ship;
            systems = new ArrayList<ShipSystem>();
            systems.add(SHIELD, new Ship.Sheilds(INIT_SHIELD));
            systems.add(LRSENSOR, new Ship.LRSensors(INIT_LRSENSOR));
            systems.add(SRSENSOR, new Ship.SRSensors(INIT_SRSENSOR));
            systems.add(MASER, new Ship.MaserCannon(INIT_MASER));
            systems.add(LAUNCHER, new Ship.Launchers(INIT_TRT_MISSILE));
            systems.add(ION, new Ship.IonEngines(INIT_ENGINE));
            systems.add(HYPER, new Ship.HypEngines(INIT_ENGINE));
            try {
                this.calculateSystemLoad();
            } catch (CriticalPowerException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        /**
         * REQUIRES: @param system - be a valid system id *see settings.Configs
         * MODIFIES: nothing EFFECTS: @returns the power level of the given
         * system
         */
        public double getSystemPower(int system) {
            return systems.get(system).getPower();
        }

        /**
         * REQUIRES: nothing MODIFIES: game state EFFECTS: cycles through all
         * ship systems and calls their act method. This will have different
         * results depending on what the player has asked the ship to do ie.
         * move, shoot, etc.
         *
         * @throws CollissionException is potentially generated when the ship
         * moves to a new sector
         */
        public void cycle() {
            for (ShipSystem sys : systems) {
                sys.act();
            }
        }

        /**
         * REQUIRES: nothing MODIFIES: nothing EFFECTS: @returns the amount of
         * power currently available to the overall system
         */
        public double getPowerAvailable() {
            return this.powerAvailable;
        }

        /**
         * REQUIRES: @param power >= 0 MODIFIES: this EFFECTS: Adds the amount
         * given the the power available to the overall system
         */
        public void recharge(double power) {
            if (this.powerAvailable + power <= MAX_POWER) {
                this.powerAvailable += power;
            } else if (this.powerAvailable + power > MAX_POWER) {
                this.powerAvailable = MAX_POWER;
            }

        }

        /**
         * REQUIRES: nothing 
         * MODIFIES: nothing 
         * EFFECTS: returns the amount of power currently being used by all ship
         * systems
         */
        public double getPowerConsumed() {
            return this.powerConsumed;
        }

        /**
         * REQUIRES: 
         *      @param system - a valid system id *see settings.Configs
         *      @param powerLevel - be a value that won't cause the power 
         *                          consumed to exceed the current power 
         *                          available 
         * MODIFIES: the given ship system
         * EFFECTS: Sets power available to the given system to the given value
         * and recalculates the current drain on the overall system.
         * @throws CriticalPowerException
         */
        public void setSystemLevel(int system, double powerLevel) 
                throws CriticalPowerException {
            //double loadIncrease = powerLevel - systems.get(system).getPower();
            //if(this.powerAvailable > (this.powerConsumed + loadIncrease)){
            systems.get(system).setPower(powerLevel);
            this.calculateSystemLoad();
            //}
        }

        /**
         * REQUIRES: @param type - be a valid system id *see settings.Configs
         * MODIFIES: nothing 
         * EFFECTS: return a pointer to the system requested in type
         */
        public ShipSystem getSystem(int type) {
            return systems.get(type);
        }

        /**
         * REQUIRES: 
         *      @param type - a valid system id *see settings.Configs
         *      @param system - an initialized Ship.ShipSystem 
         * MODIFIES: this
         * EFFECTS: replace system with the given one
         *
         * Note: this method is not needed in the current version of the game,
         * and to avoid potential bugs it is commented out
         */
        //public void setSystem(int type, ShipSystem system) {
        //	this.systems.add(type, system);
        //}
        /**
         * REQUIRES: nothing 
         * MODIFIES: this 
         * EFFECTS: drains the amount Configs.POWER_SAP from the power available 
         * If the ship's shields are active both the shields power and the power 
         * available are drained, but by a lesser amount. If the sap results in 
         * the load on the power system exceeding the power available a 
         * criticalPowerException will be generated.
         *
         */
        public void sapPower(double amount) throws CriticalPowerException {
            if (!((Engine) this.getSystem(Configs.HYPER)).getActive()) {
                this.powerAvailable -= amount;
                this.calculateSystemLoad();
            }
        }

        /**
         * REQUIRES: nothing MODIFIES: this.powerConsumed EFFECTS: Calculate the
         * gross load on the power system. Iterates through all systems and adds
         * up their power setting. If that number exceeds the power available,
         * this throws the exception
         */
        private void calculateSystemLoad() throws CriticalPowerException {
            double load = 0;
            for (ShipSystem i : systems) {
                load += i.getPower();
            }
            this.powerConsumed = load;
            if (this.powerConsumed > this.powerAvailable) {
                throw new CriticalPowerException();
            }
        }
    }

    /**
     * Overview: This class provides a common interface for all ship systems to
     * the the power system. All systems need to store the amount of power
     * available to them, and need an interface to check and change that value.
     * They also require a common interface that will cause the specific ship
     * system to do whatever it is intended to do. ie. the engines move the ship
     */
    private abstract class ShipSystem {

        private double power;

        /**
         * REQUIRES: @param powerLevel >= 0 MODIFIES: this EFFECTS: initializes
         * a new ShipSystem and sets the amount of power available to it to
         * value given
         */
        public ShipSystem(double powerLevel) {
            power = powerLevel;
        }

        /**
         * REQUIRES: nothing MODIFIES: nothing EFFECTS: @returns the amount of
         * power assigned to this
         */
        public double getPower() {
            return power;
        }

        /**
         * REQUIRES: @param powerLevel >= 0 MODIFIES: this EFFECTS: sets the
         * amount of power available to this to value given
         */
        public void setPower(double powerLevel) {
            power = powerLevel;
        }

        /**
         * REQUIRES: nothing MODIFIES: see individual overrides EFFECTS: see
         * individual overrides
         *
         * @throws CollissionException if this.act() causes the ship to move to
         * a new location the exception can be generated.
         */
        public abstract void act();
    }

    private class Sheilds extends ShipSystem {

        /**
         * REQUIRES: MODIFIES: EFFECTS:
         */
        public Sheilds(double powerLevel) {
            super(powerLevel);
        }

        /**
         * REQUIRES: nothing MODIFIES: nothing EFFECTS: stub
         */
        @Override
        public void act() {
        }

        public void sap() throws CriticalPowerException {
            if (this.getPower() > 0) {
                double powerToSap = Configs.POWER_SAP / this.getPower();
                this.setPower(this.getPower() - powerToSap);
                systems.sapPower(powerToSap);
            } else {
                systems.sapPower(Configs.POWER_SAP);
            }
        }

    }

    /**
     * Overview: This class provides a common engine interface. All engines no
     * matter their type need to be turned on, and be pointed in a useful
     * direction. They also need to provide their user with the ability to check
     * their state. However, different engines will have different consequences
     * resulting from their active state.
     */
    private abstract class Engine extends ShipSystem {

        protected boolean active;
        protected int direction;
        private int delta;

        /**
         * REQUIRES: @param powerLevel - see super MODIFIES: this EFFECTS:
         * returns an initialized engine that is currently not active
         */
        public Engine(double powerLevel) {
            super(powerLevel);
            this.active = false;
            this.direction = NEUTRAL;
            this.setDelta(-1);
        }

        /**
         * REQUIRES: 
         *      @param velocity[Ship.MAGNITUDE] >= 0
         *      @param velocity[Ship.DIRECTION] - must be a valid value as 
         *             outlined in settings.Configs 
         * MODIFIES: this 
         * EFFECTS: Changes the state of this to reflect the given vector 
         *  Note: the vector {0, Configs.NEUTRAL} sets this inactive 
         *          ie. turns the engine off
         */
        public void setActive(int[] velocity) {
            if (0 == velocity[Ship.MAGNITUDE]) {
                this.active = false;
            } else {
                this.active = true;
            }
            this.direction = velocity[Ship.DIRECTION];
            this.setDelta(-1);
        }

        /**
         * REQUIRES: nothing MODIFIES: nothing EFFECTS: @returns the active
         * state of this
         */
        public boolean getActive() {
            return active;
        }

        public int getDelta() {
            return delta;
        }

        public void setDelta(int delta) {
            this.delta = delta;
        }

        protected void moveToNextQuadrant() {
            int xCurrent = quadrant.getPosition().getCol();
            int yCurrent = quadrant.getPosition().getRow();
            switch (this.direction) {
                case (Configs.NORTH):
                    yCurrent -= 1;
                    break;
                case (Configs.EAST):
                    //x quadrant + 1
                    xCurrent += 1;
                    break;
                case (Configs.SOUTH):
                    //y quadrant + 1
                    yCurrent += 1;
                    break;
                case (Configs.WEST):
                    //x quadrant - 1
                    xCurrent -= 1;
                    break;
                case (Configs.NORTH_WEST):
                    //x quadrant - 1 && y quadrant - 1
                    xCurrent -= 1;
                    yCurrent -= 1;
                    break;
                case (Configs.NORTH_EAST):
                    //x quadrant + 1 && y quadrant - 1
                    xCurrent += 1;
                    yCurrent -= 1;
                    break;
                case (Configs.SOUTH_EAST):
                    //x quadrant + 1 && y quadrant + 1
                    xCurrent += 1;
                    yCurrent += 1;
                    break;
                case (Configs.SOUTH_WEST):
                    //x quadrant - 1 && y quadrant + 1
                    xCurrent -= 1;
                    yCurrent += 1;
                    break;
            }
            if (xCurrent > Configs.SPACE_SIZE - 1) {
                xCurrent = 0;
            }
            if (xCurrent < 0) {
                xCurrent = (Configs.SPACE_SIZE - 1);
            }
            if (yCurrent > Configs.SPACE_SIZE - 1) {
                yCurrent = 0;
            }
            if (yCurrent < 0) {
                yCurrent = (Configs.SPACE_SIZE - 1);
            }
            Quadrant next = Space.getInstance().getQuadrant(yCurrent, xCurrent);
            quadrant.unpopulate();
            next.getGeneratedObjects().add(systems.ship);
            quadrant.getGeneratedObjects().remove(systems.ship);
            quadrant = next;
            quadrant.populate();
            if (((Launchers) systems.getSystem(LAUNCHER)).getActive()) {
                ((Launchers) systems.getSystem(LAUNCHER)).clearActive();
            }
        }
    }

    /**
     * Overview: This class implements the abstract class Engine and if given
     * sufficient power and a valid vector will move the ship within the current
     * quadrant. If this is left active long enough it will move the ship to the
     * next quadrant on that vector before deactivating itself. The value of
     * this.throttle effects the rate that the ship will move through the
     * current quadrant.
     */
    private class IonEngines extends Engine {

        private int throttle;

        /**
         * REQUIRES: see super @param powerLevel MODIFIES: this EFFECTS:
         * Initializes a new this and sets the throttle to 0
         */
        public IonEngines(double powerLevel) {
            super(powerLevel);
            this.throttle = 0;
        }

        /**
         * REQUIRES: nothing MODIFIES: ship location EFFECTS: the location of
         * the ship in the current quadrant If the current vector takes the ship
         * out of the current quadrant moves to the appropriate quadrent and
         * deactivates this
         *
         * @throws CollissionException if the destination sector is already
         * occupied
         */
        @Override
        public void act() {
            if (this.active && 0 == getDelta()) {
                Sector newSec = Space.getInstance().getQuadrant(quadrant.getPosition()).getNext(sector, direction);
                if (null == newSec) {
                    moveToNextQuadrant();
                    this.setActive(STOP);
                } else if (null != newSec.getInhabitant()) {
                    newSec.getInhabitant().bump(Ship.this);
                } else {
                    setSector(newSec);
                }
                setDelta(10 - this.throttle);
            } else if (getDelta() > 0) {
                setDelta(getDelta() - 1);
            }
        }

        /**
         * REQUIRES: see super MODIFIES: this EFFECTS: see super, sets values
         * outside the scope of the super method
         */
        @Override
        public void setActive(int[] velocity) {
            super.setActive(velocity);
            this.throttle = velocity[Ship.MAGNITUDE];
            if (0 < this.throttle) {
                setDelta(Configs.MAX_ION - this.throttle);
            } else {
                setDelta(-1);
            }
        }
    }

    /**
     * Overview: This class implements the abstract class Engine and if given
     * sufficient power and a valid vector will move the ship to the next
     * quadrant in the direction indicated. This engine will remain active until
     * the user shuts it off.
     */
    private class HypEngines extends Engine {

        /**
         * REQUIRES: see super @param powerLevel 
         * MODIFIES: this 
         * EFFECTS: Initializes a new this
         */
        public HypEngines(double powerLevel) {
            super(powerLevel);
        }

        /**
         * REQUIRES: nothing 
         * MODIFIES: the ships location 
         * EFFECTS: If this has sufficient power move the ship to the next 
         * quadrant in the current direction
         */
        @Override
        public void act() {
            if (this.active) {
                if (this.getDelta() == 0) {
                    this.moveToNextQuadrant();
                } else {
                    this.setDelta(this.getDelta() - 1);
                }
            }
        }

        /**
         * REQUIRES: see super 
         * MODIFIES: this 
         * EFFECTS: see super, sets values outside the scope of the super method
         */
        @Override
        public void setActive(int[] velocity) {
            super.setActive(velocity);
            if (this.active) {
                setDelta(Configs.HYPER_DELTA);
            } else {
                setDelta(-1);
            }
        }
    }

    private class LRSensors extends ShipSystem {

        /**
         * REQUIRES: 
         * MODIFIES: 
         * EFFECTS:
         */
        public LRSensors(double powerLevel) {
            super(powerLevel);
            // TODO Auto-generated constructor stub
        }

        /**
         * REQUIRES: 
         * MODIFIES: 
         * EFFECTS:
         */
        @Override
        public void act() {
            // TODO Auto-generated method stub

        }

    }

    private class SRSensors extends ShipSystem {

        /**
         * REQUIRES: 
         * MODIFIES: 
         * EFFECTS:
         */
        public SRSensors(double powerLevel) {
            super(powerLevel);
            // TODO Auto-generated constructor stub
        }

        /**
         * REQUIRES: 
         * MODIFIES: 
         * EFFECTS:
         */
        @Override
        public void act() {
            // TODO Auto-generated method stub

        }

    }

    private class Launchers extends ShipSystem {

        /**
         * Overview: This class provides the user with the ability to launch
         * different types of projectile weapons in a given direction. This is
         * the storage and launching mechanism for weapons like triton missiles.
         */
        private static final int NONE = 0;
        private int antimatterPods;
        private int trtmissiles;
        private int type;
        private int direction;
        private AntimatterPod active;

        /**
         * REQUIRES: see super @param powerLevel MODIFIES: this EFFECTS: create
         * a new this a load it to the capacity set in settings.Configs
         */
        public Launchers(double powerLevel) {
            super(powerLevel);
            antimatterPods = ANTIMATTER_PODS;
            trtmissiles = TRT_MISSILES;
            type = NONE;
            direction = NEUTRAL;
        }

        public int getMissilesLeft() {
            return this.trtmissiles;
        }

        public int getAntiPods() {
            return this.antimatterPods;
        }

        public boolean getActive() {
            return null == active;
        }

        public void clearActive() {
            active = null;
        }

        /**
         * REQUIRES: nothing MODIFIES: game state EFFECTS: If there is a weapon
         * "in the tube" launch it
         */
        @Override
        public void act() {
            if (this.getPower() >= Configs.MIN_SYSTEM_POWER) {
                this.launch();
            }
        }

        /**
         * REQUIRES: nothing (future: this should check if there is a space
         * station in an adjoining sector) MODIFIES: this EFFECTS: resets the
         * number of projectile weapons available to their maximum values as
         * defined in settings.Configs
         */
        public void reload() {
            antimatterPods = ANTIMATTER_PODS;
            trtmissiles = TRT_MISSILES;
        }

        /**
         * REQUIRES: @param type - must be a valid projectile type *see
         * settings.Configs
         *
         * @param direction - must be a valid direction *see settings.Configs -
         * must not be settings.Configs.NEUTRAL MODIFIES: this EFFECTS: Prepare
         * the system to launch a projectile weapon in the direction indicated
         * ie. "load the tube"
         */
        public void loadtube(int type, int direction) {
            if (null != Space.getInstance().getQuadrant(quadrant.getPosition()).getNext(sector, direction)) {
                this.type = type;
                this.direction = direction;
            }
        }

        /**
         * REQUIRES: nothing MODIFIES: game state EFFECTS: Instantiate the
         * weapon "in the tube" with the information available ie. shoot the
         * weapon
         */
        private void launch() {
            if (Configs.ANITMATTER_POD == this.type && 0 < this.antimatterPods) {
                if (null == active) {
                    this.active = new AntimatterPod(sector, direction);
                    this.antimatterPods--;
                    Space.getInstance().getQuadrant(sector.getQuadPosition()).getWeaponList().add(this.active);
                }
            } else if (Configs.TRT_MISSILE == this.type && Configs.NEUTRAL != this.direction && 0 < this.trtmissiles) {
                Space.getInstance().getQuadrant(sector.getQuadPosition()).getWeaponList().add(new TritonMissile(sector, direction));
                this.trtmissiles--;
            }
            this.type = Launchers.NONE;
            this.direction = Configs.NEUTRAL;
        }

        public void detonatePod() {
            if (null != active) {
                this.active.detonate();
                this.active = null;
            }
        }
    }

    private class MaserCannon extends ShipSystem {

        /**
         * Overview: This class provides the user with the ability to shoot
         * maser shots in the direction that they choose. If they have allocated
         * sufficient power to this.
         */
        private int direction;

        /**
         * REQUIRES: see super @param powerLevel MODIFIES: this EFFECTS:
         * Initializes a new this in a safe state
         */
        public MaserCannon(double powerLevel) {
            super(powerLevel);
            this.direction = Configs.NEUTRAL;
        }

        /**
         * REQUIRES: nothing MODIFIES: game state EFFECTS: If there is
         * sufficient power in this and this is "armed", shoot this
         */
        @Override
        public void act() {
            if (this.getPower() >= Configs.MIN_SYSTEM_POWER) {
                this.shoot();
            }
        }

        /**
         * REQUIRES: caller must check the power level before calling MODIFIES:
         * game state and this EFFECTS: If this is "armed" instantiate a maser
         * shot in the this.direction and then disarm this. Note: "armed" means
         * that Configs.NEUTRAL != this.direction
         */
        private void shoot() {
            if (Configs.NEUTRAL != direction) {
                Space.getInstance().getQuadrant(sector.getQuadPosition()).getWeaponList().add(new Maser(sector, direction));
            }
            this.direction = Configs.NEUTRAL;
        }

        /**
         * REQUIRES: @param direction - must be a valid direction as defined in
         * settings.Configs MODIFIES: this EFFECTS: Sets this to armed status
         */
        public void arm(int direction) {
            if (null != Space.getInstance().getQuadrant(quadrant.getPosition()).getNext(sector, direction)) {
                this.direction = direction;
            }
        }
    }

    @Override
    public void bumped(SpaceObject object) {
        if (object instanceof Star) {
            this.selfDestruct();
        }
        if (object instanceof JovianWarship) {
            //curse at each other
        }
        if (object instanceof SpaceStation) {
            ((Engine) systems.getSystem(Configs.ION)).setActive(STOP);
        }
    }

    @Override
    public void bump(SpaceObject object) {
        object.bumped(object);
        if (object instanceof AntimatterPod) {
            this.selfDestruct();
        }
    }
}
