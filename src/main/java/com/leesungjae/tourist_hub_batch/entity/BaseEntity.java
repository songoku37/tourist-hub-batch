package com.leesungjae.tourist_hub_batch.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Column(updatable = false) // update가 일어나지 않고 null을 허용하지 않는다. (등록 시각이기 때문)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist // INSERT전 등록 및 수정시각 설정
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    @PreUpdate // UPDATE후 수정시각 설정
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
