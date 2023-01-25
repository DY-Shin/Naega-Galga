package com.ssafy.commonpjt.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wish_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_list_index", nullable = false)
    private Integer wishListIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_index", referencedColumnName = "user_index")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_index", referencedColumnName = "product_index")
    private Product product;
}