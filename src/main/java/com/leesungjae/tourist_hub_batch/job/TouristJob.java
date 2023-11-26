package com.leesungjae.tourist_hub_batch.job;

import com.leesungjae.tourist_hub_batch.step.TouristStep;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class TouristJob {

    @Autowired
    TouristStep step;

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet tasklet) {


        return new JobBuilder("myJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step.step(jobRepository, transactionManager,tasklet))
                .build();
    }
}
