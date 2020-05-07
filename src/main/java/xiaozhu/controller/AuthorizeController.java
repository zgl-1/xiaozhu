package xiaozhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import xiaozhu.dto.AccessTokenDTO;
import xiaozhu.dto.GithubUser;
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
	
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,@RequestParam(name="state") String state) {
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
		System.out.println(user);
		return "index";
	}
}
