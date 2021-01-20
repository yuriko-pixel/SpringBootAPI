package com.basiauth.basiauth.repository;

import java.util.Date;

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

	//UPDATEやDELETE文には@Transactionalと＠Modifyingをセットでつける。じゃないとエラー。
	@Modifying
	@Transactional
	@Query(value = "UPDATE m_user SET PASSWORD = :password, pass_update_date = :passUpdateDate WHERE USER_ID = :userId",nativeQuery = true)
	public void updatePassword(@Param(value = "userId") String userId,
							   @Param(value = "password") String password,
							   @Param(value = "passUpdateDate") Date passUpdateDate);

	@Modifying
	@Transactional
	@Query(value = "UPDATE m_user SET login_miss_times = :loginMissTimes, unlock = :unlock WHERE user_id = :userId", nativeQuery=true)
	public void updateLogimMissTimes(@Param(value = "userId") String userId,
									@Param(value = "loginMissTimes") int loginMissTimes,
									@Param(value = "unlock") boolean unlock);

	@Modifying
	@Transactional
	@Query(value = "UPDATE m_user SET"
				 				+ " user_name = :userName,"
				 				+ " password = :password,"
				 				+ " pass_update_date = :passUpdateDate,"
				 				+ " login_miss_times = :loginMissTimes,"
				 				+ " unlock = :unlock,"
				 				+ " enabled = :enabled,"
				 				+ " user_due_date = :userDueDate,"
				 				+ " role_id = :roleId"
				 				+ " WHERE user_id = :userId",nativeQuery=true)
	public void updateuserInfo(@Param(value = "userId") String userId,
							   @Param(value = "userName") String userName,
							   @Param(value = "password") String password,
							   @Param(value = "passUpdateDate") Date passUpdateDate,
							   @Param(value = "loginMissTimes") int loginMissTimes,
							   @Param(value = "unlock") boolean unlock,
							   @Param(value = "enabled") boolean enabled,
							   @Param(value = "userDueDate") Date userDueDate,
							   @Param(value = "roleId") String roleId);


}
