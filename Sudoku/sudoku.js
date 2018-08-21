// Sudoku array initialization
var sudoku = new Array(9);

for (var i = 0; i < sudoku.length; i++) {
    sudoku[i] = new Array(9);
}

// Sudoku shuffled initialization
var sudokuShuffled = new Array(9);

for (var i = 0; i < sudokuShuffled.length; i++) {
    sudokuShuffled[i] = new Array(9);
}

// Samples array initialization
var sampleNumbers = new Array(9);
var sampleGridSize = new Array(9);

for (var i = 0; i < sampleGridSize.length; i++) {
    sampleGridSize[i] = new Array(9);
}

/**
 * Fills the samples
 */
function fillSampleNumbers() {
    for (var i = 0; i < 9; i++) {
        sampleNumbers[i] = i + 1;
    }
}

function fillSampleGridSize() {
    for (var i = 0; i < 9; i++) {
        for (var j = 0; i < 9; i++) {
            sampleGridSize[i][j] = 1;
        }
    }
}

/**
 * Fills the sudoku
 */
function fillSudoku() {
    for (var i = 0; i < 9; i++) {
        for (var j = 0; j < 9; j++) {
            fillSampleNumbers();
            var caseFilled = false;
            while (sampleNumbers.length != 0 && !caseFilled) {
                var random = Math.floor((Math.random() * sampleNumbers.length));
                var number = sampleNumbers[random];
                if (checkRow(j, i, number) && checkCol(j, i, number) && checkBox(j, i, number)) {
                    sudoku[i][j] = number;
                    caseFilled = true;
                }
                sampleNumbers.splice(random, 1);
            }
            if (sudoku[i][j] == null) {
                return false;
            }
        }
    }
    return true;
}

var completelyFilled = false;
var iteration = 0;

/**
 * Fills the sudoku, and do it again until it's not completely full
 */
while (!completelyFilled && iteration < 1000) {
    completelyFilled = fillSudoku();
    iteration++;
}

console.log(iteration);


/**
 * Check if the number is already present in the row
 * @param {*} x 
 * @param {*} y 
 * @param {*} number 
 */
function checkRow(x, y, number) {
    for (var j = 0; j < 9; j++) {
        if (sudoku[y][j] == number && j != x) {
            return false;
        }
    }
    return true;
}

/**
 * Check if the number is already present in the column
 * @param {*} x 
 * @param {*} y 
 * @param {*} number 
 */
function checkCol(x, y, number) {
    for (var i = 0; i < 9; i++) {
        if (sudoku[i][x] == number && i != y) {
            return false;
        }
    }
    return true;
}

/**
 * Check if the number is already present in the box
 * @param {*} x 
 * @param {*} y 
 * @param {*} number 
 */
function checkBox(x, y, number) {
    var startX = Math.trunc(x / 3);
    startX += 2 * startX;
    var startY = Math.trunc(y / 3);
    startY += 2 * startY;

    for (var i = startY; i < startY + 3; i++) {
        for (var j = startX; j < startX + 3; j++) {
            if (sudoku[i][j] == number && (i != y && j != x)) {
                return false;
            }
        }
    }
    return true;
}

function shuffleSudoku(difficulty, sudoku) {
    this.sudokuShuffled = sudoku;
    var randomFound = false;
    for (var h = 0; h < difficulty; h++) {
        var randomFound = false;
        while (!randomFound) {
            var i = Math.floor((Math.random() * sampleGridSize.length));
            var j = Math.floor((Math.random() * sampleGridSize[i].length));
            if (sampleGridSize[i][j] != 0) {
                sudokuShuffled[i][j] = "";
                sampleGridSize[i][j] = 0;
                randomFound = true;
            }
        }
    }
}

fillSampleGridSize();
shuffleSudoku(65, sudoku);

// Modify the DOM to display the sudoku
for (var i = 0; i < 9; i++) {
    for (var j = 0; j < 9; j++) {
        var col = j + 1;
        var row = i + 1;
        var id = "col" + col + "_" + "row" + row;
        document.getElementById(id).setAttribute("value", sudokuShuffled[i][j]);
        if (sudokuShuffled[i][j] != "") {
            document.getElementById(id).setAttribute("disabled", true);
        }
    }
}