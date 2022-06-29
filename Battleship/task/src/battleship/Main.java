package battleship;

import java.util.Scanner;

public class Main {
    static boolean win = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Player player1 = new Player("Player 1", new Field()/*, new Field()*/);
        Player player2 = new Player("Player 2", new Field()/*, new Field()*/);

        System.out.println("Player 1, place your ships on the game field\n");
        player1.field.shipsPlacement();
        player1.turnPass();

        System.out.println("Player 2, place your ships on the game field\n");
        player2.field.shipsPlacement();
        player2.turnPass();

        player2.setEnemyField(player1.field);
        player1.setEnemyField(player2.field);

        while (true) {

            player1.enemyField.fogOfWar = true;
            System.out.println("\n" + player1.enemyField);
            System.out.println("---------------------");
            player1.enemyField.fogOfWar = false;
            System.out.println("\n" + player1.field);
            System.out.println("Player 1, it's your turn:\n");
            if (win)
                break;

            player1.shoot_emAll(scanner);
            player1.turnPass();

            player2.enemyField.fogOfWar = true;
            System.out.println("\n" + player2.enemyField);
            System.out.println("---------------------");
            player2.enemyField.fogOfWar = false;
            System.out.println("\n" + player2.field);
            System.out.println("Player 2, it's your turn:\n");
            if (win)
                break;

            player2.shoot_emAll(scanner);
            player2.turnPass();
        }
    }
}