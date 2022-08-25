package magicarrows;

import aic2022.engine.Unit;
import aic2022.user.*;

import java.util.HashSet;

public class Pathfinding {

    UnitController uc;
    Communication comm;
    Location target = null;

    BugNav bugNav = new BugNav();
    Exploration explore;

    Team myTeam, enemyTeam;

    boolean doMicro() {
        return bugNav.doMicro();
    }

    Pathfinding(UnitController rc, Exploration explore, Communication comm){
        this.uc = rc;
        this.explore = explore;
        this.comm = comm;
        myTeam = uc.getTeam();
        enemyTeam = uc.getOpponent();
    }

    public void move(Location loc){
        if (!uc.canMove()) return;
        target = loc;

        //rc.setIndicatorLine(rc.getLocation(), target, 255, 0, 0);
        bugNav.move();
    }

    boolean isSafeFromBase(Location loc) {
        if (comm.baseLocEnemy == null) return true;
        if (comm.task == 2) return true;
        return loc.distanceSquared(comm.baseLocEnemy) > UnitType.BASE.getStat(UnitStat.ATTACK_RANGE) || (uc.canSenseLocation(comm.baseLocEnemy) && uc.isObstructed(loc, comm.baseLocEnemy, uc.getOpponent())); // what if obstructed? i guess we don't care
    }

    class BugNav{

        BugNav(){}

        final int INF = 1000000;

        boolean rotateRight = true; //if I should rotate right or left
        Location lastObstacleFound = null; //latest obstacle I've found in my way
        int minDistToEnemy = INF; //minimum distance I've been to the enemy while going around an obstacle
        Location prevTarget = null; //previous target
        HashSet<Integer> visited = new HashSet<>();

        void move() {
            //No target? ==> bye!
            if (target == null) return;
            //different target? ==> previous data does not help!
            if (prevTarget == null || target.distanceSquared(prevTarget) > 0) resetPathfinding();

            //If I'm at a minimum distance to the target, I'm free!
            Location myLoc = uc.getLocation();
            int d = myLoc.distanceSquared(target);
            if (d <= minDistToEnemy) resetPathfinding();

            int code = getCode();

            if (visited.contains(code)) resetPathfinding();
            visited.add(code);

            //Update data
            prevTarget = target;
            minDistToEnemy = Math.min(d, minDistToEnemy);

            //If there's an obstacle I try to go around it [until I'm free] instead of going to the target directly
            Direction dir = myLoc.directionTo(target);
            if (lastObstacleFound != null) dir = myLoc.directionTo(lastObstacleFound);

            if (uc.canMove(dir) && isSafeFromBase(myLoc.add(dir))) resetPathfinding();

            //I rotate clockwise or counterclockwise (depends on 'rotateRight'). If I try to go out of the map I change the orientation
            //Note that we have to try at most 16 times since we can switch orientation in the middle of the loop. (It can be done more efficiently)
            for (int i = 0; i < 16; ++i){
                if (uc.canMove(dir) && isSafeFromBase(myLoc.add(dir))){
                    uc.move(dir);
                    return;
                }
                Location newLoc = myLoc.add(dir);
                if (uc.isOutOfMap(newLoc)) rotateRight = !rotateRight;
                    //If I could not go in that direction and it was not outside of the map, then this is the latest obstacle found
                else lastObstacleFound = myLoc.add(dir);
                if (rotateRight) dir = dir.rotateRight();
                else dir = dir.rotateLeft();
            }

            if (uc.canMove(dir) && isSafeFromBase(myLoc.add(dir))) uc.move(dir);

            return;
        }

        //clear some of previous data
        void resetPathfinding(){
            lastObstacleFound = null;
            minDistToEnemy = INF;
            visited.clear();
        }

        int getCode(){
            int x = uc.getLocation().x;
            int y = uc.getLocation().y;
            Direction obstacleDir = uc.getLocation().directionTo(target);
            if (lastObstacleFound != null) obstacleDir = uc.getLocation().directionTo(lastObstacleFound);
            int bit = rotateRight ? 1 : 0;
            return (((((x << 6) | y) << 4) | obstacleDir.ordinal()) << 1) | bit;
        }

        //generic method for attacking
        public boolean tryGenericAttack() {
            Location myLoc = uc.getLocation();
            // enemies
            UnitInfo[] enemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.VISION_RANGE), myTeam, true);
            UnitInfo bestUnitToAttack;

            if(enemies.length > 0) {
                bestUnitToAttack = getGenericBestUnitToAttack(enemies);
                if(bestUnitToAttack != null) {
                    uc.println("attacking " + bestUnitToAttack);
                    uc.setOrientation(myLoc.directionTo(bestUnitToAttack.getLocation()));
                    uc.attack(bestUnitToAttack.getLocation());
                    return true;
                }
            }

            // shrines, i guess
            ShrineInfo[] shrines = uc.senseShrines((int)uc.getInfo().getStat(UnitStat.ATTACK_RANGE));

            if(shrines.length > 0) {
                ShrineInfo bestShrineToAttack = getGenericBestShrineToAttack(shrines);
                if(bestShrineToAttack != null) {
                    uc.setOrientation(myLoc.directionTo(bestShrineToAttack.getLocation()));
                    uc.attack(bestShrineToAttack.getLocation());
                    return true;
                }
            }

            return false;
        }

        public UnitInfo getGenericBestUnitToAttack(UnitInfo[] units) {
            UnitInfo unitToAttack = null;
            boolean canKill = false;
            for (UnitInfo unit: units) {
                if (!uc.isObstructed(uc.getLocation(), unit.getLocation()) && unit.getType() != UnitType.BASE) comm.writeEnemyToLog(unit.getLocation()); // we just wont log obstructed enemies
                //skip if we can't attack
                Location unitLoc = unit.getLocation();
                if (!uc.canAttack(unitLoc) || (uc.getType() == UnitType.MAGE && unitLoc.distanceSquared(uc.getLocation()) <= 2)) continue;
                if (comm.task == 2 && unit.getType() == UnitType.BASE) return unit;
                // we choose the unit with the least health! if we can kill a unit, we choose the killable unit with the highest health, maximizing damage output
                if((unitToAttack == null || (!canKill && unitToAttack.getHealth() > unit.getHealth()))) {
                    unitToAttack = unit;
                    float onForest = 1f;
                    if (uc.senseTileTypeAtLocation(unit.getLocation()) == TileType.FOREST) onForest = .8f;
                    float myDamage = 0;
                    if (uc.getType() == UnitType.MAGE && uc.getInfo().getLevel() == 4) myDamage = uc.getInfo().getStat(UnitStat.ATTACK);
                    else myDamage = (uc.getInfo().getStat(UnitStat.ATTACK) - unit.getStat(UnitStat.DEFENSE)) * onForest;
                    if (myDamage > unit.getHealth()) canKill = true;
                }
                else if((canKill && unitToAttack.getHealth() < unit.getHealth())) {
                    float onForest = 1f;
                    if (uc.senseTileTypeAtLocation(unit.getLocation()) == TileType.FOREST) onForest = .8f;
                    float myDamage = 0;
                    if (uc.getType() == UnitType.MAGE && uc.getInfo().getLevel() == 4) myDamage = uc.getInfo().getStat(UnitStat.ATTACK);
                    else myDamage = (uc.getInfo().getStat(UnitStat.ATTACK) - unit.getStat(UnitStat.DEFENSE)) * onForest;
                    if (myDamage > unit.getHealth()) {
                        unitToAttack = unit;
                    }
                }
            }
            return unitToAttack;
        }

        public ShrineInfo getGenericBestShrineToAttack(ShrineInfo[] units) {

            ShrineInfo unitToAttack = null;
            for (ShrineInfo unit: units) {
                //skip if we can't attack
                if (!uc.canAttack(unit.getLocation())) continue;
                // we choose the unit with the least health!
                // there is a lot to improve here, go on and try :)
                if((unitToAttack == null || unitToAttack.getHealth() > unit.getHealth()) && unit.getOwner() != myTeam) {
                    unitToAttack = unit;
                }
                else if (unit.getOwner() == myTeam && unit.getInfluence() < GameConstants.MAX_INFLUENCE_SHRINES) {
                    if (unitToAttack == null) {
                        unitToAttack = unit;
                    }
                    else if (unitToAttack.getOwner() == myTeam) {
                        if (unit.getInfluence() < unitToAttack.getInfluence()) unitToAttack = unit;
                    }
                }
            }
            return unitToAttack;
        }

        public boolean doMicro() {

            // initialize the micros
            MicroInfo[] microInfos = new MicroInfo[Direction.values().length];
            for (int i = 0; i < 9; i++) microInfos[i] = new MicroInfo(Direction.values()[i]);

            // update micros for each enemy
            UnitInfo[] enemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.VISION_RANGE), myTeam, true);
            for(UnitInfo enemy: enemies) {
                for(MicroInfo mi : microInfos) {
                    if (!mi.canMove) continue;
                    mi.update(enemy);
                }
            }

            // TODO: update micros for each shrine (and delete check below)

            // choose best micro
            int bestIndex = -1;
            for (int i = 8; i >= 0; i--) {
                if (bestIndex < 0 || !microInfos[bestIndex].imBetterThan(microInfos[i])) {
                    if (bestIndex >= 0) uc.println(microInfos[i].loc + "is better than " + microInfos[bestIndex].loc);
                    bestIndex = i;
                }
            }

            // should micro
            if (!(microInfos[bestIndex].dir.equals(Direction.ZERO) && microInfos[bestIndex].myDPS < .000001)) { // a shrine should count? TODO: should not micro if no unobstructed enemies
                // fleeing combat attack
                if(uc.canAttack() && microInfos[bestIndex].enemyDPS == 0) {
                    tryGenericAttack();
                }
                // move
                if (uc.canMove(microInfos[bestIndex].dir)) {
                    uc.move(microInfos[bestIndex].dir);
                }
                uc.println("microing to " + microInfos[bestIndex].loc + " mydps: " + microInfos[bestIndex].myDPS + " enemydps: " + microInfos[bestIndex].enemyDPS);
                return true; // since vision can be much larger than attack range, we should sometimes keep moving
            }

            // don't move if shrine in attacking range, i guess
            ShrineInfo[] shrines = uc.senseShrines((int)uc.getInfo().getStat(UnitStat.ATTACK_RANGE));
            for (ShrineInfo unit: shrines) {
                if(unit.getOwner() != uc.getTeam() && uc.getLocation().distanceSquared(unit.getLocation()) <= uc.getInfo().getStat(UnitStat.ATTACK_RANGE) && !uc.isObstructed(uc.getLocation(), unit.getLocation())) {
                    //uc.println("microing in place");
                    return true;
                }
                if(unit.getOwner() == uc.getTeam() && uc.getLocation().distanceSquared(unit.getLocation()) <= uc.getInfo().getStat(UnitStat.ATTACK_RANGE) && !uc.isObstructed(uc.getLocation(), unit.getLocation()) && unit.getInfluence() < GameConstants.MAX_INFLUENCE_SHRINES) {
                    //uc.println("microing in place");
                    return true;
                }
            }

            // no micro
            return false;
        }


    /*
        Helper class used to store all relevant information about a given location. It includes a custom comparator to find
        which is the best location to go according to our micro.
     */

        public class MicroInfo {
            float enemyDPS;
            float myDPS;
            int minDistEnemy;
            Location loc;
            Direction dir;
            boolean canMove;

            public MicroInfo(Direction dir) {
                this.dir = dir;
                canMove = uc.canMove(dir);
                loc = uc.getLocation().add(dir);
                enemyDPS = 0;
                myDPS = 0;
                minDistEnemy = Integer.MAX_VALUE;
            }

            void update(UnitInfo unit) {
                Location enemyLocation = unit.getLocation();
                int d = enemyLocation.distanceSquared(loc);
                if (d <= unit.getStat(UnitStat.ATTACK_RANGE) && d >= unit.getStat(UnitStat.MIN_ATTACK_RANGE) && !uc.isObstructed(loc, enemyLocation, enemyTeam)) {
                    float imOnForest = 1f;
                    if (uc.senseTileTypeAtLocation(loc) == TileType.FOREST) {
                        if (uc.getType() == UnitType.RANGER && uc.getInfo().getLevel() == 4) imOnForest = 0.5f;
                        else imOnForest = 0.8f; // if im on forest ill take 20% less dmg.
                    }
                    if (comm.task == 2 && unit.getType() == UnitType.BASE) enemyDPS += 0;
                    else enemyDPS += unit.getStat(UnitStat.ATTACK)*imOnForest / (unit.getStat(UnitStat.ATTACK_COOLDOWN)); // if an enemy is in range to attack me, add their dps

                }
                if (d < minDistEnemy) minDistEnemy = d;
                // TODO: consider if the enemy im attacking is on forest. base on attack function, and include defense, etc.
                if (myDPS < .0000001 && ((canAttack() && !uc.isObstructed(loc, enemyLocation)) || (comm.task != 2 && canAttackFromCurr(enemyLocation)))) { // if i can attack something, count my dps TODO: theres really a lot more that goes into dps...
                    myDPS = uc.getInfo().getStat(UnitStat.ATTACK) / (uc.getInfo().getStat(UnitStat.ATTACK_COOLDOWN)) ;
                }
            }

            boolean canAttack() {
                float minattack = uc.getInfo().getStat(UnitStat.MIN_ATTACK_RANGE);
                if (uc.getType() == UnitType.MAGE) {
                    if (uc.getInfo().getCurrentAttackCooldown() >= 3) return false; // safe
                    minattack = 2f;
                }
                return uc.getInfo().getStat(UnitStat.ATTACK_RANGE) >= minDistEnemy && minattack <= minDistEnemy;
            }

            boolean canAttackFromCurr(Location loc) {
                float minattack = uc.getInfo().getStat(UnitStat.MIN_ATTACK_RANGE);
                if (uc.getType() == UnitType.MAGE) minattack = 2f;
                return uc.canAttack() && uc.getInfo().getStat(UnitStat.ATTACK_RANGE) >= uc.getLocation().distanceSquared(loc) && minattack <= uc.getLocation().distanceSquared(loc) && !uc.isObstructed(uc.getLocation(), loc);
            }

            //custom comparison method
            boolean imBetterThan(MicroInfo other) {
                if (!other.canMove) return true;
                if (!isSafeFromBaseMicro(other.loc)) {
                    return true;
                }
                else {
                    if (!isSafeFromBaseMicro(loc)) return false;
                }
                float dpsDiff = myDPS - enemyDPS;
                float mdpsDiff = other.myDPS - other.enemyDPS;
                //uc.println(other.loc + " dpsDiff: " + dpsDiff + " mdpsDiff: " + mdpsDiff);
                if (dpsDiff > .0000001) { // im a fight
                    if (dpsDiff - mdpsDiff > .0000001) return true; // im more winning
                    if (dpsDiff - mdpsDiff < -.0000001) return false; // im less winning
                    if (comm.task == 2 && other.enemyDPS == 0) return minDistEnemy < other.minDistEnemy; // crunching
                    if (minDistEnemy == other.minDistEnemy) return loc.distanceSquared(comm.baseLoc) < other.loc.distanceSquared(comm.baseLoc);
                    return minDistEnemy > other.minDistEnemy;
                }
                // losing fight
                else if (dpsDiff < -.0000001){
                    if (dpsDiff - mdpsDiff > .0000001) {
                        return true; // less losing than m
                    }
                    else if (dpsDiff - mdpsDiff < -.0000001) {
                        return false; // more losing than m
                    }
                    // even combat score
                    if (minDistEnemy == other.minDistEnemy) return loc.distanceSquared(comm.baseLoc) < other.loc.distanceSquared(comm.baseLoc);
                    return minDistEnemy > other.minDistEnemy;
                }
                // no fight or even fight (dpsDiff == 0)
                else {
                    // im an even fight
                    if (myDPS > 0) {
                        if (mdpsDiff < -.0000001) { // m is losing
                            return true;
                        }
                        else if (mdpsDiff > .0000001) { // m is winning
                            return false;
                        }
                        else if (other.myDPS > 0) { // m is an even fight
                            if (minDistEnemy == other.minDistEnemy) return loc.distanceSquared(comm.baseLoc) < other.loc.distanceSquared(comm.baseLoc);
                            return minDistEnemy > other.minDistEnemy;
                        }
                        else { // m is not a fight
                            return true;
                        }
                    }
                    /*else if (other.myDPS > 0) { // m is an even fight
                        return false;
                    }
                    // even combat score
                    return minDistEnemy >= other.minDistEnemy; // max mindist
                     */
                    return true;
                }
            }
        }

        boolean isSafeFromBaseMicro (Location loc) {
            if (comm.baseLocEnemy == null) return true;
            if (comm.task == 2) return true;
            return loc.distanceSquared(comm.baseLocEnemy) > UnitType.BASE.getStat(UnitStat.ATTACK_RANGE) || (uc.canSenseLocation(comm.baseLocEnemy) && uc.isObstructed(loc, comm.baseLocEnemy, uc.getOpponent())); // what if obstructed? i guess we don't care
        }
    }
}
