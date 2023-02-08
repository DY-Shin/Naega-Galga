package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Timestamp reserveAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_index", referencedColumnName = "user_index", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_index", referencedColumnName = "user_index")
    private User guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_index", referencedColumnName = "product_index")
    private Product product;
}