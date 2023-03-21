package com.totoblog.data.repository;

import com.totoblog.data.dto.response.KeywordNativeDTO;
import com.totoblog.data.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {


    /*** 인기 검색어 10개 조회 ***/
    @Query(nativeQuery = true,
            value ="select keyword, cnt from (select keyword, count(*) as cnt from keyword group by keyword) tb_cnt order by cnt desc limit 10")
    List<KeywordNativeDTO> getBest10KeywordList();



}