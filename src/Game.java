import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    static String[][] map = {
            {"*", "*", "*", "*"},
            {"*", "*", "*", "*"},
            {"*", "*", "*", "*"},
            {"*", "*", "*", "*"}
    };

    static int playerX, playerY, enemy1X, enemy1Y, enemy2X, enemy2Y, giftX, giftY;

    static boolean gameFlag = false;
    static String endGameMessage = "";
    static Random random = new Random();

    public static void printMap() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void generatePlayerAndEnemy() {
        playerX = random.nextInt(4);
        playerY = random.nextInt(4);
        map[playerX][playerY] = "P";

        enemy1X = random.nextInt(4);
        enemy1Y = random.nextInt(4);
        while (enemy1X==playerX && enemy1Y==playerY) {
            enemy1X = random.nextInt(4);
            enemy1Y = random.nextInt(4);
        }
        map[enemy1X][enemy1Y] = "E";

        enemy2X = random.nextInt(4);
        enemy2Y = random.nextInt(4);
        while ((enemy2X==playerX && enemy2Y==playerY) || (enemy2X==enemy1X && enemy2Y==enemy1Y)) {
            enemy2X = random.nextInt(4);
            enemy2Y = random.nextInt(4);
        }
        map[enemy2X][enemy2Y] = "E";
    }

    public static void generateGift() {
        giftX = random.nextInt(4);
        giftY = random.nextInt(4);
        while ((giftX==playerX && giftY==playerY) || (giftX==enemy1X && giftY==enemy1Y) || (giftX==enemy2X && giftY==enemy2Y)) {
            giftX = random.nextInt(4);
            giftY = random.nextInt(4);
        }
        map[giftX][giftY] = "G";
    }

    public static void displayGift() {
        if((giftX!=enemy1X && giftY!=enemy1Y) || (giftX!=enemy2X && giftY!=enemy2Y)) {
            map[giftX][giftY] = "G";
        }
    }

    public static void movePlayer() {
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
        if (playerY == -1) playerY = map.length - 1;
        if (playerY == map.length) playerY = 0;
        if (playerX == -1) playerX = map.length - 1;
        if (playerX == map.length)  playerX = 0;
        map[playerX][playerY] = "P";
    }

    public static void moveEnemies() {
        map[enemy1X][enemy1Y] = "*";
        map[enemy2X][enemy2Y] = "*";

        int backandforth1 = random.nextInt(2);
        int backandforth2 = random.nextInt(2);

        if (backandforth1 == 0) {
            enemy1X ++;
        } else {
            enemy1X --;
        }

        if (backandforth2 == 0) {
            enemy2Y ++;
        } else {
            enemy2Y --;
        }

        if (enemy1X == -1) enemy1X = map.length - 1;
        if (enemy1X == map.length) enemy1X = 0;
        if (enemy2Y == -1) enemy2Y = map.length - 1;
        if (enemy2Y == map.length) enemy2Y = 0;

        map[enemy1X][enemy1Y] = "E";
        map[enemy2X][enemy2Y] = "E";
    }

    public static void checkMapState() {
        if ((playerX==enemy1X && playerY==enemy1Y) || (playerX==enemy2X && playerY==enemy2Y)) {
            endGameMessage = "You lost!";
            gameFlag = true;
        } else if (playerX==giftX && playerY==giftY) {
            endGameMessage = "Congratulations! You won the game!";
            gameFlag = true;
        }
    }

    public static void main(String[] args) {
        generatePlayerAndEnemy();
        generateGift();
        while (!gameFlag) {
            printMap();
            movePlayer();
            moveEnemies();
            displayGift();
            checkMapState();
        }
        System.out.println(endGameMessage);
    }
}