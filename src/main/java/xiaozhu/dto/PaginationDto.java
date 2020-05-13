package xiaozhu.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaginationDto {
	private List<QuestionDto> questions;
	private boolean showPrevious;
	private boolean showFirstPage;
	private boolean showNext;
	private boolean showEndPage;
	private Integer page;
	private List<Integer> pages = new ArrayList<Integer>();
	private Integer totalPage;

	public void setPagination(Integer totalPage, Integer page2) {

		this.totalPage=totalPage; 
		page = page2;
		pages.add(page2);
		for (int i = 1; i <= 3; i++) {
			if (page2 - i > 0) {
				pages.add(0, page2 - i);
			}
			if (page2 + i <= totalPage) {
				pages.add(page2 + i);
			}
		}

		// 是否展示上一页
		if (page2 == 1) {
			showPrevious = false;
		} else {
			showPrevious = true;
		}

		// 是否展示下一页
		if (page2 == totalPage) {
			showNext = false;
		} else {
			showNext = true;
		}

		// 是否展示第一页
		if (pages.contains(1)) {
			showFirstPage = false;
		} else {
			showFirstPage = true;
		}

		// 是否展示最后一页
		if (pages.contains(totalPage)) {
			showEndPage = false;
		} else {
			showEndPage = true;
		}
	}
}
