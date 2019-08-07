package com.liwinon.phoneScanning.QiyeWX.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.liwinon.phoneScanning.QiyeWX.dao.primaryRepo.BirthdayDao;

@Controller
@CrossOrigin
public class QySendMsgController {
	@Autowired
	private BirthdayDao bdao;

	@GetMapping(value = "/phone/birthday")
	public String birthday(String name,Model model) {
		model.addAttribute("name", name + "，");
		model.addAttribute("content", bdao.findRandomBirthday().getContent());
		System.out.println("获取到的name:"+name);
		return "BirthdayCake";
	}
	
	@GetMapping(value="/phone/EntryInfo")
	public String entryInfo(String name,Model model) {
		//model.addAttribute("name",name);
		return "entryInfo";
	}

}
