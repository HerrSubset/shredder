package net.herrsubset.shredder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class FileNameTest {

    private static List<String> EMPTY_LIST = new ArrayList<>();

    @Test
    public void fileNameWithOnlyATitleReturnsThatTitle() {
        String title = "my tax bill";

        FileName fileName = new FileName(title, "", EMPTY_LIST, null);

        assertEquals("my tax bill.pdf", fileName.toString());
    }

    @Test
    public void titleCanNotBeNull() {
        assertThrows(RuntimeException.class, fileName(null, "", EMPTY_LIST, null));
    }

    @Test
    public void emptyTitleIsNotAllowed() {
        assertThrows(IllegalArgumentException.class, fileName("", "", EMPTY_LIST, null));
    }

    @Test
    public void correspondentIsPrependedToTitle() {
        String title = "my car insurance";
        String correspondent = "big insurance company";

        FileName fileName = new FileName(title, correspondent, new ArrayList<>(), null);

        assertEquals("big insurance company - my car insurance.pdf", fileName.toString());
    }

    @Test
    public void correspondentCanNotBeNull() {
        assertThrows(RuntimeException.class, fileName("hi", null, EMPTY_LIST, null));
    }

    @Test
    public void singleTagIsAppendedAfterTitle() {
        String title = "my account overview";
        String correspondent = "j.p. morgan";
        String tag = "financial";

        FileName fileName = new FileName(title, correspondent, asList(tag), null);

        assertEquals("j.p. morgan - my account overview - financial.pdf", fileName.toString());
    }

    @Test
    public void multipleTagsAreAppendedAfterTitleAndSeparatedByCommas() {
        String title = "my pay slip";
        String correspondent = "amazon";
        String tag1 = "financial";
        String tag2 = "work";

        FileName fileName = new FileName(title, correspondent, asList(tag1, tag2), null);

        assertEquals("amazon - my pay slip - financial,work.pdf", fileName.toString());
    }

    @Test
    public void emptyTagsAreSkipped() {
        String title = "my pay slip";
        String correspondent = "amazon";
        String tag1 = "";
        String tag2 = "work";

        FileName fileName = new FileName(title, correspondent, asList(tag1, tag2), null);

        assertEquals("amazon - my pay slip - work.pdf", fileName.toString());
    }

    @Test
    public void nullTagsAreSkipped() {
        String title = "my pay slip";
        String correspondent = "amazon";
        String tag1 = "financial";
        String tag2 = null;

        FileName fileName = new FileName(title, correspondent, asList(tag1, tag2), null);

        assertEquals("amazon - my pay slip - financial.pdf", fileName.toString());
    }

    @Test
    public void tagsCanNotBeNull() {
        assertThrows(NullPointerException.class, fileName("t", "c", null, null));
    }

    @Test
    public void tagsAreOmittedWhenNoCorrespondentIsPresent() {
        String title = "gardener invoice";
        String correspondent = "";
        String tag1 = "expenses";

        FileName fileName = new FileName(title, correspondent, asList(tag1), null);

        assertEquals("gardener invoice.pdf", fileName.toString());
    }

    @Test
    public void dateIsWrittenToCorrectFormat() {
        String title = "gardener invoice";
        String correspondent = "mr green";
        String tag1 = "expenses";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName = new FileName(title, correspondent, asList(tag1), date);

        assertEquals("20180219Z - mr green - gardener invoice - expenses.pdf", fileName.toString());
    }

    @Test
    public void dateCanBeAddedWhenNoTagsAreGiven() {
        String title = "gardener invoice";
        String correspondent = "mr green";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName = new FileName(title, correspondent, EMPTY_LIST, date);

        assertEquals("20180219Z - mr green - gardener invoice.pdf", fileName.toString());
    }

    @Test
    public void dateIsOmittedWhenNoCorrespondentIsPresent() {
        String title = "gardener invoice";
        String correspondent = "";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName = new FileName(title, correspondent, EMPTY_LIST, date);

        assertEquals("gardener invoice.pdf", fileName.toString());
    }

    @Test
    public void fileNameIsNeverEqualToNull() {
        FileName fileName = new FileName("title", "", EMPTY_LIST, null);
        assertFalse(fileName.equals(null));
    }

    @Test
    public void fileNamesWithAllFieldsEqualAreEqual() {
        String title = "gardener invoice";
        String correspondent = "mr green";
        String tag = "expenses";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName1 = new FileName(title, correspondent, asList(tag), date);
        FileName fileName2 = new FileName(title, correspondent, asList(tag), date);

        assertEquals(fileName1, fileName2);
    }

    @Test
    public void fileNamesWithAllFieldsEqualButNoDatesAreEqual() {
        String title = "gardener invoice";
        String correspondent = "mr green";
        String tag = "expenses";

        FileName fileName1 = new FileName(title, correspondent, asList(tag), null);
        FileName fileName2 = new FileName(title, correspondent, asList(tag), null);

        assertEquals(fileName1, fileName2);
    }

    @Test
    public void fileNamesWithDifferingTitlesAreNotEqual() {
        String title1 = "Gardener invoice";
        String title2 = "invoice";
        String correspondent = "mr green";
        String tag = "expenses";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName1 = new FileName(title1, correspondent, asList(tag), date);
        FileName fileName2 = new FileName(title2, correspondent, asList(tag), date);

        assertNotEquals(fileName1, fileName2);
    }

    @Test
    public void fileNamesWithDifferingCorrespondentsAreNotEqual() {
        String title = "Gardener invoice";
        String correspondent1 = "mr green";
        String correspondent2 = "mr red";
        String tag = "expenses";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName1 = new FileName(title, correspondent1, asList(tag), date);
        FileName fileName2 = new FileName(title, correspondent2, asList(tag), date);

        assertNotEquals(fileName1, fileName2);
    }

    @Test
    public void fileNameWithExtraTagIsNotEqual() {
        String title = "Gardener invoice";
        String correspondent = "mr green";
        String tag1 = "expenses";
        String tag2 = "gardening";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName1 = new FileName(title, correspondent, asList(tag1), date);
        FileName fileName2 = new FileName(title, correspondent, asList(tag1, tag2), date);

        assertNotEquals(fileName1, fileName2);
    }

    @Test
    public void fileNameWithoutTagsIsNotEqual() {
        String title = "Gardener invoice";
        String correspondent = "mr green";
        String tag = "expenses";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName1 = new FileName(title, correspondent, asList(tag), date);
        FileName fileName2 = new FileName(title, correspondent, asList(), date);

        assertNotEquals(fileName1, fileName2);
    }

    @Test
    public void fileNameWithoutDateIsNotEqual() {
        String title = "Gardener invoice";
        String correspondent = "mr green";
        String tag = "expenses";
        LocalDate date = LocalDate.of(2018, 02, 19);

        FileName fileName1 = new FileName(title, correspondent, asList(tag), date);
        FileName fileName2 = new FileName(title, correspondent, asList(tag), null);

        assertNotEquals(fileName1, fileName2);
    }

    @Test
    public void fileNameWithDifferentDateIsNotEqual() {
        String title = "Gardener invoice";
        String correspondent = "mr green";
        String tag = "expenses";
        LocalDate date1 = LocalDate.of(2018, 02, 19);
        LocalDate date2 = LocalDate.of(2019, 03, 9);

        FileName fileName1 = new FileName(title, correspondent, asList(tag), date1);
        FileName fileName2 = new FileName(title, correspondent, asList(tag), date2);

        assertNotEquals(fileName1, fileName2);
    }


    /***************************************
     * Utilities
     **************************************/
    private Executable fileName(String title, String correspondent, List<String> tags, LocalDate date) {
        return () -> new FileName(title, correspondent, tags, date);
    }
}