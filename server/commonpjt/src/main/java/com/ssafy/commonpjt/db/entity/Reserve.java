package com.ssafy.commonpjt.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "reserve")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_index")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_index", referencedColumnName = "chat_index")
    private ChatRoom chatIndex;

    @Column(nullable = false)
    private LocalDate reserveDate;

    @Column(nullable = false)
    private LocalTime reserveTime;

    @Builder
    public Reserve(ChatRoom chatIndex, LocalDate reserveDate, LocalTime reserveTime) {
        this.chatIndex = chatIndex;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
    }
}
