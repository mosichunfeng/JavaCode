<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"></meta>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"></link>
    <script type="text/javascript" src="../static/jQuery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.js"></script>
    <link rel="stylesheet" href="../static/css/student.css"/>
</head>
<body>


<div class="container">
        <span>
            <h1 align="center" class="h1">欢迎进入学生管理系统</h1>
        </span>

    <div class="hh">
        <span class="label label-warning zz"><#if user.username??>${user.username}</#if></span>
        <a class="btn btn-info btn-sm" href="http://www.jiandev.cn:4397/logout">退出登录</a>
    </div>
    <br>
    <br>

    <form action="http://www.jiandev.cn:4397/student/pageQuery" class="form-inline xi">
        <div class="form-group">
            <label for="student_id" class="sr-only" >学号：</label>
            <input type="text" name="student_id" <#if searchInfo.student_id??>value="${searchInfo.student_id}"</#if> class="form-control" id="student_id" placeholder="学号">
        </div>
        <div class="form-group">
            <label for="student_name" class="sr-only" >姓名：</label>
            <input type="text" name="student_name" class="form-control" <#if searchInfo.student_name??>value="${searchInfo.student_name}"</#if> id="student_name" placeholder="姓名">
        </div>
        <div class="form-group">
            <label for="student_class" class="sr-only" >班级：</label>
            <input type="text" name="student_class" class="form-control" <#if searchInfo.student_class??>value="${searchInfo.student_class}"</#if> id="student_class" placeholder="班级">
        </div>
        <div class="form-group">
            <label for="student_tel" class="sr-only" >手机号：</label>
            <input type="text" name="student_tel" class="form-control" <#if searchInfo.student_tel??>value="${searchInfo.student_tel}"</#if> id="student_tel" placeholder="手机号">
        </div>

        <div class="form-group">
            <label for="work_detail" class="sr-only" >是否实习</label>
            <select name="work_detail" class="form-control" <#if searchInfo.work_detail??>value="${searchInfo.work_detail}"</#if>>
                <option value="">全部</option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </div>
        <div class="form-group">
            <label for="submit" class="sr-only">搜索:</label>
            <input type="submit" class="btn btn-default" placeholder="搜索" />
        </div>
    </form>
    <br/>
    <table class="table table-bordered table-striped table-hover ">
        <tr class="warning">
            <td>学号</td>
            <td>姓名</td>
            <td>班级</td>
            <td>性别</td>
            <td>手机号</td>
            <td>是否实习</td>
            <td>可操作列表</td>
        </tr>


            <#if result.result.studentList??>
                <#list result.result.studentList as student>
                <tr class="success">
                    <td>${student.student_id}</td>
                    <td>${student.student_name}</td>
                    <td>${student.student_class}</td>
                    <td>${student.student_gender}</td>
                    <td>
                        <#if student.student_tel??>
                            ${student.student_tel}
                        <#else>
                            无
                        </#if>
                    </td>
                    <td>
                    <#if student.work_detail??>
                        <#if student.work_detail==0>
                            未实习
                        <#else>
                            已实习
                        </#if>
                        <#else >
                        未实习
                    </#if>
                    </td>
                    <td>
                        <button type="button" class="btn btn-info" onclick="ShowCreateModal(${student.id},${student.student_id})">编辑</button>
                        <button type="button" class="btn btn-success" onclick="ShowCreateModal2('${student.student_name}',${student.id})">
                            删除
                        </button>
                        <button type="button" class="btn btn-warning">查看详情</button>
                    </td>
                </tr>
                </#list>
            </#if>
    </table>
    <div class="xi">
        <form class="form-inline">
            <div class="form-group">
                <div class="input-inline input-inline">
                    <#if result.basePage.pageNo != 1>
                        <a class="btn btn-info btn-sm" href="http://www.jiandev.cn:4397/student/pageQuery?student_id=
                        <#if searchInfo.student_id??>
                                ${searchInfo.student_id}
                        </#if>
                        &student_name=
                        <#if searchInfo.student_name??>
                            ${searchInfo.student_name}
                        </#if>
                    &student_class=
                        <#if searchInfo.student_class??>
                            ${searchInfo.student_class}
                        </#if>
                    &student_student_tel=
                        <#if searchInfo.student_student_tel??>
                            ${searchInfo.student_student_tel}
                        </#if>
                    &work_detail=
                        <#if searchInfo.work_detail??>
                            ${searchInfo.work_detail}
                        </#if>">首页</a>
                        <a class="btn btn-info btn-sm" href="http://www.jiandev.cn:4397/student/pageQuery?pageNo=${result.basePage.pageNo-1}&rowSrt=${result.basePage.rowSrt-result.basePage.pageSize}&student_id=
                        <#if searchInfo.student_id??>
                                ${searchInfo.student_id}
                        </#if>
                        &student_name=
                        <#if searchInfo.student_name??>
                            ${searchInfo.student_name}
                        </#if>
                    &student_class=
                        <#if searchInfo.student_class??>
                            ${searchInfo.student_class}
                        </#if>
                    &student_student_tel=
                        <#if searchInfo.student_student_tel??>
                            ${searchInfo.student_student_tel}
                        </#if>
                    &work_detail=
                        <#if searchInfo.work_detail??>
                            ${searchInfo.work_detail}
                        </#if>">上一页</a>
                    </#if>


                    <#if (result.basePage.pageNo<result.basePage.pages)>
                        <a class="btn btn-info btn-sm"
                           href="http://www.jiandev.cn:4397/student/pageQuery?pageNo=${result.basePage.pageNo+1}&rowSrt=${result.basePage.rowSrt+result.basePage.pageSize}&student_id=
                        <#if searchInfo.student_id??>
                                ${searchInfo.student_id}
                        </#if>
                        &student_name=
                        <#if searchInfo.student_name??>
                            ${searchInfo.student_name}
                        </#if>
                    &student_class=
                        <#if searchInfo.student_class??>
                            ${searchInfo.student_class}
                        </#if>
                    &student_student_tel=
                        <#if searchInfo.student_student_tel??>
                            ${searchInfo.student_student_tel}
                        </#if>
                    &work_detail=
                        <#if searchInfo.work_detail??>
                            ${searchInfo.work_detail}
                        </#if>">下一页</a>
                        <a class="btn btn-info btn-sm"
                           href="http://www.jiandev.cn:4397/student/pageQuery?pageNo=${result.basePage.pages}&student_id=
                        <#if searchInfo.student_id??>
                                ${searchInfo.student_id}
                        </#if>
                        &student_name=
                        <#if searchInfo.student_name??>
                            ${searchInfo.student_name}
                        </#if>
                    &student_class=
                        <#if searchInfo.student_class??>
                            ${searchInfo.student_class}
                        </#if>
                    &student_student_tel=
                        <#if searchInfo.student_student_tel??>
                            ${searchInfo.student_student_tel}
                        </#if>
                    &work_detail=
                        <#if searchInfo.work_detail??>
                            ${searchInfo.work_detail}
                        </#if>">尾页</a>
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
                            <input type="hidden" id="uid"></input>
                            <input type="hidden" id="student_id2"></input>
                            <label for="student_name2" class="col-form-label sr-only">姓名</label>
                            <input type="text"  autofocus class="form-control" id="student_name2"
                                   placeholder="姓名">
                            <label for="student_class2" class="col-form-label">班级</label>
                            <br>
                            <select id="student_class2" class="form-control">
                                <#--<option value="网络工程15201">网络工程15201</option>-->
                            </select>
                            <label for="student_gender2" class="col-form-label">性别</label>
                            <select id="student_gender2" class="form-control">
                                <option value="男">男</option>
                                <option value="女">女</option>
                            </select>
                            <label for="student_tel2" class="col-form-label sr-only">手机号</label>
                            <input type="text" autofocus class="form-control" id="student_tel2" placeholder="手机号">
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
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                            <label for="message-text" class="control-label" id="delete_info"></label>
                            <input type="hidden" id="uid2"></input>
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

    <!--自动消失模态框-->
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
<script type="text/javascript" src="../static/js/student.js"></script>
</body>
</html>