package com.taklip.yoda.controller;

import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.util.DateUtil;
import com.taklip.yoda.common.util.SiteUtil;
import com.taklip.yoda.dto.ContentDTO;
import com.taklip.yoda.model.Brand;
import com.taklip.yoda.model.Category;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentBrand;
import com.taklip.yoda.model.ContentContributor;
import com.taklip.yoda.model.HomePage;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.ContentBrandService;
import com.taklip.yoda.service.ContentContributorService;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.HomePageService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.vo.ContentSearchForm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author askar
 */
@Controller
@RequestMapping(value = "/controlpanel/content")
public class ContentController {
    @Autowired
    private UserService userService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private ContentContributorService contentContributorService;

    @Autowired
    private ContentBrandService contentBrandService;

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @GetMapping
    public String list(Map<String, Object> model, @RequestParam(defaultValue = "0") Integer offset) {
        Page<Content> page = contentService.getContents(offset * 10, 10);

        model.put("page", page);
        model.put("searchForm", new ContentSearchForm());

        return "controlpanel/content/list";
    }

    @GetMapping("/add")
    public ModelAndView setupForm(Map<String, Object> model) {
        ContentDTO content = new ContentDTO();

        List<Category> categories = categoryService.getCategories();

        model.put("categories", categories);
        model.put("content", content);
        model.put("contentType", "content");

        return new ModelAndView("controlpanel/content/form", model);
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(
            @PathVariable Long id, Map<String, Object> model) {
        ContentDTO content = contentService.getContentDetail(id);

        List<Category> categories = categoryService.getCategories();

        model.put("categories", categories);
        model.put("content", content);
        model.put("contentType", "content");

        return "controlpanel/content/form";
    }

    @PostMapping(value = "/save")
    public ModelAndView save(
            @ModelAttribute Content content, @RequestParam Long categoryId,
            BindingResult result, HttpServletRequest request, RedirectAttributes redirect) throws Throwable {
        ModelMap model = new ModelMap();

        if (result.hasErrors()) {
            List<Category> categories = categoryService.getCategories();

            model.put("categories", categories);
            model.put("errors", "errors");

            return new ModelAndView("controlpanel/content/form", model);
        }

        if (content.getId() == null) {
            contentService.create(content, categoryId);
        } else {
            contentService.updateContent(content, categoryId);
        }

        saveContentContributors(request, content);

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/content/" + content.getId() + "/edit", model);
    }

    @GetMapping("/{contentId}/contentbrand/add")
    public ModelAndView initCreationForm(
            @PathVariable Long contentId, Map<String, Object> model) {
        ContentBrand contentBrand = new ContentBrand();

        contentBrand.setContentId(contentId);

        List<Brand> brands = brandService.getBrands();

        model.put("contentBrand", contentBrand);
        model.put("brands", brands);

        return new ModelAndView("controlpanel/content/editContentBrand", model);
    }

    @GetMapping("/contentbrand/{id}/edit")
    public String initContentBrandUpdateForm(@PathVariable Long id, Map<String, Object> model) {
        ContentBrand contentBrand = contentBrandService.getById(id);

        List<Brand> brands = brandService.getBrands();

        model.put("contentBrand", contentBrand);
        model.put("brands", brands);

        return "controlpanel/content/editContentBrand";
    }

    @PostMapping("/contentbrand/save")
    public ModelAndView processCreationForm(
            @ModelAttribute ContentBrand contentBrand, RedirectAttributes redirect) {
        ModelMap model = new ModelMap();

        String brandName = StringUtils.EMPTY;
        String brandLogo = StringUtils.EMPTY;

        Brand brand;

        if (null != contentBrand.getBrandId()) {
            brand = brandService.getBrand(contentBrand.getBrandId());
            brandName = brand.getName();
            brandLogo = brand.getImagePath();
        }

        contentBrand.setBrandName(brandName);
        contentBrand.setBrandLogo(brandLogo);

        contentBrandService.create(contentBrand);

        redirect.addFlashAttribute("globalMessage", "success");

        return new ModelAndView("redirect:/controlpanel/content/contentbrand/" + contentBrand.getId() + "/edit", model);
    }

    @PostMapping("/remove")
    public String deleteContent(
            @ModelAttribute Content content) {
        // ContentImage contentImage = content.getImage();
        //
        // if (contentImage != null) {
        // contentImageService.deleteContentImage(contentImage);
        // }

        // Iterator iterator = content.getImages().iterator();

        // while (iterator.hasNext()) {
        // contentImage = (ContentImage)iterator.next();
        //
        // contentImageService.deleteContentImage(contentImage);
        // }

        // Iterator iterator = (Iterator) contentDb.getMenus().iterator();

        // while (iterator.hasNext()) {
        // Menu menu = (Menu) iterator.next();
        // menu.setContent(null);
        // }

        contentService.deleteContent(content.getId());

        // Indexer.getInstance(siteId).removeContent(content);

        return "redirect:/controlpanel/content/list";
    }

    @RequestMapping(value = "/resetCounter")
    public void resetCounter(
            @PathVariable Long contentId, HttpServletResponse response)
            throws Throwable {
        // User user = AuthenticatedUtil.getAuthenticatedUser();

        ContentDTO content = contentService.getContentDetail(contentId);

        contentService.resetHitCounter(contentId);

        JSONObject jsonResult = new JSONObject();

        jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
        jsonResult.put("updateBy", content.getUpdateBy());
        jsonResult.put("updateTime", DateUtil.getFullDatetime(content.getUpdateTime()));

        String jsonString = jsonResult.toString();

        response.setContentType("text/html");
        response.setContentLength(jsonString.length());

        OutputStream outputStream = response.getOutputStream();

        outputStream.write(jsonString.getBytes());
        outputStream.flush();
    }

    // public JSONObject createJsonSelectedMenus(Long siteId, Content content)
    // throws Exception {
    // JSONObject jsonResult = new JSONObject();

    // Iterator iterator = content.getMenus().iterator();

    // Vector<JSONObject> menus = new Vector<JSONObject>();

    // while (iterator.hasNext()) {
    // Menu menu = (Menu) iterator.next();

    // JSONObject menuObject = new JSONObject();

    // menuObject.put("menuId", menu.getId());
    // menuObject.put("menuLongDesc", menuService.formatMenuName(siteId,
    // menu.getId()));
    // menuObject.put("menuWindowMode", menu.getMenuWindowMode());
    // menuObject.put("menuWindowTarget", menu.getMenuWindowTarget());
    // menus.add(menuObject);
    // }

    // jsonResult.put("menus", menus);
    // jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);

    // return jsonResult;
    // }

    // @RequestMapping("/removeMenus")
    // public void removeMenus(
    // @PathVariable Long contentId,
    // @RequestParam("removeMenus") long[] menuIds,
    // HttpServletRequest request, HttpServletResponse response)
    // throws Throwable {
    // User user = AuthenticatedUtil.getAuthenticatedUser();

    // Content content = contentService.getContentById(contentId);

    // if (menuIds != null) {
    // for (int i = 0; i < menuIds.length; i++) {
    // Menu menu = menuService.getMenu(menuIds[i]);

    // menu.setContent(null);
    // menu.setMenuUrl("");
    // menu.setMenuType("");

    // menuService.updateMenu(menu);
    // }
    // }

    // // content.setUpdateBy(user.getId());
    // // content.setUpdateTime(LocalDateTime.now());

    // contentService.updateContent(content);

    // JSONObject jsonResult =
    // createJsonSelectedMenus(SiteUtil.getDefaultSite().getId(), content);

    // jsonResult.put("updateBy", content.getUpdateBy());
    // jsonResult.put("updateTime",
    // DateUtil.getFullDatetime(content.getUpdateTime()));

    // String jsonString = jsonResult.toString();

    // response.setContentType("text/html");
    // response.setContentLength(jsonString.length());

    // OutputStream outputStream = response.getOutputStream();

    // outputStream.write(jsonString.getBytes());
    // outputStream.flush();
    // }

    // @RequestMapping("/addMenus")
    // public void addMenus(
    // @PathVariable Long contentId,
    // @RequestParam String menuWindowTarget,
    // @RequestParam String menuWindowMode,
    // @RequestParam long[] addMenus,
    // HttpServletRequest request, HttpServletResponse response)
    // throws Throwable {
    // User user = AuthenticatedUtil.getAuthenticatedUser();

    // Content content = contentService.getContentById(contentId);

    // if (addMenus != null) {
    // for (int i = 0; i < addMenus.length; i++) {
    // menuService.updateMenu(
    // SiteUtil.getDefaultSite().getId(), addMenus[i], content, null, "",
    // menuWindowMode, menuWindowTarget, Constants.MENU_CONTENT);
    // }
    // }

    // // content.setUpdateBy(user.getId());
    // // content.setUpdateTime(LocalDateTime.now());

    // contentService.updateContent(content);

    // JSONObject jsonResult =
    // createJsonSelectedMenus(SiteUtil.getDefaultSite().getId(), content);

    // jsonResult.put("updateBy", content.getUpdateBy());
    // jsonResult.put("updateDate",
    // DateUtil.getFullDatetime(content.getUpdateTime()));

    // String jsonString = jsonResult.toString();

    // response.setContentType("text/html");
    // response.setContentLength(jsonString.length());

    // OutputStream outputStream = response.getOutputStream();

    // outputStream.write(jsonString.getBytes());
    // outputStream.flush();
    // }

    @PostMapping("/{id}/uploadImage")
    public String uploadImage(
            @RequestParam MultipartFile file,
            @PathVariable long id,
            HttpServletRequest request)
            throws Throwable {
        // User user = PortalUtil.getAuthenticatedUser();

        if (file.getBytes().length <= 0) {
            return "redirect:/controlpanel/content/" + id + "/edit";
        }

        if (StringUtils.isEmpty(file.getName())) {
            return "redirect:/controlpanel/content/" + id + "/edit";
        }

        contentService.updateContentImage(
                SiteUtil.getDefaultSite().getId(), id, file);

        // ImageScaler scaler = null;
        //
        // try {
        // scaler = new ImageScaler(fileData, file.getContentType());
        // scaler.resize(600);
        // }
        // catch (Exception e) {
        // jsonResult.put("status", Constants.WEBSERVICE_STATUS_FAILED);
        // jsonResult.put("message", "error.image.invalid");
        //
        // String jsonString = jsonResult.toString();
        //
        // response.setContentType("text/html");
        // response.setContentLength(jsonString.length());
        //
        // OutputStream outputStream = response.getOutputStream();
        //
        // outputStream.write(jsonString.getBytes());
        // outputStream.flush();
        //
        // return;
        // }

        // ContentImage contentImage = contentImageService.addContentImage(
        // user.getLastVisitSiteId(), user.getUserId(), file.getOriginalFilename(),
        // file.getContentType(), scaler.getBytes(), scaler.getHeight(),
        // scaler.getWidth());

        // jsonResult = createJsonImages(admin.getSiteId(), content);
        // jsonResult.put("recUpdateBy", content.getRecUpdateBy());
        // jsonResult.put("recUpdateDatetime",Format.getFullDatetime(content.getRecUpdateDatetime()));
        //
        // String jsonString = jsonResult.toString();
        //
        // response.setContentType("text/html");
        // response.setContentLength(jsonString.length());
        //
        // OutputStream outputStream = response.getOutputStream();
        // outputStream.write(jsonString.getBytes());
        // outputStream.flush();

        return "redirect:/controlpanel/content/" + id + "/edit";
    }

    public JSONObject createJsonImages(int siteId, Content content)
            throws Exception {
        JSONObject jsonResult = new JSONObject();

        // ContentImage defaultImage = content.getImage();
        //
        // if (defaultImage != null) {
        // JSONObject jsonDeFaultImage = new JSONObject();
        //
        // jsonDeFaultImage.put("imageId", defaultImage.getImageId());
        // jsonDeFaultImage.put("imageName", defaultImage.getImageName());
        // jsonResult.put("defaultImage", jsonDeFaultImage);
        // }

        // Iterator iterator = content.getImages().iterator();

        // Vector<JSONObject> vector = new Vector<JSONObject>();
        //
        // while (iterator.hasNext()) {
        // ContentImage image = (ContentImage) iterator.next();
        //
        // JSONObject jsonImage = new JSONObject();
        //
        // jsonImage.put("imageId", image.getImageId());
        // jsonImage.put("imageName", image.getImageName());
        // vector.add(jsonImage);
        // }
        //
        // jsonResult.put("images", vector);
        jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);

        return jsonResult;
    }

    // public void removeImages(
    // @PathVariable("contentId") Long contentId,
    // @RequestParam("removeImage") Long imageId,
    // HttpServletRequest request, HttpServletResponse response)
    // throws Throwable {
    // User user = PortalUtil.getAuthenticatedUser();
    //
    // Content content = contentService.deleteContentImage(
    // user.getLastVisitSiteId(), user.getUserId(), contentId, imageId);
    //
    // JSONObject jsonResult = createJsonImages(user.getLastVisitSiteId(), content);
    //
    // jsonResult.put("updateBy", content.getUpdateBy());
    // jsonResult.put("updateDate",
    // Format.getFullDatetime(content.getUpdateDate()));
    //
    // String jsonString = jsonResult.toString();
    //
    // response.setContentType("text/html");
    // response.setContentLength(jsonString.length());
    //
    // OutputStream outputStream = response.getOutputStream();
    //
    // outputStream.write(jsonString.getBytes());
    // outputStream.flush();
    // }

    // @RequestMapping("/defaultImage")
    // public void defaultImage(
    // @PathVariable("contentId") Long contentId,
    // @RequestParam("cefaultImageId") Long defaultImageId,
    // HttpServletRequest request, HttpServletResponse response)
    // throws Throwable {
    // Admin admin = getAdminBean(request);
    //
    // Content content = contentService.updateDefaultContentImage(admin.getSiteId(),
    // admin.getUserId(), contentId, defaultImageId);
    //
    // JSONObject jsonResult = createJsonImages(admin.getSiteId(), content);
    //
    // jsonResult.put("updateBy", content.getUpdateBy());
    // jsonResult.put("updateDate",
    // Format.getFullDatetime(content.getUpdateDate()));
    //
    // String jsonString = jsonResult.toString();
    //
    // response.setContentType("text/html");
    // response.setContentLength(jsonString.length());
    //
    // OutputStream outputStream = response.getOutputStream();
    //
    // outputStream.write(jsonString.getBytes());
    // outputStream.flush();
    // }

    // @Deprecated
    // public void createAdditionalInfo(
    // User user, Content content, ContentEditCommand command)
    // throws Exception {
    // int siteId = user.getLastVisitSiteId();
    //
    // Iterator iterator = content.getMenus().iterator();
    //
    // Vector<ContentMenuDisplayCommand> selectedMenuVector = new
    // Vector<ContentMenuDisplayCommand>();
    //
    // while (iterator.hasNext()) {
    // Menu menu = (Menu) iterator.next();
    //
    // ContentMenuDisplayCommand menuDisplayForm = new ContentMenuDisplayCommand();
    //
    // menuDisplayForm.setMenuId(menu.getMenuId());
    // menuDisplayForm.setMenuLongDesc(menuService.formatMenuName(siteId,
    // menu.getMenuId()));
    // menuDisplayForm.setMenuWindowMode(menu.getMenuWindowMode());
    // menuDisplayForm.setMenuWindowTarget(menu.getMenuWindowTarget());
    //
    // selectedMenuVector.add(menuDisplayForm);
    // }
    //
    // ContentMenuDisplayCommand selectedMenuList[] = new
    // ContentMenuDisplayCommand[selectedMenuVector.size()];
    //
    // selectedMenuVector.copyInto(selectedMenuList);
    //
    // command.setSelectedMenus(selectedMenuList);
    // command.setSelectedMenusCount(selectedMenuList.length);
    //
    // Section section = content.getSection();
    //
    // if (section != null) {
    // command.setSelectedSection(sectionService.formatSectionName(siteId,
    // section.getSectionId()));
    // }
    //
    // command.setMenuList(menuService.makeMenuTreeList(siteId));
    // command.setSectionTree(sectionService.makeSectionTree(siteId));
    //
    //// iterator = content.getImages().iterator();
    ////
    //// List<ContentImage> images = new ArrayList<ContentImage>();
    ////
    //// while (iterator.hasNext()) {
    //// ContentImage image = (ContentImage) iterator.next();
    ////
    //// images.add(image);
    //// }
    ////
    //// command.setImages(images);
    //
    // command.setHomePage(false);
    //
    // if (getHomePage(siteId, content.getContentId()) != null) {
    // command.setHomePage(true);
    // }
    // }

    public void saveContentContributors(HttpServletRequest request, Content content) {
        Enumeration<String> names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();

            if (name.startsWith("contributorId")) {
                String userId = request.getParameter(name);

                if (StringUtils.isEmpty(userId)) {
                    continue;
                }

                User user = userService.getUser(Long.valueOf(userId));

                if (null == user) {
                    continue;
                }

                ContentContributor contributor = new ContentContributor();

                contributor.setApproved(true);
                contributor.setContentId(content.getId());
                contributor.setProfilePhotoSmall(user.getProfilePhotoSmall());
                contributor.setUserId(user.getId());
                contributor.setUsername(user.getUsername());
                contributor.setVersion("1.0");

                List<ContentContributor> results = contentContributorService.getByContentIdAndUserId(content.getId(),
                        user.getId());

                if (results.isEmpty()) {
                    contentContributorService.create(contributor);
                }
            }
        }
    }

    private HomePage getHomePage(int siteId, Long contentId) {
        List<HomePage> homePages = homePageService.getHomePages(siteId);

        for (HomePage homePage : homePages) {
            Content homePageContent = homePage.getContent();
            if (homePageContent != null) {
                if (homePageContent.getId().longValue() == contentId.longValue()) {
                    return homePage;
                }
            }
        }

        return null;
    }

    @RequestMapping(value = "/remove")
    public String removeContents(
            @RequestParam String ids) {
        String[] arrIds = ids.split(",");

        for (int i = 0; i < arrIds.length; i++) {
            contentService.deleteContent(Long.valueOf(arrIds[i]));
        }

        return "redirect:/controlpanel/content";
    }

    @PostMapping("/search")
    public String search(
            @ModelAttribute ContentSearchForm form, Map<String, Object> model)
            throws Throwable {
        List<Content> contents = contentService.search(
                SiteUtil.getDefaultSite().getId(), form.getTitle(), form.getPublished(),
                null, null, form.getPublishDateStart(), form.getPublishDateEnd(),
                form.getExpireDateStart(), form.getExpireDateEnd());

        model.put("contents", contents);
        model.put("searchForm", form);

        return "controlpanel/content/list";
    }
}