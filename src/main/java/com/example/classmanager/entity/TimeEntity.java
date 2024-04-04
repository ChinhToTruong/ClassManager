package com.example.classmanager.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class TimeEntity {
    @CreatedDate
    private LocalDateTime createdAt;

    //    @Temporal(TemporalType.TIMESTAMP)
    @CreatedBy
    private String createdBy;

    //    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    //    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedBy
    private String modifiedBy;
}
