package com.ecagataydogan.kredinbizdeservice.repository;

import com.ecagataydogan.kredinbizdeservice.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
}
