package com.dmc30.clientui.proxy;

import com.dmc30.clientui.model.bean.utilisateur.AbonneBean;
import com.dmc30.clientui.model.bean.utilisateur.UserAuthenticationBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-api", url = "localhost:9001")
public interface UserApiProxy {

    @GetMapping("/users/check")
    String check();

    @PostMapping("users/login")
    String login(@RequestBody UserAuthenticationBean user);

    @PostMapping("users/signin")
    String signin(@RequestBody AbonneBean abonne, @RequestParam int paysId);
}
