$('[name="status"]').bootstrapSwitch({
    onText: "开启",
    offText: "关闭",
    onColor: "success",
    offColor: "info",
    size: "default",
    onSwitchChange: function (event, state) {
        if (state == true) {
            $(this).val("1");
        } else {
            $(this).val("0");
        }
    }
});

function addUser(){
    $("#createFileMModal").modal('show')
}

$("#addSure").click(function(){
    var username = $("#username").val();
    var password = $("#password").val();

    xmlHttp = createXMLHttpRequest();
    xmlHttp.open("POST", "http://139.199.170.177:4397/authority/addUser", "true");
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send("username="+username+"&password="+password);
    xmlHttp.onreadystatechange = function () {
        checkStatus("修改成功",xmlHttp);
    }
});

function createXMLHttpRequest() {
    try {
        return new XMLHttpRequest();
    } catch (e) {
        alert("哥们儿,你的浏览器太过时了,换个吧!");
    }
}


function submitEvent(id) {
    var add_name = "add" + id;
    var modify_name = "modify" + id;
    var delete_name = "delete" + id;
    var download_name = "download" + id;
    var upload_name = "upload" + id;

    var add = $("#" + add_name).val();
    var modify = $("#" + modify_name).val();
    var delete1 = $("#" + delete_name).val();
    var download = $("#" + download_name).val();
    var upload = $("#" + upload_name).val();

    var xmlHttp = createXMLHttpRequest();

    xmlHttp.open("POST", "http://139.199.170.177:4397/authority/modify", "true");
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var data = "id=" + id +"&auth_add=" + add + "&auth_modify=" + modify
        + "&auth_delete=" + delete1 + "&auth_download=" + download + "&auth_upload=" + upload;
    xmlHttp.send(data);
    xmlHttp.onreadystatechange = function () {
        checkStatus("修改成功",xmlHttp);
    }
}

function book(title) {
    $("#exampleModalLabel2").html(title)
    $('#myModal').modal('show');
    setTimeout(function () {
        $("#myModal").modal("hide")
    }, 2000);
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

function changePage(id) {
    window.location.href="http://139.199.170.177:4397/authority/getIndexByGroup?group_id="+id;

}