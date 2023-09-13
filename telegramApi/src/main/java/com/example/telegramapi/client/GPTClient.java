package com.example.telegramapi.client;

import com.example.telegramapi.entities.TranslatedListModel;
import feign.Param;
import feign.RequestLine;
import jakarta.websocket.server.PathParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "dictionaryApi", url = "${application.config.dictionary-url}")
public interface GPTClient {
    @RequestMapping(method = RequestMethod.GET, value = "/translate/{text}")
    TranslatedListModel getMessage(@PathVariable("text") String text, @RequestParam("langs") String langs);

    @RequestMapping(method = RequestMethod.GET, value = "/translate/detailed/{word}")
    String getDetailedTranslate(@PathVariable("word") String word, @RequestParam("langs") String langs);

    @RequestMapping(method = RequestMethod.GET, value = "/test/{answer}")
    List<String> getTests(@PathVariable("answer") String answer, @RequestParam("lang") String lang);

    @RequestMapping(method = RequestMethod.GET, value = "/test/random/{amount}")
    String getRandomWordList(@PathVariable("amount") Integer amount, @RequestParam("lang") String lang, @RequestParam("level") String level);
}
