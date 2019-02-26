<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ncqa答题模块</title>
    <meta name="viewport" content="width=device-width,initial-scale=1"></meta>
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css"></link>
    <script type="text/javascript" src="../static/jQuery/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../static/bootstrap/js/bootstrap.js"></script>
    <link rel="stylesheet" href="../static/css/question.css"/>

</head>
<body>
<div class="container">
    <span>
    <h1 align="center" class="h1">欢迎进入题库管理系统</h1>
    </span>

    <div class="hh">
        <span class="label label-warning zz"><#if user.username??>${user.username}</#if></span>
        <a class="btn btn-info btn-sm" href="http://139.199.170.177:4397/logout">退出登录</a>
    </div>
    <br>
    <br>


    <table class="table table-bordered table-striped table-hover ">
        <tr class="warning">
            <td>编号</td>
            <td>问题描述</td>
            <td>选项类型</td>
            <td>选项A</td>
            <td>选项B</td>
            <td>选项C</td>
            <td>选项D</td>
            <td>正确答案</td>
            <td>可操作列表</td>
        </tr>
        <#if result.result.questionList??>
        <#list result.result.questionList as question>
            <tr class="info">
                <td>${question.id}</td>
                <td>${question.content}</td>
                <#if question.select_type==0>
                    <td>单选</td>
                <#else>
                    <td>多选</td>
                </#if>
                <#list question.answerList as answer>
                    <td>${answer.answer_content}</td>
                </#list>
                <td>
                    ${question.rightAnswer.right_answer_index}
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
                        <a class="btn btn-success " href="http://139.199.170.177:4397/question/pageQuery">首页</a>
                        <a class="btn btn-success " href="http://139.199.170.177:4397/question/pageQuery?pageNo=${result.basePage.pageNo-1}&rowSrt=${result.basePage.rowSrt-result.basePage.pageSize}">上一页</a>
                    </#if>
                    <#if (result.basePage.pageNo<result.basePage.pages)>
                        <a class="btn btn-success" href="http://139.199.170.177:4397/question/pageQuery?pageNo=${result.basePage.pageNo+1}&rowSrt=${result.basePage.rowSrt+result.basePage.pageSize}">下一页</a>
                        <a class="btn btn-success" href="http://139.199.170.177:4397/question/pageQuery?pageNo=${result.basePage.pages}">尾页</a>
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