function start() {
    $("#createFileMModal").modal('show');
}


$("#createFileSureBut").click(function () {
    var cost_time = $("#call_time").val();
    if(cost_time.trim()==''){
        cost_time = 5;
    }

    obj = document.getElementsByName("classes");
    check_val = [];
    for (k in obj) {
        if (obj[k].checked)
            check_val.push(obj[k].value);
    }
    var xmlHttp = createXMLHttpRequest();
    xmlHttp.open("POST", "http://139.199.170.177:4397/call/insertCall", "true");
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send("cost_time=" + cost_time+"&classes="+check_val);
    xmlHttp.onreadystatechange = function () {
        checkStatus("添加成功",xmlHttp);
    }
})


function checkStatus(info, xmlHttp) {
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


