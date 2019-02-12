function ShowCreateModal(id, student_id) {
    console.log("id===>"+id);
    console.log("student_id===>"+student_id);
    $("#uid").attr("value",id);
    $("#student_id2").attr("value",student_id);

    //拉取下拉框的数据
    var xmlHttp = createXMLHttpRequest();
    xmlHttp.open("GET", "http://www.jiandev.cn:4397/student/getAvailableClass", "false");
    xmlHttp.send(null);
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            var text = xmlHttp.responseText;
            console.log(text);
            var s = text.replace('[', '').replace(']', '').replace(/\"/g, '').trim();
            var arr = s.split(",");
            for (var i = 0; i < arr.length; i++) {
                var option = document.createElement("option");
                $(option).val(arr[i]);
                $(option).text(arr[i]);
                $("#student_class2").append(option);

            }
            $("#createFileTitle").text("编辑" + student_id);
            $('#createFileMModal').modal('show');
        }
    };


}

// 关闭弹框， 获取输入值，然后执行逻辑
$("#createFileSureBut").click(function () {
    $('#createFileMModal').modal('hide');

    var id = $("#uid").val();
    var student_id2 = $("#student_id2").val();
    var student_name2 = $("#student_name2").val();
    var student_class2 = $("#student_class2").val();
    var student_tel2 = $("#student_tel2").val();
    var student_gender2 = $("#student_gender2").val();

    var xmlHttp = createXMLHttpRequest();
    xmlHttp.open("POST", "http://www.jiandev.cn:4397/student/updateStudent", "true");
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
    var data = "id=" + id + "&student_id="+student_id2+ "&student_name=" + student_name2 +
        "&student_class=" + student_class2 + "&student_tel=" +
        student_tel2+"&student_gender="+student_gender2;
    xmlHttp.send(data);
    xmlHttp.onreadystatechange = function () {
        checkStatus("修改成功!",xmlHttp);
    }

});

$('#myModal').on('shown.bs.modal', function () {
    $('#myInput').focus()
})

//关闭弹窗
$("#return_main").click(function () {
    $("#exampleModal").modal("hide");
})

$("#make_sure").click(function () {
    $("#exampleModal").modal("hide");
    var id = $("#uid2").val();
    //执行aJax删除
    var xmlHttp = createXMLHttpRequest();
    xmlHttp.open("POST","http://www.jiandev.cn:4397/student/deleteStudent","false");
    xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    xmlHttp.send("id="+id);
    xmlHttp.onreadystatechange = function () {
        checkStatus("删除成功!",xmlHttp);
    }
})

function createXMLHttpRequest() {
    try {
        return new XMLHttpRequest();
    } catch (e) {
        alert("哥们儿,你的浏览器太过时了,换个吧!");
    }
}

function ShowCreateModal2(name, id) {
    $("#uid2").attr("value",id);
    $("#delete_info").text("确定要删除" + name + "吗？");
    $("#exampleModal").modal("show");
    console.log(name);
    console.log(id);

}


function book(){
    $('#myModal').modal('show');
    setTimeout(function(){
        $("#myModal").modal("hide")
    },1200);
}

function setCookie(name, value) {
    var exp = new Date();
    exp.setTime(exp.getTime() + 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
function getCookie(name)
{
    var regExp = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    var arr = document.cookie.match(regExp);
    if (arr == null) {
        return null;
    }
    return unescape(arr[2]);
}

function checkStatus(info,xmlHttp) {
    if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
        var text = xmlHttp.responseText;
        var json = jQuery.parseJSON(text);
        if (json.code == 200) {
            book(info);
            location.reload();
        } else {
            book(json.remark);
        }
    }
}

function book(title) {
    $("#exampleModalLabel2").html(title)
    $('#myModal').modal('show');
    setTimeout(function () {
        $("#myModal").modal("hide")
    }, 2000);
}