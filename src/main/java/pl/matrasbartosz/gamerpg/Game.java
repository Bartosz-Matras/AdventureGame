package pl.matrasbartosz.gamerpg;


import org.springframework.stereotype.Service;
import pl.matrasbartosz.gamerpg.user.User;
import pl.matrasbartosz.gamerpg.user.UserService;

import java.util.Scanner;

@Service
public class Game {

    private static User user;

    private final UserService userService;

    public Game(UserService userService){
        this.userService = userService;
    }

    public void play() {
        boolean selectedOption = false;
        Scanner scanner = new Scanner(System.in);
        printStartInfo();

        while (!selectedOption) {
            System.out.print("\nOption: ");
            String outputString = scanner.nextLine();
            int outputInt;
            try {
                outputInt = Integer.parseInt(outputString);
            }catch (NumberFormatException e) {
                System.out.println("Wybrano niepoprawny numer\n");
                printStartInfo();
                continue;
            }

            switch (outputInt) {
                case 1 -> {
                    login();
                    selectedOption = true;
                }
                case 2 -> {
                    register();
                    selectedOption = true;
                }
                case 3 -> {
                    closeGame();
                    selectedOption = true;
                }
                default -> {
                    System.out.println("Wybrano niepoprawny numer\n");
                    printStartInfo();
                }
            }
        }
        scanner.close();
    }

    private void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nLogin to the system: ");
        System.out.print("Enter username or email: ");
        String login = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String email = login.contains("@gmail.com") ? login : "";
        String username = email.isEmpty() ? login : "";
        User user = this.userService.getUser(username, email, password);
        System.out.println(user);

        scanner.close();
    }

    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nEnter login: ");

        scanner.close();
        login();
    }

    public void closeGame() {
        System.out.println("closeGame");
    }


    public void printStartInfo() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Welcome to RPG Game, please register or login!");
        System.out.println("1. Login");
        System.out.println("2. Create account");
        System.out.println("3. Close");
    }

}
