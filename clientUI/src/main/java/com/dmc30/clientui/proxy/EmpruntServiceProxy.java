package com.dmc30.clientui.proxy;

import com.dmc30.clientui.bean.bibliotheque.CreateEmpruntBean;
import com.dmc30.clientui.bean.bibliotheque.PretBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emprunt-service", url = "localhost:9003")
public interface EmpruntServiceProxy {

    @PostMapping("/emprunts/create")
    PretBean createEmprunt(@RequestBody CreateEmpruntBean createEmpruntBean);
}
