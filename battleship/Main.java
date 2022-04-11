package battleship;

public class Main {
    static Ships shipsPlayer1 = new Ships();
    static Ships shipsPlayer2 = new Ships();
    static Game gamePlayer1 = new Game(shipsPlayer1,"Player 1");
    static Game gamePlayer2 = new Game(shipsPlayer2,"Player 2");

    public static void main(String[] args) throws IllegalAccessException {
        gamePlayer1.placedMenu("Player 1");
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        gamePlayer2.placedMenu("Player2");
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        while (true) {
            gamePlayer1.shotMenu();
            gamePlayer2.takeShot();
            if (gamePlayer2.isEnd()) {
                break;
            }
            System.out.println("Press Enter and pass the move to another player");
            System.out.println("...");
            gamePlayer2.shotMenu();
            gamePlayer1.takeShot();
            if (gamePlayer1.isEnd()) {
                break;
            }
            System.out.println("Press Enter and pass the move to another player");
            System.out.println("...");
        }


    }


}