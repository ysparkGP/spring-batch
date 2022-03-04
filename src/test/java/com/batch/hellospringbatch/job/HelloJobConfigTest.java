package com.batch.hellospringbatch.job;

import com.batch.hellospringbatch.BatchTestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBatchTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HelloJobConfig.class, BatchTestConfig.class})
public class HelloJobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void success() throws Exception {
        // when
        // jobLauncherTestUtils 의 setJob 메서드는 @SpringBootTest 를 통해서
        // JobLauncherTestUtils 가 오토와이어링 될 때, job 들을 자동으로 와이어링 해준다.
        // setJob 에도 Autowired 어노테이션이 걸려있기 때문에.
        // 그래서 @ContextConfiguration 에 HelloJobConfig 만 등록을 해줘야 다른 잡들을
        // 쳐다보지 않게 된다.
        JobExecution execution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);

    }
}
