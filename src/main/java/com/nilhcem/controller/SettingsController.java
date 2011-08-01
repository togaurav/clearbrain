package com.nilhcem.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.nilhcem.business.UserBo;
import com.nilhcem.form.SettingsForm;
import com.nilhcem.model.User;
import com.nilhcem.validator.SettingsValidator;

/**
 * Spring MVC Controller class for displaying settings.
 *
 * @author Nilhcem
 * @since 1.0
 */
@Controller
@PreAuthorize("hasRole('RIGHT_USER')")
public final class SettingsController extends AbstractController {
	@Autowired
	private UserBo userBo;
	@Autowired
	private SettingsValidator settingsValidator;

	/**
	 * Define JS i18n keys.
	 */
	public SettingsController() {
		super();
		final String[] i18nJs = {"settings.err.pwd", "settings.err.pwdConf", "settings.err.mailRegist", "settings.err.mail",
			"settings.ok.mail", "settings.ok.pwd", "settings.ok.pwdConf", "settings.cancel.confirm"};
		super.setI18nJsValues(i18nJs, "^settings\\.");
	}

	/**
	 * Initialize settings form, giving it the Settings model.
	 *
	 * @param model Model map.
	 * @return the Settings view.
	 */
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public String initSettingsPage(ModelMap model) {
		User user = getCurrentUser();
		SettingsForm settingsForm = new SettingsForm();
		settingsForm.setEmail(user.getEmail());
		settingsForm.setCurrentEmail(user.getEmail());
		settingsForm.setEditPassword("no");
		settingsForm.setLang(user.getLanguage().getCode());
		model.addAttribute("settingsform", settingsForm);
		return "logged/settings";
	}

	/**
	 * Update users profile information, or delete a User.
	 *
	 * @param settingsForm The settings form.
	 * @param result Binding result.
	 * @param status Session status.
	 * @param request HTTP request.
	 * @return A new view (logged/settings).
	 */
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public ModelAndView submitSettingsPage(@ModelAttribute("settingsform") SettingsForm settingsForm, BindingResult result,
		SessionStatus status, HttpServletRequest request) {
		ModelAndView modelAndView;
		HttpSession session = request.getSession();

		//Delete
		if (WebUtils.hasSubmitParameter(request, "_action_delete")) {
			//TODO: Prevent CSRF
			//mark the user as deletable
			userBo.markAsDeletable(getCurrentUser());

			//proceed logout
			SecurityContextHolder.clearContext();
			request.getSession().invalidate();

			//drop cookies
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
			}

			//Redirect to confirmation page
			modelAndView = new ModelAndView("redirectWithoutModel:account-deleted");
		} else { //Update settings
			settingsValidator.validate(settingsForm, result);
			if (result.hasErrors()) {
				session.setAttribute("settings_ko", ""); //to display error message on client side
				modelAndView = new ModelAndView("logged/settings");
			} else {
				userBo.updateSettings(getCurrentUser(), settingsForm);
				status.setComplete();
				session.setAttribute("settings_ok", ""); //to display confirmation message on client side
				modelAndView = new ModelAndView("redirectWithoutModel:settings");
			}
		}
		return modelAndView;
	}

	/**
	 * Check if email is available.
	 *
	 * @param email The email we need to check.
	 * @return true if available (or if the emailToCheck is the same as the currentEmail), false is not available.
	 */
	@RequestMapping(value = "/settings_check_email", method = RequestMethod.POST, params = { "emailToCheck" })
	@ResponseBody
	public boolean checkEmailAvailability(@RequestParam(value = "emailToCheck", required = true) String email) {
		User user = userBo.findByEmail(email);
		return ((user == null) || (user.getEmail().equalsIgnoreCase(getCurrentUser().getEmail())));
	}
}
