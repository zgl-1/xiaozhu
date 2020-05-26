package xiaozhu.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import xiaozhu.dto.FileDto;
import xiaozhu.provider.UCloudProvider;

@Controller
public class FileController {

	@Autowired
	UCloudProvider uCloudProvider;
	
	@ResponseBody
	@RequestMapping("/file/upload")
	public FileDto upload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)request;
		MultipartFile file = multipartRequest.getFile("editormd-image-file");
		try {
			String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
			FileDto fileDto=new FileDto();
			fileDto.setSuccess(1);
			fileDto.setUrl(fileName);
			return fileDto;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileDto fileDto=new FileDto();
		fileDto.setSuccess(1);
		fileDto.setUrl("/images/gg.jpg");
		
		return fileDto;
	}
}
