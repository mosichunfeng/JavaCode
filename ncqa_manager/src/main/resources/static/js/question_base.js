$(document).ready(function () {


});


!function () {
    laydate.skin('molv'); //切换皮肤，请查看skins下面皮肤库
    laydate({
        elem: '#demo'
    }); //绑定元素
}();
//日期范围限制
var start = {
    elem: '#start',
    format: 'YYYY-MM-DD hh:mm:ss',
    min: laydate.now(), //设定最小日期为当前日期
    max: '2099-06-16', //最大日期
    istime: true,
    istoday: false,
    v1: null,
    choose: function (datas) {
        end.min = datas; //开始日选好后，重置结束日的最小日期
        end.start = datas //将结束日的初始值设定为开始日
        v1 = datas;
    }
};

var end = {
    elem: '#end',
    format: 'YYYY-MM-DD hh:mm:ss',
    min: laydate.now(),
    max: '2099-06-16',
    istime: true,
    istoday: false,
    v2: null,
    choose: function (datas) {
        start.max = datas; //结束日选好后，充值开始日的最大日期
        v2 = datas;
    }
};
laydate(start);
laydate(end);

var isIn = false;

//编辑,新增按钮事件响应
function modifyEvent(id) {
    $("#createFileTitle").text("编辑题库信息");
    $('#createFileMModal').modal('show');
    $("#qb_id").attr("value", id);

    //拉取下拉框的数据
    getChoose(isIn);
    whereIn = 0;
}

var whereIn;

//确定修改/新增按钮事件响应
$("#createFileSureBut").click(function () {
        var id = $("#qb_id").val();
        var name = $("#qb_name").val();
        var description = $("#qb_description").val();
        var start_time = v1;
        var end_time = v2;
        var available = $("#available").val();
        var options = $("#qb_kind option:selected");
        var kind = options.val();
        if (typeof(v1) == "undefined" || typeof(v2) == "undefined") {
            alert("请选择有效的时间");
        } else {
            var xmlHttp = createXMLHttpRequest();
            if (whereIn == 0) {
                //修改
                xmlHttp.open("POST", "http://127.0.0.1:4397/question/updateQuestionBase", "true");
                xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                var data = "id=" + id + "&name=" + name + "&kind_id=" + kind +
                    "&description=" + description + "&start_time=" +
                    start_time + "&end_time=" + end_time + "&available" + available;
                xmlHttp.send(data);
                xmlHttp.onreadystatechange = function () {
                    checkStatus("修改成功!",xmlHttp);
                }
            } else {
                xmlHttp.open("POST", "http://127.0.0.1:4397/question/insertQuestionBase", "true");
                xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                var data = "name=" + name + "&kind_id=" + kind +
                    "&description=" + description + "&start_time=" +
                    start_time + "&end_time=" + end_time + "&available" + available;
                xmlHttp.send(data);
                xmlHttp.onreadystatechange = function () {
                    checkStatus("添加成功!",xmlHttp);
                }
            }
        }
    }
);


function createBaseEvent(id) {
    $("#createFileTitle").text("新增题库");
    $('#createFileMModal').modal('show');
    $("#qb_id").attr("value", id);
    //拉取下拉框的数据
    getChoose(isIn);
    whereIn = 1;
}


function deleteEvent(id) {
    $("#delete_info").text("你你确定要删除该题库吗？");
    $("#exampleModal").modal("show");
    $("#qb_id2").attr("value", id);
}

$("#make_sure").click(function () {
    //执行ajax删除
    var id = $("#qb_id2").val();
    var xmlHttp = createXMLHttpRequest();
    xmlHttp.open("POST", "http://127.0.0.1:4397/question/deleteQuestionBase", "true");
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send("id=" + id);
    xmlHttp.onreadystatechange = function () {
        checkStatus("删除成功",xmlHttp);
    }

});

function createXMLHttpRequest() {
    try {
        return new XMLHttpRequest();
    } catch (e) {
        alert("哥们儿,你的浏览器太过时了,换个吧!");
    }
}

function book(title) {
    $("#exampleModalLabel2").html(title)
    $('#myModal').modal('show');
    setTimeout(function () {
        $("#myModal").modal("hide")
    }, 2000);
}

function getChoose(isInArgue) {
    if (!isInArgue) {
        var xmlHttp = createXMLHttpRequest();
        xmlHttp.open("GET", "http://127.0.0.1:4397/question/findAllKind", "false");
        xmlHttp.send(null);
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
                isIn = true;
                var text = xmlHttp.responseText;
                var json = jQuery.parseJSON(text);
                var arr = json.result;

                for (var i = 0; i < arr.length; i++) {
                    var option = document.createElement("option");
                    $(option).val(arr[i].id);
                    $(option).text(arr[i].name);
                    $("#qb_kind").append(option);
                }
            }
        };
    }
}

/**
 * 打开导入文件模态框
 */
function importFile(id){
    $("#qb_id3").attr("value",id);
    console.log("value:"+$("#qb_id3").val());
    $("#uploadModel").modal('show');
}

$("#select_file").click(function () {
    $("#input-file").click();
});
function test(file){
    console.log(file)
}

$("#input-file").on("change",function (e) {
    var e = e || window.event;
    //获取 文件 个数 取消的时候使用
    var files = e.target.files;
     if(files.length>0){
         var fileName = files[0].name;
         $("#select-info").text(fileName);
     }else{
         //清空文件名
         $("#select-info").text("未选择文件");
     }
});

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