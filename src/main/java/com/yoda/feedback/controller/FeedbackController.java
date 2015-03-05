package com.yoda.feedback.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.feedback.FeedbackAddValidator;
import com.yoda.feedback.model.Feedback;
import com.yoda.feedback.service.FeedbackService;

@Controller
public class FeedbackController {
	@Autowired
	FeedbackService feedbackService;

	@RequestMapping(value = "/controlpanel/feedback", method = RequestMethod.GET)
	public ModelAndView showPanel() {

		List<Feedback> feedbacks = new ArrayList<Feedback>();

		try {
			feedbacks = feedbackService.getFeedbacks();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(
			"controlpanel/feedback/list", "feedbacks", feedbacks);
	}

	@RequestMapping(value = "/controlpanel/feedback/{id}", method = RequestMethod.GET)
	public ModelAndView viewFeedback(@PathVariable("id") long id) {
		Feedback feedback = feedbackService.getFeedback(id);

		return new ModelAndView(
			"controlpanel/feedback/view", "feedback", feedback);
	}

	@RequestMapping(value = "/feedback/add", method = RequestMethod.POST)
	public ModelAndView addFeedback(
			@ModelAttribute Feedback feedback,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {

		new FeedbackAddValidator().validate(feedback, result);

		ModelAndView model = new ModelAndView();

		model.setViewName("redirect:/");

		if(result.hasErrors()) {
			return new ModelAndView("redirect:/");
		}

		feedbackService.addFeedback(feedback);

//		model.addObject("successMessage", "successfully-submit");

		return model;
	}

	@RequestMapping(value = "/controlpanel/feedback/remove", method = RequestMethod.GET)
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