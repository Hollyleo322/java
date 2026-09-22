import { applyRootStyles } from './src/utils.js';
import { GameBoard } from './src/game-board.js';
import { rootStyles, keyCodes, userActionEnum } from './src/config.js';
import { userAction } from './src/query.js'
import { Next } from './src/next.js';
alert("Press S to start the game");
applyRootStyles(rootStyles);
const gameBoard = new GameBoard(document.querySelector('#game-board'));
const next = new Next(document.querySelector("#next"));

const $sidePanel = document.querySelector('#side-panel');
document.addEventListener('keydown', function (event) {
    if (keyCodes.up.includes(event.code)) {
        userAction(userActionEnum.Up, gameBoard, next);
    }
    if (keyCodes.right.includes(event.code)) {
        userAction(userActionEnum.Right, gameBoard, next);
    }
    if (keyCodes.down.includes(event.code)) {
        userAction(userActionEnum.Down, gameBoard, next)
    }
    if (keyCodes.left.includes(event.code)) {
        userAction(userActionEnum.Left, gameBoard, next);
    }
    if (keyCodes.end.includes(event.code)) {
        userAction(userActionEnum.Terminate, gameBoard, next);
        window.location = "main.html"
    }
    if (keyCodes.start.includes(event.code)) {
        userAction(userActionEnum.Start, gameBoard, next);
    }
    if (keyCodes.pause.includes(event.code)) {
        userAction(userActionEnum.Pause, gameBoard, next)
    }
    if (keyCodes.action.includes(event.code)) {
        userAction(userActionEnum.Action, gameBoard, next)
    }
});
