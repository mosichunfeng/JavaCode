<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>ncqa班级模块</title>

    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../static/jQuery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.js"></script>
    <link rel="stylesheet" href="../static/css/class.css">
</head>

<body>
<div class="container">
    <span>
            <h1 align="center" class="h1">欢迎进入班级管理系统</h1>
    </span>

    <div class="hh">
        <span class="label label-warning zz"><#if user.username??>${user.username}</#if></span>
        <a class="btn btn-info btn-sm" href="http://139.199.170.177:4397/main.html">首页</a>
        <a class="btn btn-info btn-sm" href="http://139.199.170.177:4397/logout">退出登录</a>
        <button class="btn btn-success btn-sm" onclick="insert()">新增班级</a></button>
    </div>
    <br>
    <br>

    <table class="table table-bordered table-striped table-hover ">
        <tr class="warning">
            <td>编号</td>
            <td>班级名称</td>
            <td>人数</td>
            <td>实习权限</td>
            <td>可操作列表</td>
        </tr>

         <#if result.result.classInfoList??>
             <#list result.result.classInfoList as classInfo>
                <tr class="info">
                    <td>${classInfo.id}</td>
                    <td>${classInfo.name}</td>
                    <td>${classInfo.member}</td>
                    <td>
                    <#if classInfo.work_detail == 0>
                        有
                    <#else>
                            无
                    </#if>
                    </td>
                    <td>
                        <button type="button" class="btn btn-info" onclick="modify(${classInfo.id},'${classInfo.name}')">编辑</button>
                        <button type="button" class="btn btn-success" onclick="drop(${classInfo.id},'${classInfo.name}')">删除</button>
                        <a type="button" class="btn btn-warning" href="http://139.199.170.177:4397/student/pageQuery?student_class_id=${classInfo.id}&flag=1">学生列表</a>
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
                        <a class="btn btn-success " href="http://139.199.170.177:4397/student/pageQueryClass">首页</a>
                        <a class="btn btn-success " href="http://139.199.170.177:4397/student/pageQueryClass?pageNo=${result.basePage.pageNo-1}&rowSrt=${result.basePage.rowSrt-result.basePage.pageSize}">上一页</a>
                    </#if>
                    <#if (result.basePage.pageNo<result.basePage.pages)>
                        <a class="btn btn-success" href="http://139.199.170.177:4397/student/pageQueryClass?pageNo=${result.basePage.pageNo+1}&rowSrt=${result.basePage.rowSrt+result.basePage.pageSize}">下一页</a>
                        <a class="btn btn-success" href="http://139.199.170.177:4397/student/pageQueryClass?pageNo=${result.basePage.pages}">尾页</a>
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
                            <label for="student_name2" class="col-form-label sr-only">班级名称</label>
                            <input id="class_id" type="hidden"></input>
                            <input type="text"  autofocus class="form-control" id="className"
                                   placeholder="班级名称">
                            <br>
                            <br>
                            <label for="shixi" class="col-form-label">实习权限</label>
                            <select name="shixi" class="form-control" id="shixi">
                                <option value="1">有</option>
                                <option value="0">无</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="createFileSureBut">确定</button>
                </div>
            </div>
        </div>
    </div>

    <!--删除对话框-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">确认框</h4>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="message-text" class="control-labe" id="delete_info"></label>
                            <input id="class_id2" type="hidden">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="return_main" data-dismiss="modal">返回</button>
                    <button type="button" class="btn btn-primary" id="make_sure">确认</button>
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

<script type="text/javascript" src="../static/js/class.js"></script>
</body>

</html>