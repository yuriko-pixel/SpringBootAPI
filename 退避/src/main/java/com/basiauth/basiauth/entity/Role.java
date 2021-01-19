package com.basiauth.basiauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_role")
public class Role {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name = "role_id")
    @Getter
    @Setter
    private String roleId;

    @Column(name = "role_name")
    @Getter
    @Setter
    private String roleName;
}
