package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class SolutionGenerator {


    // Las instrucciones disponibles estaran definidas por la unidad a la que pertenezcan.
    // El peor caso es aquella unidad que tengan todas las instrucciones del plan de estudios disponibles

    // Tiene que cumplir las reglas de validación

    // (?) Combinaciones con repeticion - n sobre k,
    // siendo n defaultBlocks.length y 1(ese uno es avanzar/retrodecer) <= k
    // Siempre, casi siempre va a haber menos bloques disponibles de los que vamos a utilizar
    // Entonces, a lo mejor podemos hacer set de combinaciones
    // Ejemplo de wikipedia: https://es.wikipedia.org/wiki/Combinaciones_con_repetición
    // X={a, b, c, d}
    // aaa	aab	aac	aad	abb	abc	abd	acc	acd	add
    // bbb	bbc	bbd	bcc	bcd	bdd	ccc	ccd	cdd	ddd
    // Esas son las combinaciones disponibles, entonces ahora hago otra vez combinaciones pero de los resultados

    // Árbol con dos tipos de probabilidades a calcular:
    // Llamemos a 'a' uno de los sucesos necesarios y a 'b' el otro suceso necesario
    // Se hace la combinatoria de los otros tres elementos que no sean necesarios
    // Combinatoria de posición: Primero una combinatoria simple de bcd
    // y luego con los resultados pivotar la posición con a

    // Echar un ojo a Principio del palomar

    // defaultBlocks: instrucciones por defecto en la unidad
    // combinacionActual: indices de la combinacion que se esta formando
    // r: tamaño de la combinación que se va a generar
    // start & end:
    public static void combinacionesGenerador(ArrayList<ArrayList<String>> combinaciones,
                                              ArrayList<String> defaultBlocks,
                                              int[] combinacionActual, int index, int r, int ini, int fin) {
        // https://www.geeksforgeeks.org/combinaciones-with-repetitions/

        // Cuando el indice se convierte en el limite, es que se puede guardar la solucion
        if (index == r) {
            ArrayList<String> combinacion = new ArrayList<>();
            for (int i : combinacionActual) {
                combinacion.add(defaultBlocks.get(i));
            }
            combinaciones.add(combinacion);
        } else {
            // Elegir todos los elementos posibles de uno en uno. Como se puede repetir no se tiene en cuenta si ha sido
            // elegido ya. Se procede a la recurrencia.
            for (int i = ini; i <= fin; i++) {
                combinacionActual[index] = i;
                combinacionesGenerador(combinaciones, defaultBlocks, combinacionActual, index + 1, r, i, fin);
            }
        }
    }

    static void combNonCandidate(ArrayList<ArrayList<Integer>> combinaciones, int[] arr, int[] combinacionActual,
                                 int index, int r, int ini, int fin) {
        if (index == r) {
            ArrayList<Integer> combinacion = new ArrayList<>();
            for (int i = 0; i < r; i++) {
                combinacion.add(arr[combinacionActual[i]]);
            }
            combinaciones.add(combinacion);
        } else {
            for (int i = ini; i <= fin; i++) {
                combinacionActual[index] = i;
                combNonCandidate(combinaciones, arr, combinacionActual, index + 1, r, i, fin);
            }
        }
    }

    // Casos base
    //Combinaciones con repetición (n k):
    //
    //Si tengo menos elementos disponibles de los agrupados lo que voy a hacer es coger los elementos por orden,
    //y hacer combinaciones con ese elemento repitiendo de 1 a m veces
    //
    //1º caso crítico:
    //Una sola instrucción
    //n = 1  1 <= k < m siendo m un numero natural
    //Ejemplo de avance => A, k = 4
    //
    //2º caso normal:
    //Dos instrucciones -> n < k
    //n = 1  1 <= k < m siendo m un numero natural
    //Ejemplo de avance => A giro derecha => T, k = 4
    //AT de 4
    //TAAA ATAA AATA
    //TTTA TATT TTAT
    //AATT ATTA TTAA ATAT TATA
    //3º caso
    // n > r: de momento lo dejamos hasta que sea un problema
    public static void buildcombinacion(ArrayList<ArrayList<String>> combinaciones, ArrayList<String> defaultBlocks, int r) {
        ArrayList<String> newSetDefaultBlocks = defaultBlocks;
        for (int i = 0; i < defaultBlocks.size(); i++) {
            while (newSetDefaultBlocks.size() < r) {
                newSetDefaultBlocks.add(defaultBlocks.get(i));
            }
            int[] combinacionActual = new int[r + 1];
            combinacionesGenerador(combinaciones, newSetDefaultBlocks, combinacionActual, 0, r, 0, newSetDefaultBlocks.size() - 1);
            newSetDefaultBlocks = defaultBlocks;
        }
    }

    // defaultBlocks: instrucciones por defecto en la unidad
    // r: tamaño de la combinación que se va a generar
    public ArrayList<String[]> solucionCombinacionesGenerador(ArrayList<String> defaultBlocks, int r, ArrayList<String> bloquesObligatorios) {
        // (?) r deberia pasar a la solucion parcial con su propio valor o se divide en partes para creaar subsoluciones

        // IMPORTANTE: que pasa si defaultBlocks.size() es mayor que r

        ArrayList<ArrayList<String>> combinaciones = new ArrayList<>();
        int[] combinacionActual = new int[r + 1];
        combinacionesGenerador(combinaciones, defaultBlocks, combinacionActual, 0, r, 0, defaultBlocks.size() - 1);
        // Ahora tengo las soluciones sin filtrar
        // Separar las que son candidatas por si mismas del resto
        // Para que sea candidata debe cumplir los requisitos
        // Reglas que se me van ocurriendo:
        // Que haya siempre un avance o un retroceder
        // Si hay tres giros seguidos, cambiarlo por el giro contrario
        // No puede haber un avanzar y un retroceder seguidos, da igual el orden
        // ^ Esta todavía no se mete en SolutionGeneratorRules porque puede haber teletransportes
        // Se puede meter como regla o se puede poner un teleport
        ArrayList<ArrayList<String>> discardedSolutions = new ArrayList<>();
        ArrayList<String[]> combinacionesFiltradas = new ArrayList<>();
        for (ArrayList<String> combinacion : combinaciones) {
            if (isCandidate(combinacion, bloquesObligatorios)) {
                combinacionesFiltradas.add((String[]) combinacion.toArray());
            }
        }
        return combinacionesFiltradas;
//        // Reciclaje de soluciones descartadas
//        combinacionActual = new int[2];
//        int[] arr = new int[discardedSolutions.size()];
//        ArrayList<ArrayList<Integer>> newCandidates = new ArrayList<>();
//        combNonCandidate(newCandidates, combinacionActual, arr, 0, r, 0, defaultBlocks.size() - 1);
//        for (ArrayList<Integer> newCandidate : newCandidates) {
//            ArrayList<String> candidate = new ArrayList<>();
//            for (Integer index : newCandidate) {
//                candidate.addAll(discardedSolutions.get(index));
//            }
//            if (isCandidate(candidate, bloquesObligatorios)) {
//                solutions.add(candidate);
//            }
//        }
    }

    static boolean isCandidate(ArrayList<String> combinacion, ArrayList<String> bloquesObligatorios) {
        String[] keyBlocks = new String[]{"advance", "backwards"};
        if (bloquesObligatorios != null) {
            System.arraycopy(bloquesObligatorios.toArray(), 0, keyBlocks, 0, bloquesObligatorios.size());
        }
        int[] keyBlocksReps = SolutionGeneratorRules.leastOneKeyBlock(keyBlocks, combinacion);
        for (int reps : keyBlocksReps) {
            if (reps < 1) {
                return false;
            }
        }
        int prevSize = combinacion.size();
        SolutionGeneratorRules.simplifyTurns(combinacion);
        if (combinacion.size() != prevSize) {
            return false;
        }
        if (!SolutionGeneratorRules.lastBlock(combinacion)) {
            return false;
        }
        return true;
    }





    //___________________________________v2_CON_PROGRAMACION_DINAMICA___________________________________________________

    // Hay un problema grave, hasta ahora he supuesto que con el set de datos dado habria repeticion. Pero en el
    // set de datos no hay repeticion porque siempre hay un bloque de cada tipo. Por tanto, a partir de "defaultBlocks"
    // hay que generar un multiconjunto con bloques repetidos. Primer planteamiento al problema con ejemplo:
    // "defaultBlocks":  ["advance","backwards","turnRight","turnLeft","action"]
    // "solution": ["turnLeft","advance","turnLeft","advance","advance","action","turnRight","action","advance","advance"]
    // n = defaultBlocks.length = 5
    // r = solution.length = 10
    // int [] subsetDefaultBlocks = new Array(n).fill(0);
    // Como en solutionGenerator se pasa por parametro bloquesObligatorios, los que pertenezcan a esa lista tendra que
    // haber uno como minimo. Si hay un bloque bloquesObligatorios que no aparece en defaultBlocks, se añade al array y
    // también tiene que haber uno como mínimo (Esto puede ocurrir con frecuencia con el bloque de bucles for)
    // int newR = r - bloquesObligatorios.length
    // for (let i = 0; i < newR; i++) {
    // numbers[Math.floor(Math.random() * newR) % (n - 1)]++;
    // }
    // Con esta implementacion deberia pasar a permutaciones con repeticion o
    // permutaciones de los indices del array que podria ser mas sencillo (?)

    // 2. Para seguir adelante con el algoritmo habrá que tener en cuenta la teoria que hay en:
    // file:///E:/hlocal/MARP/DINAMICA/progdinamica.pdf y el ejemplo que hay en https://www.youtube.com/watch?v=2GEIvssFZRg

    public ArrayList<String[]> solutionGeneratorDP(ArrayList<String> defaultBlocks, int r, ArrayList<String> bloquesObligatorios) {
        if (defaultBlocks == null || defaultBlocks.size() == 0) {
            return new ArrayList<>();
        }
        Set<String> auxSet = new HashSet<>();
        auxSet.addAll(defaultBlocks);
        auxSet.addAll(bloquesObligatorios);
        ArrayList<String> newDefaultBlocks = new ArrayList<>(auxSet);

        int[] subsetDefaultBlocks = new int[newDefaultBlocks.size()];
        if (newDefaultBlocks.size() > r) {
            // Se prodia hacer con programacion dinamica pero hay muy pocas ocasiones en las que se generen soluciones
            // mas pequeñas que el set de datos. Ademas serian pocas combinaciones en total.
            return solucionCombinacionesGenerador(newDefaultBlocks, r, bloquesObligatorios);
        }
        else if (newDefaultBlocks.size() < r) {
            Arrays.fill(subsetDefaultBlocks, 0);
            for (String keyBlock : bloquesObligatorios) {
                int index = newDefaultBlocks.indexOf(keyBlock);
                subsetDefaultBlocks[index]++;
            }

            int newR = r - bloquesObligatorios.size();
            for (int i = 0; i < newR; i++) {
                subsetDefaultBlocks[(int) (Math.floor(Math.random() * newR) % (newDefaultBlocks.size() - 1))]++;
            }
        }
        else {
            Arrays.fill(subsetDefaultBlocks, 1);
        }

        // Ahora hay que hacer el nuevo subconjunto en un array para poder trabajar con los indices para las permutaciones
        String[] permutationArray = new String[r];
        int index = 0;
        for (int i = 0; i < newDefaultBlocks.size(); i++) {
            for (int x = 0; x < subsetDefaultBlocks[i]; x++) {
                permutationArray[index] = defaultBlocks.get(i);
                index++;
            }
        }

        // Ahora que tengo el set para la permutacion preparado
        int[] permutationIndexes = IntStream.rangeClosed(0, r).toArray();
        int factorial = IntStream.rangeClosed(1, r).reduce(1, (int x, int y) -> x * y);
        // Para la programacion dinamica en lugar de una matriz como es habitual en la mayoria de este tipo de
        // problemas. Para mejorar la gestion del espacio utilizare dos listas de arrays que contengan la permutacion
        // actual y la permutacion anterior

        ArrayList<int[]> permutacionAnterior = new ArrayList<>(factorial);
        ArrayList<int[]> permutacionActual = new ArrayList<>(factorial);
        // Primera permutacion que es la lista ya ordenada
        permutacionAnterior.add(new int[]{permutationIndexes[r - 1]});

        for (int posActual = r - 1; posActual > 0; posActual--) {
            // Al principio de la iteracion limpiamos la permutacion actual para calcular la nueva y luego se actualiza
            // la previa
            permutacionActual.clear();
            for (int [] permutacion : permutacionAnterior) {
                permutacionActual.addAll(addPermutacion(permutacion, permutationIndexes[posActual]));
            }
            if(posActual - 1 > 0) {
                permutacionAnterior.clear();
                permutacionAnterior.addAll(permutacionActual);
            }
        }
        // Una vez calculadas las permutaciones de los indices, se traduce de numero a string para tener los
        // bloques. Despues habria que aplicar las reglas de las soluciones candidatas para descartar.
        ArrayList<String[]> solucionesPosibles = new ArrayList<>();
        for (int [] permutacion : permutacionActual) {
            String [] solucion = new String[r];
            for (int i = 0; i < r; i++){
                solucion[i] = permutationArray[permutacion[i]];
            }
            solucionesPosibles.add(solucion);
        }
        return solucionesPosibles;
    }

    private ArrayList<int[]> addPermutacion(int [] permutacion, int siguiente) {
        int limit = permutacion.length + 1;
        ArrayList<int[]> permutaciones = new ArrayList<>(limit);
        for (int x = 0; x <  limit; x++){
            int[] permutacionNueva = new int[limit];
            int i = 0;
            for (int j = 0; j < limit; j++){
                // Si los indices coinciden me quedo con el siguiente elemento a permutar como pivote y el resto es lo
                // que voy cambiando para hacer la nueva permutacion
                permutacionNueva[j] = (j == x)? siguiente : permutacion[i++];
            }
            permutaciones.add(permutacionNueva);
        }
        return  permutaciones;
    }
}

