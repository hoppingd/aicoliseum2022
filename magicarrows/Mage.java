package magicarrows;

import aic2022.user.*;

public class Mage extends MyUnit {
        Direction[] allDirections = Direction.values();

        Team myTeam, enemyTeam;
        Location nearestLoggedEnemy;

        Mage(UnitController uc){
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
            tryImplode();
            swapToSafety();
            tryImplode();
        }

        void tryImplode() {
            Location myLoc = uc.getLocation();
            // if level 3 mage with low hp adjacent to enemy, implode
            if (uc.getType() == UnitType.MAGE && uc.getInfo().getHealth() < 17) {
                UnitInfo[] adjacentEnemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.VISION_RANGE), myTeam, true);
                if (adjacentEnemies.length > 0 && uc.canUseSecondAbility(myLoc)) uc.useSecondAbility(myLoc);
            }
        }

        void swapToSafety() {
            uc.println("swapping to safety!");
            if (uc.getInfo().getCurrentAbilityICooldown() >= 1) return;
            Location myLoc = uc.getLocation();
            UnitInfo[] swapUnits = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.ABILITY_1_RANGE));
            UnitInfo[] enemies = uc.senseUnits((int)uc.getInfo().getStat(UnitStat.VISION_RANGE), myTeam, true);
            int bestDamage = 0;
            Location swapTarget = null;
            for (UnitInfo enemy : enemies) {
                Location enemyLoc = enemy.getLocation();
                if (enemyLoc.distanceSquared(myLoc) <= enemy.getStat(UnitStat.ATTACK_RANGE) && enemyLoc.distanceSquared(myLoc) >= enemy.getStat(UnitStat.MIN_ATTACK_RANGE) && !uc.isObstructed(enemyLoc, myLoc, enemyTeam)) {
                    float imOnForest = 1f;
                    if (uc.senseTileTypeAtLocation(myLoc) == TileType.FOREST) {
                        imOnForest = 0.8f; // if im on forest ill take 20% less dmg.
                    }
                    bestDamage += enemy.getStat(UnitStat.ATTACK)*imOnForest / enemy.getStat(UnitStat.ATTACK_COOLDOWN); // if an enemy is in range to attack me, add their dps
                }
            }
            uc.println("dmg from home: " + bestDamage);
            for (UnitInfo unit : swapUnits) {
                Location unitLoc = unit.getLocation();
                int damageToTake = 0;
                if (!uc.canUseFirstAbility(unitLoc)) continue;
                for (UnitInfo enemy : enemies) {
                    Location enemyLoc = enemy.getLocation();
                    if (enemyLoc.isEqual(unitLoc)) {
                        if (enemyLoc.distanceSquared(myLoc) <= enemy.getStat(UnitStat.ATTACK_RANGE) && enemyLoc.distanceSquared(myLoc) >= enemy.getStat(UnitStat.MIN_ATTACK_RANGE) && !uc.isObstructed(enemyLoc, myLoc, enemyTeam)) {
                            float imOnForest = 1f;
                            if (uc.senseTileTypeAtLocation(myLoc) == TileType.FOREST) {
                                imOnForest = 0.8f; // if im on forest ill take 20% less dmg.
                            }
                            damageToTake += enemy.getStat(UnitStat.ATTACK)*imOnForest / enemy.getStat(UnitStat.ATTACK_COOLDOWN); // if an enemy is in range to attack me, add their dps
                            uc.println("swapping with enemy");
                        }
                    }
                    else if (enemyLoc.distanceSquared(unitLoc) <= enemy.getStat(UnitStat.ATTACK_RANGE) && enemyLoc.distanceSquared(unitLoc) >= enemy.getStat(UnitStat.MIN_ATTACK_RANGE) && !uc.isObstructed(enemyLoc, unitLoc, enemyTeam)) {
                        float imOnForest = 1f;
                        if (uc.senseTileTypeAtLocation(unitLoc) == TileType.FOREST) {
                            imOnForest = 0.8f; // if im on forest ill take 20% less dmg.
                        }
                        damageToTake += enemy.getStat(UnitStat.ATTACK)*imOnForest / enemy.getStat(UnitStat.ATTACK_COOLDOWN); // if an enemy is in range to attack me, add their dps
                    }
                }
                uc.println("dmg from " + unitLoc + ": " + damageToTake);
                if (damageToTake < bestDamage) {
                    swapTarget = unitLoc;
                    bestDamage = damageToTake;
                }
            }
            if (swapTarget != null && uc.canUseFirstAbility(swapTarget)) {
                uc.println("swapping to " + swapTarget);
                uc.useFirstAbility(swapTarget);
            }
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

        void tryLevel() {
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

