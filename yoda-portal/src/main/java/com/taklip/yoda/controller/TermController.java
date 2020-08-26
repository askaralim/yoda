package com.taklip.yoda.controller;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/controlpanel/term")
public class TermController {
    @Autowired
    TermService termService;

    @GetMapping
    public String showTerms(Map<String, Object> model, @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        PageInfo<Term> page = termService.getTerms(offset * 10, 10);

        model.put("page", page);

        return "controlpanel/term/list";
    }

    @GetMapping("/add")
    public ModelAndView initCreationForm(Map<String, Object> model) {
        Term term = new Term();

        model.put("term", term);
        model.put("contentType", "term");

        return new ModelAndView("controlpanel/term/form", model);
    }

    @GetMapping(value = "/{id}/edit")
    public String initUpdateForm(
            @PathVariable("id") Long id, Map<String, Object> model) {
        Term term = termService.getTerm(id);

        model.put("term", term);
        model.put("contentType", "term");

        return "controlpanel/term/form";
    }

    @PostMapping("/save")
    public ModelAndView save(
            @Valid Term term, BindingResult result, RedirectAttributes redirect) {
        ModelMap model = new ModelMap();

        if (result.hasErrors()) {
            model.put("errors", "errors");
            return new ModelAndView("controlpanel/term/form", model);
        }

        termService.save(term);

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/term/" + term.getId() + "/edit", model);
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("ids") String ids) {
        String[] arrIds = ids.split(",");

        for (int i = 0; i < arrIds.length; i++) {
            termService.delete(Long.valueOf(arrIds[i]));
        }

        return "redirect:/controlpanel/term";
    }
}