export function drawMaze(field) {
    var canvas = document.getElementById("canvas");
    var cr = canvas.getContext("2d");
    cr.lineWidth = 2;
    cr.strokeStyle = "black";
    cr.clearRect(0, 0, 500, 500);
    cr.beginPath();
    cr.strokeRect(0, 0, 499, 499);
    drawVertical(field.vertical, field.rows, field.cols, cr);
    drawHorizontal(field.horizontal, field.rows, field.cols, cr);
    cr.stroke();
}

function drawVertical(vertical, rows, cols, cr) {
    var sizeCol = 500 / cols;
    var sizeRow = 500 / rows;
    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < cols - 1; j++) {
            if (vertical[i][j] == 1) {
                cr.moveTo((j + 1) * sizeCol, i * sizeRow);
                cr.lineTo((j + 1) * sizeCol, (i + 1) * sizeRow);
            }
        }
    }
}
function drawHorizontal(horizontal, rows, cols, cr) {
    var sizeCol = 500 / cols;
    var sizeRow = 500 / rows;
    for (let i = 0; i < rows - 1; i++) {
        for (let j = 0; j < cols; j++) {
            if (horizontal[i][j] == 1) {
                cr.moveTo(j * sizeCol, (i + 1) * sizeRow);
                cr.lineTo((j + 1) * sizeCol, (i + 1) * sizeRow);
            }
        }
    }
}
export function drawPath(path) {
    var canvas = document.getElementById("canvas");
    var cr = canvas.getContext("2d");
    cr.lineWidth = 2;
    cr.strokeStyle = "red";
    var sizeCol = 500 / path.cols;
    var sizeRow = 500 / path.rows;
    var decision = path.path;
    var isVerticalPrevious = false;
    cr.beginPath();
    for (let i = 0; i < decision.length; i++) {
        if (i == 0) {
            isVerticalPrevious = drawFinishedCell(decision[i], sizeCol, sizeRow, cr);
        }
        else {
            if (needVertical(decision[i])) {
                if (!isVerticalPrevious) {
                    cr.moveTo(decision[i].x * sizeCol + sizeCol / 2, decision[i].y * sizeRow + sizeRow / 2);
                    drawHalfHorizontalToChild(decision[i - 1], sizeCol, sizeRow, cr);
                    cr.moveTo(decision[i].x * sizeCol + sizeCol / 2, decision[i].y * sizeRow + sizeRow / 2);
                    drawHalfVerticalToParent(decision[i], sizeCol, sizeRow, cr);
                }
                else {
                    drawFullVertical(decision[i], sizeCol, sizeRow, cr);
                }
                isVerticalPrevious = true;
            }
            else {
                if (isVerticalPrevious) {
                    cr.moveTo(decision[i].x * sizeCol + sizeCol / 2, decision[i].y * sizeRow + sizeRow / 2);
                    drawHalfHorizontalToParent(decision[i], sizeCol, sizeRow, cr);
                    cr.moveTo(decision[i].x * sizeCol + sizeCol / 2, decision[i].y * sizeRow + sizeRow / 2);
                    drawHalfVerticalToChild(decision[i - 1], sizeCol, sizeRow, cr);
                }
                else {
                    drawFullHorizontal(decision[i], sizeCol, sizeRow, cr);
                }
                isVerticalPrevious = false;
            }
        }
    }
    drawStartedCell(decision[decision.length - 1], sizeCol, sizeRow, cr);
    cr.stroke();
}
function drawFullHorizontal(point, sizeCol, sizeRow, cr) {
    cr.moveTo(point.x * sizeCol, point.y * sizeRow + sizeRow / 2);
    cr.lineTo((point.x + 1) * sizeCol, point.y * sizeRow + sizeRow / 2);
}
function drawFullVertical(point, sizeCol, sizeRow, cr) {
    cr.moveTo(point.x * sizeCol + sizeCol / 2, point.y * sizeRow);
    cr.lineTo(point.x * sizeCol + sizeCol / 2, (point.y + 1) * sizeRow);
}
function drawStartedCell(point, sizeCol, sizeRow, cr) {
    cr.moveTo(point.parent.x * sizeCol + sizeCol / 2, point.parent.y * sizeRow + sizeRow / 2);
    if (needVertical(point)) {
        drawHalfVerticalToChild(point, sizeCol, sizeRow, cr);
    }
    else {
        drawHalfHorizontalToChild(point, sizeCol, sizeRow, cr);
    }
}
function drawHalfVerticalToChild(point, sizeCol, sizeRow, cr) {
    if (isParentAbove(point)) {
        cr.lineTo(point.parent.x * sizeCol + sizeCol / 2, (point.parent.y + 1) * sizeRow);
    }
    else {
        cr.lineTo(point.parent.x * sizeCol + sizeCol / 2, point.parent.y * sizeRow);
    }
}
function drawHalfHorizontalToChild(point, sizeCol, sizeRow, cr) {
    if (isParentRight(point)) {
        cr.lineTo(point.parent.x * sizeCol, point.parent.y * sizeRow + sizeRow / 2);
    }
    else {
        cr.lineTo((point.parent.x + 1) * sizeCol, point.parent.y * sizeRow + sizeRow / 2);
    }
}
function drawFinishedCell(point, sizeCol, sizeRow, cr) {
    var vertical = false;
    cr.moveTo((point.x + 1) * sizeCol - sizeCol / 2, (point.y + 1) * sizeRow - sizeRow / 2);
    if (needVertical(point)) {
        vertical = true;
        drawHalfVerticalToParent(point, sizeCol, sizeRow, cr);
    }
    else {
        drawHalfHorizontalToParent(point, sizeCol, sizeRow, cr);
    }
    return vertical;
}
function needVertical(point) {
    var result = false;
    if (point.parent.y != point.y) {
        result = true;
    }
    return result;
}
function drawHalfVerticalToParent(point, sizeCol, sizeRow, cr) {
    if (isParentAbove(point)) {
        cr.lineTo((point.x + 1) * sizeCol - sizeCol / 2, point.y * sizeRow);
    }
    else {
        cr.lineTo((point.x + 1) * sizeCol - sizeCol / 2, (point.y + 1) * sizeRow);
    }
}
function isParentAbove(point) {
    return point.parent.y < point.y;
}
function drawHalfHorizontalToParent(point, sizeCol, sizeRow, cr) {
    if (isParentRight(point)) {
        cr.lineTo((point.x + 1) * sizeCol, (point.y + 1) * sizeRow - sizeRow / 2);
    }
    else {
        cr.lineTo(point.x * sizeCol, (point.y + 1) * sizeRow - sizeRow / 2);
    }
}
function isParentRight(point) {
    return point.parent.x > point.x;
}