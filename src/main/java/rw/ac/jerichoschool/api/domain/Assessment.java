package rw.ac.jerichoschool.api.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assessment")
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "assessment_date")
    private LocalDate assessmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AssessmentStatus status;

    @Column(name = "term")
    private String term;

    @Column(name = "max_marks")
    private int maxMarks;

    @ManyToOne
    @JoinColumn(name = "pupil_id")
    private Pupil pupil;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public UUID getId() {
        return id;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public AssessmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssessmentStatus status) {
        this.status = status;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
