package com.taklip.yoda.controller;

import com.taklip.yoda.model.Feedback;
import com.taklip.yoda.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/controlpanel/feedback")
public class FeedbackController {
	@Autowired
	FeedbackService feedbackService;

	@GetMapping
	public ModelAndView list() {
		return new ModelAndView(
			"controlpanel/feedback/list", "feedbacks", feedbackService.getFeedbacks());
	}

	@GetMapping(value = "/{id}")
	public ModelAndView viewFeedback(@PathVariable("id") long id) {
		return new ModelAndView(
			"controlpanel/feedback/view", "feedback", feedbackService.getFeedback(id));
	}

	@PostMapping("/add")
	public ModelAndView addFeedback(
			@Valid Feedback feedback, BindingResult result) {
		ModelAndView model = new ModelAndView();

		model.setViewName("redirect:/");

		if(result.hasErrors()) {
			return new ModelAndView("redirect:/");
		}

		feedbackService.addFeedback(feedback);

		return model;
	}

	@GetMapping("/controlpanel/feedback/remove")
	public String removeFeedbacks(
			@RequestParam("feedbackIds") String feedbackIds,
			HttpServletRequest request) {
		String[] arrIds = feedbackIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			Feedback feedback = feedbackService.getFeedback(Long.valueOf(arrIds[i]));

			feedbackService.deleteFeedback(feedback);
		}

		return "redirect:/controlpanel/feedback";
	}
}