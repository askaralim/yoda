package com.taklip.yoda.controller;

import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.common.util.ExtraFieldUtil;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.dto.ItemDTO;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/controlpanel/item")
@Slf4j
public class ItemController {
    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    SiteService siteService;

    @Autowired
    BrandService brandService;

    @Autowired
    ContentService contentService;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String showItems(Map<String, Object> model,
            @RequestParam(defaultValue = "0") Integer offset) {
        Page<ItemDTO> page = itemService.getItems(offset * 10, 10);

        model.put("page", page);

        return "controlpanel/item/list";
    }

    @GetMapping("/add")
    public ModelAndView initCreationForm(Map<String, Object> model) {
        ItemDTO item = new ItemDTO();

        populateForm(model, item);

        return new ModelAndView("controlpanel/item/form", model);
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(
            @PathVariable Long id, Map<String, Object> model) {
        ItemDTO item = itemService.getItemDetail(id);

        populateForm(model, item);

        return "controlpanel/item/form";
    }

    @PostMapping("/save")
    public ModelAndView save(
            Item item, @RequestParam Long brandId,
            BindingResult result, RedirectAttributes redirect,
            HttpServletRequest request) {

        ModelMap model = new ModelMap();

        if (result.hasErrors()) {
            model.put("errors", "errors");
            return new ModelAndView("controlpanel/item/form", model);
        }

        item.setBrandId(brandId);

        // ExtraFieldUtil.setExtraFields(request, item);
        // ExtraFieldUtil.setBuyLinks(request, item);

        // log.info("before create item: {}", item);

        // if (item.getId() == null) {
        //     itemService.create(item);
        // } else {
        //     itemService.update(item);
        // }

        log.info("after create item: {}", item);

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/item/" + item.getId() + "/edit", model);
    }

    @PostMapping("/{id}/uploadImage")
    public String uploadImage(
            @RequestParam MultipartFile file, @PathVariable Long id)
            throws Throwable {
        if (file.getBytes().length <= 0) {
            return "redirect:/controlpanel/item/" + id + "/edit";
        }

        if (StringUtils.isBlank(file.getName())) {
            return "redirect:/controlpanel/item/" + id + "/edit";
        }

        // String savedPath = new FileUploader().saveFile(file);

        itemService.updateItemImage(id, file);

        return "redirect:/controlpanel/item/" + id + "/edit";
    }

    @ResponseBody
    @PostMapping("/item/{id}/rating")
    public String score(
            @PathVariable Long id, @RequestParam String thumb) {
        Item item = itemService.getItem(id);

        int rating = 0;

        if (thumb.equals("up")) {
            rating = item.getRating() + 1;
        } else if (thumb.equals("down")) {
            rating = item.getRating() - 1;
        }

        item.setRating(rating);

        // itemService.update(item);

        JSONObject jsonResult = new JSONObject();

        jsonResult.put("rating", rating);

        return jsonResult.toString();
    }

    @RequestMapping("/remove")
    public String removeItems(@RequestParam String ids) {
        String[] arrIds = ids.split(",");

        for (int i = 0; i < arrIds.length; i++) {
            itemService.remove(Long.valueOf(arrIds[i]));
        }

        return "redirect:/controlpanel/item";
    }

    private void populateForm(Map<String, Object> model, ItemDTO item) {
        List<ContentDTO> contents = contentService.getContents();
        List<Category> categories = categoryService.getCategories();
        List<Brand> brands = brandService.getBrands();

        model.put("item", item);
        model.put("brands", brands);
        model.put("categories", categories);
        model.put("contents", contents);

        model.put("contentType", "item");
    }
}