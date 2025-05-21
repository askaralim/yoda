package com.taklip.yoda.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.TermService;

/**
 * @author askar
 */
@RestController
@RequestMapping("/api/v1/term")
public class TermApiController {
    @Autowired
    TermService termService;

    @GetMapping
    public ResponseEntity<Page<Term>> list(@RequestParam(defaultValue = "0") Integer offset) {
        Page<Term> page = termService.getTerms(offset, 5);

        for (Term term : page.getRecords()) {
            shortenTermDescription(term);
        }

        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Term> get(@PathVariable Long id) {
        Term term = termService.getTerm(id);

        return new ResponseEntity<>(term, HttpStatus.OK);
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