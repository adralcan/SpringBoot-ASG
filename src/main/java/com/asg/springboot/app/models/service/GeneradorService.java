package com.asg.springboot.app.models.service;

import com.asg.springboot.app.logic.SolutionGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class GeneradorService {

    SolutionGenerator solutionGenerator = new SolutionGenerator();

    public ArrayList<String []> soluciones;

    public String servicio() {
        String ejemplo = "ejecutando algun proceso importante...";
        return ejemplo;
    }

    public ArrayList<String []> mostrarSoluciones() {
        ArrayList<String> defaultBlocks = new ArrayList<>();
        String[] bloques = {"advance","backwards","turnRight","turnLeft","action"};
        Collections.addAll(defaultBlocks, bloques);
        ArrayList<String> extraBlocks = new ArrayList<>();
        String[] bloquesObligatorios = {"advance", "advance", "advance", "turnRight", "jump", "backwards"};
        Collections.addAll(extraBlocks, bloquesObligatorios);
        ArrayList<String []> soluciones = solutionGenerator.solutionGeneratorDP(defaultBlocks, 7, extraBlocks);

        for (int i = 0; i < soluciones.size(); i++){
            System.out.println("Solucion " + i + ": " + Arrays.toString(soluciones.get(i)));
        }
        return soluciones;
    }
    public ArrayList<String []> mostrarSoluciones(ArrayList<String> defaultBlocks, int solutionSize,  ArrayList<String> mandatoryBlocks) {
        ArrayList<String []> soluciones = solutionGenerator.solutionGeneratorDP(defaultBlocks, solutionSize, mandatoryBlocks);
        for (int i = 0; i < soluciones.size(); i++){
            System.out.println("Solucion " + i + ": " + Arrays.toString(soluciones.get(i)));
        }
        return soluciones;
    }
}
