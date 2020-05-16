package xiaozhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xiaozhu.dto.GithubUser;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.User;
import xiaozhu.model.UserExample;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	public void createOrUpdate(User user) {
		// TODO Auto-generated method stub
		UserExample userExample=new UserExample();
		userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
		List<User> selectByExample = userMapper.selectByExample(userExample);
		if(selectByExample.size()==0)
		{
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
		}else {
			User user2 = selectByExample.get(0);
			User user3=new User();
			user3.setGmtModified(System.currentTimeMillis());
			user3.setToken(user.getToken());
			user3.setName(user.getName());
			user3.setAvatarUrl(user.getAvatarUrl());
			UserExample example=new UserExample();
			example.createCriteria().andIdEqualTo(user2.getId());
			userMapper.updateByExampleSelective(user3, userExample);
		}
	}
}
