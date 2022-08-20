package com.asg.springboot.app.logic;


import java.util.ArrayList;
import java.util.Arrays;

public class SolutionGeneratorRules {
    static int[] leastOneKeyBlock(String[] keyBlocks, ArrayList<String> candidateSolution) {
        int cont[] = new int[keyBlocks.length];
        Arrays.fill(cont, 0);
        int i = 0;
        for (String block : candidateSolution) {
            for (String keyBlock : keyBlocks) {
                if (block.equals(keyBlock)) cont[i]++;
                i++;
            }
            i = 0;
        }
        return cont;
    }

    static ArrayList<String> simplifyTurns(ArrayList<String> candidateSolution) {
        if (candidateSolution.size() <= 3) return candidateSolution;
        int cont = 0;
        boolean same = false;
        for (int i = 1; i <= candidateSolution.size(); i++) {
            if (candidateSolution.get(i).equals("turnLeft") || candidateSolution.get(i).equals("turnRight")) {
                if (candidateSolution.get(i).equals(candidateSolution.get(i - 1))) {
                    if (!same) {
                        same = true;
                        cont = 2;
                    } else {
                        cont++;
                    }
                }
            }
            if (cont == 3) {
                same = false;
                cont = 0;
                candidateSolution.set(i, candidateSolution.get(i).equals("turnLeft") ? "turnRight" : "turnLeft");
                candidateSolution.subList(i - 2, i - 1).clear();
                i -= 2;
            }
        }
        return candidateSolution;
    }

    static boolean lastBlock(ArrayList<String> candidateSolution) {
        return candidateSolution.get(candidateSolution.size() - 1).equals("advance") ||
                candidateSolution.get(candidateSolution.size() - 1).equals("backwards");
    }
}

