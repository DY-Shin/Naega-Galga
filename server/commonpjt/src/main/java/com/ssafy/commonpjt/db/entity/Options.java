package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "options")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_index", nullable = false)
    private Integer optionIndex;

    @ColumnDefault("false")
    private boolean optionAirConditioner;

    @ColumnDefault("false")
    private boolean optionFridge;

    @ColumnDefault("false")
    private boolean optionWashingMachine;

    @ColumnDefault("false")
    private boolean optionGasStove;

    @ColumnDefault("false")
    private boolean optionInduction;

    @ColumnDefault("false")
    private boolean optionMicroWave;

    @ColumnDefault("false")
    private boolean optionDesk;

    @ColumnDefault("false")
    private boolean optionWifi;

    @ColumnDefault("false")
    private boolean optionCloset;

    @ColumnDefault("false")
    private boolean optionBed;
}