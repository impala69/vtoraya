package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Will", "Smith", (byte) 50);
        userService.saveUser("Rassel", "Crow", (byte) 90);
        userService.saveUser("Vova", "Ivanov", (byte) 10);
        userService.saveUser("Walter", "White", (byte) 45);
        userService.removeUserById(3);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
