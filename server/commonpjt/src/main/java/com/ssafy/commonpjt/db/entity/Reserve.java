package com.ssafy.commonpjt.db.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reserve")
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_index")
    private Integer reserveIndex;

    @CreatedDate
    private Date reserveDate;

    private DateTime
}
