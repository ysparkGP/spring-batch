package com.batch.hellospringbatch.job;


import com.batch.hellospringbatch.core.domain.PlainText;
import com.batch.hellospringbatch.core.domain.ResultText;
import com.batch.hellospringbatch.core.repository.PlainTextRepository;
import com.batch.hellospringbatch.core.repository.ResultTextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class PlainTextJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PlainTextRepository plainTextRepository;
    private final ResultTextRepository resultTextRepository;

    @Bean("plainTextJob")
    public Job plainTextJob(Step plainTextStep){

        return jobBuilderFactory.get("plainTextJob")
                .incrementer(new RunIdIncrementer())
                .start(plainTextStep)
                .build();
    }

    // 위 Job 이 실행되는 동안만 살아있게 설정
    @JobScope
    @Bean("plainTextStep")
    public Step plainTextStep(ItemReader plainTextReader,
                              ItemProcessor plainTextProcessor,
                              ItemWriter plainTextWriter){
        return stepBuilderFactory.get("plainTextStep")
                // 읽어올 타입, 프로세싱할 타입
                .<PlainText, String>chunk(5)
                .reader(plainTextReader)
                .processor(plainTextProcessor)
                .writer(plainTextWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<PlainText> plainTextReader(){
        return new RepositoryItemReaderBuilder<PlainText>()
                .name("plainTextReader")
                .repository(plainTextRepository)
                .methodName("findBy")
                // commitInterval 한번에 읽을 아이템의 갯수
                .pageSize(5)
                // 조건을 리스트로 설정
                .arguments(java.util.List.of())
                // 어떤 순서대로 데이터를 읽을지 지정
                .sorts(Collections.singletonMap("id", Sort.Direction.DESC))
                .build();
    }

    @StepScope
    @Bean
    // 프로세싱하기 전의 타입, 프로세싱할 타입
    public ItemProcessor<PlainText, String> plainTextProcessor(){
//        return new ItemProcessor<PlainText, String>() {
//            @Override
//            public String process(PlainText item) throws Exception {
//                return null;
//            }
//        }
        return item -> "processed " + item.getText();
    }


    @StepScope
    @Bean
    public ItemWriter<String> plainTextWriter(){
//        return new ItemWriter<String>() {
//            @Override
//            public void write(List<? extends String> items) throws Exception {
//
//            }
//        }
        // 람다식으로 표현
        return items -> {
            items.forEach(item -> resultTextRepository.save(new ResultText(null, item)));
            System.out.println("=== chunk is finished");
        };
    }

}
