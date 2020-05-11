package xiaozhu.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xiaozhu.dto.AccessTokenDTO;
import xiaozhu.dto.GithubUser;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.User;
import xiaozhu.provider.GithubProvider;

@Controller
public class AuthorizeController {

	@Autowired
	private GithubProvider githubProvider;
	
	@Value("${github.client.id}")
	private String clientid;
	
	@Value("${github.client.secret}")
	private String clientSecret;

	@Value("${github.redirect.url}")
	private String redirectUrl;
	
	@Autowired
	UserMapper userMapper;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,
						   @RequestParam(name="state") String state,
						   HttpServletRequest request,
						   HttpServletResponse response) {
		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientid);
		System.out.println(clientid);
		accessTokenDTO.setClient_secret(clientSecret);
		System.out.println(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUrl);
		System.out.println(redirectUrl);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser user = githubProvider.getUser(accessToken);
		if(user!=null&&user.getId()!=null)
		{
			String token=UUID.randomUUID().toString();
			User user2=new User(); 
			user2.setToken(token);
			user2.setAccountId(String.valueOf(user.getId()));
			user2.setName(user.getName());
			user2.setGmtCreate(System.currentTimeMillis());
			user2.setGmtModified(user2.getGmtCreate());
			user2.setAvatarUrl(user.getAvatarUrl());
			userMapper.insert(user2);
			response.addCookie(new Cookie("token", token));
			return "redirect:/";
		}
		else {
			return "redirect:/";
		}
	}
}
