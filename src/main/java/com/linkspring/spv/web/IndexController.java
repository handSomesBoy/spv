package com.linkspring.spv.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @项目名称：lyd-channel
 * @包名：com.lyd.channel.web
 * @类描述：
 * @创建人：spv
 * @创建时间：2017-11-28 18:52
 * @version：V1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {
	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	//@RequestMapping(value = {"/toLogin"})
	@RequestMapping(value = {"/toLogin","/"}, method = RequestMethod.GET)
	public String toLogin() {
		return "toLogin";
	}

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = {"/home"}, method = RequestMethod.GET)
	public String toHome() {
		return "framework/home";
	}

	@RequestMapping(value = {"/mainPage"}, method = RequestMethod.GET)
	public String main(){
		return "framework/mainPage";
	}

	@RequestMapping(value = {"/unauthorized"}, method = RequestMethod.GET)
	public String unauthorized(){
		return "error/unauthorized";
	}

	//通用 mapping
	/*@RequestMapping("/{page}") public String toPage(
			@PathVariable("page") String page) {
		logger.debug("-------------toindex------------" + page);
		return page;
	}*/
}
