/*******************************************************************************
 * 提交回复
 * 
 * @returns
 */
function post() {
	var id = $("#question_id").val();
	var content = $("#content").val();
	comment2target(id, 1, content);

}

function comment2target(targetId, type, content) {
	if (!content) {
		alert("不能回复空内容！");
		return;
	}

	$
			.ajax({
				type : "POST",
				url : "/comment",
				contentType : "application/json",
				data : JSON.stringify({
					"parentId" : targetId,
					"content" : content,
					"type" : type
				}),
				success : function(response) {
					if (response.code == 200) {
						window.location.reload();
					} else {
						if (response.code == 2003) {
							var isAccepted = confirm(response.message);
							if (isAccepted) {
								window
										.open("https://github.com/login/oauth/authorize?client_id=95f9b3e29339bb45a211&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
								window.localStorage.setItem("closable", true);
							}
						} else {
							alert(response.message);
						}

					}
				},
				dataType : "json"
			});
}

function comment(e) {
	var id = e.getAttribute("data-id");
	var content = $("#input-" + id).val();
	comment2target(id, 2, content);
}

/*******************************************************************************
 * 二级评论
 * 
 * @returns
 */
function coll(e) {
	var id = e.getAttribute("data-id");
	var comments = $("#comment-" + id);
	var collapse = e.getAttribute("data-collapse");
	if (collapse) {
		comments.removeClass("in");
		e.removeAttribute("data-collapse");
		e.classList.remove("active");
	} else {
		var subCommentContainer = $("#comment-" + id);
		if (subCommentContainer.children().length != 1) {
			// 展开二级评论
			comments.addClass("in");
			// 标记二级评论展开状态
			e.setAttribute("data-collapse", "in");
			e.classList.add("active");
		} else {
			$.getJSON("/comment/" + id,function(data) {
								console.log(data);
								$.each(data.data.reverse(),function(index, comment) {
									var mediaLeftElement = $(
										"<div/>",{"class" : "media-left"}).append(
											$("<img/>",{"class" : "media-object img-rounded","src" : comment.user.avatarUrl}));
												var mediaBodyElement = $("<div/>",
													{"class" : "media-body"}).append(
														$("<h5/>",{"class" : "media-heading","html" : comment.user.name})).append(
															$("<div/>",{"html" : comment.content})).append(
																$("<div/>",{"class" : "menu"}).append(
																	$("<span/>",{"class" : "pull-right","html" : moment(
																		comment.gmtCreate).format('YYYY-MM-DD')})));
													var mediaElement = $(
															"<div/>",
															{
																"class" : "media"
															})
															.append(
																	mediaLeftElement)
															.append(
																	mediaBodyElement);
													var commentElement = $(
															"<div/>",
															{
																"class" : "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
															}).append(
															mediaElement);
													subCommentContainer
															.prepend(commentElement);
												});
								// 展开二级评论
								comments.addClass("in");
								// 标记二级评论展开状态
								e.setAttribute("data-collapse", "in");
								e.classList.add("active");
							});
		}
		/*$.getJSON("/comment/" + id, function(data) {
			console.log(data);
			
			 * var commentBody = $("#comment-body-" + id);
			 * 
			 * var items = []; $.each(data.data, function(key, val) {
			 * items.push("<li id='" + key + "'>" + val + "</li>"); }); $("<ul/>", {
			 * "class" : "my-new-list", html : items.join("")
			 * }).appendTo("body");
			 
		});

		// 展开二级评论
		comments.addClass("in");
		// 标记二级评论展开
		e.setAttribute("data-collapse", "in");
		e.classList.add("active");*/
	}
}

function collapseComments(e) {
	var id = e.getAttribute("data-id");
	var comments = $("#comment-" + id);

	// 获取一下二级评论的展开状态
	var collapse = e.getAttribute("data-collapse");
	if (collapse) {
		// 折叠二级评论
		comments.removeClass("in");
		e.removeAttribute("data-collapse");
		e.classList.remove("active");
	} else {
		var subCommentContainer = $("#comment-" + id);
		if (subCommentContainer.children().length != 1) {
			// 展开二级评论
			comments.addClass("in");
			// 标记二级评论展开状态
			e.setAttribute("data-collapse", "in");
			e.classList.add("active");
		} else {
			$
					.getJSON(
							"/comment/" + id,
							function(data) {
								$
										.each(
												data.data.reverse(),
												function(index, comment) {
													var mediaLeftElement = $(
															"<div/>",
															{
																"class" : "media-left"
															})
															.append(
																	$(
																			"<img/>",
																			{
																				"class" : "media-object img-rounded",
																				"src" : comment.user.avatarUrl
																			}));

													var mediaBodyElement = $(
															"<div/>",
															{
																"class" : "media-body"
															})
															.append(
																	$(
																			"<h5/>",
																			{
																				"class" : "media-heading",
																				"html" : comment.user.name
																			}))
															.append(
																	$(
																			"<div/>",
																			{
																				"html" : comment.content
																			}))
															.append(
																	$(
																			"<div/>",
																			{
																				"class" : "menu"
																			})
																			.append(
																					$(
																							"<span/>",
																							{
																								"class" : "pull-right",
																								"html" : moment(
																										comment.gmtCreate)
																										.format(
																												'YYYY-MM-DD')
																							})));

													var mediaElement = $(
															"<div/>",
															{
																"class" : "media"
															})
															.append(
																	mediaLeftElement)
															.append(
																	mediaBodyElement);

													var commentElement = $(
															"<div/>",
															{
																"class" : "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
															}).append(
															mediaElement);

													subCommentContainer
															.prepend(commentElement);
												});
								// 展开二级评论
								comments.addClass("in");
								// 标记二级评论展开状态
								e.setAttribute("data-collapse", "in");
								e.classList.add("active");
							});
		}
	}
}