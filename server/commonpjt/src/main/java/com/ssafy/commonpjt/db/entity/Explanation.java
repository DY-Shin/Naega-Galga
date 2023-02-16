package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Explanation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "explanation_index", nullable = false)
    Integer explanationIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_index", referencedColumnName = "meeting_index")
    Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_index", referencedColumnName = "user_index")
    User reserveUser;

}
