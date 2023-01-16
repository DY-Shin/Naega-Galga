package com.ssafy.commonpjt.db.repository;

import com.ssafy.commonpjt.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findById(String s);



    @Query(value = "select m from Member m where m.id=:id")
    public Member findBymemid(@Param("id") String isdf);

    @Modifying
    @Query(value = "update x from Member y where y.id=:id")
    public Member update(@Param("id") String xf);


//    @Query(value = "select m from Member m join fetch team ")
//    List<Member> df()
}
