package com.ssafy.commonpjt.db.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.boot.model.naming.ImplicitNameSource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_index", nullable = false)
    private Integer productIndex;

    @Column(nullable = false)
    private String productDetail;

    @Column(nullable = false)
    private String productFloor;

    @Column(nullable = false)
    private String productType;

    @Column(nullable = false)
    private String productPrice;

    @ColumnDefault("0")
    private Integer productManageCost;

    @Column(nullable = false)
    private String productSize;

    @Column(nullable = false)
    private String productRooms;

    @Column(nullable = false)
    private String productDirection;


    @Column(nullable = true, columnDefinition = "TEXT")
    @Builder.Default
    private String productPhoto = "NULL";

    @Column(nullable = false)
    private String productAnimal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_index", referencedColumnName = "building_index")
    private Building building;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_index", referencedColumnName = "option_index")
    private Options options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_index", referencedColumnName = "user_index")
    private User productSeller;
}