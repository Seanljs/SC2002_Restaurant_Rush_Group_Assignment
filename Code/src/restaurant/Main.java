package restaurant;

import java.util.Scanner;
import restaurant.control.GameConfig;
import restaurant.control.GameController;
import restaurant.control.GameQuitException;
import restaurant.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ConsoleUI ui = new ConsoleUI(in);
        try {
            new GameController(GameConfig.base(), ui).run();
        } catch (GameQuitException e) {
            System.out.println(e.getMessage() + " Game ended early.");
        }
    }
}
