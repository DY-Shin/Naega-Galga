package com.ssafy.commonpjt.db.entity;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @Column(name = "member_id")
    private String id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;



}
