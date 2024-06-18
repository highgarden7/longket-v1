package com.longketdan.longket.v1.model.entity.community;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "crew")
@Entity
public class Crew {
    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private String name;

    private Long capacity;

    private String banner;

    private boolean isOpened;

    private List<String> tagList;

    private String instaId;
}
