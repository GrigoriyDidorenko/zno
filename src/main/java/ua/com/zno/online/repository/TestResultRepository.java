package ua.com.zno.online.repository;

import ua.com.zno.online.dto.statistics.SubjectStatistics;
import ua.com.zno.online.dto.statistics.TestStatistics;
import ua.com.zno.online.domain.TestResult;

import java.util.List;

/**
 * Created by quento on 12.04.17.
 */


public interface TestResultRepository extends AbstractRepository<TestResult> {

    List<SubjectStatistics> getSubjectStatistics(long userId);

    List<TestStatistics> getTestsStatisticsBySubject(long userId, long subjectId);

}
