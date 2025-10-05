package com.ayhanunlu.repository;

import com.ayhanunlu.data.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankRepository extends JpaRepository<BankEntity,Long> {
}
