package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "building")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="building_index", nullable = false)
    private Integer buildingIndex;

    @Column(nullable = false)
    private String buildingAddress;

    @Column(nullable = false)
    private String buildingRoadAddress;

    @Column(nullable = false)
    private String buildingName;

    @Column(nullable = false)
    private boolean buildingElevator;

    @Column(nullable = false)
    private boolean buildingParking;

    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
    private List<Product> product = new ArrayList<>();
}