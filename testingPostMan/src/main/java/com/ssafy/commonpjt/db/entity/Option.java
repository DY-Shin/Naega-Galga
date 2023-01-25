package com.ssafy.commonpjt.db.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Option {
    @Id
    @GeneratedValue
    private int optionIndex;

    @Column(nullable = true)
    @Builder.Default
    private int optionAirConditioner = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionFridge = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionWashingMachine = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionGasStove = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionInduction = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionMicrowave = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionDesk = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionWifi = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionCloset = 0;

    @Column(nullable = true)
    @Builder.Default
    private int optionBed = 0;
}
