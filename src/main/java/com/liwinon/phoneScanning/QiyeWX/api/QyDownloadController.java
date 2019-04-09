package com.liwinon.phoneScanning.QiyeWX.api;

import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystem;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class QyDownloadController {
	/**
	 * 新入职文档下载
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/phone/qywx/downloadDoc")
	public ResponseEntity<InputStreamResource> entryInfo(String name, HttpServletRequest req) {
		String path = "";
		if (StringUtils.isNotBlank(name)) {
			if ("task".equals(name)) {
				path = "C:\\Users\\8700\\Desktop\\Doc\\F-LWN-31.2.4.7-R00 试用期目标任务书.docx";
			} else if ("check".equals(name)) {
				path = "C:\\Users\\8700\\Desktop\\Doc\\F-LWN-31.2.4.8-R00 试用期目标任务考核表.docx";
			}
			FileSystemResource file = new FileSystemResource(path);
			try {
				String fileName = new String(file.getFilename().getBytes(),"iso8859-1");
				if(file.exists()) {
					HttpHeaders headers =  new HttpHeaders();
					headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
					headers.add("Content-Disposition", "attachment;fileName="+fileName);
					headers.add("Pragma", "no-cache");
					headers.add("Expires", "0");
					return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
							.contentType(MediaType.parseMediaType("application/octet-stream"))
							.body(new InputStreamResource(file.getInputStream()));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return null;
	}

}
