package com.longketdan.longket.v1.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "resource")
public class Resource extends BaseEntity{
    /**
     * 리소스 명
     */
    @Column(name = "name")
    private String name;

    /**
     * 리소스 url
     */
    @Column(name = "rsc_url")
    private String rscUrl;

    /**
     * 리소스 크기(byte)
     */
    @Column(name = "length")
    private Long length;
}
