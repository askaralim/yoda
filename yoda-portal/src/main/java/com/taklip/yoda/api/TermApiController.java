package com.taklip.yoda.api;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/v1/term")
public class TermApiController {
    @Autowired
    TermService termService;

    @GetMapping
    public ResponseEntity<PageInfo<Term>> list(@RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        PageInfo<Term> page = termService.getTerms(offset, 5);

        for (Term term : page.getList()) {
            shortenTermDescription(term);
        }

        return new ResponseEntity(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Term> get(@PathVariable("id") Long id) {
        Term term = termService.getTerm(id);
        return new ResponseEntity(term, HttpStatus.OK);
    }

    private Term shortenTermDescription(Term term) {
        String desc = term.getDescription();

        if (desc.length() > 200) {
            desc = desc.substring(0, 200);

            if (desc.indexOf("img") > 0) {
                desc = desc.substring(0, desc.indexOf("<img"));
            }

            desc = desc.trim().concat("...");

            term.setDescription(desc);
        }

        return term;
    }
}