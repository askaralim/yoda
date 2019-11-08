package com.taklip.yoda.api;

import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Term;
import com.taklip.yoda.service.TermService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/term")
public class TermApiController {
	@Autowired
	TermService termService;

	@GetMapping
	public ResponseEntity<Pagination<Term>> list(@RequestParam(value="offset", defaultValue="0") Integer offset) {
		Pagination<Term> page = termService.getTerms(new RowBounds(offset, 5));

		for (Term term : page.getData()) {
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