package com.batch.hellospringbatch.job.parallel;

import com.batch.hellospringbatch.dto.AmountDto;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

/*
Step 을 동시에 처리하는 Parallel Step
단일 프로세스에서 Flow 를 사용해 스텝을 동시에 실행한다.
 */

@Configuration
@AllArgsConstructor
public class ParallelStepJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job parallelJob(Flow splitFlow){
        return jobBuilderFactory.get("parallelJob")
                .incrementer(new RunIdIncrementer())
                .start(splitFlow)
                .build()
                .build();
    }

    @Bean
    public Flow splitFlow(TaskExecutor taskExecutor,
                          Flow flowAmountFileStep,
                          Flow flowAnotherStep){
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(taskExecutor)
                // 스텝 분기 처리
                .add(flowAmountFileStep, flowAnotherStep)
                .build();
    }
    @Bean
    public Flow flowAmountFileStep(Step amountFileStep){
        return new FlowBuilder<SimpleFlow>("flowAmountFileStep")
                .start(amountFileStep)
                .end();
    }

    @Bean
    public Step amountFileStep(FlatFileItemReader<AmountDto> amountFileItemReader,
                                ItemProcessor<AmountDto, AmountDto> amountFileItemProcessor,
                                FlatFileItemWriter<AmountDto> amountFileItemWriter,
                                TaskExecutor taskExecutor){
        return stepBuilderFactory.get("multiThreadStep")
                .<AmountDto,AmountDto>chunk(10)
                .reader(amountFileItemReader)
                .processor(amountFileItemProcessor)
                .writer(amountFileItemWriter)
                .build();
    }

    @Bean
    public Flow flowAnotherStep(Step anotherStep){
        return new FlowBuilder<SimpleFlow>("anotherStep")
                .start(anotherStep)
                .build();
    }



    @Bean
    public Step anotherStep(){
        return stepBuilderFactory.get("anotherStep")
                .tasklet((contribution, chunkContext) -> {
                   Thread.sleep(50);
                    System.out.println("Another Step Completed. Thread = "+
                           Thread.currentThread().getName());
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
