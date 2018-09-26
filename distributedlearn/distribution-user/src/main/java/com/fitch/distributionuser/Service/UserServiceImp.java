package com.fitch.distributionuser.Service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fitch.distributionuser.IService.IUserService;
import com.fitch.distrubutionserver.Entity.LoginReq;

@Service(version = "1.0.0")
public class UserServiceImp implements IUserService {
    @Override
    public void login(LoginReq loginReq) {

    }
}
