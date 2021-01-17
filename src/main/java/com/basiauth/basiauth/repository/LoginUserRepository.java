package com.basiauth.basiauth.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.basiauth.basiauth.entity.LoginUser;

@Repository
public interface LoginUserRepository extends CrudRepository<LoginUser,Long> {
	public LoginUser findOneByUserId(String userId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE m_user SET PASSWORD = :password WHERE USER_ID = :userId",nativeQuery = true)
	public void updatePassword(@Param(value = "userId") String userId, @Param(value = "password") String password);
}
