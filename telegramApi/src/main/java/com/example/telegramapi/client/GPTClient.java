package com.example.telegramapi.client;

import com.example.telegramapi.entities.TranslatedListModel;
import feign.Param;
import feign.RequestLine;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "dictionaryApi", url = "${application.config.dictionary-url}")
public interface GPTClient {
    @RequestMapping(method = RequestMethod.GET, value ="/translate/{text}")
    TranslatedListModel getMessage(@PathVariable("text") String text, @RequestParam("langs") String langs);
}
