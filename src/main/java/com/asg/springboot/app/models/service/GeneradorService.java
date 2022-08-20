package com.asg.springboot.app.models.service;

import com.asg.springboot.app.logic.SolutionGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GeneradorService {

    SolutionGenerator solutionGenerator = new SolutionGenerator();
    public String servicio() {
        String ejemplo = "ejecutando algun proceso importante...";
        return ejemplo;
    }

    public String mostrarSoluciones() {
        ArrayList<String> defaultBlocks = new ArrayList<>();
        String[] bloques = {"advance", "turnRight", "turnLeft", "backwards"};
        Collections.addAll(defaultBlocks, bloques);
        ArrayList<String> extraBlocks = new ArrayList<>();
        String[] bloquesObligatorios = {"advance", "advance", "advance", "turnLeft", "turnRight"};
        Collections.addAll(extraBlocks, bloquesObligatorios);
        ArrayList<String []> soluciones = solutionGenerator.solutionGeneratorDP(defaultBlocks, 7, extraBlocks);

        for (int i = 0; i < soluciones.size(); i++){
            System.out.println("Solucion " + i + ": " + Arrays.toString(soluciones.get(i)));
        }
        return "prueba";
    }
}
