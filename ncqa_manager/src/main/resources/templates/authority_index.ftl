<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>ncqa权限模块</title>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../static/jQuery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap-switch.css">
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap-switch.js"></script>
    <link rel="stylesheet" href="../static/css/authority.css/">

</head>

<body>
<div class="container">
    <div class="">
        <h1 align="center" 欢迎进入权限模块</h1> </div>
    <div class="hh">
        <span class="label label-warning zz"><#if user.username??>${user.username}</#if></span>
        <a class="btn btn-info btn-sm" href="http://127.0.0.1:4397/main.html">首页</a>
        <a class="btn btn-info btn-sm" href="http://127.0.0.1:4397/logout">退出登录</a>
        <button class="btn btn-info btn-sm" onclick="addUser()">添加用户</button>
    </div>

    <ul id="myTab" class="nav nav-tabs">
        <li class="active">
            <a href="#student" data-toggle="tab">
                学生模块
            </a>
        </li>
        <li><a href="#question" data-toggle="tab">答题模块</a></li>
        <li><a href="#register" data-toggle="tab">签到模块</a></li>
    </ul>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="student">
            <table class="table table-bordered table-striped table-hover ">
                <tr class="success">
                    <td>编号</td>
                    <td>账号</td>
                    <td>新增权限</td>
                    <td>修改权限</td>
                    <td>删除权限</td>
                    <td>上传权限</td>
                    <td>下载权限</td>
                    <td>操作</td>
                </tr>
                <#if result.authorityList??>
                    <#list result.authorityList as authority>
                        <tr>
                            <td>${authority.id}</td>
                            <td>${authority.username}</td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="add${authority.id}"
                                           <#if authority.auth_add==1>checked="true"</#if> type="checkbox"
                                           value="${authority.auth_add}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="modify${authority.id}" type="checkbox"
                                           <#if authority.auth_modify==1>checked="true"</#if>
                                           value="${authority.auth_modify}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="delete${authority.id}" type="checkbox"
                                           <#if authority.auth_delete==1>checked="true"</#if>
                                           value="${authority.auth_delete}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="download${authority.id}" type="checkbox"
                                           <#if authority.auth_download==1>checked="true"</#if>
                                           value="${authority.auth_download}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="upload${authority.id}" type="checkbox"
                                           <#if authority.auth_upload==1>checked="true"</#if>
                                           value="${authority.auth_upload}" data-size="small">
                                </div>
                            </td>
                            <td>
                                <button class="btn btn-success btn-xs" onclick="submitEvent(${authority.id})">提交
                                </button>
                            </td>
                        </tr>
                    </#list>
                </#if>
            </table>
        </div>
        <div class="tab-pane fade" id="question">
            <table class="table table-bordered table-striped table-hover ">
                <tr class="success">
                    <td>编号</td>
                    <td>账号</td>
                    <td>新增权限</td>
                    <td>修改权限</td>
                    <td>删除权限</td>
                    <td>上传权限</td>
                    <td>下载权限</td>
                    <td>操作</td>
                </tr>
                <#if result.authorityList2??>
                    <#list result.authorityList2 as authority>
                        <tr>
                            <td>${authority.id}</td>
                            <td>${authority.username}</td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="add${authority.id}"
                                           <#if authority.auth_add==1>checked="true"</#if> type="checkbox"
                                           value="${authority.auth_add}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="modify${authority.id}" type="checkbox"
                                           <#if authority.auth_modify==1>checked="true"</#if>
                                           value="${authority.auth_modify}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="delete${authority.id}" type="checkbox"
                                           <#if authority.auth_delete==1>checked="true"</#if>
                                           value="${authority.auth_delete}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="download${authority.id}" type="checkbox"
                                           <#if authority.auth_download==1>checked="true"</#if>
                                           value="${authority.auth_download}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="upload${authority.id}" type="checkbox"
                                           <#if authority.auth_upload==1>checked="true"</#if>
                                           value="${authority.auth_upload}" data-size="small">
                                </div>
                            </td>
                            <td>
                                <button class="btn btn-success btn-xs" onclick="submitEvent(${authority.id})">提交
                                </button>
                            </td>
                        </tr>
                    </#list>
                </#if>
            </table>
        </div>
        <div class="tab-pane fade" id="register">
            <table class="table table-bordered table-striped table-hover ">
                <tr class="success">
                    <td>编号</td>
                    <td>账号</td>
                    <td>查看权限</td>
                    <td>修改权限</td>
                    <td>删除权限</td>
                    <td>上传权限</td>
                    <td>下载权限</td>
                    <td>操作</td>
                </tr>
                <#if result.authorityList3??>
                    <#list result.authorityList3 as authority>
                        <tr>
                            <td>${authority.id}</td>
                            <td>${authority.username}</td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="add${authority.id}"
                                           <#if authority.auth_add==1>checked="true"</#if> type="checkbox"
                                           value="${authority.auth_add}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="modify${authority.id}" type="checkbox"
                                           <#if authority.auth_modify==1>checked="true"</#if>
                                           value="${authority.auth_modify}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="delete${authority.id}" type="checkbox"
                                           <#if authority.auth_delete==1>checked="true"</#if>
                                           value="${authority.auth_delete}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="download${authority.id}" type="checkbox"
                                           <#if authority.auth_download==1>checked="true"</#if>
                                           value="${authority.auth_download}" data-size="small">
                                </div>
                            </td>
                            <td class="tdd">
                                <div class="ha">
                                    <input name="status" id="upload${authority.id}" type="checkbox"
                                           <#if authority.auth_upload==1>checked="true"</#if>
                                           value="${authority.auth_upload}" data-size="small">
                                </div>
                            </td>
                            <td>
                                <button class="btn btn-success btn-xs" onclick="submitEvent(${authority.id})">提交
                                </button>
                            </td>
                        </tr>
                    </#list>
                </#if>
            </table>
        </div>
    </div>

    <!--弹出框编辑-->
    <div class="modal fade" id="createFileMModal" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="createFileTitle">
                        添加新的管理员
                    </h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">

                            <label for="username" class="col-form-label sr-only">姓名</label>
                            <input type="text" autofocus class="form-control" id="username"
                                   placeholder="用户名">
                            <br>
                            <br>
                            <label for="password" class="col-form-label sr-only">密码</label>
                            <input type="text" autofocus class="form-control" id="password" placeholder="密码">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="addSure">确定</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal" role="dialog" data-backdrop="false" aria-hidden="true">
        <div class="modal-dialog bg">
            <div class="modal-content xi">
                <div class="modal-body">
                    <h4 class="modal-body label label-success" id="exampleModalLabel2">操作成功!</h4>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="../static/js/authority.js"></script>
</body>

</html>