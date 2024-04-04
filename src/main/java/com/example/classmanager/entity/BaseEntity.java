package com.example.classmanager.entity;

import com.example.classmanager.entity.enums.EGender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@MappedSuperclass
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private LocalDateTime dateOfBirth;

    private EGender gender;

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
