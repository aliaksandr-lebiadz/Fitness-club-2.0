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
    public String changeLocale(@RequestParam String locale,
                               @RequestParam String country,
                               HttpServletRequest request){
        Locale localeToSet = new Locale(locale, country);
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, localeToSet);
        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return ControllerUtils.createRedirect(currentPage);
    }

}
