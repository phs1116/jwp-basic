String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".answerWrite input[type=submit]").click(addAnswer);
$(".form-delete").submit(deleteAnswer);

function addAnswer(e){
  e.preventDefault();
  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    success : function (json, status) {
      var answerTemplate = $("#answerTemplate").html();
      var template = answerTemplate.format(json.savedAnswer.writer, new Date(json.savedAnswer.createdDate), json.savedAnswer.contents, json.savedAnswer.answerId);
      $(".qna-comment-slipp-articles").prepend(template);
    }
  });
}

function deleteAnswer(e){
  e.preventDefault();
  var queryString = $(this).closest("form").serialize();
  var article = $(this).closest(".article");
  $.ajax({
    type: 'post',
    url: '/api/qna/deleteAnswer',
    data: queryString,
    dataType: 'json',
    success : function (json, status) {
      if(json.result.status) {
        article.remove();
      } else{
        alert("삭제 실패");
      }
    }

  })

}