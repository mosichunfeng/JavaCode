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
		<h1 align="center">欢迎进入学生管理系统</h1>
	</span>

    <div class="hh">
        <span>袁睿(管理员)</span>
        <a href="#">退出登录</a>
    </div>
    <br>
    <br>

    <form action="" class="form-inline">
        <div class="form-group">
            <label for="student_id" class="sr-only">学号：</label>
            <input type="text" class="form-control" id="student_id" placeholder="学号">
        </div>
        <div class="form-group">
            <label for="student_name" class="sr-only">姓名：</label>
            <input type="text" class="form-control" id="student_name" placeholder="姓名">
        </div>
        <div class="form-group">
            <label for="student_id" class="sr-only">班级：</label>
            <select name="class_name"  class="form-control">
                <option value="all">全部</option>
                <option value="网络工程15201">网络工程15201</option>
                <option value="网络工程15202">网络工程15202</option>
                <option value="网络工程15203">网络工程15203</option>
                <option value="网络工程15204">网络工程15204</option>
                <option value="物联网15201">物联网15201</option>
                <option value="物联网15202">物联网15202</option>
                <option value="物联网15203">物联网15203</option>
                <option value="物联网15204">物联网15204</option>
            </select>
        </div>
        <div class="form-group">
            <label for="student_tel" class="sr-only">手机号：</label>
            <input type="text" class="form-control" id="student_tel" placeholder="姓名">
        </div>

        <div class="form-group">
            <label for="student_tel" class="sr-only">是否实习</label>
            <select name="class_name" class="form-control">
                <option value="2">全部</option>
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </div>
        <div class="form-group">
            <label for="student_tel" class="sr-only">查询：</label>
            <input type="submit" class="btn btn-default"/>
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
        <#if content??>
            <#list content as student>
                <tr class="success">
                    <td>${student.student_id}</td>
                    <td>${student.student_name}</td>
                    <td>${student.student_class}</td>
                    <td>${student.student_gender}</td>
                    <#if student.student_tel??>
                            <td>${student.student_tel}</td>
                    <#else>
                             <td>无</td>
                    </#if>
                    <#if student.work_detail==0>
                        <td>未实习</td>
                    <#else>
                        <td>已实习</td>
                    </#if>


                    <td>
                        <button type="button" class="btn btn-info">编辑</button>
                        <button type="button" class="btn btn-success">删除</button>
                        <button type="button" class="btn btn-warning">查看详情</button>
                    </td>
                </tr>
            </#list>
        </#if>
    </table>
    <div class="xi">
        <ul class="pagination pagination-lg">
            <li><a href="#">&laquo;</a></li>
            <li><a href="#">1</a></li>
            <li><a href="#">2</a></li>
            <li><a href="#">3</a></li>
            <li><a href="#">4</a></li>
            <li><a href="#">5</a></li>
            <li><a href="#">6</a></li>
            <li><a href="#">7</a></li>
            <li><a href="#">8</a></li>
            <li><a href="#">9</a></li>
            <li><a href="#">&raquo;</a></li>
        </ul><br>
    </div>
</div>
</body>
</html>