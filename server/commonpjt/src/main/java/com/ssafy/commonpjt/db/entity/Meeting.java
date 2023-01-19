package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "meeting")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting {
    @Id
    @Column(name = "meeting_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer meetingIndex;

    @Column(length = 1000)
    private String meetingUrl;

    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "reserve_index", referencedColumnName = "reserve_index")
    private Reserve reserve;
}
