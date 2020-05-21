/*******************************************************************************
 * 提交回复
 * 
 * @returns
 */
function post() {
	var id = $("#question_id").val();
	var content = $("#content").val();

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
					"parentId" : id,
					"content" : content,
					"type" : 1
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

/*******************************************************************************
 * 二级评论
 * 
 * @returns
 */
function coll(e) {
	var id = e.getAttribute("data-id");
	var comments = $("#comment-" + id);
	var collapse=e.getAttribute("data-collapse");
	if(collapse){
		comments.removeClass("in");
		e.removeAttribute("data-collapse");
		e.classList.remove("active");
	}else{
		//展开二级评论
		comments.addClass("in");
		//标记二级评论展开
		e.setAttribute("data-collapse", "in");
		e.classList.add("active");
	}
	
	
}