package xiaozhu.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xiaozhu.dto.NotificationDto;
import xiaozhu.dto.PaginationDto;
import xiaozhu.enums.NotificationStatusEnum;
import xiaozhu.enums.NotificationTypeEnum;
import xiaozhu.exception.CustomErrorEnum;
import xiaozhu.exception.CustomException;
import xiaozhu.exception.ICustomErrorCode;
import xiaozhu.mapper.NotificationMapper;
import xiaozhu.mapper.UserMapper;
import xiaozhu.model.Notification;
import xiaozhu.model.NotificationExample;
import xiaozhu.model.User;

@Service
public class NotificationService {
	
	@Autowired
	NotificationMapper notificationMapper;
	
	@Autowired
	UserMapper usermapper;
	
	public PaginationDto list(Long userid, Integer page, Integer size) {
		NotificationExample example=new NotificationExample();
		example.createCriteria().andReceiverEqualTo(userid);
		Integer count = (int)notificationMapper.countByExample(example);
		PaginationDto<NotificationDto> paginationDto = new PaginationDto<>();
		Integer totalPage;
		if (count % size == 0) {
			totalPage = count / size;
		} else {
			totalPage = count / size + 1;
		}
		if (count==0) {
			totalPage=1;
		}
		
		paginationDto.setPagination(totalPage, page);
		
		if (page < 1) {
			page = 1;
		}
		
		if (page > paginationDto.getTotalPage()) {
			page = paginationDto.getTotalPage();
		}
		
		Integer offset = size * (page - 1);
		NotificationExample questionExample=new NotificationExample();
		questionExample.createCriteria().andReceiverEqualTo(userid);
		questionExample.setOrderByClause("gmt_creater desc");
		List<Notification> list = notificationMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset,size));
		List<NotificationDto> notificationDtos = new ArrayList<>();
		
		if(list.size()==0)
		{
			return paginationDto;
		}
		for (Notification notification : list) {
			NotificationDto notificationDto=new NotificationDto();
			BeanUtils.copyProperties(notification, notificationDto);
			notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
			notificationDtos.add(notificationDto);
		}
		paginationDto.setData(notificationDtos);
		return paginationDto;
	}

	public Long unreadCount(Long id) {
		NotificationExample example=new NotificationExample();
		example.createCriteria().andReceiverEqualTo(id).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
		return notificationMapper.countByExample(example);
	}

	public NotificationDto read(Long id, User user) {
		Notification notification = notificationMapper.selectByPrimaryKey(id);
		if(notification==null)
		{
			throw new CustomException(CustomErrorEnum.NOTIFICATION_NOT_FOUND);
		}
		if(notification.getReceiver()!=user.getId())
		{
			throw new CustomException(CustomErrorEnum.READ_NOTIFICATION_FAIL);
		}
		notification.setStatus(NotificationStatusEnum.READ.getStatus());
		notificationMapper.updateByPrimaryKey(notification);
		
		NotificationDto notificationDto=new NotificationDto();
		BeanUtils.copyProperties(notification, notificationDto);
		notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
		return notificationDto;
	}
	
}
