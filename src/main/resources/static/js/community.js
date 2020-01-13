
$(document).ready(function() {

});

function postReply() {
    var questionId = $("#question_id").val();
    var replyText = $("#comment_context").val();

    $.ajax({
      type: "POST",
      url: "/comment",
      contentType: "application/json",
      data: JSON.stringify({
        "parentId": questionId,
        "context": replyText,
        "type": 1
      }),
      success: function(resp) {
        if (resp.code == 1000) {
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