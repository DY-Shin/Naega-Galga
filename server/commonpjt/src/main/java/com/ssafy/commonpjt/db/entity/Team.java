package com.ssafy.commonpjt.db.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private String id;

    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<Member> members;

}
