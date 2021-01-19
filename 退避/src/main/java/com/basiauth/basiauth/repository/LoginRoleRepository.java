package com.basiauth.basiauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.basiauth.basiauth.entity.Role;

@Repository
public interface LoginRoleRepository extends CrudRepository<Role,Long> {

	//findOneByRoleidだとエラー。(entityのRoleをroleIdで宣言しているため)
	public Role findOneByRoleId(String roleId);
}
