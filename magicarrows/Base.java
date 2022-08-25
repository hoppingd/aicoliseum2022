package magicarrows;

import aic2022.user.*;

public class Base extends MyUnit {

    final int CRUNCH_ROUND = 1300;
    final int CRUNCH_REP = 4000;

    Direction[] allDirections = Direction.values();
    int spawnCount = 0;

    Team myTeam, enemyTeam;

    Base(UnitController uc){
        super(uc);
        myTeam = uc.getTeam();
        enemyTeam = uc.getOpponent();
    }

    void play(){
        path.bugNav.tryGenericAttack();
        checkForEmergency();
        tryBuild();
    }

    void checkForEmergency() {
        if (comm.task == 2) return;
        UnitInfo[] nearbyUnits = uc.senseUnits(32, enemyTeam);
        Location myLoc = uc.getLocation();
        for (UnitInfo u : nearbyUnits) {
            if (!uc.isObstructed(myLoc, u.getLocation(), enemyTeam)) {
                comm.setTask(1);
                return;
            }
        }
        if (comm.task == 1) comm.setTask(0);
        int enemyRep = uc.getReputation(enemyTeam);
        if (enemyRep > uc.getReputation(myTeam) && (enemyRep > CRUNCH_REP || uc.getRound() >= CRUNCH_ROUND)) comm.setTask(2);
    }

    boolean shouldBuildKnight() {
        return spawnCount == 1 || spawnCount % 10 == 0;
    }

    boolean shouldBuildRanger() {
        return spawnCount % 4 != 0;
    }

    boolean shouldBuildMage() {
        return spawnCount % 4 == 0;
    }

    boolean shouldBuildExplorer() {
        return spawnCount == 0;
    }

    boolean tryBuild() {
        //if (!uc.isActionReady()) return false;
        Location myLoc = uc.getLocation();
        Location bestLoc = null;
        int bestDps = 10000;
        UnitInfo[] enemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.VISION_RANGE), myTeam, true);
        if(shouldBuildExplorer()) {
            for (Direction dir : allDirections) {
                if (uc.canSpawn(UnitType.EXPLORER, dir)) {
                    Location spawnLoc = myLoc.add(dir);
                    int myDps = 0;
                    for (UnitInfo enemy : enemies) {
                        if (spawnLoc.distanceSquared(enemy.getLocation()) <= enemy.getStat(UnitStat.ATTACK_RANGE) && !uc.isObstructed(spawnLoc, enemy.getLocation(), enemyTeam)) {
                            myDps += enemy.getStat(UnitStat.ATTACK) / enemy.getStat(UnitStat.ATTACK_COOLDOWN);
                        }
                    }
                    if (bestLoc == null) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                    else if (myDps < bestDps) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                }
            }
            if (bestLoc != null) {
                uc.spawn(UnitType.EXPLORER, myLoc.directionTo(bestLoc));
                spawnCount++;
                return true;
            }
        }
        else if(shouldBuildKnight()) {
            for (Direction dir : allDirections) {
                if (uc.canSpawn(UnitType.KNIGHT, dir)) {
                    Location spawnLoc = myLoc.add(dir);
                    int myDps = 0;
                    for (UnitInfo enemy : enemies) {
                        if (spawnLoc.distanceSquared(enemy.getLocation()) <= enemy.getStat(UnitStat.ATTACK_RANGE) && !uc.isObstructed(spawnLoc, enemy.getLocation(), enemyTeam)) {
                            myDps += enemy.getStat(UnitStat.ATTACK) / enemy.getStat(UnitStat.ATTACK_COOLDOWN);
                        }
                    }
                    if (bestLoc == null) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                    else if (myDps < bestDps) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                }
            }
            if (bestLoc != null) {
                uc.spawn(UnitType.KNIGHT, myLoc.directionTo(bestLoc));
                spawnCount++;
                return true;
            }
        }
        else if(shouldBuildRanger()) {
            for (Direction dir : allDirections) {
                if (uc.canSpawn(UnitType.RANGER, dir)) {
                    Location spawnLoc = myLoc.add(dir);
                    int myDps = 0;
                    for (UnitInfo enemy : enemies) {
                        if (spawnLoc.distanceSquared(enemy.getLocation()) <= enemy.getStat(UnitStat.ATTACK_RANGE) && !uc.isObstructed(spawnLoc, enemy.getLocation(), enemyTeam)) {
                            myDps += enemy.getStat(UnitStat.ATTACK) / enemy.getStat(UnitStat.ATTACK_COOLDOWN);
                        }
                    }
                    if (bestLoc == null) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                    else if (myDps < bestDps) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                }
            }
            if (bestLoc != null) {
                uc.spawn(UnitType.RANGER, myLoc.directionTo(bestLoc));
                spawnCount++;
                return true;
            }
        }
        else if (shouldBuildMage()){
            for (Direction dir : allDirections) {
                if (uc.canSpawn(UnitType.MAGE, dir)) {
                    Location spawnLoc = myLoc.add(dir);
                    int myDps = 0;
                    for (UnitInfo enemy : enemies) {
                        if (spawnLoc.distanceSquared(enemy.getLocation()) <= enemy.getStat(UnitStat.ATTACK_RANGE) && !uc.isObstructed(spawnLoc, enemy.getLocation(), enemyTeam)) {
                            myDps += enemy.getStat(UnitStat.ATTACK) / enemy.getStat(UnitStat.ATTACK_COOLDOWN);
                        }
                    }
                    if (bestLoc == null) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                    else if (myDps < bestDps) {
                        bestLoc = spawnLoc;
                        bestDps = myDps;
                    }
                }
            }
            if (bestLoc != null) {
                uc.spawn(UnitType.MAGE, myLoc.directionTo(bestLoc));
                spawnCount++;
                return true;
            }
        }

        return false;
    }
}
