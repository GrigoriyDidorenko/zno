package ua.com.zno.online.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by quento on 26.03.17.
 */
@Configuration
@EncryptablePropertySource(name = "application-prod", value = "classpath:application-prod.yml")
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "mailSendingExecutorPool")
    public TaskExecutor mailSendingExecutorPool() {
        return singleTaskExecutor();
    }

    private static TaskExecutor singleTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        return executor;
    }
}
