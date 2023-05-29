package com.binha.demo.repository;

import com.binha.demo.model.Member;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface MemberRepository extends CrudRepository<Member, String> {
}
