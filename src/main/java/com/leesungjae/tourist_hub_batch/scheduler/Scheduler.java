package com.leesungjae.tourist_hub_batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    private boolean executed = false;

//    @Scheduled(cron = "*/50 * * * * *") // 1분마다 실행
    @Scheduled(fixedDelay = Long.MAX_VALUE) // 한 번만 실행
    public void TouristDataJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {

        if(!executed){
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("requestTime", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters); // 실행시킬 잡, 실행시킬 잡에 대한 파라미터??
            executed = true;
        }

    }
}