package com.ssafy.commonpjt.db.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "meeting")
@Getter
@Setter
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
    private Timestamp reserveAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private User guest;
}
