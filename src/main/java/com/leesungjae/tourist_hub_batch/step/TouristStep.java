package com.leesungjae.tourist_hub_batch.step;


import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TouristStep {
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet tasklet) {
        return new StepBuilder("myTasklet", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();

    }
}
