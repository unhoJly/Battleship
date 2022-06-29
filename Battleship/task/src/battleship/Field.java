package battleship;

import java.util.*;

public class Field {
    final char[][] FIELD = new char[10][10];
    final char EMPTY = '~';
    final char SHIP = 'O';
    final char MISS = 'M';
    final char HIT = 'X';
    boolean fogOfWar;
    Map<String, String> ships = new HashMap<>();
    List<String> namesCheck =
            new ArrayList<>(List.of("Aircraft Carrier", "Battleship", "Cruiser", "Destroyer", "Submarine"));
    int oDownCounter = 17;

    public Field() {
        for (char[] chars : FIELD)
            Arrays.fill(chars, EMPTY);
    }

    public void shipsPlacement() {
        Scanner scanner = new Scanner(System.in);
        String begin, end;
        int y1, y2, dx, dy;
        char x1, x2;

        System.out.println(this);

        for (ShipType shipType : ShipType.values()) {
            System.out.println("Enter the coordinates of the " + shipType.name + " (" + shipType.size + " cells):\n");

            while (true) {
                begin = scanner.next();
                end = scanner.next();

                x1 = begin.split("")[0].charAt(0);
                y1 = Integer.parseInt(begin.split("\\D")[1]) - 1;
                x2 = end.split("")[0].charAt(0);
                y2 = Integer.parseInt(end.split("\\D")[1]) - 1;

                if (x1 > x2) {
                    x1 = (char) (x1 ^ x2);
                    x2 = (char) (x1 ^ x2);
                    x1 = (char) (x1 ^ x2);
                } else if (y1 > y2) {
                    y1 = y1 ^ y2;
                    y2 = y1 ^ y2;
                    y1 = y1 ^ y2;
                }

                dx = x2 - x1;
                dy = y2 - y1;

                if (dx == 0)
                    dy++;
                else if (dy == 0)
                    dx++;

                if ((dx == 0 && dy != shipType.size) || (dy == 0 && dx != shipType.size)) {
                    System.out.println("\nError! Wrong length of the " + shipType.name + "! Try again:\n");
                } else if (dx != 0 && dy != 0) {
                    System.out.println("\nError! Wrong ship location! Try again:\n");
                } else if (!spaceCheck(x1, x2, y1, y2)) {
                    System.out.println("\nError! You placed it too close to another one. Try again:\n");
                } else
                    break;
            }

            if (dx == 0)
                for (int i = 0; i < dy; i++) {
                    FIELD[x1 - 65][y1 + i] = SHIP;
                    ships.put(Character.toString(x1) + (y1 + i + 1), shipType.name);
                }
            else if (dy == 0)
                for (int i = 0; i < dx; i++) {
                    FIELD[x1 - 65 + i][y1] = SHIP;
                    ships.put(Character.toString(x1 + i) + (y1 + 1), shipType.name);
                }
            System.out.println("\n" + this);
        }
    }

    private boolean spaceCheck(char x1, char x2, int y1, int y2) {
        int leftX = 1, rightX = 1, leftY = 1, rightY = 1;

        if (x1 == 'A')
            leftX = 0;
        if (x2 == 'J')
            rightX = 0;
        if (y1 == 0)
            leftY = 0;
        if (y2 == 9)
            rightY = 0;

        if (x1 == x2) {
            for (int i = x1 - 65 - leftX; i <= x2 - 65 + rightX; i += x2 - 65 + rightX)
                for (int j = y1 - leftY; j <= y2 + rightY; j++)
                    if (FIELD[i][j] == SHIP)
                        return false;

            if (FIELD[x1 - 65][y1 - leftY] == SHIP || FIELD[x2 - 65][y2 + rightY] == SHIP)
                return false;
        }

        if (y1 == y2) {
            for (int i = x1 - 65 - leftX; i <= x2 - 65 + rightX; i++)
                for (int j = y1 - leftY; j <= y2 + rightY; j += y2 + rightY)
                    if (FIELD[i][j] == SHIP)
                        return false;

            return FIELD[x1 - 65 - leftX][y1] != SHIP && FIELD[x2 - 65 + rightX][y2] != SHIP;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(" ");

        for (int i = 0; i < FIELD.length; i++)
            builder.append(" ").append(i + 1);

        builder.append("\n");

        for (int i = 0; i < FIELD.length; i++) {
            builder.append((char) ('A' + i));

            for (int j = 0; j < FIELD.length; j++)
                if (fogOfWar && (FIELD[i][j] == 'O' || FIELD[i][j] == '~'))
                    builder.append(" ").append('~');
                else
                    builder.append(" ").append(FIELD[i][j]);

            builder.append("\n");
        }

        return builder.toString();
    }
}