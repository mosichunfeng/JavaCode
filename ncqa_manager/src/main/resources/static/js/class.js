var p = 0;

function modify(id,name){
    $("#className").attr('value',name);
    $("#createFileTitle").text("编辑"+name);
    $("#class_id").attr('value',id);
    p=1;
    $("#createFileMModal").modal('show');
}

function drop(id,name){
    $("#class_id2").attr('value',id);
    $("#delete_info").text("您确定要删除"+name+"吗？");
    $("#exampleModal").modal('show');
}

function insert() {
    $("#createFileTitle").text("新增班级");
    p=0;
    $("#createFileMModal").modal('show');
}

$("#createFileSureBut").click(function () {
    var class_id = $("#class_id").val();
    var name = $("#className").val();
    var work_detail = $("#shixi").val();
    var data = "id="+class_id+"&name="+name+"&work_detail="+work_detail;

    var xmlHttp = createXMLHttpRequest();
    if (p == 1) {
        //修改
        xmlHttp.open("POST", "http://127.0.0.1:4397/student/modifyClass", "true");
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlHttp.send(data);
        xmlHttp.onreadystatechange = function () {
            checkStatus("修改成功!",xmlHttp);
        }
    } else {
        xmlHttp.open("POST", "http://127.0.0.1:4397/student/insertClass", "true");
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlHttp.send(data);
        xmlHttp.onreadystatechange = function () {
            checkStatus("添加成功!",xmlHttp);
        }
    }
})

$("#make_sure").click(function () {
    var class_id = $("#class_id2").val();
    var xmlHttp = createXMLHttpRequest();
    xmlHttp.open("POST", "http://127.0.0.1:4397/student/deleteClass", "true");
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send("id=" + class_id);
    xmlHttp.onreadystatechange = function () {
        checkStatus("删除成功",xmlHttp);
    }
})

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

function createXMLHttpRequest() {
    try {
        return new XMLHttpRequest();
    } catch (e) {
        alert("哥们儿,你的浏览器太过时了,换个吧!");
    }
}


