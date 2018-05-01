// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: 'post',
    url: '/api/qna/addAnswer',
    data: queryString,
    dataType: 'json',
    error: function onError(xhr, status) {
      alert("error");
    },
    success: function onSuccess(json, status) {
      var answer = json.answer;
      var answerTemplate = $("#answerTemplate").html();
      var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
      $(".qna-comment-slipp-articles").prepend(template);
    },
  });
}


String.prototype.format = function () {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function (match, number) {
    return typeof args[number] != 'undefined'
      ? args[number]
      : match
      ;
  });
};

$(document).on("click", ".qna-comment button[type=submit]", deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  var deleteBtn = $(this);
  var queryString = deleteBtn.closest(".form-delete").serialize();

  $.ajax({
    type: 'post',
    url: '/api/qna/deleteAnswer',
    data: queryString,
    dataType: 'json',
    error: function onError(xhr, status) {
      alert("error");
    },
    success: function onSuccess(json, status) {
      deleteBtn.closest(".article").remove();
    },
  });
}