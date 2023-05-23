package com.example.house_kt_demo.repo

import com.example.house_kt_demo.entity.Housing
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import org.springframework.data.repository.PagingAndSortingRepository

interface HousingRepo:JpaRepositoryImplementation<Housing, Int>, PagingAndSortingRepository<Housing, Int> {
}