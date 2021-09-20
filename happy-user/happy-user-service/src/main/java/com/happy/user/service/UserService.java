package com.happy.user.service;

public interface UserService {
    Boolean checkUserData(String data, Integer type);

    Boolean sendVerifyCode(String phone);
}
