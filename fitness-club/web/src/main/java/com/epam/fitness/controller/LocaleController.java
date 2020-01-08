package com.epam.fitness.controller;

import com.epam.fitness.utils.CurrentPageGetter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@RequestMapping("/locale")
public class LocaleController {

    private static final String LOCALE_ATTRIBUTE = "locale";

    @RequestMapping("/change")
    public String changeLocale(@RequestParam String language,
                               @RequestParam String country,
                               HttpServletRequest request){
        Locale locale = new Locale(language, country);
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

}
