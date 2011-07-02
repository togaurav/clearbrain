package com.nilhcem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nilhcem.business.CategoryBo;
import com.nilhcem.business.NoteBo;
import com.nilhcem.model.Category;
import com.nilhcem.model.Note;

/**
 * Spring MVC Controller class for Ajax requests from dashboard.
 * 
 * @author Nilhcem
 * @since 1.0
 */
@Controller
@PreAuthorize("hasRole('RIGHT_USER')")
@RequestMapping("/dashboard-js")
public class DashboardControllerAjax extends AbstractController {
	@Autowired
	private CategoryBo categoryBo;
	@Autowired
	private NoteBo noteBo;

	/**
	 * Add a category.
	 *
	 * @param name Category's name.
	 * @return The added category, or null if failed.
	 */
	@RequestMapping(method = RequestMethod.POST, params = { "addCat" })
	public @ResponseBody Category addCategory(@RequestParam(value = "addCat", required = true) String name) {
		return categoryBo.addCategory(getCurrentUser(), name);
	}

	/**
	 * Remove a category.
	 *
	 * @param catId Category's id.
	 * @return true.
	 */
	@RequestMapping(method = RequestMethod.POST, params = { "rmCat" })
	public @ResponseBody boolean removeCategory(@RequestParam(value = "rmCat", required = true) Long catId) {
		categoryBo.removeCategory(getCurrentUser(), catId);
		return true;
	}

	/**
	 * Update a category's position.
	 *
	 * @param catId The category's id we need to update.
	 * @param oldId The previous category (the new one will be before or after this one).
	 * @param before The category will be before (== true) or after (== false) prevId.
	 */
	@RequestMapping(method = RequestMethod.POST, params = { "updPos" })
	public @ResponseBody boolean updatePosition(@RequestParam(value = "updPos", required = true) Long catId, 
		@RequestParam(value = "prev", required = true) Long oldId,
		@RequestParam(value = "before", required = true) boolean before) {
		try {
			categoryBo.updatePosition(getCurrentUser(), catId, oldId, before);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

//	/**
//	 * Display or hide a category.
//	 *
//	 * @param catId The category's id we need to display or hide.
//	 * @param display True if we need to display the category, otherwise false.
//	 * @return true.
//	 */
//	@RequestMapping(method = RequestMethod.POST, params = { "display" })
//	public @ResponseBody boolean showHideCategory(@RequestParam(value = "id", required = true) Long catId,
//		@RequestParam(value = "display", required = true) boolean displayed) {
//		categoryBo.showHideCategory(getCurrentUser(), catId, displayed);
//		return true;
//	}

	/**
	 * Add a quick note.
	 *
	 * @param name Note's name.
	 * @param catId Category's id (or 0 if null).
	 * @return The added note.
	 */
	@RequestMapping(method = RequestMethod.POST, params = { "addNote" })
	public @ResponseBody Note addNote(@RequestParam(value = "addNote", required = true) String name,
		@RequestParam(value = "catId", required = true) Long catId) {
		return noteBo.addNote(getCurrentUser(), name, catId);
	}

	/**
	 * Assign a category to a note.
	 *
	 * @param catId The category's id we need to display or hide.
	 * @param noteId The note's id which will contain the category.
	 * @return true.
	 */
	@RequestMapping(method = RequestMethod.POST, params = { "assignCat" })
	public @ResponseBody boolean assignCatToNote(@RequestParam(value = "assignCat", required = true) Long catId,
		@RequestParam(value = "noteId", required = true) Long noteId) {
		noteBo.assignCategoryToNote(getCurrentUser(), catId, noteId);
		return true;
	}
}
