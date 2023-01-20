package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_index", nullable = false)
    private Integer userIndex;

    @OneToMany(mappedBy = "user")
    private List<WishList> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Product> product = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<ChatRoom> seller = new ArrayList<>();

    @OneToMany(mappedBy = "buyer")
    private List<ChatRoom> buyer = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<ChatMessage> sender = new ArrayList<>();
}