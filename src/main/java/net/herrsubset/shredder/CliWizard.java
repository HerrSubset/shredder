package net.herrsubset.shredder;

import java.time.LocalDate;
import java.util.ArrayList;

public class CliWizard {
    private final Prompt prompt;

    public CliWizard(Prompt prompt) {
        this.prompt = prompt;
    }

    public FileName createFileName() {
        String title = prompt.string("Enter the file name: ");
        String correspondent = prompt.string("Enter the correspondent: ");

        ArrayList<String> tags = new ArrayList<>();
        boolean running = true;
        while (running) {
            String tag = prompt.string("Enter a tag: (blank goes to next step) ");
            if (tag == null || tag.isEmpty()) {
                running = false;
            } else {
                tags.add(tag);
            }
        }

        LocalDate date = prompt.date("Enter the date (yyyy-mm-dd): ");

        return new FileName(title, correspondent, tags, date);
    }
}
