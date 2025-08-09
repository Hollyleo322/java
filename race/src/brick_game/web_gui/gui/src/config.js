export const GAME_BOARD_WIDTH = 10;
export const GAME_BOARD_HEIGHT = 20;
export const NEXT_WIDTH = 5;


export const rootStyles = {
    '--tile-size': '20px',
    '--tile-color': '#eee',
    '--tile-active-color': '#222',
    '--game-board-width': GAME_BOARD_WIDTH,
    '--game-board-height': GAME_BOARD_HEIGHT,
    '--game-board-gap': '2px',
    '--game-board-background': '#333',
};

export const keyCodes = {
    up: ['ArrowUp', 'KeyW', 'KeyI'],
    right: ['ArrowRight', 'KeyD', 'KeyL'],
    down: ['ArrowDown', 'KeyS', 'KeyK'],
    left: ['ArrowLeft', 'KeyA', 'KeyJ'],
    end: ['KeyQ'],
    start: ['KeyS'],
    pause: ['Enter'],
    action: ['Space']
};
export const userActionEnum = {
    Start: 0,
    Pause: 1,
    Terminate: 2,
    Left: 3,
    Right: 4,
    Up: 5,
    Down: 6,
    Action: 7
}
export const condition = {
    Game: 10,
    Pause_game: 11,
    Error_memory: 12,
    End_of_game: 13,
    Change_level: 14,
    Win: 15,
    Pre_exit_situation: 16
}