package ua.com.zno.online.dto.entities;

import ua.com.zno.online.dto.AbstractDTO;

import java.util.Set;

/**
 * Created by quento on 26.03.17.
 */
public class SubjectDTO extends AbstractDTO {

    private String name;

    private Set<TestDTO> tests;

    private String articleJSON;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TestDTO> getTests() {
        return tests;
    }

    public void setTests(Set<TestDTO> tests) {
        this.tests = tests;
    }

    public String getArticleJSON() {
        return articleJSON;
    }

    public void setArticleJSON(String articleJSON) {
        this.articleJSON = articleJSON;
    }


}
