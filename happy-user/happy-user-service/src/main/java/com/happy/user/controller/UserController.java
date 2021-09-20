package com.happy.user.controller;

import com.happy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/9/7 10:09
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 校验数据是否可用
     *
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data")String data,
                                                @PathVariable(value = "type") Integer type){
        Boolean bool=userService.checkUserData(data,type);
        if(bool==null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(bool);
    }
    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("/code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone){
        Boolean boo = this.userService.sendVerifyCode(phone);
        if (boo == null) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (!boo) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
