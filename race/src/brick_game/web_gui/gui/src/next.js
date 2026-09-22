import { NEXT_WIDTH } from './config.js';

export class Next {
    constructor($next) {
        this.element = $next;
        this.tiles = [];
        for (let i = 0; i < NEXT_WIDTH; ++i) {
            for (let j = 0; j < NEXT_WIDTH; ++j) {
                const $tile = document.createElement('div');
                $tile.classList.add('tile');
                $tile.id = `position-${i}-${j}`;
                this.tiles.push($tile);
                this.element.append($tile);
            }
        }
    }

    getTile(x, y) {
        return this.tiles[x * NEXT_WIDTH + y];
    }

    enableTile(x, y) {
        this.getTile(x, y).classList.add('active');
    }

    disableTile(x, y) {
        this.getTile(x, y).classList.remove('active');
    }
    offNext() {
        this.element.classList.add('hidden');
    }
    changeNext(state) {
        for (let i = 0; i < NEXT_WIDTH; ++i) {
            for (let j = 0; j < NEXT_WIDTH; ++j) {
                if (state.next[i][j] == true) {
                    this.enableTile(i, j);
                } else {
                    this.disableTile(i, j);
                }
            }
        }
    }
}
