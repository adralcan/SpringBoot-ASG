class Generador {
    static generateSolutions() {
        debugger;
        let instructions = ["advance", "backwards", "turnRight", "turnLeft", "action", "jump"];
        let defaultBlocks = ["advance"];
        let mandatoryBlocks = [];
        let solutionSize = parseInt($('#instructionNumber').val());
        let cont = solutionSize;
        for (let instructionsKey in instructions) {
            if ($('#' + instructions[instructionsKey] + 'Check').is(":checked")) {
                if (defaultBlocks.indexOf(instructions[instructionsKey]) === -1) {
                    defaultBlocks.push(instructions[instructionsKey]);
                }
            }
            if (cont > 0) {
                let instructionsReps = parseInt($('#' + instructions[instructionsKey] + 'Number').val());
                let rep = 0;
                while (rep < instructionsReps && cont > 0) {
                    mandatoryBlocks.push(instructions[instructionsKey]);
                    cont--;
                    rep++;
                }
            }
        }
        let params = {
            defaultBlocks: defaultBlocks,
            solutionSize: solutionSize,
            mandatoryBlocks: mandatoryBlocks
        };

        $.ajax({type: "POST", data : params, url : "/generateSolutions", traditional: true}).done(() => {
            debugger;
            window.location.reload();
        });
    }
}