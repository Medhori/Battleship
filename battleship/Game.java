package battleship;

import java.lang.reflect.Field;
import java.util.Scanner;

public class Game {

    static Scanner scanner = new Scanner(System.in);
    private final Ships ships;
    private final String playerName;
    private boolean end  =  false;

    public Game(Ships ships, String playerName) {
        this.ships = ships;
        this.playerName = playerName;
    }

    public  void shotMenu() {
        print(ships.fogArray);
        System.out.println("---------------------");
        print(ships.array);
        System.out.println();
        System.out.println(playerName + ", it's your turn:");

    }
    public boolean isEnd() {
        return end;
    }

    public void takeShot() throws IllegalAccessException {
        boolean check = true;
        while (check) {

            String shot = scanner.nextLine();
            System.out.println();
            char row = shot.charAt(0);
            int col = Integer.parseInt(shot.substring(1));
            int shotRow = rowIndex(row);
            int shotCol = colIndex(col);

            if (shotRow == -1 || shotCol == -1) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                System.out.println();

            } else {
                if (ships.array[shotRow][shotCol] == 'O' || ships.array[shotRow][shotCol] =='X') {
                    ships.array[shotRow][shotCol] = 'X';
                    String coordination = shotRow + "" + shotCol;
                    ships.shotShip(coordination);

                    if (sankShip()) {
                        if (ships.list.size() == 0) {
                            System.out.println();
                            System.out.println("You sank the last ship. You won. Congratulations!");
                            end = true;
                            break;
                        } else {
                            System.out.println();
                            System.out.println("You sank a ship! Specify a new target:");
                            System.out.println();
                        }
                    } else {
                        System.out.println();
                        System.out.println("You hit a ship!");
                        System.out.println();
                    }
                } else if (ships.array[shotRow][shotCol] == 'M') {
                    System.out.println("You missed!");
                    System.out.println();
                } else {
                    ships.array[shotRow][shotCol] = 'M';
                    System.out.println();
                    System.out.println("You missed!");
                }

                check = false;
            }

        }


    }

    private  boolean sankShip() throws IllegalAccessException {
        boolean check = false;
        Class<? extends Ships> item = ships.getClass();
        Field[] fields = item.getDeclaredFields();
        for (Field fd : fields) {
            if (fd.getType() == int.class && (int) fd.get(ships) == 0 && ships.list.contains(fd.getName())) {
                ships.list.remove(fd.getName());
                check = true;
                break;
            }
        }
        return check;
    }


    public  void print(char[][] array) {
        char a = 'A';
        System.out.println(" 1 2 3 4 5 6 7 8 9 10");
        for (char[] chars : array) {
            System.out.print(a++ + " ");
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
    }

    public  void place(char[][] array, int fromRow, int toRow, int fromCol, int toCol, String shipName) {
        for (int i = fromRow; i <= toRow; i++) {
            for (int j = fromCol; j <= toCol; j++) {
                array[i][j] = 'O';
                String coordination = i + "" + j;
                ships.put(coordination, shipName);

            }
        }
    }


    public  boolean checkTouch(char[][] array, int fromRow, int toRow, int fromCol, int toCol) {
        boolean check = false;
        for (int i = fromRow; i <= toRow; i++) {
            for (int j = fromCol; j <= toCol; j++) {
                if (array[i][j] == 'O') {
                    check = true;
                    break;
                }
            }

        }
        return check;
    }

    public  boolean checkCloser(char[][] array, int fromRow, int toRow, int fromCol, int toCol) {
        boolean check = false;
        //check horizontal ship head
        if (fromRow == toRow) {
            if (fromCol > 0) {
                if (array[fromRow][fromCol - 1] != '~') {
                    check = true;
                }
            }
            if (toCol < array.length - 1) {
                if (array[fromRow][toCol + 1] != '~') {
                    check = true;
                }
            }
        }
        //check vertical ship head
        if (fromCol == toCol) {
            if (fromRow > 0) {
                if (array[fromRow - 1][fromCol] != '~') {
                    check = true;
                }
            }
            if (toRow < array.length - 1) {
                if (array[toRow + 1][fromCol] != '~') {
                    check = true;
                }
            }
        }
        //check horizontal ship body
        if (fromRow == toRow) {
            if (fromRow > 0) {
                for (int i = fromCol; i <= toCol; i++) {
                    if (array[fromRow - 1][i] != '~') {
                        check = true;
                        break;
                    }
                }

                if (fromRow < array.length - 1) {
                    for (int i = fromCol; i <= toCol; i++) {
                        if (array[fromRow + 1][i] != '~') {
                            check = true;
                            break;
                        }
                    }
                }
            }
        }
        //check vertical ship body
        if (fromCol == toCol) {
            if (fromCol > 0) {
                for (int i = fromRow; i <= toRow; i++) {
                    if (array[i][fromCol - 1] != '~') {
                        check = true;
                        break;
                    }
                }
            }
            if (toCol < array.length - 1) {
                for (int i = fromRow; i < toRow; i++) {
                    if (array[i][toCol + 1] != '~') {
                        check = true;
                        break;
                    }
                }
            }
        }
        return check;
    }


    public  int rowIndex(char value) {
        char[] row = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int index = -1;
        for (int i = 0; i < row.length; i++) {
            if (row[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }

    public  int colIndex(int value) {
        int[] row = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int index = -1;
        for (int i = 0; i < row.length; i++) {
            if (row[i] == value) {
                index = i;
                break;
            }
        }
        return index;
    }

    public  void placedMenu(String playerName) {
        System.out.println(playerName + ", place your ships on the game field");
        System.out.println();
        print(ships.array);
        System.out.println();
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        System.out.println();
        placingShip("Carrier", 5);
        System.out.println();
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        System.out.println();
        placingShip("BattlesShip", 4);
        System.out.println();
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        System.out.println();
        placingShip("Submarine", 3);
        System.out.println();
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        System.out.println();
        placingShip("Cruiser", 3);
        System.out.println();
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        System.out.println();
        placingShip("Destroyer", 2);
        System.out.println();

    }

    public  void placingShip(String shipName, int shipLength) {
        boolean test = true;
        while (test) {
            String str = scanner.nextLine();
            System.out.println();
            String[] string = str.split(" ");

            char startRow = string[0].charAt(0);
            int startCol = Integer.parseInt(string[0].substring(1));
            char endRow = string[1].charAt(0);
            int endCol = Integer.parseInt(string[1].substring(1));
            int fromRow;
            int toRow;
            int fromCol;
            int toCol;

            if (startRow <= endRow) {
                fromRow = rowIndex(startRow);
                toRow = rowIndex(endRow);
            } else {
                fromRow = rowIndex(endRow);
                toRow = rowIndex(startRow);
            }

            if (startCol <= endCol) {
                fromCol = colIndex(startCol);
                toCol = colIndex(endCol);
            } else {
                fromCol = colIndex(endCol);
                toCol = colIndex(startCol);
            }

            if (toCol == fromCol && shipLength != range(fromRow, toRow) || fromRow == toRow && shipLength != range(fromCol, toCol)) {
                System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            } else if (fromCol == -1 || fromRow == -1 || toCol == -1 || toRow == -1) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            } else if (fromRow != toRow && fromCol != toCol) {
                System.out.println("Error! Wrong ship location! Try again:");
            } else if (checkTouch(ships.array, fromRow, toRow, fromCol, toCol)) {
                System.out.println("Error!");
            } else if (checkCloser(ships.array, fromRow, toRow, fromCol, toCol)) {
                System.out.println("Error! You placed it too close to another one. Try again:");

            } else {
                place(ships.array, fromRow, toRow, fromCol, toCol, shipName);
                print(ships.array);
                test = false;
            }
            System.out.println();
        }
    }

    public  int range(int index1, int index2) {
        int count = 0;
        for (int i = index1; i <= index2; i++) {
            count++;
        }
        return count;
    }
}
