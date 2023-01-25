package com.ssafy.commonpjt.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue
    private int productIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Building building;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Option option;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private User user;

    @Column(nullable = false)
    private String productDetail;

    @Column(nullable = false)
    private String productFloor;

    @Column(nullable = false)
    private String productType;

    @Column(nullable = false)
    private String productPrice;

    @Column(nullable = true)
    private int productManageCost ;

    @Column(nullable = false)
    private String productSize;

    @Column(nullable = false)
    private String productRooms;

    @Column(nullable = false)
    private String productDirection;

//    @Column(nullable = false)
//    private String productPhoto;

    @Column(nullable = false)
    private String productAnimal;
}
