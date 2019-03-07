<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>ncqa签到模块</title>

    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../static/jQuery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.js"></script>
    <link rel="stylesheet" href="../static/css/call.css">
</head>

<body>
<div class="container">
    <span>
            <h1 align="center" class="h1">欢迎进入点名管理系统</h1>
    </span>

    <div class="hh">
        <span class="label label-warning zz"><#if user.username??>${user.username}</#if></span>
        <a class="btn btn-info btn-sm" href="http://127.0.0.1:4397/main.html">首页</a>
        <a class="btn btn-info btn-sm" href="http://127.0.0.1:4397/logout">退出登录</a>
        <button class="btn btn-success btn-sm" onclick="start()">开始点名</a></button>
    </div>
    <br>
    <br>

    <table class="table table-bordered table-striped table-hover ">
        <tr class="warning">
            <td>编号</td>
            <td>班级列表</td>
            <td>开始时间</td>
            <td>结束时间</td>
            <td>状态</td>
            <td>可操作列表</td>
        </tr>

         <#if result.result.callList??>
             <#list result.result.callList as call>
                <tr class="info">
                    <td>${call.id}</td>
                    <td>${call.classes}</td>
                    <td>${call.start_time}</td>
                    <td>${call.end_time}</td>
                    <td>
                    <#if call.status == 0>
                        进行中
                    <#else>
                        已结束
                    </#if>
                    </td>
                    <td>
                        <a type="button" class="btn btn-info"
                           href="http://127.0.0.1:4397/register/findRegisterListByCallId?call_id=${call.id}">查看列表
                        </a>
                        <a type="button" class="btn btn-success"
                           href="http://127.0.0.1:4397/call/exportCallExcel?call_id=${call.id}">导出
                            <a type="button" class="btn btn-warning"
                               href="http://127.0.0.1:4397/call/exportNoCallExcel?call_id=${call.id}">导出未签到</a>
                        </a>
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
                        <a class="btn btn-success " href="http://127.0.0.1:4397/call/pageQuery">首页</a>
                        <a class="btn btn-success "
                           href="http://127.0.0.1:4397/call/pageQuery?pageNo=${result.basePage.pageNo-1}&rowSrt=${result.basePage.rowSrt-result.basePage.pageSize}">上一页</a>
                    </#if>
                    <#if (result.basePage.pageNo<result.basePage.pages)>
                        <a class="btn btn-success"
                           href="http://127.0.0.1:4397/call/pageQuery?pageNo=${result.basePage.pageNo+1}&rowSrt=${result.basePage.rowSrt+result.basePage.pageSize}">下一页</a>
                        <a class="btn btn-success"
                           href="http://127.0.0.1:4397/call/pageQuery?pageNo=${result.basePage.pages}">尾页</a>
                    </#if>
                    <input type="text" class="form-control input-medium" id="page_index" placeholder="页数">
                    <button class="btn btn-success ">跳页</button>

                </div>
            </div>
        </form>
    </div>

    <!--弹出框编辑-->
    <div class="modal fade" id="createFileMModal" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createFileTitle"></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <input type="text" autofocus class="form-control" required="required" id="call_time" pattern="^([1-9]|[1-9]\\d|100)$" title="请输入1-100的整数!"
                                   placeholder="签到时间(分钟),不填则为5">
                            <br>
                            <label class="label-info">选择点名班级</label>
                            <#if result.result.classList??>
                                <#list result.result.classList as classInfo>
                                    <li>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" name="classes" id="cbox" required="required"
                                                   value="${classInfo.id}">${classInfo.name}
                                        </label>
                                    </li>
                                </#list>
                            </#if>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="createFileSureBut">确定</button>
                </div>
            </div>
        </div>
    </div>


    <!-- data-backdrop="false"去除遮罩层  -->
    <div class="modal fade" id="myModal" role="dialog" data-backdrop="false" aria-hidden="true">
        <div class="modal-dialog bg">
            <div class="modal-content xi">
                <div class="modal-body">
                    <h4 class="modal-body label label-success" id="exampleModalLabel2"></h4>
                </div>
            </div>
        </div>
    </div>

</div>

</div>

<script type="text/javascript" src="../static/js/call.js"></script>
</body>

</html>