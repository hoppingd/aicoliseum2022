package magicarrows;

import aic2022.user.*;

public class Ranger extends MyUnit {
    Direction[] allDirections = Direction.values();

    Team myTeam, enemyTeam;
    Location nearestLoggedEnemy;

    Ranger(UnitController uc){
        super(uc);
        myTeam = uc.getTeam();
        enemyTeam = uc.getOpponent();
    }

    void play(){
        Location newNearest = comm.getLoggedEnemies();
        tryLevel();
        if (newNearest != null) {
            nearestLoggedEnemy = newNearest;
        }

        if (!bfs.doMicro() && !fleeNeutral()) {
            tryMove();
        }
        tryOpenChest();
        tryAttack();
        seekEnemyBase();
        useArtifacts();
        tangle();
        forest();
    }

    boolean fleeNeutral() {
        // lure neutrals to base
        UnitInfo[] neutrals = uc.senseUnits(Team.NEUTRAL);
        for (UnitInfo unit : neutrals) {
            if (uc.getLocation().distanceSquared(unit.getLocation()) <= unit.getStat(UnitStat.VISION_RANGE) && !uc.isObstructed(uc.getLocation(), unit.getLocation(), enemyTeam)) {
                bfs.move(comm.baseLoc);
                return true;
            }
        }
        return false;
    }

    void forest() {
        if (uc.getInfo().getLevel() < 3) return;
        if (uc.getInfo().getCurrentAbilityIICooldown() >= 1) return;
        Location myLoc = uc.getLocation();
        if (uc.senseTileTypeAtLocation(myLoc) != TileType.FOREST) {
            if (uc.canUseSecondAbility(myLoc)) {
                uc.useSecondAbility(myLoc);
                return;
            }
        }
        for (Direction dir : allDirections) {
            Location prospect = myLoc.add(dir);
            if (!uc.isOutOfMap(prospect)) {
                TileType t = uc.senseTileTypeAtLocation(prospect);
                if (t == TileType.PLAINS) {
                    if (uc.canUseSecondAbility(prospect)) {
                        uc.useSecondAbility(prospect);
                        return;
                    }
                }
            }
        }

    }

    void tangle() {
        if (uc.getInfo().getLevel() < 2 || uc.getInfo().getCurrentAbilityICooldown() >= 1) return;
        Location myLoc = uc.getLocation();
        UnitInfo[] enemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.ABILITY_1_RANGE), myTeam, true);
        Location bestLoc = null;
        float bestDist = 10000;
        for (UnitInfo enemy : enemies) {
            Location enemyLoc = enemy.getLocation();
            if (uc.isObstructed(myLoc, enemyLoc) || enemy.getType() == UnitType.BASE) continue;
            float d = myLoc.distanceSquared(enemyLoc) * enemy.getType().getStat(UnitStat.MOVEMENT_COOLDOWN); // account for movement speed
            if (bestLoc == null) {
                bestLoc = enemyLoc;
                bestDist = d;
            }
            else if (d < bestDist) {
                bestLoc = enemyLoc;
                bestDist = d;
            }
        }
        if (bestLoc != null) {
            uc.drawLineDebug(myLoc, bestLoc, 50,50,50);
            uc.useFirstAbility(bestLoc);
        }
    }

    void tryLevel() {
        if (uc.getInfo().getLevel() == 2) return; // only go to lvl 2
        if (uc.getInfo().getHealth() < uc.getInfo().getStat(UnitStat.MAX_HEALTH) * .5) return;
        UnitInfo[] enemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.VISION_RANGE), myTeam, true);
        if (enemies.length == 0) {
            if (uc.getInfo().getLevel() < 2 || uc.getReputation() > 100) {
                if (uc.canLevelUp()) {
                    uc.levelUp();
                }
            }
        }
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
        if (comm.task == 2) {
            loc = comm.baseLocEnemy;
            if (loc == null) loc = explore.getExploreTarget(); // can specify possible hq locations
        }
        if (loc == null) loc = getClosestInterest(); // if too far from HQ, don't bother
        if (loc == null) {
            loc = nearestLoggedEnemy;
            if (loc != null && uc.canSenseLocation(loc)) {
                UnitInfo u = uc.senseUnitAtLocation(loc);
                if (u == null || u.getTeam() == myTeam) {
                    loc = null;
                    nearestLoggedEnemy = null;
                }
            }
        }
        if (loc == null) loc = explore.getExploreTarget();
        if (loc == null) loc = comm.baseLocEnemy;
        if (loc == null) loc = comm.baseLoc;
        bfs.move(loc);
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
            if (uc.isObstructed(myLoc, s.getLocation())) continue; // dont path to obstructed shrine
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
