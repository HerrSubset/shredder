package net.herrsubset.shredder;

import java.time.LocalDate;

public interface Prompt {

    String string(String prompt);
    LocalDate date(String prompt);
}
