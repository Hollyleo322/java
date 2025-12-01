import { drawMaze, drawPath } from './draw.js';
export function queryMaze() {
    var loadInput = document.getElementById("fileInput");
    const form = new FormData();
    form.append('file', loadInput.files[0]);
    fetch('http://localhost:8080/upload', {
        method: 'POST',
        body: form,
    }).then(response => {
        if (response.ok) {
            return response.json();
        } else {
            console.error('Failed to upload file');
        }
    }).then(jsn => {
        drawMaze(jsn);
    });
}

export function queryAutoGen() {
    var rows = document.getElementById("inputRows");
    var cols = document.getElementById("inputCols");
    if (cols.value > 2 && rows.value > 2 && cols.value < 51 && rows.value < 51) {
        fetch('http://localhost:8080/auto-gen', {
            method: 'POST',
            body: JSON.stringify({
                rows: rows.value,
                cols: cols.value
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                console.error('Failed to generate maze');
            }
        }).then(jsn => {
            drawMaze(jsn);
        })
    }
    else {
        alert("Please enter a valid number of rows and columns");
    }
}
export function queryDecision() {
    var startedX = document.getElementById("inputstartedX");
    var startedY = document.getElementById("inputstartedY");
    var finishedX = document.getElementById("inputfinishedX");
    var finishedY = document.getElementById("inputfinishedY");
    fetch('http://localhost:8080/current-field').then(response => {
        if (response.ok) {
            return response.json();
        } else {
            alert("Field does not loaded");
        }
    }).then(response => {
        if (startedX.value >= 0 && startedY.value >= 0 && finishedX.value >= 0 && finishedY.value >= 0 && startedX.value < response.cols && startedY.value < response.rows && finishedX.value < response.cols && finishedY.value < response.rows) {
            drawMaze(response);
            fetch('http://localhost:8080/path', {
                method: 'POST',
                body: JSON.stringify({
                    startedX: startedX.value,
                    startedY: startedY.value,
                    finishedX: finishedX.value,
                    finishedY: finishedY.value
                }),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.ok) {
                    return response.json();
                } else if (response.status.valueOf(406)) {
                    alert("Maze is not loaded");
                }
            }).then(jsn => {
                drawPath(jsn);
            });
        } else {
            alert("Please enter a valid number of rows and columns");
        }
    });
}