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

		$.getJSON("/comment/" + id, function(data) {
			console.log(data);
			/*var commentBody = $("#comment-body-" + id);

			var items = [];
			$.each(data.data, function(key, val) {
				items.push("<li id='" + key + "'>" + val + "</li>");
			});
			$("<ul/>", {
				"class" : "my-new-list",
				html : items.join("")
			}).appendTo("body");*/
		});

		// 展开二级评论
		comments.addClass("in");
		// 标记二级评论展开
		e.setAttribute("data-collapse", "in");
		e.classList.add("active");
	}

}