<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../static/jQuery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript" src="../static/js/datejs/laydate.js"></script>
    <link rel="stylesheet" href="../static/css/question_base.css">
</head>

<body>
<div class="container">
    <div class="">
        <h1 align="center" class="">欢迎进入题库管理系统</h1>
    </div>

    <div class="hh">
        <span class="label label-warning zz"><#if user.username??>${user.username}</#if></span>
        <a class="btn btn-info btn-sm" href="http://www.jiandev.cn:4397/logout">退出登录</a>
        <button class="btn btn-success btn-sm" onclick="createBaseEvent(1008611)">新增题库</a></button>
    </div>

    <table class="table table-bordered table-striped table-hover ">


        <tr class="warning">
            <td>编号</td>
            <td>类别</td>
            <td>题库名</td>
            <td>题库描述</td>
            <td>创建时间</td>
            <td>开始日期</td>
            <td>截止日期</td>
            <td>是否有效</td>
            <td>操作列表</td>
        </tr>
 <#if result.result.questionBaseList??>
     <#list result.result.questionBaseList as questionBase>
        <tr class="success">
            <td>${questionBase.id}</td>
            <td>${questionBase.kind_name}</td>
            <td>${questionBase.name}</td>
            <td>${questionBase.description}</td>
            <td>${questionBase.create_time}</td>
            <td>${questionBase.start_time}</td>
            <td>${questionBase.end_time}</td>
            <#if questionBase.available==1>
                <td>有效</td>
                <#else >
                   <td>无效</td>
            </#if>
            <td>
                <a class="btn btn-info success" href="http://www.jiandev.cn:4397/question/pageQuery?question_base_id=${questionBase.id}">查看</a>
                <button type="button" class="btn btn-info" onclick="modifyEvent(${questionBase.id})">编辑</button>
                <button type="button" class="btn btn-success" onclick="deleteEvent(${questionBase.id})">删除</button>
                <button type="button" class="btn btn-info" onclick="importFile(${questionBase.id})">导入</button>
                <a class="btn btn-warning" href="http://www.jiandev.cn:4397/question/exportExamGrade?question_base_id=${questionBase.id}">导出</a>
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
                        <a class="btn btn-success " href="http://www.jiandev.cn:4397/question/pageQueryBase">首页</a>
                        <a class="btn btn-success " href="http://www.jiandev.cn:4397/question/pageQueryBase?pageNo=${result.basePage.pageNo-1}&rowSrt=${result.basePage.rowSrt-result.basePage.pageSize}">上一页</a>
                    </#if>
                    <#if (result.basePage.pageNo<result.basePage.pages)>
                        <a class="btn btn-success" href="http://www.jiandev.cn:4397/question/pageQueryBase?pageNo=${result.basePage.pageNo+1}&rowSrt=${result.basePage.rowSrt+result.basePage.pageSize}">下一页</a>
                        <a class="btn btn-success" href="http://www.jiandev.cn:4397/question/pageQueryBase?pageNo=${result.basePage.pages}">尾页</a>
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
                    <h5 class="modal-title" id="createFileTitle">编辑抗压吧年度答题</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group form">
                            <input type="hidden" id="qb_id"></input>
                            <label>请输入题库名称:</label><br>
                            <input type="text" id="qb_name">
                            <br><br>
                            <label>请选择题库类型:</label><br>
                            <select id="qb_kind" style="height:30px">

                            </select>
                            <br>
                            <br>
                            请输入题库描述：<br>
                            <textarea id="qb_description" style="width:400px"></textarea>
                            <br>
                            <br>
                            <div class="box">
                                <div class="demo3">
                                    <!-- <ul class="inline"> -->
                                    开始日：
                                    <li class="inline laydate-icon" id="start"
                                        style="width:200px; margin-right:10px;"></li>
                                    <br>
                                    结束日：
                                    <li class="inline laydate-icon" id="end" style="width:200px;"></li>
                                    <!-- </ul> -->

                                </div>
                            </div>
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
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel1"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="sure">确认框</h4>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <label for="make_sure_title" class="control-label" id="delete_info"></label>
                            <input type="hidden" id="qb_id2"></input>
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

    <!--上传文件对话框-->
    <!--删除对话框-->
    <div class="modal fade" id="uploadModel" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel1"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="sure">Excel模板上传</h4>
                </div>
                <div class="modal-body">
                    <form enctype="multipart/form-data" action="http://www.jiandev.cn:4397/question/importQuestion" method="post">
                        <input type="hidden" name="question_base_id" id="qb_id3"></input>
                        <input type="file" name="proxyfile" id="input-file" style="display: none;"  accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel">
                        <button type="button" class="btn btn-success" id="select_file">选择文件</button>
                        <br>
                        <br>
                        <label class="control-label" id="select-info">未选择文件</label>
                        <br>
                        <br>
                        <a class="btn btn-success" href="http://www.jiandev.cn:4397/question/exportTemplate" id="tp_dw">模板下载</a>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" id="cacel" data-dismiss="modal">返回</button>
                    <input type="submit" class="btn btn-info" value="确定"></>
                </form>
                </div>
            </div>
        </div>
    </div>


    <!-- data-backdrop="false"去除遮罩层  -->
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

<script type="text/javascript" src="../static/js/question_base.js"></script>
</body>

</html>