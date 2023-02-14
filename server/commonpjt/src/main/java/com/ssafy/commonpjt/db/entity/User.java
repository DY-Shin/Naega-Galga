package com.ssafy.commonpjt.db.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_index")
    private Integer userIndex;

    @Column(nullable = false, length = 50, unique = true)
    private String userId;

    @Column(nullable = false, length = 100)
    private String userPassword;

    @Column(nullable = false, length = 50)
    private String userPhone;

    @Column(name= "user_name" ,nullable = false, length = 50)
    private String name;

    @Column(length = 50, unique = true)
    private String corporateRegistrationNumber;

    @Column(nullable = false, length = 100)
    private String userAddress;

    @OneToMany(mappedBy = "productSeller", orphanRemoval = true)
    private List<Product> productSeller = new ArrayList<>();

    @OneToMany(mappedBy = "seller", orphanRemoval = true)
    private List<ChatRoom> seller = new ArrayList<>();

    @OneToMany(mappedBy = "buyer", orphanRemoval = true)
    private List<ChatRoom> buyer = new ArrayList<>();

    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    private List<ChatMessage> sender = new ArrayList<>();

    @OneToMany(mappedBy = "guest", orphanRemoval = true)
    private List<Meeting> meetingGuest =  new ArrayList<>();

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private List<Meeting> meetingOwner = new ArrayList<>();

    @OneToMany(mappedBy = "reserveUser", orphanRemoval = true)
    private List<Explanation> reserveUser = new ArrayList<>();

    // 권한 설정을 외래 키로 부여
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getUserName(){
        return name;
    }

    @Override
    public String getUsername(){
        return userId;
    }

    @Override
    public String getPassword(){
        return userPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 비밀번호 수정
    public void updatePassword(PasswordEncoder passwordEncoder, String userPassword) {
        this.userPassword = passwordEncoder.encode(userPassword);
    }

    // 패스워드 암호화
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(userPassword);
    }

    // 패스워드 일치 확인
    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword) {
        return passwordEncoder.matches(checkPassword, getPassword());
    }
}
