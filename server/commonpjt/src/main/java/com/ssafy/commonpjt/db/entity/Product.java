package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.boot.model.naming.ImplicitNameSource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_index", nullable = false)
    private Integer ProductIndex;

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

    @Column(nullable = false)
    private String productPhoto;

    @Column(nullable = false)
    private String productAnimal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_index", referencedColumnName = "building_index")
    private Building building;

    @OneToOne
    @JoinColumn(name = "option_index", referencedColumnName = "option_index")
    private Options options;

    @OneToMany(mappedBy = "product")
    private List<WishList> wishList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "seller_index", referencedColumnName = "user_index")
    private User user;
}