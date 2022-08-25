package magicarrows;

import aic2022.user.*;

public class Barbarian extends MyUnit {
    Direction[] allDirections = Direction.values();

    Team myTeam, enemyTeam;
    Location nearestLoggedEnemy;

    Barbarian(UnitController uc){
        super(uc);
        myTeam = uc.getTeam();
        enemyTeam = uc.getOpponent();
    }

    void play(){
        Location newNearest = comm.getLoggedEnemies();
        if (newNearest != null) {
            nearestLoggedEnemy = newNearest;
        }

        if (!rushEnemy() && !path.doMicro()) {
            tryMove();
        }
        tryOpenChest();
        tryAttack();
        seekEnemyBase();
        useArtifacts();
    }

    boolean isSafeFromBase(Location loc) {
        if (comm.baseLocEnemy == null) return true;
        return loc.distanceSquared(comm.baseLocEnemy) > UnitType.BASE.getStat(UnitStat.ATTACK_RANGE); // what if obstructed? i guess we don't care
    }

    // rush archers TODO: optimize movement
    boolean rushEnemy() {
        UnitInfo[] enemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.VISION_RANGE), myTeam, true);
        Location myLoc = uc.getLocation();
        Location nearestRanger = null;
        int bestDist = 10000;
        for (UnitInfo enemy : enemies) {
            if (enemy.getType() != UnitType.RANGER) return false;
            if (nearestRanger == null) {
                nearestRanger = enemy.getLocation();
                bestDist = myLoc.distanceSquared(nearestRanger);
            }
            else if (myLoc.distanceSquared(enemy.getLocation()) < bestDist) {
                nearestRanger = enemy.getLocation();
                bestDist = myLoc.distanceSquared(nearestRanger);
            }
        }
        if (nearestRanger == null || !isSafeFromBase(nearestRanger)) return false;
        path.move(nearestRanger);
        return true;
    }

    void useArtifacts() {
        ArtifactInfo[] artifacts = uc.getArtifacts();
        if (artifacts.length == 0) return;
        if (uc.canUseArtifact(0)) {
            uc.useArtifact(0);
            uc.println("used an artifact");
            return;
        }
        tryGiveArtifact(0);
    }

    // idk just randomly toss it around
    boolean tryGiveArtifact(int index) {
        for (Direction dir : allDirections) {
            if (dir.isEqual(Direction.ZERO)) continue;
            if (!(uc.canGiveArtifact(index, dir))) continue;
            uc.giveArtifact(index, dir);
            uc.println("gave an artifact");
            return true;
        }
        return false;
    }

    void seekEnemyBase() {
        if (comm.baseLocEnemy != null) return;
        UnitInfo[] units = uc.senseUnits(enemyTeam);
        for (UnitInfo u : units) {
            if (u.getType() == UnitType.BASE) {
                comm.writeEnemyBase(u.getLocation());
                break;
            }
        }
    }

    void tryOpenChest() {
        Location myLoc = uc.getLocation();
        for (Direction dir : allDirections) {
            Location prospect = myLoc.add(dir);
            if (!(uc.canSenseLocation(prospect))) continue;
            ChestInfo chest = uc.senseChestAtLocation(prospect);
            if (chest == null) continue;
            // for now, open the first chest we find
            if (uc.canOpenChest(dir)) {
                uc.openChest(dir);
                break;
            }
        }

    }

    void tryMove() {
        Location loc = null;
        if (comm.task == 1) loc = comm.baseLoc;
        if (comm.task == 2) loc = comm.baseLocEnemy;
        if (loc == null) loc = getClosestInterest(); // if too far from HQ, don't bother TODO: can get stuck if obstructed
        if (loc == null) {
            loc = nearestLoggedEnemy;
            if (loc != null) uc.println(nearestLoggedEnemy);
            if (loc != null && uc.canSenseLocation(loc) && uc.senseUnitAtLocation(loc) == null) {
                loc = null;
                nearestLoggedEnemy = null;
                uc.println("the nearest logged unit was cleared");
            }
        }
        if (loc == null) loc = explore.getExploreTarget();
        if (loc == null) loc = comm.baseLoc; // TODO: ultimately, this should be last enemy seen. we should look at the queue
        path.move(loc);
    }

    void tryAttack() {
        if (!uc.canAttack()) return;
        path.bugNav.tryGenericAttack();
    }

    Location getClosestInterest(){
        Location myLoc = uc.getLocation();
        // prioritize shrines
        ShrineInfo bestShrine = null;
        int bestShrineDist = 10000;
        ShrineInfo[] shrines = uc.senseShrines();
        for (ShrineInfo s : shrines) {
            if (s.getOwner() != myTeam) {
                int dist = myLoc.distanceSquared(s.getLocation());
                if (bestShrine == null) {
                    bestShrine = s;
                    bestShrineDist = dist;
                }
                else if (dist < bestShrineDist) {
                    bestShrine = s;
                    bestShrineDist = dist;
                }
            }
        }
        if (bestShrine != null) return bestShrine.getLocation();

        // then chests TODO: dungeons. consider entering only if no enemies are nearby and sufficient rounds
        Location bestChest = null;
        int bestDist = 10000;
        ChestInfo[] chests = uc.senseChests();
        for (ChestInfo chest : chests) {
            int dist = myLoc.distanceSquared(chest.getLocation());
            if (bestChest == null) {
                bestChest = chest.getLocation();
                bestDist = dist;
            }
            else if (dist < bestDist) {
                bestChest = chest.getLocation();
                bestDist = dist;
            }
        }
        return bestChest;
    }
}
