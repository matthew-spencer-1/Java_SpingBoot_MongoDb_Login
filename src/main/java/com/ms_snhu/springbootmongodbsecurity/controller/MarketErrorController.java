package com.ms_snhu.springbootmongodbsecurity.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MarketErrorController
 * 
 * Implements error handling for the application. Provides redirects to error
 * page as appropriate
 * 
 * @author matthewspencer
 */
@Controller
public class MarketErrorController implements ErrorController {

	@Value("${snhu.studentName}")
	private String studentName;

	/**
	 * handleError handles the error by attaching the error message to the model and
	 * pushing it to the view
	 * 
	 * @param message
	 *            message to send to view
	 * @param model
	 *            Model object
	 * @return view to which the flow is sent
	 */
	@RequestMapping("/error")
	public String handleError(
			@RequestParam(name = "message", required = false, defaultValue = "No Further Information Available") String message,
			Model model) {

		model.addAttribute("message", message);
		model.addAttribute("name", studentName);

		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
