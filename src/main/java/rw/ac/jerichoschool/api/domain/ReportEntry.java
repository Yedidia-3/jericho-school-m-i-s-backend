package rw.ac.jerichoschool.api.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "report_entry")
public class ReportEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "competence")
    private String competence;

    @Column(name = "comment")
    private String comment;

    @Column(name = "marks")
    private int marks;

    @Column(name = "entered_date")
    private LocalDate enteredDate;

    @ManyToOne
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;

    public UUID getId() {
        return id;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public LocalDate getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(LocalDate enteredDate) {
        this.enteredDate = enteredDate;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
}
