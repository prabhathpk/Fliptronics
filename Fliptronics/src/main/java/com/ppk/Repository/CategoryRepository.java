package com.ppk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppk.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer>{

}
