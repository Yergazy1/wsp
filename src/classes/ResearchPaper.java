package classes;
import enums.CitationFormat;

import java.io.Serializable;
import java.util.Date;

public class ResearchPaper implements Serializable,Comparable<ResearchPaper> {
    private String title;
    private String authors;
    private String journal;
    private String doi;
    private int pages;
    private int citations;
    private Date publicationDate;

    public ResearchPaper(String title, String authors, String journal, String doi, int pages, int citations, Date publicationDate) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.doi = doi;
        this.pages = pages;
        this.citations = citations;
        this.publicationDate = publicationDate;
    }

    public int getCitations() {
        return citations;
    }

    public String getCitation(CitationFormat format) {
        if (format == CitationFormat.PLAIN_TEXT) {
            return title + ", " + authors + ", " + journal + ", DOI: " + doi;
        } else {
            return String.format("@article{%s, title={%s}, author={%s}, journal={%s}, doi={%s}}",
                    doi, title, authors, journal, doi);
        }
    }

    @Override
    public String toString() {
        return String.format("ResearchPaper{title='%s', citations=%d, date=%s}", title, citations, publicationDate);
    }
    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(this.citations, other.citations);
    }

    public Date getPublicationDate() {
        return publicationDate;
    }
}