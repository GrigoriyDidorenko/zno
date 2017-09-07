package ua.com.zno.online.repository;

import org.springframework.data.jpa.repository.Query;
import ua.com.zno.online.DTOs.statistic.SubjectStatistics;
import ua.com.zno.online.domain.TestResult;
import ua.com.zno.online.domain.user.User;

import java.util.List;

/**
 * Created by quento on 12.04.17.
 */


public interface TestResultRepository extends AbstractRepository<TestResult> {

    List<SubjectStatistics.TestStatistics> getStatisticsForUser(long userId);

}
