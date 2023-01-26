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
public class WishList {
    @Id
    @GeneratedValue
    private int wishListIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;
}
