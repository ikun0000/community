
$(document).ready(function() {

});

// 提交回复
function postReply() {
    var questionId = $("#question_id").val();
    var replyText = $("#comment_context").val();
    comment2targer(questionId, 1, replyText);
}

function comment2targer(targetId, type, content) {
    if (!content) {
        alert("评论内容不能为空");
        return;
    }

    $.ajax({
      type: "POST",
      url: "/comment",
      contentType: "application/json",
      data: JSON.stringify({
        "parentId": targetId,
        "context": content,
        "type": type
      }),
      success: function(resp) {
        if (resp.code == 1000) {
            window.location.reload();
            $("#reply_area").hide();
        } else if (resp.code == 2003) {
            var isAccept = confirm(resp.message);

            if (isAccept) {
                window.open("https://github.com");
                window.localStorage.setItem("closeble", true);
            } else {
            }

        } else {
            alert(resp.message);
        }
      },
      dataType: "json"
    });
}

function comment(self) {
    var commentId = self.getAttribute("data-id");
    var replyText = $("#input-" + commentId).val();
    comment2targer(commentId, 2, replyText);
}

// 展开二级评论
function callapseComments(self) {
    var id = self.getAttribute("data-id");
    var comments = $("#comment-" + id);

    if (comments.hasClass("in")) {
        // 折叠二级评论
        comments.removeClass("in");
    } else {
        // 展开二级评论

        $.getJSON( "/comment/" + id, function( resp ) {
            var comments = $("#comment-" + id);
            var replys = $("#replys-" + id);

            $(replys).empty();

            $.each( resp.data, function( key, val ) {
                var dateObj = new Date(val.gmtCreate);
                var dateText = dateObj.getFullYear() + "-" + ( dateObj.getMonth() + 1 ) + "-" + dateObj.getDate();
                var html = "<div class='media' >" +
                           "<div class='media-left'>" +
                           "<a href='#'>" +
                           "<img class='media-object img-thumbnail' " + "src='" + val.user.avatarUrl + "'" + " style='width: 64px; height: 64px'>" +
                           "</a>" +
                           "</div>" +
                           "<div class='media-body'>" +
                           "<h5>" + val.comments + "</h5>" +
                           "<div>" +
                           "<span style='color: #999;' class='pull-right'>" + dateText + "</span>" +
                           "</div>" +
                           "</div>" +
                           "<hr>" +
                           "</div>";
                $(replys).prepend(html);
            });

        });

        comments.addClass("in");
    }

}