package xyz.deved.socialmedia.socialmedia.greeting;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {
    private MessageSource messageSource;
    public HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello there";
    }
    // This code below will create an internationalized language based on consumer preference
    // The messages are defined in the messages.properties
    // The localization will only work with accept-language header
    @GetMapping("/hello-international")
    public String helloInternational() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
        //return "Hello there";
    }
}
