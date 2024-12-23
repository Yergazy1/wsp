package classes;
import java.util.ArrayList;
import java.util.List;

import exceptions.NonResearcherException;


public class ResearchProject {
	private String nameOfProject;
	private String description;
	private List<Researcher> teamMembers;

	public ResearchProject(String nameOfProject, String description){
		this.nameOfProject = nameOfProject;
		this.description = description;
		this.teamMembers = new ArrayList<>();
	}
	public void addTeamMember(Researcher researcher) throws NonResearcherException {
		if (researcher instanceof Researcher) {
			teamMembers.add(researcher);
		} else {
			throw new NonResearcherException("Only researchers can join the research project.");
		}
	}

	@Override
	public String toString() {
		return "ResearchProject{name='" + nameOfProject + "', description='" + description + "', teamSize=" + teamMembers.size() + "}";
	}
}