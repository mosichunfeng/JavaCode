<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>ncqa签到模块</title>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../static/jQuery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="../static/js/datejs/laydate.js"></script>
    <link rel="stylesheet" href="../static/css/register.css">
</head>

<body>

<div class="container">
    <div class="">
        <h1 align="center" class="">欢迎进入签到管理系统</h1>
    </div>

    <div class="hh">
        <span class="label label-warning zz"><#if user.username??>${user.username}</#if></span>
        <a class="btn btn-info btn-sm" href="http://139.199.170.177:4397/logout">退出登录</a>
    </div>

    <table class="table table-bordered table-striped table-hover ">
        <tr class="warning">
            <td>编号</td>
            <td>班级</td>
            <td>学号</td>
            <td>姓名</td>
            <td>签到时间</td>
            <td>签到地点</td>
            <td>可操作列表</td>
        </tr>
        <#if result.result.registerList??>
            <#list result.result.registerList as register>
            <tr class="success">
                <td>${register.id}</td>
                <td>${register.student_class                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             }</td>
                <td>${register.student_id}</td>
                <td>${register.student_name}</td>
                <td>${register.start_time}</td>
                <td>${register.address}</td>
                <td>
                    <a type="button" class="btn btn-primary" id="make_sure">查看照片</a>
                </td>
            </tr>
            </#list>
        </#if>
    </table>

    <!-- 分页 -->
    <div class="xi">
        <form class="form-inline">
            <div class="form-group">
                <div class="input-inline input-inline">
                    <#if result.basePage.pageNo != 1>
                        <a class="btn btn-success " href="http://139.199.170.177:4397/register/findRegisterListByCallId?call_id=${call_id}">首页</a>
                        <a class="btn btn-success " href="http://139.199.170.177:4397/register/findRegisterListByCallId?call_id=${call_id}&pageNo=${result.basePage.pageNo-1}&rowSrt=${result.basePage.rowSrt-result.basePage.pageSize}">上一页</a>
                    </#if>
                    <#if (result.basePage.pageNo<result.basePage.pages)>
                        <a class="btn btn-success" href="http://139.199.170.177:4397/register/findRegisterListByCallId?call_id=${call_id}&pageNo=${result.basePage.pageNo+1}&rowSrt=${result.basePage.rowSrt+result.basePage.pageSize}">下一页</a>
                        <a class="btn btn-success" href="http://139.199.170.177:4397/register/findRegisterListByCallId?call_id=${call_id}&pageNo=${result.basePage.pages}">尾页</a>
                    </#if>
                    <input type="text" class="form-control input-medium" id="page_index" placeholder="页数">
                    <button class="btn btn-success ">跳页</button>

                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>