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


    /**
     * Schema:
     * {
     *     "adviceTitle": "String",
     *     "advices": "Map<String, String>",
     *     "additionalResourcesTitle": "String"
     *     "additionalResources": "List<String>"
     * }
     *
     * Example:
     *
     * {
     *     "adviceTitle": "Корисні поради",
     *     "advices": {
     *         "Елена Кононенко, учитель математики Технического лицея НТУУ 'КПИ'" : "Прогоните дополнительно тесты по
     *         самым проблемным темам, а перед тестированием все полностью, чтобы довести навыки до автоматизма.",
     *         "Елена Кирдягенко" "блааа"
     *     },
     *     "additionalResourcesTitle" : "Додаткові матеріали",
     *     "additionalResources": [
     *          "Критерії оцінювання завдання з розгорнутою відповіддю з математики (2016 рік)",
     *          "Критерії оцінювання завдання з розгорнутою відповіддю з математики (2017 рік)"
     *     ]
     * }
     */
    @Column(name = "article", nullable = false, length = 1000)
    private String articleJSON;

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

    public String getArticleJSON() {
        return articleJSON;
    }

    public void setArticleJSON(String articleJSON) {
        this.articleJSON = articleJSON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Subject subject = (Subject) o;

        return name != null ? name.equals(subject.name) : subject.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
