package com.likelion.pbl.member.domain;

import com.likelion.pbl.assignment.domain.Assignment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String major;

    private String part;

    private int generation;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType roleType;

    @Column(name = "student_id")
    private String studentId;

    private String position;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    protected Member() {
    }

    public Member(
            String name,
            String major,
            String part,
            int generation,
            RoleType roleType,
            String studentId,
            String position
    ) {
        this.name = name;
        this.major = major;
        this.part = part;
        this.generation = generation;
        this.roleType = roleType;
        this.studentId = studentId;
        this.position = position;
    }

    public void updateInfo(String name, String major, String part) {
        this.name = name;
        this.major = major;
        this.part = part;
    }

    public void updateStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void updatePosition(String position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public String getPart() {
        return part;
    }

    public int getGeneration() {
        return generation;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPosition() {
        return position;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
}
