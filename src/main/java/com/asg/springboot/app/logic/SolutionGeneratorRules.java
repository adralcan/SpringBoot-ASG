package com.asg.springboot.app.logic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionGeneratorRules {


    static String[] moveBlocks = new String[]{"advance", "backwards"};
    static String[] turnBlocks = new String[]{"turnLeft", "turnRight"};
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

    static boolean simplifyTurnsV2(ArrayList<String> candidateSolution) {
        if (candidateSolution.size() <= 3) return true;
        String [] movementType = SolutionGeneratorRules.turnBlocks;
        ArrayList<Integer> indices = new ArrayList<>();
        for (int movements = 0; movements < movementType.length; movements++) {
            int finalMovements = movements;
            indices = new ArrayList<>(IntStream.range(0, candidateSolution.size())
                    .filter((i) -> Objects.equals(candidateSolution.get(i), movementType[finalMovements]))
                    .boxed().collect(Collectors.toSet()));
            if (indices.size() >= 3) {
                for (int i = 2; i < indices.size(); i++) {
                    if (indices.get(i) - 2 == indices.get(i - 2) && indices.get(i) - 1 == indices.get(i - 1)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Se descartan las soluciones que tienen bloques opuestos seguidos
    static boolean oppositeBlocksTogether(ArrayList<String> candidateSolution) {
        ArrayList<String[]> movementTypes = new ArrayList<>();
        movementTypes.add(SolutionGeneratorRules.moveBlocks);
        movementTypes.add(SolutionGeneratorRules.turnBlocks);
        ArrayList<ArrayList<Integer>> indices = new ArrayList<>();
        for (String[] movementType : movementTypes) {
            for (int movements = 0; movements < movementType.length; movements++) {
                int finalMovements = movements;
                indices.add(new ArrayList<>(IntStream.range(0, candidateSolution.size())
                        .filter((i) -> Objects.equals(candidateSolution.get(i), movementType[finalMovements]))
                        .boxed().collect(Collectors.toSet())));
            }
            for (int i = 0; i < indices.get(0).size(); i++) {
                int elem = indices.get(0).get(i);
                if (indices.get(1).contains(elem - 1) || indices.get(1).contains(elem + 1)) {
                    return false;
                }
            }
            indices.clear();
        }
        return true;
    }

    static boolean lastBlock(ArrayList<String> candidateSolution) {
        return candidateSolution.get(candidateSolution.size() - 1).equals("advance") ||
                candidateSolution.get(candidateSolution.size() - 1).equals("backwards");
    }
}

