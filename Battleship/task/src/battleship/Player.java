package battleship;

import java.util.Scanner;

public class Player {
    String name;
    Field field;
    Field enemyField;

    public Player(String name, Field field) {
        this.name = name;
        this.field = field;
    }

    public void setEnemyField(Field enemyField) {
        this.enemyField = enemyField;
    }

    public void shoot_emAll(Scanner scanner) {
        boolean sank = false;
        int x, y;

        String coordinates = scanner.next();
        x = coordinates.split("")[0].charAt(0) - 65;
        y = Integer.parseInt(coordinates.split("\\D")[1]) - 1;

        if (x < 0 || x > 9 || y < 0 || y > 9) {
            System.out.println("\nError! You entered the wrong coordinates! Try again:\n");

        } else if (enemyField.FIELD[x][y] == enemyField.SHIP) {
            enemyField.FIELD[x][y] = enemyField.HIT;

            enemyField.oDownCounter--;

            if (enemyField.oDownCounter == 0) {
                Main.win = true;
                System.out.println("You sank the last ship. You won. Congratulations!");
            }

            enemyField.ships.remove(coordinates);

            for (String s : enemyField.namesCheck)
                if (!enemyField.ships.containsValue(s)) {
                    sank = enemyField.namesCheck.remove(s);
                    break;
                }

            if (sank) {
                System.out.println("You sank a ship!\n");
            } else {
                System.out.println("You hit a ship!\n");
            }

            enemyField.fogOfWar = false;
            System.out.println("\n" + this);
        } else if (enemyField.FIELD[x][y] == enemyField.HIT) {
            System.out.println("\n" + this);

            enemyField.ships.remove(coordinates);

            for (String s : enemyField.namesCheck)
                if (!enemyField.ships.containsValue(s)) {
                    sank = true;
                    break;
                }

            if (sank) {
                System.out.println("You sank a ship!\n");
            } else {
                System.out.println("You hit a ship!\n");
            }
        } else if (enemyField.FIELD[x][y] == enemyField.EMPTY || enemyField.FIELD[x][y] == enemyField.MISS) {
            enemyField.FIELD[x][y] = enemyField.MISS;
            System.out.println("\nYou missed!");
        }
    }

    public void turnPass() {
        System.out.println("Press Enter and pass the move to another player");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}