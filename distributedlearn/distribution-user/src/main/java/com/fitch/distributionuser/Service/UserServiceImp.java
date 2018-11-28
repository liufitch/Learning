package com.fitch.distributionuser.Service;


import com.alibaba.dubbo.config.annotation.Service;
import com.fitch.distrubutionserver.Entity.LoginReq;
import com.fitch.distrubutionserver.IService.IUserService;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;

@Service(version = "${dubbo.service.version}")
public class UserServiceImp implements IUserService {

    @Override
    public Boolean login(String userName, String encrytionPWD) {
        return true;
    }
}
