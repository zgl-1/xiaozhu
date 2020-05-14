package xiaozhu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xiaozhu.dto.GithubUser;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User user) {
		// TODO Auto-generated method stub
		User dbuser =userMapper.findByAccountId(user.getAccountId());
		if(dbuser==null)
		{
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}else {
			dbuser.setGmtModified(System.currentTimeMillis());
			dbuser.setToken(user.getToken());
			dbuser.setName(user.getName());
			dbuser.setAvatarUrl(user.getAvatarUrl());
			userMapper.update(dbuser);
		}
	}
}
