package com.taklip.yoda.controller;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.SolutionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/controlpanel/solution")
public class SolutionController {
    @Autowired
    SolutionService solutionService;

    @Autowired
    ItemService itemService;

    @GetMapping
    public String showSolutions(Map<String, Object> model, @RequestParam(name = "offset", defaultValue = "0") Integer offset) {
        PageInfo<Solution> page = solutionService.getSolutions(offset, 10);

        model.put("page", page);

        return "controlpanel/solution/list";
    }

    @GetMapping("/add")
    public ModelAndView initCreationForm(Map<String, Object> model) {
        Solution solution = new Solution();

        model.put("solution", solution);

        return new ModelAndView("controlpanel/solution/form", model);
    }

    @GetMapping(value = "/{id}/edit")
    public String initUpdateForm(
            @PathVariable("id") Long id, Map<String, Object> model) {
        Solution solution = solutionService.getSolution(id);

        model.put("solution", solution);

        return "controlpanel/solution/form";
    }

    @PostMapping("/save")
    public ModelAndView save(
            @Valid Solution solution, BindingResult result, RedirectAttributes redirect) {
        ModelMap model = new ModelMap();

        if (result.hasErrors()) {
            model.put("errors", "errors");
            return new ModelAndView("controlpanel/solution/form", model);
        }

        solutionService.save(solution);

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/solution/" + solution.getId() + "/edit", model);
    }

    @PostMapping("/{id}/uploadImage")
    public String uploadImage(
            @RequestParam("file") MultipartFile file, @PathVariable("id") Long id)
            throws Throwable {
        if (file.getBytes().length <= 0) {
            return "redirect:/controlpanel/solution/" + id + "/edit";
        }

        if (StringUtils.isBlank(file.getName())) {
            return "redirect:/controlpanel/solution/" + id + "/edit";
        }

        solutionService.updateSolutionImage(id, file);

        return "redirect:/controlpanel/solution/" + id + "/edit";
    }

    @GetMapping("/{solutionId}/solutionItem/add")
    public ModelAndView initCreationForm(
            @PathVariable("solutionId") Long solutionId, Map<String, Object> model) {
        SolutionItem solutionItem = new SolutionItem();

        solutionItem.setSolutionId(solutionId);

        List<Item> items = itemService.getItems();

        model.put("solutionItem", solutionItem);
        model.put("items", items);

        return new ModelAndView("controlpanel/solution/editSolutionItem", model);
    }

    @GetMapping("/solutionItem/{id}/edit")
    public String initSolutionItemUpdateForm(@PathVariable("id") Long id, Map<String, Object> model) {
        SolutionItem solutionItem = solutionService.getSolutionItemsById(id);

        List<Item> items = itemService.getItems();

        model.put("solutionItem", solutionItem);
        model.put("items", items);

        return "controlpanel/solution/editSolutionItem";
    }

    @PostMapping(value = "/solutionItem/save")
    public ModelAndView processCreationForm(
            @ModelAttribute("solutionItem") SolutionItem solutionItem, RedirectAttributes redirect) {
        ModelMap model = new ModelMap();

        solutionService.saveSolutionItem(solutionItem);

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/solution/solutionItem/" + solutionItem.getId() + "/edit", model);
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("ids") String ids) {
        String[] arrIds = ids.split(",");

        for (int i = 0; i < arrIds.length; i++) {
            solutionService.delete(Long.valueOf(arrIds[i]));
        }

        return "redirect:/controlpanel/term";
    }
}