var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {

            _this.save();
        });
        $('#btn-update').on('click', function (){

            _this.update();
        });
    },
    save : function () {
        alert('누름');
        var data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val()
        };
        alert('버튼');
        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        alert('수정누름');
        var data = {
            title: ${'#title'}.val(),
            content: ${'#content'}.val()
        };
        var id = ${'#id'}.val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert("수정완료");
            window.location.href = '/';
        }).fail(function(error){
            alert("수정실패, ERROR: " + JSON.stringify(error));
        });
    }
    delete : function(){
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        )}.done(function(){
            alert('삭제완료');
        }).fail(function(error){
            alert('삭제실패, ERROR: '+JSON.stringify(error));
        });
    }
};

main.init();