package net.herrsubset.shredder;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        CliPrompt prompt = new CliPrompt();
        CliWizard wizard = new CliWizard(prompt);


        Path scanDir = prompt.path("Enter source folder: ");
        Path targetDir = prompt.path("Enter the target folder: ");

        TargetDirectory targetDirectory = new TargetDirectory(targetDir);

        for (File file : scanDir.toFile().listFiles()) {
            display(file);
            FileName fileName = wizard.createFileName();
            targetDirectory.write(file, fileName);
        }
    }

    private static void display(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while opening file");
        }
    }
}
