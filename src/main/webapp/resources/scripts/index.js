class Generador {
    static generateSolutions() {
        let defaultBlocks = ["advance", "backwards", "turnRight", "turnLeft", "action"];
        let solutionSize = 7;
        let mandatoryBlocks = ["advance", "advance", "advance", "turnRight", "jump", "backwards"];
        let params = {
            defaultBlocks: defaultBlocks,
            solutionSize: solutionSize,
            mandatoryBlocks: mandatoryBlocks
        };

        $.ajax({type: "POST", data : params, url : "/generateSolutions", traditional: true}).done(() => {
            window.location.reload();
        });
    }
}