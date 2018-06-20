package net.herrsubset.shredder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CliPrompt implements Prompt {

    private Scanner scanner = new Scanner(System.in);


    @Override
    public String string(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public LocalDate date(String prompt) {
        do {
            String input = string(prompt);
            if (input.isEmpty()) return null;

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format, try again (leave blank to skip)");
            }
        } while (true);
    }

    public Path path(String prompt) {
        String path = string(prompt);
        return Paths.get(path);
    }
}
