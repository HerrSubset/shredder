package net.herrsubset.shredder;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CliWizardTest {

    @Test
    public void wizardStopsWhenNoCorrespondentIsGiven() {
        LinkedList<String> strings = linkedList("Filename", "");
        LinkedList<LocalDate> dates = linkedList();
        TestPrompt prompt = new TestPrompt(strings, dates);
        CliWizard wizard = new CliWizard(prompt);

        FileName fileName = wizard.createFileName();

        FileName expectedFileName = new FileName("Filename", "", linkedList(), null);

        assertEquals(expectedFileName, fileName);
    }

    @Test
    public void wizardAddsCorrespondentWhenNoTagsOrDateAreGiven() {
        LinkedList<String> strings = linkedList("tax return", "IRS");
        LinkedList<LocalDate> dates = linkedList();
        TestPrompt prompt = new TestPrompt(strings, dates);
        CliWizard wizard = new CliWizard(prompt);

        FileName fileName = wizard.createFileName();

        FileName expectedFileName = new FileName("tax return", "IRS", linkedList(), null);

        assertEquals(expectedFileName, fileName);
    }

    @Test
    public void wizardAddsTagsWhenGiven() {
        LinkedList<String> strings = linkedList("tax return", "IRS", "financial", "taxes");
        LinkedList<LocalDate> dates = linkedList();
        TestPrompt prompt = new TestPrompt(strings, dates);
        CliWizard wizard = new CliWizard(prompt);

        FileName fileName = wizard.createFileName();

        FileName expectedFileName = new FileName("tax return", "IRS", linkedList("financial", "taxes"), null);

        assertEquals(expectedFileName, fileName);
    }

    @Test
    public void wizardAddsDateWhenGiven() {
        LinkedList<String> strings = linkedList("tax return", "IRS", "financial", "taxes");
        LinkedList<LocalDate> dates = linkedList(LocalDate.of(2018, 06, 23));
        TestPrompt prompt = new TestPrompt(strings, dates);
        CliWizard wizard = new CliWizard(prompt);

        FileName fileName = wizard.createFileName();

        FileName expectedFileName = new FileName("tax return", "IRS", linkedList("financial", "taxes"), LocalDate.of(2018, 06, 23));

        assertEquals(expectedFileName, fileName);
    }


    /******************************************************
     * Utilities
     *****************************************************/
    private <T> LinkedList<T> linkedList(T... items) {
        LinkedList<T> result = new LinkedList<>();
        for (T item : items) {
            result.add(item);
        }
        return result;
    }

    private class TestPrompt implements Prompt {

        private final Queue<String> strings;
        private final Queue<LocalDate> dates;

        TestPrompt(Queue<String> strings, Queue<LocalDate> dates) {
            this.strings = strings;
            this.dates = dates;
        }

        @Override
        public String string(String prompt) {
            return strings.poll();
        }

        @Override
        public LocalDate date(String prompt) {
            return dates.poll();
        }
    }
}