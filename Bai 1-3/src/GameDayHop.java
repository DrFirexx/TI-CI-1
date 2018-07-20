import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameDayHop {
    static String[][] map = new String[10][10];

    static int playerX, playerY, boxX, boxY, exitX, exitY;

    static boolean gameFlag = false;
    static String endGameMessage = "";
    static Random random = new Random(1);
    static Random randombox = new Random(2);

    public static void generateMap() {
        for (int i = 0; i < 10; i++) {
            map[0][i] = "|";
            map[9][i] = "|";
        }

        for (int i = 0; i < 10; i++) {
            map[i][0] = "-";
            map[i][9] = "-";
        }

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                map[i][j] = "*";
            }
        }
    }

    public static void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void generatePlayerGiftFinish() {
        playerX = random.nextInt(9);
        playerY = random.nextInt(9);
        map[playerX][playerY] = "P";

        boxX = randombox.nextInt(8);
        boxY = randombox.nextInt(8);
        while ((boxX==playerX && boxY==playerY)) {
            boxX = random.nextInt(8);
            boxY = random.nextInt(8);
        }
        map[boxX][boxY] = "B";

        exitX = random.nextInt(9);
        exitY = random.nextInt(9);
        while ((exitX==playerX && exitY==playerY) || (exitX==boxX && exitY==boxY)) {
            exitX = random.nextInt(9);
            exitY = random.nextInt(9);
        }
        map[exitX][exitY] = "E";
    }

    public static void movePlayerAndBox() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhap ky tu ban phim: ");
        String key = scanner.next();

        map[playerX][playerY] = "*";

        switch (key) {
            case "w":
                playerY -= 1;
                break;
            case "s":
                playerY += 1;
                break;
            case "a":
                playerX -= 1;
                break;
            case "d":
                playerX += 1;
                break;
        }
        if (playerY == 0) playerY = 1;
        if (playerY == map.length-1) playerY = map.length-2;
        if (playerX == 0) playerX = 1;
        if (playerX == map.length-1)  playerX = map.length-2;
        map[playerX][playerY] = "P";
        if ((playerX==boxX) && (playerY==boxY)) moveBox(key);
    }

    public static void moveBox(String key) {
        switch (key) {
            case "w":
                boxY -= 1;
                break;
            case "s":
                boxY += 1;
                break;
            case "a":
                boxX -= 1;
                break;
            case "d":
                boxX += 1;
                break;
        }
        if (boxY == 0) boxY = 1;
        if (boxY == map.length-1) boxY = map.length-2;
        if (boxX == 0) boxX = 1;
        if (boxX == map.length-1) boxX = map.length-2;
        map[boxX][boxY] = "B";
    }

    public static void checkMapState() {
        if ((boxX==1 && exitX!=1) || (boxX==8 && exitX!=8) || (boxY==1 && exitY!=1) || (boxY==8 && exitY!=8)) {
            endGameMessage = "You lost!";
            gameFlag = true;
        } else if (boxX==exitX && boxY==exitY) {
            endGameMessage = "Congratulations! You won the game!";
            gameFlag = true;
        }
    }

    public static void main(String[] args) {
        generateMap();
        generatePlayerGiftFinish();
        while (!gameFlag) {
            printMap();
            movePlayerAndBox();
            checkMapState();
        }
        System.out.println(endGameMessage);
    }
}
