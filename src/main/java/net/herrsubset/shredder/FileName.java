package net.herrsubset.shredder;

import com.google.common.base.Joiner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class takes some info about a file and creates a
 * filename based on that. It follows the rules set at
 * https://paperless.readthedocs.io/en/latest/guesswork.html
 * in order to be compatible with paperless.
 */
public class FileName {

    private final String title;
    private final String correspondent;
    private final List<String> tags;
    private final LocalDate date;

    public FileName(String title, String correspondent, List<String> tags, LocalDate date) {
        this.title = checkNotNull(title);
        checkArgument(!title.isEmpty());
        this.correspondent = checkNotNull(correspondent);
        this.tags = tags.stream()
                .filter(tag -> tag != null)
                .filter(tag -> !tag.isEmpty())
                .collect(Collectors.toList());
        this.date = date;
    }


    @Override
    public String toString() {
        String name = title;
        if (!correspondent.isEmpty()) {
            name = correspondent + " - " + name;

            if (!tags.isEmpty()) {
                name = name + " - " + Joiner.on(",").join(tags);
            }
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyMMdd");
                name = date.format(formatter) + "Z - " + name;
            }
        }
        return name + ".pdf";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof  FileName)) return false;

        FileName other = (FileName) o;
        if (!this.title.equals(other.title)) return false;
        if (!this.correspondent.equals(other.correspondent)) return false;
        if (!this.tags.equals(other.tags)) return false;
        if (this.date != null && other.date == null) return false;
        if (this.date == null && other.date != null) return false;
        if (this.date != null && other.date != null && !this.date.equals(other.date)) return false;

        return true;
    }
}
