package com.taklip.yoda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.controller.PortalBaseController;
import com.taklip.yoda.model.ContactUs;
import com.taklip.yoda.service.ContactUsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/contactus")
@Slf4j
public class ContactUsApiController extends PortalBaseController {
    @Autowired
    private ContactUsService contactUsService;

   @GetMapping
   public ResponseEntity<Page<ContactUs>> getContactUs(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(contactUsService.getByPage(offset, limit));
   }

    @GetMapping("/{id}")
    public ResponseEntity<ContactUs> getContactUsById(@PathVariable Long id) {
        return ResponseEntity.ok(contactUsService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ContactUs> createContactUs(@RequestBody ContactUs contactUs) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactUsService.create(contactUs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactUs> update(@PathVariable Long id, @RequestBody ContactUs contactUs) {
        return ResponseEntity.status(HttpStatus.OK).body(contactUsService.update(contactUs));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactUsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
