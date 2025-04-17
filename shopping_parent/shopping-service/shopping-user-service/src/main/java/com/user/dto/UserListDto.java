package com.user.dto;

import com.user.domain.UserBase;
import com.user.domain.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class UserListDto extends UserBase {

    private List<String> role;

}
