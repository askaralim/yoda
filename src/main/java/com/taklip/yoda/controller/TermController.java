package com.taklip.yoda.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.TermService;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/controlpanel/term")
public class TermController {
    @Autowired
    TermService termService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showTerms(Map<String, Object> model,
            @RequestParam(defaultValue = "0") Integer offset) {
        Page<Term> page = termService.getTerms(offset * 10, 10);

        model.put("page", page);

        return "controlpanel/term/list";
    }

    @GetMapping("/add")
    public ModelAndView initCreationForm(Map<String, Object> model) {
        Term term = new Term();

        List<ContentDTO> contents = contentService.getContents();
        List<Category> categories = categoryService.getCategories();

        model.put("term", term);
        model.put("contents", contents);
        model.put("categories", categories);
        model.put("contentType", "term");

        return new ModelAndView("controlpanel/term/form", model);
    }

    @GetMapping(value = "/{id}/edit")
    public String initUpdateForm(@PathVariable Long id, Map<String, Object> model) {
        Term term = termService.getTerm(id);

        List<ContentDTO> contents = contentService.getContents();
        List<Category> categories = categoryService.getCategories();

        model.put("term", term);
        model.put("contents", contents);
        model.put("categories", categories);
        model.put("contentType", "term");

        return "controlpanel/term/form";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Term term, BindingResult result,
            RedirectAttributes redirect) {
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
    public String remove(@RequestParam String ids) {
        String[] arrIds = ids.split(",");

        for (int i = 0; i < arrIds.length; i++) {
            termService.delete(Long.valueOf(arrIds[i]));
        }

        return "redirect:/controlpanel/term";
    }
}
