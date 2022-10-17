package com.codegym.games.minesweeper;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {

    private int countFlags;

    private static final String FLAG = "\uD83D\uDEA9";
    private static final String MINE = "\uD83D\uDCA3";
    private static final int SIDE = 9;
    private final GameObject[][] gameField = new GameObject[SIDE][SIDE];

    private int countMinesOnField;

    public void onMouseLeftClick(int x, int y) {
        openTile(x, y);
    }

    private void openTile(int x, int y) { //
        if (gameField[y][x].isMine) {
            setCellValue(x, y, MINE);
        } else if (!gameField[y][x].isMine && gameField[y][x].countMineNeighbors == 0) { // to finish
            for (GameObject gameObject : getNeighbors(gameField[y][x])) {
                if (!gameObject.isOpen && gameObject.countMineNeighbors == 0) {
                    gameObject.isOpen = true;
                    openTile(gameObject.x, gameObject.y);
                } else if (!gameObject.isOpen && gameObject.countMineNeighbors >= 1) {
                    gameObject.isOpen = true;
                    setCellValue(x, y, String.valueOf(gameObject.countMineNeighbors));
                }
            }
        }
        gameField[x][y].isOpen = true;
        setCellColor(x, y, Color.GREEN);
    }

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private void countMineNeighbors() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                if (!gameField[y][x].isMine) {
                    for (GameObject gameObject : getNeighbors(gameField[y][x])) {
                        if (gameObject.isMine) {
                            gameField[y][x].countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    public static void launchA() {
        MinesweeperGame.launch();
    }
}

class Default {
    public static void main(String[] args) {
        MinesweeperGame.launchA();
    }
}