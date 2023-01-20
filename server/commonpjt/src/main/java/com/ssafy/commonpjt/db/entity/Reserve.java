package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reserve")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_index", nullable = false)
    private Integer reserveIndex;

    private LocalDate reserveDate;

    private LocalTime reserveTime;

    @ManyToOne
    @JoinColumn(name = "chat_index", referencedColumnName = "chat_index")
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "reserve")
    private List<Meeting> meeting = new ArrayList<>();
}
