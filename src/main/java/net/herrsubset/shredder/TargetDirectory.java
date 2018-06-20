package net.herrsubset.shredder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TargetDirectory {
    private final Path targetDir;

    public TargetDirectory(Path targetDir) {
        this.targetDir = targetDir;
    }

    public void write(File f, FileName name) {
        Path target = targetDir.resolve(name.toString());
        try {
            Path copy = Files.copy(f.toPath(), target);
            System.out.println("Saved file at: " + copy);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while copying file");
        }
    }
}
