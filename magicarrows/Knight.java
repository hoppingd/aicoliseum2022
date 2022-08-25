package magicarrows;

import aic2022.user.*;

import java.util.ArrayList;
import java.util.List;

public class Knight extends MyUnit {

    Direction[] allDirections = Direction.values();

    Team myTeam, enemyTeam;
    Location nearestLoggedEnemy;
    List<Location> dungeons = new ArrayList<>();
    boolean isInDungeon = false;
    Location dungeonExit;

    Knight(UnitController uc){
        super(uc);
        myTeam = uc.getTeam();
        enemyTeam = uc.getOpponent();
    }

    void play(){
        Location newNearest = comm.getLoggedEnemies();
        if (uc.getRound() % 400 == 0) {
            dungeons.clear();
        }
        //dungeon();
        if (newNearest != null) {
            nearestLoggedEnemy = newNearest;
        }
        if (!isInDungeon) {
            if (!rushEnemy() && !bfs.doMicro() && !fleeNeutral()) {
                tryMove();
            }
        }
        else {
            tryMove();
        }
        tryOpenChest();
        tryAttack();
        seekEnemyBase();
        useArtifacts();
    }

    boolean hasEntered(Location loc) {
        for (Location dungeon : dungeons) {
            if (dungeon.isEqual(loc)) return true;
        }
        return false;
    }

    void dungeon() {
        if (isInDungeon) return;
        Location myLoc = uc.getLocation();
        for (Direction dir : allDirections) {
            Location prospect = myLoc.add(dir);
            if (uc.isOutOfMap(prospect)) continue;
            if (uc.senseTileTypeAtLocation(prospect) != TileType.DUNGEON_ENTRANCE) continue;
            if (hasEntered(prospect)) continue;
            for(Direction dir2 : allDirections) {
                if (uc.canEnterDungeon(dir, dir2)) {
                    uc.enterDungeon(dir, dir2);
                    isInDungeon = true;
                    dungeons.add(prospect);
                    dungeonExit = uc.getLocation();
                    return;
                }
            }
        }
    }

    void tryExit() {
        if (!isInDungeon) return;
        Location myLoc = uc.getLocation();
        for (Direction dir : allDirections) {
            Location prospect = myLoc.add(dir);
            if (uc.isOutOfMap(prospect)) continue;
            if (uc.senseTileTypeAtLocation(prospect) != TileType.DUNGEON_ENTRANCE) continue;
            for(Direction dir2 : allDirections) {
                if (uc.canEnterDungeon(dir, dir2)) {
                    uc.enterDungeon(dir, dir2);
                    isInDungeon = false;
                    dungeonExit = null;
                    return;
                }
            }
        }
    }

    boolean isSafeFromBase(Location loc) {
        if (comm.baseLocEnemy == null) return true;
        return loc.distanceSquared(comm.baseLocEnemy) > UnitType.BASE.getStat(UnitStat.ATTACK_RANGE) || (uc.canSenseLocation(comm.baseLocEnemy) && !uc.isObstructed(loc, comm.baseLocEnemy, uc.getOpponent())); // what if obstructed? i guess we don't care
    }

    // rush archers TODO: optimize movement
    boolean rushEnemy() {
        UnitInfo[] enemies = uc.senseUnits(enemyTeam);
        Location myLoc = uc.getLocation();
        Location nearestRanger = null;
        int bestDist = 10000;
        for (UnitInfo enemy : enemies) {
            if (enemy.getType() != UnitType.RANGER) continue;
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
        bfs.move(nearestRanger);
        return true;
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
        if (loc == null) loc = getClosestInterest(); // if too far from HQ, don't bother TODO: can get stuck if obstructed
        if (loc == null && isInDungeon) loc = dungeonExit;
        if (loc == null && uc.getReputation(myTeam) < uc.getReputation(enemyTeam)) loc = explore.getExploreTarget();
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
        // then chests
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
        if (bestChest != null) return bestChest;

        // dungeons
        /*if (isInDungeon) {
            tryExit();
            return null;
        }

        Location[] tiles = uc.senseVisibleTiles(TileType.DUNGEON_ENTRANCE);
        Location bestDungeon = null;
        bestDist = 10000;
        for (Location tile : tiles) {
            if (hasEntered(tile)) continue; // i've already entered this cycle
            int dist = myLoc.distanceSquared(tile);
            if (bestDungeon == null) {
                bestDungeon = tile;
                bestDist = dist;
            }
            else if (dist < bestDist) {
                bestDungeon = tile;
                bestDist = dist;
            }
        }
        return bestDungeon;*/
        return null;
    }



}
