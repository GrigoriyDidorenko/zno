package ua.com.zno.online.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by quento on 26.03.17.
 */

@Entity
@Table(catalog = "zno", name = "subjects")
public class Subject extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private Set<Test> tests;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                '}';
    }
}
