import { condition, userActionEnum } from './config.js';
import { Next } from './next.js';
export function userAction(action, gameBoard, next) {
    var req = new XMLHttpRequest();
    req.open("POST", "http://localhost:8080/api/actions", false);
    var webUserAction = {
        action_id: action,
        hold: false
    };
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(webUserAction));
    if (action != userActionEnum.Terminate) {
        updateInterface(gameBoard, next);
        checkCondition(gameBoard);
    }
    if (action == userActionEnum.Start) {
        clearTimeouts();
        updateState(gameBoard, next);
    }
}

export function updateState(gameBoard, next) {
    var reqUpdate = new XMLHttpRequest();
    reqUpdate.open("GET", "http://localhost:8080/api/updatestate", false);
    reqUpdate.send(null);
    var req = new XMLHttpRequest();
    req.open("GET", "http://localhost:8080/api/state", false);
    req.send(null);
    var state = JSON.parse(req.responseText)
    changeAddData(state, gameBoard, next);
    setTimeout(() => updateState(gameBoard, next), 1000 - 90 * state.lvl);
    checkCondition(gameBoard);
}
function updateInterface(gameBoard, next) {
    var req = new XMLHttpRequest();
    req.open("GET", "http://localhost:8080/api/state", false);
    req.send(null);
    var state = JSON.parse(req.responseText)
    changeAddData(state, gameBoard, next);
}
function getRaceCondition() {
    var req = new XMLHttpRequest();
    req.open("GET", "http://localhost:8080/api/condition-of-race", false);
    req.send(null);
    return JSON.parse(req.responseText)
}

function changeAddData(state, gameBoard, next) {
    if (getId() == 1) {
        next.changeNext(state);
    }
    else {
        document.getElementById("next").style.display = 'none';
    }
    document.getElementById("valueScore").innerHTML = state.score;
    document.getElementById("valueHighScore").innerHTML = state.high_score;
    document.getElementById("valueLevel").innerHTML = state.lvl;
    document.getElementById("valueSpeed").innerHTML = state.speed;
    gameBoard.changeBoard(state);
}
function getId() {
    var req = new XMLHttpRequest();
    req.open("GET", "http://localhost:8080/api/gameid", false);
    req.send(null);
    return JSON.parse(req.responseText)
}
function getCgameCondition() {
    var req = new XMLHttpRequest();
    req.open("GET", "http://localhost:8080/api/condition-c-game", false);
    req.send(null);
    return JSON.parse(req.responseText)
}
function checkCondition(gameBoard) {
    var gameId = getId();
    var cond;
    if (gameId == 1 || gameId == 2) {
        cond = getCgameCondition();
    }
    else {
        cond = getRaceCondition();
    }
    if (cond == condition.Pre_exit_situation) {
        if (confirm("End of the game! Do you want restart ?")) {
            clearTimeouts();
            userAction(userActionEnum.Start, gameBoard);
        }
        else {
            userAction(userActionEnum.Terminate, gameBoard);
            window.location = "main.html";
        }
    }
    if (cond == userActionEnum.Pause) {
        if (confirm("Game is paused! Do you want resume ?")) {
            userAction(userActionEnum.Pause, gameBoard);
        }
        else {
            checkCondition(gameBoard);
        }
    }
}
function clearTimeouts() {
    var id = window.setTimeout(function () { }, 0);
    while (id--) {
        window.clearTimeout(id);
    }
}