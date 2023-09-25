package com.example.telegramapi.client;

import com.example.telegramapi.entities.tests_data.TranslatedListModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "dictionaryApi", url = "${application.config.dictionary-url}")
public interface GPTClient {
    @RequestMapping(method = RequestMethod.GET, value = "/translate/{text}")
    String getMessage(@PathVariable("text") String text, @RequestParam("langs") String langs);

    @RequestMapping(method = RequestMethod.GET, value = "/translate/check/{text}")
    String check(@PathVariable("text") String text);

    @RequestMapping(method = RequestMethod.GET, value = "/translate/detailed/{word}")
    String getDetailedTranslate(@PathVariable("word") String word, @RequestParam("langs") String langs);

    @RequestMapping(method = RequestMethod.GET, value = "/test/{answer}")
    String getTests(@PathVariable("answer") String answer, @RequestParam("lang") String lang);

    @RequestMapping(method = RequestMethod.GET, value = "/test/random/{amount}")
    String getRandomWordList(@PathVariable("amount") Integer amount, @RequestParam("lang") String lang, @RequestParam("level") String level);
}
