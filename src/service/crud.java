package service;

import menu.MenuAll;

import java.util.Scanner;

public interface crud<E> {
    void display();

    E creatNew(Scanner scanner);

    void add(Scanner scanner);

    void delete(Scanner scanner, MenuAll menu);

    void update(Scanner scanner);

}
