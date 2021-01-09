package com.basiauth.basiauth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="m_user")
public class LoginUser{

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name = "user_id")
    @Getter
    @Setter
    private String userId;

    @Column(name = "user_name")
    @Getter
    @Setter
    private String userName;

    @Column(name = "password")
    @Getter
    @Setter
    private String password;

    @Column(name = "pass_update_date")
    @Getter
    @Setter
    private Date passUpdateDate;

    @Column(name = "login_miss_times")
    @Getter
    @Setter
    private int loginMissTimes;

    @Column(name = "unlock")
    @Getter
    @Setter
    private boolean unlock;

    @Column(name = "enabled")
    @Getter
    @Setter
    private boolean enabled;

    @Column(name = "user_due_date")
    @Getter
    @Setter
    private Date userDueDate;

    @Column(name = "role_id")
    @Getter
    @Setter
    private String roleId;

}
