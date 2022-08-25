package magicarrows;

import aic2022.user.*;

public class BFSBarb extends BFS
{
    BFSBarb(UnitController uc, Exploration explore, Communication comm){
        super(uc, explore, comm);
    }

    Location l30;
    double v30;
    Direction d30;
    double p30;

    Location l31;
    double v31;
    Direction d31;
    double p31;

    Location l32;
    double v32;
    Direction d32;
    double p32;

    Location l33;
    double v33;
    Direction d33;
    double p33;

    Location l34;
    double v34;
    Direction d34;
    double p34;

    Location l42;
    double v42;
    Direction d42;
    double p42;

    Location l43;
    double v43;
    Direction d43;
    double p43;

    Location l44;
    double v44;
    Direction d44;
    double p44;

    Location l45;
    double v45;
    Direction d45;
    double p45;

    Location l46;
    double v46;
    Direction d46;
    double p46;

    Location l47;
    double v47;
    Direction d47;
    double p47;

    Location l48;
    double v48;
    Direction d48;
    double p48;

    Location l54;
    double v54;
    Direction d54;
    double p54;

    Location l55;
    double v55;
    Direction d55;
    double p55;

    Location l56;
    double v56;
    Direction d56;
    double p56;

    Location l57;
    double v57;
    Direction d57;
    double p57;

    Location l58;
    double v58;
    Direction d58;
    double p58;

    Location l59;
    double v59;
    Direction d59;
    double p59;

    Location l60;
    double v60;
    Direction d60;
    double p60;

    Location l61;
    double v61;
    Direction d61;
    double p61;

    Location l62;
    double v62;
    Direction d62;
    double p62;

    Location l67;
    double v67;
    Direction d67;
    double p67;

    Location l68;
    double v68;
    Direction d68;
    double p68;

    Location l69;
    double v69;
    Direction d69;
    double p69;

    Location l70;
    double v70;
    Direction d70;
    double p70;

    Location l71;
    double v71;
    Direction d71;
    double p71;

    Location l72;
    double v72;
    Direction d72;
    double p72;

    Location l73;
    double v73;
    Direction d73;
    double p73;

    Location l74;
    double v74;
    Direction d74;
    double p74;

    Location l75;
    double v75;
    Direction d75;
    double p75;

    Location l80;
    double v80;
    Direction d80;
    double p80;

    Location l81;
    double v81;
    Direction d81;
    double p81;

    Location l82;
    double v82;
    Direction d82;
    double p82;

    Location l83;
    double v83;
    Direction d83;
    double p83;

    Location l84;
    double v84;
    Direction d84;
    double p84;

    Location l85;
    double v85;
    Direction d85;
    double p85;

    Location l86;
    double v86;
    Direction d86;
    double p86;

    Location l87;
    double v87;
    Direction d87;
    double p87;

    Location l88;
    double v88;
    Direction d88;
    double p88;

    Location l93;
    double v93;
    Direction d93;
    double p93;

    Location l94;
    double v94;
    Direction d94;
    double p94;

    Location l95;
    double v95;
    Direction d95;
    double p95;

    Location l96;
    double v96;
    Direction d96;
    double p96;

    Location l97;
    double v97;
    Direction d97;
    double p97;

    Location l98;
    double v98;
    Direction d98;
    double p98;

    Location l99;
    double v99;
    Direction d99;
    double p99;

    Location l100;
    double v100;
    Direction d100;
    double p100;

    Location l101;
    double v101;
    Direction d101;
    double p101;

    Location l106;
    double v106;
    Direction d106;
    double p106;

    Location l107;
    double v107;
    Direction d107;
    double p107;

    Location l108;
    double v108;
    Direction d108;
    double p108;

    Location l109;
    double v109;
    Direction d109;
    double p109;

    Location l110;
    double v110;
    Direction d110;
    double p110;

    Location l111;
    double v111;
    Direction d111;
    double p111;

    Location l112;
    double v112;
    Direction d112;
    double p112;

    Location l113;
    double v113;
    Direction d113;
    double p113;

    Location l114;
    double v114;
    Direction d114;
    double p114;

    Location l120;
    double v120;
    Direction d120;
    double p120;

    Location l121;
    double v121;
    Direction d121;
    double p121;

    Location l122;
    double v122;
    Direction d122;
    double p122;

    Location l123;
    double v123;
    Direction d123;
    double p123;

    Location l124;
    double v124;
    Direction d124;
    double p124;

    Location l125;
    double v125;
    Direction d125;
    double p125;

    Location l126;
    double v126;
    Direction d126;
    double p126;

    Location l134;
    double v134;
    Direction d134;
    double p134;

    Location l135;
    double v135;
    Direction d135;
    double p135;

    Location l136;
    double v136;
    Direction d136;
    double p136;

    Location l137;
    double v137;
    Direction d137;
    double p137;

    Location l138;
    double v138;
    Direction d138;
    double p138;


    Direction getBestDir(Location target){
        l84 = uc.getLocation();
        v84 = 0;
        l85 = l84.add(Direction.NORTH);
        v85 = 1000000;
        d85 = null;
        l72 = l84.add(Direction.NORTHWEST);
        v72 = 1000000;
        d72 = null;
        l71 = l84.add(Direction.WEST);
        v71 = 1000000;
        d71 = null;
        l70 = l84.add(Direction.SOUTHWEST);
        v70 = 1000000;
        d70 = null;
        l83 = l84.add(Direction.SOUTH);
        v83 = 1000000;
        d83 = null;
        l96 = l84.add(Direction.SOUTHEAST);
        v96 = 1000000;
        d96 = null;
        l97 = l84.add(Direction.EAST);
        v97 = 1000000;
        d97 = null;
        l98 = l84.add(Direction.NORTHEAST);
        v98 = 1000000;
        d98 = null;
        l99 = l98.add(Direction.NORTH);
        v99 = 1000000;
        d99 = null;
        l86 = l99.add(Direction.WEST);
        v86 = 1000000;
        d86 = null;
        l73 = l86.add(Direction.WEST);
        v73 = 1000000;
        d73 = null;
        l60 = l73.add(Direction.WEST);
        v60 = 1000000;
        d60 = null;
        l59 = l60.add(Direction.SOUTH);
        v59 = 1000000;
        d59 = null;
        l58 = l59.add(Direction.SOUTH);
        v58 = 1000000;
        d58 = null;
        l57 = l58.add(Direction.SOUTH);
        v57 = 1000000;
        d57 = null;
        l56 = l57.add(Direction.SOUTH);
        v56 = 1000000;
        d56 = null;
        l69 = l56.add(Direction.EAST);
        v69 = 1000000;
        d69 = null;
        l82 = l69.add(Direction.EAST);
        v82 = 1000000;
        d82 = null;
        l95 = l82.add(Direction.EAST);
        v95 = 1000000;
        d95 = null;
        l108 = l95.add(Direction.EAST);
        v108 = 1000000;
        d108 = null;
        l109 = l108.add(Direction.NORTH);
        v109 = 1000000;
        d109 = null;
        l110 = l109.add(Direction.NORTH);
        v110 = 1000000;
        d110 = null;
        l111 = l110.add(Direction.NORTH);
        v111 = 1000000;
        d111 = null;
        l112 = l111.add(Direction.NORTH);
        v112 = 1000000;
        d112 = null;
        l100 = l112.add(Direction.NORTHWEST);
        v100 = 1000000;
        d100 = null;
        l87 = l100.add(Direction.WEST);
        v87 = 1000000;
        d87 = null;
        l74 = l87.add(Direction.WEST);
        v74 = 1000000;
        d74 = null;
        l61 = l74.add(Direction.WEST);
        v61 = 1000000;
        d61 = null;
        l47 = l61.add(Direction.SOUTHWEST);
        v47 = 1000000;
        d47 = null;
        l46 = l47.add(Direction.SOUTH);
        v46 = 1000000;
        d46 = null;
        l45 = l46.add(Direction.SOUTH);
        v45 = 1000000;
        d45 = null;
        l44 = l45.add(Direction.SOUTH);
        v44 = 1000000;
        d44 = null;
        l43 = l44.add(Direction.SOUTH);
        v43 = 1000000;
        d43 = null;
        l55 = l43.add(Direction.SOUTHEAST);
        v55 = 1000000;
        d55 = null;
        l68 = l55.add(Direction.EAST);
        v68 = 1000000;
        d68 = null;
        l81 = l68.add(Direction.EAST);
        v81 = 1000000;
        d81 = null;
        l94 = l81.add(Direction.EAST);
        v94 = 1000000;
        d94 = null;
        l107 = l94.add(Direction.EAST);
        v107 = 1000000;
        d107 = null;
        l121 = l107.add(Direction.NORTHEAST);
        v121 = 1000000;
        d121 = null;
        l122 = l121.add(Direction.NORTH);
        v122 = 1000000;
        d122 = null;
        l123 = l122.add(Direction.NORTH);
        v123 = 1000000;
        d123 = null;
        l124 = l123.add(Direction.NORTH);
        v124 = 1000000;
        d124 = null;
        l125 = l124.add(Direction.NORTH);
        v125 = 1000000;
        d125 = null;
        l113 = l125.add(Direction.NORTHWEST);
        v113 = 1000000;
        d113 = null;
        l101 = l113.add(Direction.NORTHWEST);
        v101 = 1000000;
        d101 = null;
        l88 = l101.add(Direction.WEST);
        v88 = 1000000;
        d88 = null;
        l75 = l88.add(Direction.WEST);
        v75 = 1000000;
        d75 = null;
        l62 = l75.add(Direction.WEST);
        v62 = 1000000;
        d62 = null;
        l48 = l62.add(Direction.SOUTHWEST);
        v48 = 1000000;
        d48 = null;
        l34 = l48.add(Direction.SOUTHWEST);
        v34 = 1000000;
        d34 = null;
        l33 = l34.add(Direction.SOUTH);
        v33 = 1000000;
        d33 = null;
        l32 = l33.add(Direction.SOUTH);
        v32 = 1000000;
        d32 = null;
        l31 = l32.add(Direction.SOUTH);
        v31 = 1000000;
        d31 = null;
        l30 = l31.add(Direction.SOUTH);
        v30 = 1000000;
        d30 = null;
        l42 = l30.add(Direction.SOUTHEAST);
        v42 = 1000000;
        d42 = null;
        l54 = l42.add(Direction.SOUTHEAST);
        v54 = 1000000;
        d54 = null;
        l67 = l54.add(Direction.EAST);
        v67 = 1000000;
        d67 = null;
        l80 = l67.add(Direction.EAST);
        v80 = 1000000;
        d80 = null;
        l93 = l80.add(Direction.EAST);
        v93 = 1000000;
        d93 = null;
        l106 = l93.add(Direction.EAST);
        v106 = 1000000;
        d106 = null;
        l120 = l106.add(Direction.NORTHEAST);
        v120 = 1000000;
        d120 = null;
        l134 = l120.add(Direction.NORTHEAST);
        v134 = 1000000;
        d134 = null;
        l135 = l134.add(Direction.NORTH);
        v135 = 1000000;
        d135 = null;
        l136 = l135.add(Direction.NORTH);
        v136 = 1000000;
        d136 = null;
        l137 = l136.add(Direction.NORTH);
        v137 = 1000000;
        d137 = null;
        l138 = l137.add(Direction.NORTH);
        v138 = 1000000;
        d138 = null;
        l126 = l138.add(Direction.NORTHWEST);
        v126 = 1000000;
        d126 = null;
        l114 = l126.add(Direction.NORTHWEST); // droid vision
        v114 = 1000000;
        d114 = null;


            if (!uc.isOutOfMap(l71)) {
                if (uc.canMove(Direction.WEST)) {
                    p71 = 10 + getWeight(l71);
                    if (v71 > v84 + p71) {
                        v71 = v84 + p71;
                        d71 = Direction.WEST;
                    }
                }
            }
            if (!uc.isOutOfMap(l83)) {
                if (uc.canMove(Direction.SOUTH)) {
                    p83 = 10 + getWeight(l83);
                    if (v83 > v84 + p83) {
                        v83 = v84 + p83;
                        d83 = Direction.SOUTH;
                    }
                    if (v83 > v71 + p83) {
                        v83 = v71 + p83;
                        d83 = d71;
                    }
                }
            }
            if (!uc.isOutOfMap(l85)) {
                if (uc.canMove(Direction.NORTH)) {
                    p85 = 10 + getWeight(l85);
                    if (v85 > v84 + p85) {
                        v85 = v84 + p85;
                        d85 = Direction.NORTH;
                    }
                    if (v85 > v71 + p85) {
                        v85 = v71 + p85;
                        d85 = d71;
                    }
                }
            }
            if (!uc.isOutOfMap(l97)) {
                if (uc.canMove(Direction.WEST)) {
                    p97 = 10 + getWeight(l97);
                    if (v97 > v84 + p97) {
                        v97 = v84 + p97;
                        d97 = Direction.EAST;
                    }
                    if (v97 > v85 + p97) {
                        v97 = v85 + p97;
                        d97 = d85;
                    }
                    if (v97 > v83 + p97) {
                        v97 = v83 + p97;
                        d97 = d83;
                    }
                }
            }
            if (!uc.isOutOfMap(l70)) {
                if (uc.canMove(Direction.SOUTHWEST)) {
                    p70 = 10 + getWeight(l70);
                    if (v70 > v84 + p70) {
                        v70 = v84 + p70;
                        d70 = Direction.SOUTHWEST;
                    }
                    if (v70 > v71 + p70) {
                        v70 = v71 + p70;
                        d70 = d71;
                    }
                    if (v70 > v83 + p70) {
                        v70 = v83 + p70;
                        d70 = d83;
                    }
                }
            }
            if (!uc.isOutOfMap(l72)) {
                if (uc.canMove(Direction.NORTHWEST)) {
                    p72 = 10 + getWeight(l72);
                    if (v72 > v84 + p72) {
                        v72 = v84 + p72;
                        d72 = Direction.NORTHWEST;
                    }
                    if (v72 > v71 + p72) {
                        v72 = v71 + p72;
                        d72 = d71;
                    }
                    if (v72 > v85 + p72) {
                        v72 = v85 + p72;
                        d72 = d85;
                    }
                }
            }
            if (!uc.isOutOfMap(l96)) {
                if (uc.canMove(Direction.SOUTHEAST)) {
                    p96 = 10 + getWeight(l96);
                    if (v96 > v84 + p96) {
                        v96 = v84 + p96;
                        d96 = Direction.SOUTHEAST;
                    }
                    if (v96 > v97 + p96) {
                        v96 = v97 + p96;
                        d96 = d97;
                    }
                    if (v96 > v83 + p96) {
                        v96 = v83 + p96;
                        d96 = d83;
                    }
                }
            }
            if (!uc.isOutOfMap(l98)) {
                if (uc.canMove(Direction.NORTHEAST)) {
                    p98 = 10 + getWeight(l98);
                    if (v98 > v84 + p98) {
                        v98 = v84 + p98;
                        d98 = Direction.NORTHEAST;
                    }
                    if (v98 > v85 + p98) {
                        v98 = v85 + p98;
                        d98 = d85;
                    }
                    if (v98 > v97 + p98) {
                        v98 = v97 + p98;
                        d98 = d97;
                    }
                }
            }
            if (!uc.isOutOfMap(l58)) {
                p58 = 10 + getWeight(l58);
                if (v58 > v71 + p58) {
                    v58 = v71 + p58;
                    d58 = d71;
                }
                if (v58 > v70 + p58) {
                    v58 = v70 + p58;
                    d58 = d70;
                }
                if (v58 > v72 + p58) {
                    v58 = v72 + p58;
                    d58 = d72;
                }
            }
            if (!uc.isOutOfMap(l82)) {
                p82 = 10 + getWeight(l82);
                if (v82 > v83 + p82) {
                    v82 = v83 + p82;
                    d82 = d83;
                }
                if (v82 > v70 + p82) {
                    v82 = v70 + p82;
                    d82 = d70;
                }
                if (v82 > v96 + p82) {
                    v82 = v96 + p82;
                    d82 = d96;
                }
            }
            if (!uc.isOutOfMap(l86)) {
                p86 = 10 + getWeight(l86);
                if (v86 > v85 + p86) {
                    v86 = v85 + p86;
                    d86 = d85;
                }
                if (v86 > v72 + p86) {
                    v86 = v72 + p86;
                    d86 = d72;
                }
                if (v86 > v98 + p86) {
                    v86 = v98 + p86;
                    d86 = d98;
                }
            }
            if (!uc.isOutOfMap(l110)) {
                p110 = 10 + getWeight(l110);
                if (v110 > v97 + p110) {
                    v110 = v97 + p110;
                    d110 = d97;
                }
                if (v110 > v98 + p110) {
                    v110 = v98 + p110;
                    d110 = d98;
                }
                if (v110 > v96 + p110) {
                    v110 = v96 + p110;
                    d110 = d96;
                }
            }
            if (!uc.isOutOfMap(l57)) {
                p57 = 10 + getWeight(l57);
                if (v57 > v71 + p57) {
                    v57 = v71 + p57;
                    d57 = d71;
                }
                if (v57 > v70 + p57) {
                    v57 = v70 + p57;
                    d57 = d70;
                }
                if (v57 > v58 + p57) {
                    v57 = v58 + p57;
                    d57 = d58;
                }
            }
            if (!uc.isOutOfMap(l59)) {
                p59 = 10 + getWeight(l59);
                if (v59 > v71 + p59) {
                    v59 = v71 + p59;
                    d59 = d71;
                }
                if (v59 > v72 + p59) {
                    v59 = v72 + p59;
                    d59 = d72;
                }
                if (v59 > v58 + p59) {
                    v59 = v58 + p59;
                    d59 = d58;
                }
            }
            if (!uc.isOutOfMap(l69)) {
                p69 = 10 + getWeight(l69);
                if (v69 > v83 + p69) {
                    v69 = v83 + p69;
                    d69 = d83;
                }
                if (v69 > v70 + p69) {
                    v69 = v70 + p69;
                    d69 = d70;
                }
                if (v69 > v82 + p69) {
                    v69 = v82 + p69;
                    d69 = d82;
                }
                if (v69 > v57 + p69) {
                    v69 = v57 + p69;
                    d69 = d57;
                }
            }
            if (!uc.isOutOfMap(l73)) {
                p73 = 10 + getWeight(l73);
                if (v73 > v85 + p73) {
                    v73 = v85 + p73;
                    d73 = d85;
                }
                if (v73 > v72 + p73) {
                    v73 = v72 + p73;
                    d73 = d72;
                }
                if (v73 > v86 + p73) {
                    v73 = v86 + p73;
                    d73 = d86;
                }
                if (v73 > v59 + p73) {
                    v73 = v59 + p73;
                    d73 = d59;
                }
            }
            if (!uc.isOutOfMap(l95)) {
                p95 = 10 + getWeight(l95);
                if (v95 > v83 + p95) {
                    v95 = v83 + p95;
                    d95 = d83;
                }
                if (v95 > v96 + p95) {
                    v95 = v96 + p95;
                    d95 = d96;
                }
                if (v95 > v82 + p95) {
                    v95 = v82 + p95;
                    d95 = d82;
                }
            }
            if (!uc.isOutOfMap(l99)) {
                p99 = 10 + getWeight(l99);
                if (v99 > v85 + p99) {
                    v99 = v85 + p99;
                    d99 = d85;
                }
                if (v99 > v98 + p99) {
                    v99 = v98 + p99;
                    d99 = d98;
                }
                if (v99 > v86 + p99) {
                    v99 = v86 + p99;
                    d99 = d86;
                }
            }
            if (!uc.isOutOfMap(l109)) {
                p109 = 10 + getWeight(l109);
                if (v109 > v97 + p109) {
                    v109 = v97 + p109;
                    d109 = d97;
                }
                if (v109 > v96 + p109) {
                    v109 = v96 + p109;
                    d109 = d96;
                }
                if (v109 > v110 + p109) {
                    v109 = v110 + p109;
                    d109 = d110;
                }
                if (v109 > v95 + p109) {
                    v109 = v95 + p109;
                    d109 = d95;
                }
            }
            if (!uc.isOutOfMap(l111)) {
                p111 = 10 + getWeight(l111);
                if (v111 > v97 + p111) {
                    v111 = v97 + p111;
                    d111 = d97;
                }
                if (v111 > v98 + p111) {
                    v111 = v98 + p111;
                    d111 = d98;
                }
                if (v111 > v110 + p111) {
                    v111 = v110 + p111;
                    d111 = d110;
                }
                if (v111 > v99 + p111) {
                    v111 = v99 + p111;
                    d111 = d99;
                }
            }
            if (!uc.isOutOfMap(l56)) {
                p56 = 10 + getWeight(l56);
                if (v56 > v70 + p56) {
                    v56 = v70 + p56;
                    d56 = d70;
                }
                if (v56 > v57 + p56) {
                    v56 = v57 + p56;
                    d56 = d57;
                }
                if (v56 > v69 + p56) {
                    v56 = v69 + p56;
                    d56 = d69;
                }
            }
            if (!uc.isOutOfMap(l60)) {
                p60 = 10 + getWeight(l60);
                if (v60 > v72 + p60) {
                    v60 = v72 + p60;
                    d60 = d72;
                }
                if (v60 > v59 + p60) {
                    v60 = v59 + p60;
                    d60 = d59;
                }
                if (v60 > v73 + p60) {
                    v60 = v73 + p60;
                    d60 = d73;
                }
            }
            if (!uc.isOutOfMap(l108)) {
                p108 = 10 + getWeight(l108);
                if (v108 > v96 + p108) {
                    v108 = v96 + p108;
                    d108 = d96;
                }
                if (v108 > v109 + p108) {
                    v108 = v109 + p108;
                    d108 = d109;
                }
                if (v108 > v95 + p108) {
                    v108 = v95 + p108;
                    d108 = d95;
                }
            }
            if (!uc.isOutOfMap(l112)) {
                p112 = 10 + getWeight(l112);
                if (v112 > v98 + p112) {
                    v112 = v98 + p112;
                    d112 = d98;
                }
                if (v112 > v99 + p112) {
                    v112 = v99 + p112;
                    d112 = d99;
                }
                if (v112 > v111 + p112) {
                    v112 = v111 + p112;
                    d112 = d111;
                }
            }
            if (!uc.isOutOfMap(l45)) {
                p45 = 10 + getWeight(l45);
                if (v45 > v58 + p45) {
                    v45 = v58 + p45;
                    d45 = d58;
                }
                if (v45 > v57 + p45) {
                    v45 = v57 + p45;
                    d45 = d57;
                }
                if (v45 > v59 + p45) {
                    v45 = v59 + p45;
                    d45 = d59;
                }
            }
            if (!uc.isOutOfMap(l81)) {
                p81 = 10 + getWeight(l81);
                if (v81 > v82 + p81) {
                    v81 = v82 + p81;
                    d81 = d82;
                }
                if (v81 > v69 + p81) {
                    v81 = v69 + p81;
                    d81 = d69;
                }
                if (v81 > v95 + p81) {
                    v81 = v95 + p81;
                    d81 = d95;
                }
            }
            if (!uc.isOutOfMap(l87)) {
                p87 = 10 + getWeight(l87);
                if (v87 > v86 + p87) {
                    v87 = v86 + p87;
                    d87 = d86;
                }
                if (v87 > v73 + p87) {
                    v87 = v73 + p87;
                    d87 = d73;
                }
                if (v87 > v99 + p87) {
                    v87 = v99 + p87;
                    d87 = d99;
                }
            }
            if (!uc.isOutOfMap(l123)) {
                p123 = 10 + getWeight(l123);
                if (v123 > v110 + p123) {
                    v123 = v110 + p123;
                    d123 = d110;
                }
                if (v123 > v111 + p123) {
                    v123 = v111 + p123;
                    d123 = d111;
                }
                if (v123 > v109 + p123) {
                    v123 = v109 + p123;
                    d123 = d109;
                }
            }
            if (!uc.isOutOfMap(l44)) {
                p44 = 10 + getWeight(l44);
                if (v44 > v58 + p44) {
                    v44 = v58 + p44;
                    d44 = d58;
                }
                if (v44 > v57 + p44) {
                    v44 = v57 + p44;
                    d44 = d57;
                }
                if (v44 > v56 + p44) {
                    v44 = v56 + p44;
                    d44 = d56;
                }
                if (v44 > v45 + p44) {
                    v44 = v45 + p44;
                    d44 = d45;
                }
            }
            if (!uc.isOutOfMap(l46)) {
                p46 = 10 + getWeight(l46);
                if (v46 > v58 + p46) {
                    v46 = v58 + p46;
                    d46 = d58;
                }
                if (v46 > v59 + p46) {
                    v46 = v59 + p46;
                    d46 = d59;
                }
                if (v46 > v60 + p46) {
                    v46 = v60 + p46;
                    d46 = d60;
                }
                if (v46 > v45 + p46) {
                    v46 = v45 + p46;
                    d46 = d45;
                }
            }
            if (!uc.isOutOfMap(l68)) {
                p68 = 10 + getWeight(l68);
                if (v68 > v82 + p68) {
                    v68 = v82 + p68;
                    d68 = d82;
                }
                if (v68 > v69 + p68) {
                    v68 = v69 + p68;
                    d68 = d69;
                }
                if (v68 > v56 + p68) {
                    v68 = v56 + p68;
                    d68 = d56;
                }
                if (v68 > v81 + p68) {
                    v68 = v81 + p68;
                    d68 = d81;
                }
            }
            if (!uc.isOutOfMap(l74)) {
                p74 = 10 + getWeight(l74);
                if (v74 > v86 + p74) {
                    v74 = v86 + p74;
                    d74 = d86;
                }
                if (v74 > v73 + p74) {
                    v74 = v73 + p74;
                    d74 = d73;
                }
                if (v74 > v60 + p74) {
                    v74 = v60 + p74;
                    d74 = d60;
                }
                if (v74 > v87 + p74) {
                    v74 = v87 + p74;
                    d74 = d87;
                }
            }
            if (!uc.isOutOfMap(l94)) {
                p94 = 10 + getWeight(l94);
                if (v94 > v82 + p94) {
                    v94 = v82 + p94;
                    d94 = d82;
                }
                if (v94 > v95 + p94) {
                    v94 = v95 + p94;
                    d94 = d95;
                }
                if (v94 > v108 + p94) {
                    v94 = v108 + p94;
                    d94 = d108;
                }
                if (v94 > v81 + p94) {
                    v94 = v81 + p94;
                    d94 = d81;
                }
            }
            if (!uc.isOutOfMap(l100)) {
                p100 = 10 + getWeight(l100);
                if (v100 > v86 + p100) {
                    v100 = v86 + p100;
                    d100 = d86;
                }
                if (v100 > v99 + p100) {
                    v100 = v99 + p100;
                    d100 = d99;
                }
                if (v100 > v112 + p100) {
                    v100 = v112 + p100;
                    d100 = d112;
                }
                if (v100 > v87 + p100) {
                    v100 = v87 + p100;
                    d100 = d87;
                }
            }
            if (!uc.isOutOfMap(l122)) {
                p122 = 10 + getWeight(l122);
                if (v122 > v110 + p122) {
                    v122 = v110 + p122;
                    d122 = d110;
                }
                if (v122 > v109 + p122) {
                    v122 = v109 + p122;
                    d122 = d109;
                }
                if (v122 > v108 + p122) {
                    v122 = v108 + p122;
                    d122 = d108;
                }
                if (v122 > v123 + p122) {
                    v122 = v123 + p122;
                    d122 = d123;
                }
            }
            if (!uc.isOutOfMap(l124)) {
                p124 = 10 + getWeight(l124);
                if (v124 > v110 + p124) {
                    v124 = v110 + p124;
                    d124 = d110;
                }
                if (v124 > v111 + p124) {
                    v124 = v111 + p124;
                    d124 = d111;
                }
                if (v124 > v112 + p124) {
                    v124 = v112 + p124;
                    d124 = d112;
                }
                if (v124 > v123 + p124) {
                    v124 = v123 + p124;
                    d124 = d123;
                }
            }
            if (!uc.isOutOfMap(l43)) {
                p43 = 10 + getWeight(l43);
                if (v43 > v57 + p43) {
                    v43 = v57 + p43;
                    d43 = d57;
                }
                if (v43 > v56 + p43) {
                    v43 = v56 + p43;
                    d43 = d56;
                }
                if (v43 > v44 + p43) {
                    v43 = v44 + p43;
                    d43 = d44;
                }
            }
            if (!uc.isOutOfMap(l47)) {
                p47 = 10 + getWeight(l47);
                if (v47 > v59 + p47) {
                    v47 = v59 + p47;
                    d47 = d59;
                }
                if (v47 > v60 + p47) {
                    v47 = v60 + p47;
                    d47 = d60;
                }
                if (v47 > v46 + p47) {
                    v47 = v46 + p47;
                    d47 = d46;
                }
            }
            if (!uc.isOutOfMap(l55)) {
                p55 = 10 + getWeight(l55);
                if (v55 > v69 + p55) {
                    v55 = v69 + p55;
                    d55 = d69;
                }
                if (v55 > v56 + p55) {
                    v55 = v56 + p55;
                    d55 = d56;
                }
                if (v55 > v68 + p55) {
                    v55 = v68 + p55;
                    d55 = d68;
                }
                if (v55 > v43 + p55) {
                    v55 = v43 + p55;
                    d55 = d43;
                }
            }
            if (!uc.isOutOfMap(l61)) {
                p61 = 10 + getWeight(l61);
                if (v61 > v73 + p61) {
                    v61 = v73 + p61;
                    d61 = d73;
                }
                if (v61 > v60 + p61) {
                    v61 = v60 + p61;
                    d61 = d60;
                }
                if (v61 > v74 + p61) {
                    v61 = v74 + p61;
                    d61 = d74;
                }
                if (v61 > v47 + p61) {
                    v61 = v47 + p61;
                    d61 = d47;
                }
            }
            if (!uc.isOutOfMap(l107)) {
                p107 = 10 + getWeight(l107);
                if (v107 > v95 + p107) {
                    v107 = v95 + p107;
                    d107 = d95;
                }
                if (v107 > v108 + p107) {
                    v107 = v108 + p107;
                    d107 = d108;
                }
                if (v107 > v94 + p107) {
                    v107 = v94 + p107;
                    d107 = d94;
                }
            }
            if (!uc.isOutOfMap(l113)) {
                p113 = 10 + getWeight(l113);
                if (v113 > v99 + p113) {
                    v113 = v99 + p113;
                    d113 = d99;
                }
                if (v113 > v112 + p113) {
                    v113 = v112 + p113;
                    d113 = d112;
                }
                if (v113 > v100 + p113) {
                    v113 = v100 + p113;
                    d113 = d100;
                }
            }
            if (!uc.isOutOfMap(l121)) {
                p121 = 10 + getWeight(l121);
                if (v121 > v109 + p121) {
                    v121 = v109 + p121;
                    d121 = d109;
                }
                if (v121 > v108 + p121) {
                    v121 = v108 + p121;
                    d121 = d108;
                }
                if (v121 > v122 + p121) {
                    v121 = v122 + p121;
                    d121 = d122;
                }
                if (v121 > v107 + p121) {
                    v121 = v107 + p121;
                    d121 = d107;
                }
            }
            if (!uc.isOutOfMap(l125)) {
                p125 = 10 + getWeight(l125);
                if (v125 > v111 + p125) {
                    v125 = v111 + p125;
                    d125 = d111;
                }
                if (v125 > v112 + p125) {
                    v125 = v112 + p125;
                    d125 = d112;
                }
                if (v125 > v124 + p125) {
                    v125 = v124 + p125;
                    d125 = d124;
                }
                if (v125 > v113 + p125) {
                    v125 = v113 + p125;
                    d125 = d113;
                }
            }
            if (!uc.isOutOfMap(l32)) {
                p32 = 10 + getWeight(l32);
                if (v32 > v45 + p32) {
                    v32 = v45 + p32;
                    d32 = d45;
                }
                if (v32 > v44 + p32) {
                    v32 = v44 + p32;
                    d32 = d44;
                }
                if (v32 > v46 + p32) {
                    v32 = v46 + p32;
                    d32 = d46;
                }
            }
            if (!uc.isOutOfMap(l80)) {
                p80 = 10 + getWeight(l80);
                if (v80 > v81 + p80) {
                    v80 = v81 + p80;
                    d80 = d81;
                }
                if (v80 > v68 + p80) {
                    v80 = v68 + p80;
                    d80 = d68;
                }
                if (v80 > v94 + p80) {
                    v80 = v94 + p80;
                    d80 = d94;
                }
            }
            if (!uc.isOutOfMap(l88)) {
                p88 = 10 + getWeight(l88);
                if (v88 > v87 + p88) {
                    v88 = v87 + p88;
                    d88 = d87;
                }
                if (v88 > v74 + p88) {
                    v88 = v74 + p88;
                    d88 = d74;
                }
                if (v88 > v100 + p88) {
                    v88 = v100 + p88;
                    d88 = d100;
                }
            }
            if (!uc.isOutOfMap(l136)) {
                p136 = 10 + getWeight(l136);
                if (v136 > v123 + p136) {
                    v136 = v123 + p136;
                    d136 = d123;
                }
                if (v136 > v124 + p136) {
                    v136 = v124 + p136;
                    d136 = d124;
                }
                if (v136 > v122 + p136) {
                    v136 = v122 + p136;
                    d136 = d122;
                }
            }
            if (!uc.isOutOfMap(l31)) {
                p31 = 10 + getWeight(l31);
                if (v31 > v45 + p31) {
                    v31 = v45 + p31;
                    d31 = d45;
                }
                if (v31 > v44 + p31) {
                    v31 = v44 + p31;
                    d31 = d44;
                }
                if (v31 > v43 + p31) {
                    v31 = v43 + p31;
                    d31 = d43;
                }
                if (v31 > v32 + p31) {
                    v31 = v32 + p31;
                    d31 = d32;
                }
            }
            if (!uc.isOutOfMap(l33)) {
                p33 = 10 + getWeight(l33);
                if (v33 > v45 + p33) {
                    v33 = v45 + p33;
                    d33 = d45;
                }
                if (v33 > v46 + p33) {
                    v33 = v46 + p33;
                    d33 = d46;
                }
                if (v33 > v47 + p33) {
                    v33 = v47 + p33;
                    d33 = d47;
                }
                if (v33 > v32 + p33) {
                    v33 = v32 + p33;
                    d33 = d32;
                }
            }
            if (!uc.isOutOfMap(l67)) {
                p67 = 10 + getWeight(l67);
                if (v67 > v81 + p67) {
                    v67 = v81 + p67;
                    d67 = d81;
                }
                if (v67 > v68 + p67) {
                    v67 = v68 + p67;
                    d67 = d68;
                }
                if (v67 > v55 + p67) {
                    v67 = v55 + p67;
                    d67 = d55;
                }
                if (v67 > v80 + p67) {
                    v67 = v80 + p67;
                    d67 = d80;
                }
            }
            if (!uc.isOutOfMap(l75)) {
                p75 = 10 + getWeight(l75);
                if (v75 > v87 + p75) {
                    v75 = v87 + p75;
                    d75 = d87;
                }
                if (v75 > v74 + p75) {
                    v75 = v74 + p75;
                    d75 = d74;
                }
                if (v75 > v61 + p75) {
                    v75 = v61 + p75;
                    d75 = d61;
                }
                if (v75 > v88 + p75) {
                    v75 = v88 + p75;
                    d75 = d88;
                }
            }
            if (!uc.isOutOfMap(l93)) {
                p93 = 10 + getWeight(l93);
                if (v93 > v81 + p93) {
                    v93 = v81 + p93;
                    d93 = d81;
                }
                if (v93 > v94 + p93) {
                    v93 = v94 + p93;
                    d93 = d94;
                }
                if (v93 > v107 + p93) {
                    v93 = v107 + p93;
                    d93 = d107;
                }
                if (v93 > v80 + p93) {
                    v93 = v80 + p93;
                    d93 = d80;
                }
            }
            if (!uc.isOutOfMap(l101)) {
                p101 = 10 + getWeight(l101);
                if (v101 > v87 + p101) {
                    v101 = v87 + p101;
                    d101 = d87;
                }
                if (v101 > v100 + p101) {
                    v101 = v100 + p101;
                    d101 = d100;
                }
                if (v101 > v113 + p101) {
                    v101 = v113 + p101;
                    d101 = d113;
                }
                if (v101 > v88 + p101) {
                    v101 = v88 + p101;
                    d101 = d88;
                }
            }
            if (!uc.isOutOfMap(l135)) {
                p135 = 10 + getWeight(l135);
                if (v135 > v123 + p135) {
                    v135 = v123 + p135;
                    d135 = d123;
                }
                if (v135 > v122 + p135) {
                    v135 = v122 + p135;
                    d135 = d122;
                }
                if (v135 > v121 + p135) {
                    v135 = v121 + p135;
                    d135 = d121;
                }
                if (v135 > v136 + p135) {
                    v135 = v136 + p135;
                    d135 = d136;
                }
            }
            if (!uc.isOutOfMap(l137)) {
                p137 = 10 + getWeight(l137);
                if (v137 > v123 + p137) {
                    v137 = v123 + p137;
                    d137 = d123;
                }
                if (v137 > v124 + p137) {
                    v137 = v124 + p137;
                    d137 = d124;
                }
                if (v137 > v125 + p137) {
                    v137 = v125 + p137;
                    d137 = d125;
                }
                if (v137 > v136 + p137) {
                    v137 = v136 + p137;
                    d137 = d136;
                }
            }
            if (!uc.isOutOfMap(l42)) {
                p42 = 10 + getWeight(l42);
                if (v42 > v56 + p42) {
                    v42 = v56 + p42;
                    d42 = d56;
                }
                if (v42 > v43 + p42) {
                    v42 = v43 + p42;
                    d42 = d43;
                }
                if (v42 > v55 + p42) {
                    v42 = v55 + p42;
                    d42 = d55;
                }
            }
            if (!uc.isOutOfMap(l48)) {
                p48 = 10 + getWeight(l48);
                if (v48 > v60 + p48) {
                    v48 = v60 + p48;
                    d48 = d60;
                }
                if (v48 > v47 + p48) {
                    v48 = v47 + p48;
                    d48 = d47;
                }
                if (v48 > v61 + p48) {
                    v48 = v61 + p48;
                    d48 = d61;
                }
            }
            if (!uc.isOutOfMap(l120)) {
                p120 = 10 + getWeight(l120);
                if (v120 > v108 + p120) {
                    v120 = v108 + p120;
                    d120 = d108;
                }
                if (v120 > v121 + p120) {
                    v120 = v121 + p120;
                    d120 = d121;
                }
                if (v120 > v107 + p120) {
                    v120 = v107 + p120;
                    d120 = d107;
                }
            }
            if (!uc.isOutOfMap(l126)) {
                p126 = 10 + getWeight(l126);
                if (v126 > v112 + p126) {
                    v126 = v112 + p126;
                    d126 = d112;
                }
                if (v126 > v113 + p126) {
                    v126 = v113 + p126;
                    d126 = d113;
                }
                if (v126 > v125 + p126) {
                    v126 = v125 + p126;
                    d126 = d125;
                }
            }
            if (!uc.isOutOfMap(l30)) {
                p30 = 10 + getWeight(l30);
                if (v30 > v44 + p30) {
                    v30 = v44 + p30;
                    d30 = d44;
                }
                if (v30 > v43 + p30) {
                    v30 = v43 + p30;
                    d30 = d43;
                }
                if (v30 > v31 + p30) {
                    v30 = v31 + p30;
                    d30 = d31;
                }
                if (v30 > v42 + p30) {
                    v30 = v42 + p30;
                    d30 = d42;
                }
            }
            if (!uc.isOutOfMap(l34)) {
                p34 = 10 + getWeight(l34);
                if (v34 > v46 + p34) {
                    v34 = v46 + p34;
                    d34 = d46;
                }
                if (v34 > v47 + p34) {
                    v34 = v47 + p34;
                    d34 = d47;
                }
                if (v34 > v33 + p34) {
                    v34 = v33 + p34;
                    d34 = d33;
                }
                if (v34 > v48 + p34) {
                    v34 = v48 + p34;
                    d34 = d48;
                }
            }
            if (!uc.isOutOfMap(l54)) {
                p54 = 10 + getWeight(l54);
                if (v54 > v68 + p54) {
                    v54 = v68 + p54;
                    d54 = d68;
                }
                if (v54 > v55 + p54) {
                    v54 = v55 + p54;
                    d54 = d55;
                }
                if (v54 > v67 + p54) {
                    v54 = v67 + p54;
                    d54 = d67;
                }
                if (v54 > v42 + p54) {
                    v54 = v42 + p54;
                    d54 = d42;
                }
            }
            if (!uc.isOutOfMap(l62)) {
                p62 = 10 + getWeight(l62);
                if (v62 > v74 + p62) {
                    v62 = v74 + p62;
                    d62 = d74;
                }
                if (v62 > v61 + p62) {
                    v62 = v61 + p62;
                    d62 = d61;
                }
                if (v62 > v75 + p62) {
                    v62 = v75 + p62;
                    d62 = d75;
                }
                if (v62 > v48 + p62) {
                    v62 = v48 + p62;
                    d62 = d48;
                }
            }
            if (!uc.isOutOfMap(l106)) {
                p106 = 10 + getWeight(l106);
                if (v106 > v94 + p106) {
                    v106 = v94 + p106;
                    d106 = d94;
                }
                if (v106 > v107 + p106) {
                    v106 = v107 + p106;
                    d106 = d107;
                }
                if (v106 > v93 + p106) {
                    v106 = v93 + p106;
                    d106 = d93;
                }
                if (v106 > v120 + p106) {
                    v106 = v120 + p106;
                    d106 = d120;
                }
            }
            if (!uc.isOutOfMap(l114)) {
                p114 = 10 + getWeight(l114);
                if (v114 > v100 + p114) {
                    v114 = v100 + p114;
                    d114 = d100;
                }
                if (v114 > v113 + p114) {
                    v114 = v113 + p114;
                    d114 = d113;
                }
                if (v114 > v101 + p114) {
                    v114 = v101 + p114;
                    d114 = d101;
                }
                if (v114 > v126 + p114) {
                    v114 = v126 + p114;
                    d114 = d126;
                }
            }
            if (!uc.isOutOfMap(l134)) {
                p134 = 10 + getWeight(l134);
                if (v134 > v122 + p134) {
                    v134 = v122 + p134;
                    d134 = d122;
                }
                if (v134 > v121 + p134) {
                    v134 = v121 + p134;
                    d134 = d121;
                }
                if (v134 > v135 + p134) {
                    v134 = v135 + p134;
                    d134 = d135;
                }
                if (v134 > v120 + p134) {
                    v134 = v120 + p134;
                    d134 = d120;
                }
            }
            if (!uc.isOutOfMap(l138)) {
                p138 = 10 + getWeight(l138);
                if (v138 > v124 + p138) {
                    v138 = v124 + p138;
                    d138 = d124;
                }
                if (v138 > v125 + p138) {
                    v138 = v125 + p138;
                    d138 = d125;
                }
                if (v138 > v137 + p138) {
                    v138 = v137 + p138;
                    d138 = d137;
                }
                if (v138 > v126 + p138) {
                    v138 = v126 + p138;
                    d138 = d126;
                }
            }

            int dx = target.x - l84.x;
            int dy = target.y - l84.y;
        if (dx == -4) {
            if (dy == -2) {
                return d30;
            } else if (dy == -1) {
                return d31;
            } else if (dy == 0) {
                return d32;
            } else if (dy == 1) {
                return d33;
            } else if (dy == 2) {
                return d34;
            }
        } else if (dx == -3) {
            if (dy == -3) {
                return d42;
            } else if (dy == -2) {
                return d43;
            } else if (dy == -1) {
                return d44;
            } else if (dy == 0) {
                return d45;
            } else if (dy == 1) {
                return d46;
            } else if (dy == 2) {
                return d47;
            } else if (dy == 3) {
                return d48;
            }
        } else if (dx == -2) {
            if (dy == -4) {
                return d54;
            } else if (dy == -3) {
                return d55;
            } else if (dy == -2) {
                return d56;
            } else if (dy == -1) {
                return d57;
            } else if (dy == 0) {
                return d58;
            } else if (dy == 1) {
                return d59;
            } else if (dy == 2) {
                return d60;
            } else if (dy == 3) {
                return d61;
            } else if (dy == 4) {
                return d62;
            }
        } else if (dx == -1) {
            if (dy == -4) {
                return d67;
            } else if (dy == -3) {
                return d68;
            } else if (dy == -2) {
                return d69;
            } else if (dy == -1) {
                return d70;
            } else if (dy == 0) {
                return d71;
            } else if (dy == 1) {
                return d72;
            } else if (dy == 2) {
                return d73;
            } else if (dy == 3) {
                return d74;
            } else if (dy == 4) {
                return d75;
            }
        } else if (dx == 0) {
            if (dy == -4) {
                return d80;
            } else if (dy == -3) {
                return d81;
            } else if (dy == -2) {
                return d82;
            } else if (dy == -1) {
                return d83;
            } else if (dy == 0) {
                return d84;
            } else if (dy == 1) {
                return d85;
            } else if (dy == 2) {
                return d86;
            } else if (dy == 3) {
                return d87;
            } else if (dy == 4) {
                return d88;
            }
        } else if (dx == 1) {
            if (dy == -4) {
                return d93;
            } else if (dy == -3) {
                return d94;
            } else if (dy == -2) {
                return d95;
            } else if (dy == -1) {
                return d96;
            } else if (dy == 0) {
                return d97;
            } else if (dy == 1) {
                return d98;
            } else if (dy == 2) {
                return d99;
            } else if (dy == 3) {
                return d100;
            } else if (dy == 4) {
                return d101;
            }
        } else if (dx == 2) {
            if (dy == -4) {
                return d106;
            } else if (dy == -3) {
                return d107;
            } else if (dy == -2) {
                return d108;
            } else if (dy == -1) {
                return d109;
            } else if (dy == 0) {
                return d110;
            } else if (dy == 1) {
                return d111;
            } else if (dy == 2) {
                return d112;
            } else if (dy == 3) {
                return d113;
            } else if (dy == 4) {
                return d114;
            }
        } else if (dx == 3) {
            if (dy == -3) {
                return d120;
            } else if (dy == -2) {
                return d121;
            } else if (dy == -1) {
                return d122;
            } else if (dy == 0) {
                return d123;
            } else if (dy == 1) {
                return d124;
            } else if (dy == 2) {
                return d125;
            } else if (dy == 3) {
                return d126;
            }
        } else if (dx == 4) {
            if (dy == -2) {
                return d134;
            } else if (dy == -1) {
                return d135;
            } else if (dy == 0) {
                return d136;
            } else if (dy == 1) {
                return d137;
            } else if (dy == 2) {
                return d138;
            }
        }

            double initialDist = Math.sqrt(l84.distanceSquared(target));

            double dist30 = (initialDist - Math.sqrt(l30.distanceSquared(target))) / v30;
            double dist31 = (initialDist - Math.sqrt(l31.distanceSquared(target))) / v31;
            double dist32 = (initialDist - Math.sqrt(l32.distanceSquared(target))) / v32;
            double dist33 = (initialDist - Math.sqrt(l33.distanceSquared(target))) / v33;
            double dist34 = (initialDist - Math.sqrt(l34.distanceSquared(target))) / v34;
            double dist42 = (initialDist - Math.sqrt(l42.distanceSquared(target))) / v42;
            double dist43 = (initialDist - Math.sqrt(l43.distanceSquared(target))) / v43;
            double dist47 = (initialDist - Math.sqrt(l47.distanceSquared(target))) / v47;
            double dist48 = (initialDist - Math.sqrt(l48.distanceSquared(target))) / v48;
            double dist54 = (initialDist - Math.sqrt(l54.distanceSquared(target))) / v54;
            double dist55 = (initialDist - Math.sqrt(l55.distanceSquared(target))) / v55;
            double dist61 = (initialDist - Math.sqrt(l61.distanceSquared(target))) / v61;
            double dist62 = (initialDist - Math.sqrt(l62.distanceSquared(target))) / v62;
            double dist67 = (initialDist - Math.sqrt(l67.distanceSquared(target))) / v67;
            double dist75 = (initialDist - Math.sqrt(l75.distanceSquared(target))) / v75;
            double dist80 = (initialDist - Math.sqrt(l80.distanceSquared(target))) / v80;
            double dist88 = (initialDist - Math.sqrt(l88.distanceSquared(target))) / v88;
            double dist93 = (initialDist - Math.sqrt(l93.distanceSquared(target))) / v93;
            double dist101 = (initialDist - Math.sqrt(l101.distanceSquared(target))) / v101;
            double dist106 = (initialDist - Math.sqrt(l106.distanceSquared(target))) / v106;
            double dist107 = (initialDist - Math.sqrt(l107.distanceSquared(target))) / v107;
            double dist113 = (initialDist - Math.sqrt(l113.distanceSquared(target))) / v113;
            double dist114 = (initialDist - Math.sqrt(l114.distanceSquared(target))) / v114;
            double dist120 = (initialDist - Math.sqrt(l120.distanceSquared(target))) / v120;
            double dist121 = (initialDist - Math.sqrt(l121.distanceSquared(target))) / v121;
            double dist125 = (initialDist - Math.sqrt(l125.distanceSquared(target))) / v125;
            double dist126 = (initialDist - Math.sqrt(l126.distanceSquared(target))) / v126;
            double dist134 = (initialDist - Math.sqrt(l134.distanceSquared(target))) / v134;
            double dist135 = (initialDist - Math.sqrt(l135.distanceSquared(target))) / v135;
            double dist136 = (initialDist - Math.sqrt(l136.distanceSquared(target))) / v136;
            double dist137 = (initialDist - Math.sqrt(l137.distanceSquared(target))) / v137;
            double dist138 = (initialDist - Math.sqrt(l138.distanceSquared(target))) / v138;

            double bestEstimation = Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(dist30, dist31), dist32), dist33), dist34), dist42), dist43), dist47), dist48), dist54), dist55), dist61), dist62), dist75), dist80), dist88), dist93), dist101), dist106), dist107), dist113), dist114), dist120), dist121), dist125), dist126), dist134), dist135), dist136), dist137), dist138);

            if (bestEstimation == dist30) {
                return d30;
            }

            if (bestEstimation == dist31) {
                return d31;
            }

            if (bestEstimation == dist32) {
                return d32;
            }

            if (bestEstimation == dist33) {
                return d33;
            }

            if (bestEstimation == dist34) {
                return d34;
            }

            if (bestEstimation == dist42) {
                return d42;
            }

            if (bestEstimation == dist43) {
                return d43;
            }

            if (bestEstimation == dist47) {
                return d47;
            }

            if (bestEstimation == dist48) {
                return d48;
            }

            if (bestEstimation == dist54) {
                return d54;
            }

            if (bestEstimation == dist55) {
                return d55;
            }

            if (bestEstimation == dist61) {
                return d61;
            }

            if (bestEstimation == dist62) {
                return d62;
            }

            if (bestEstimation == dist67) {
                return d67;
            }

            if (bestEstimation == dist75) {
                return d75;
            }

            if (bestEstimation == dist80) {
                return d80;
            }

            if (bestEstimation == dist88) {
                return d88;
            }

            if (bestEstimation == dist93) {
                return d93;
            }

            if (bestEstimation == dist101) {
                return d101;
            }

            if (bestEstimation == dist106) {
                return d106;
            }

            if (bestEstimation == dist107) {
                return d107;
            }

            if (bestEstimation == dist113) {
                return d113;
            }

            if (bestEstimation == dist114) {
                return d114;
            }

            if (bestEstimation == dist120) {
                return d120;
            }

            if (bestEstimation == dist121) {
                return d121;
            }

            if (bestEstimation == dist125) {
                return d125;
            }

            if (bestEstimation == dist126) {
                return d126;
            }

            if (bestEstimation == dist134) {
                return d134;
            }

            if (bestEstimation == dist135) {
                return d135;
            }

            if (bestEstimation == dist136) {
                return d136;
            }

            if (bestEstimation == dist137) {
                return d137;
            }

            return d138;
    }

    int getWeight(Location loc) {
        TileType t = uc.senseTileTypeAtLocation(loc);
        if (!t.isPassable()) return 10000;
        if(t == TileType.FOREST && uc.getType() != UnitType.RANGER) return 5;
        return 0;
    }
}
