package classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import exceptions.InvalidSupervisorException;

public class Researcher extends Employee {
    private List<ResearchPaper> researchPapers;
    private List<ResearchProject> researchProjects;

    public Researcher(String id, String userName, String email, String password) {
        super(id, userName, email, password);
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
    }

    public void addResearchPaper(ResearchPaper paper) {
        researchPapers.add(paper);
    }

    public void printPapers(Comparator<ResearchPaper> comparator) {
        researchPapers.sort(comparator);
        for (classes.ResearchPaper paper : researchPapers) {
            System.out.println(paper);
        }
    }

    public int calculateHIndex() {
        researchPapers.sort((p1, p2) -> Integer.compare(p2.getCitations(), p1.getCitations()));
        int hIndex = 0;
        for (int i = 0; i < researchPapers.size(); i++) {
            if (researchPapers.get(i).getCitations() >= (i + 1)) {
                hIndex = i + 1;
            }
        }
        return hIndex;
    }

    public void checkSupervise() throws InvalidSupervisorException {
        if (calculateHIndex() < 3) {
            throw new InvalidSupervisorException("Supervisor has wrong h-index");
        }
    }
    @Override
    public String toString() {
        return "Researcher{" +
                "Name=" + super.getName() +
                ", ID=" + getId() +
                '}';
    }
}