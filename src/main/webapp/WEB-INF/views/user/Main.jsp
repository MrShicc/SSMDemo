
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../Common.jsp" %>
<html>
<head>
    <title>主页</title>
    <script src="<%=basePath%>/static/lib/jquery-3.5.1.min.js"></script>
    <style>
        .nav{
            padding-top: 10px;
            float: right;
            height: 50px;
            width: 765px;
        }
        .queryDiv{
            float: right;
            height: 50px;
            margin-right: 250px;
        }
        .userDiv{
            background-color: #408763;
        }
        #studentTable{
            width: 800px;
        }
        .main{
            float: right;
        }

        #AddUser{
            display: none;
            float: left;
        }
        #upDateForm{
            display: none;
            float: right;
        }
    </style>
</head>
<body>
<div class="nav">
    <span>欢迎---<%=session.getAttribute("userName")%>---</span>
    <span>  登录状态---<%=session.getAttribute("state")%>---</span>
    <input type="button" onclick="out()" value="退出" />
</div>
<div class="queryDiv">
    <form>
        <label>账号:</label>
        <input id="queryId" name="userId" type="text" placeholder="输入需要查询的用户账号" />
        <label>电话号码:</label>
        <input id="queryNumb" name="numb" type="text" placeholder="输入需要查询的电话号码" />
        <input type="button" onclick="toQuery()" value="查询" />
    </form>
</div>

<div class="main">
    <table id="studentTable">

    </table>
</div>

<div id="AddUser">
    <form>
        <h1>新增</h1>
        <label>账号:</label>
        <input id="userId" name="userId" type="text" placeholder="请输入用户名" /><br/>
        <label>密码:</label>
        <input id="userPwd" name="userPwd" type="password" placeholder="请输入密码" /><br/>
        <label>姓名</label>
        <input id="userName" name="userName" type="text" placeholder="请输入姓名" /><br/>
        <label>电话号码</label>
        <input id="numb" name="numb" type="text" placeholder="请输入电话号码" /><br/>
        <input type="button" onclick="doAdd()" value="登录" /><br/>
        <span style="color:red" id="msg"></span>
    </form>
</div>

<div id="upDateForm">
    <form >
        <h1>编辑用户信息</h1>
        <label>用户账号</label>
        <span id="idMsg"></span>
        <label> 用户账号无法修改</label>
        <br/>
        <label>用户姓名</label>
        <span id="nameMsg"></span>
        <label>修改后为</label>
        <input id="upName" name="userName" type="text" placeholder="请输入姓名" /><br/>
        <label>用户密码</label>
        <span id="pwdMsg"></span>
        <label>修改后为</label>
        <input id="upPwd" name="userPwd" type="password" placeholder="请输入密码" /><br/>
        <label>用户电话号码</label>
        <span id="numbMsg"></span>
        <label>修改后为</label>
        <input id="upNumb" name="numb" type="text" placeholder="请输入电话号码" /><br/>
        <button onclick="doUp();return false">完成</button>
        <br/><span style="color:red" id="upMsg"></span>
    </form>
</div>



</body>
<script>

    $(function(){

        //初始化查询表格数据
        initTable();

    });

    function out(){
        $.ajax({
            url:"<%=basePath %>/User/doOut"+"/?outState=0",
            type:"GET",
            async:false,
            success:function(data){
                if(data.code=="200"){
                    alert("正在退出....")
                    location.href = "<%=basePath %>/User/toLogin";
                }
                else {
                    alert("发生未知的错误!")
                }

            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //初始化表格数据
    function initTable(){
        $("#studentTable").html("");
        var htmls = "<tr>"+
            "<th>账号</th>"+
            "<th>姓名</th>"+
            "<th>密码</th>"+
            "<th>电话号码</th>"+
            "<th>操作</th>" +
            "<th><button  onclick='doAdd()'>"+"新增"+"</ button></th>"
            "</tr>";

        $.ajax({
            url:"<%=basePath %>/User/initTab",
            type:"GET",
            async:false,

            success:function(data){
                if(data.code=="200"){
                    for(var i in data.data.rows){
                        htmls += "<tr class='tabIn'>"+
                            "<td >"+data.data.rows[i].userId+"</td>"+
                            "<td>"+data.data.rows[i].userName+"</td>"+
                            "<td>"+data.data.rows[i].userPwd+"</td>"+
                            "<td>"+data.data.rows[i].numb+"</td>"+
                            "<td>"+
                            "<button  onclick='toUp(\""+data.data.rows[i].userId+"\")'>"+"编辑"+"</ button>"
                            +"  "+
                            "<button  onclick='doDelete(\""+data.data.rows[i].userId+"\")'>"+"删除"+"</ button>"
                        "</td>"+
                        "</tr>";
                    }
                    htmls += "<tr>" +
                        " <th>页码:"+data.data.page+"</th>"+
                        " <th>记录数:"+data.data.size+"</th>"+
                        " <th>总计记录数:"+data.data.total+"</th>"+
                        " <th>"+'<input type="button" onclick="NextPage(0)" value="下一页" />'+"</th>"+
                        " <th>"+'<input type="button" onclick="ReturnPage(0)" value="上一页" />'+"</th>"+
                        "</tr>";
                    $("#studentTable").html(htmls);

                }else{
                    alert(data.msg);
                }
            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //下一页
    function NextPage(i){
        $.ajax({
            url:"<%=basePath %>/User/NextPage"+"/?nub=1"+i,
            type:"GET",
            async:false,
            success:function(data){
                if(data.code=="200"){
                    alert("正在加载....")
                    initTable()
                }
                if (data.code=="201"){
                    alert("正在加载....")
                    toQuery()
                }
            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //上一页
    function ReturnPage(i){
        $.ajax({
            url:"<%=basePath %>/User/ReturnPage"+"/?nub=-1"+i,
            type:"GET",
            async:false,
            success:function(data){
                if(data.code=="200"){
                    alert("正在加载....")
                    initTable()
                }
                if (data.code=="201"){
                    alert("正在加载....")
                    toQuery()
                }
            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //条件查询表格操作
    function toQuery(){
        // 复杂json参数格式时，要使用json字符串方式提交
        var param = {
            userId:$("#queryId").val(),
            numb:$("#queryNumb").val(),
        };

        $("#studentTable").html("");
        var htmls = "<tr>"+
            "<th>账号</th>"+
            "<th>姓名</th>"+
            "<th>密码</th>"+
            "<th>电话号码</th>"+
            "<th>操作</th>" +
            "<th><button  onclick='doAdd()'>"+"新增"+"</ button></th>"
        "</tr>";

        $.ajax({
            url:"<%=basePath %>/User/queryTab",
            type:"POST",
            async:false,
            data:JSON.stringify(param),// 发送的是一个json字符串
            contentType:"application/json;charset=UTF-8",// 设置请求头描述数据内容是一个json
            success:function(data){
                if(data.code=="200"){
                    console.log(data)
                    for(var i in data.data.rows){
                        htmls += "<tr class='tabIn'>"+
                            "<td >"+data.data.rows[i].userId+"</td>"+
                            "<td>"+data.data.rows[i].userName+"</td>"+
                            "<td>"+data.data.rows[i].userPwd+"</td>"+
                            "<td>"+data.data.rows[i].numb+"</td>"+
                            "<td>"+
                            "<button  onclick='toUp(\""+data.data.rows[i].userId+"\")'>"+"编辑"+"</ button>"
                            +"  "+
                            "<button  onclick='doDelete(\""+data.data.rows[i].userId+"\")'>"+"删除"+"</ button>"
                        "</td>"+
                        "</tr>";
                    }
                    htmls += "<tr>" +
                        " <th>页码:"+data.data.page+"</th>"+
                        " <th>记录数:"+data.data.size+"</th>"+
                        " <th>总计记录数:"+data.data.total+"</th>"+
                        " <th>"+'<input type="button" onclick="NextPage(2)" value="下一页" />'+"</th>"+
                        " <th>"+'<input type="button" onclick="ReturnPage(2)" value="上一页" />'+"</th>"+
                        "</tr>";
                    $("#studentTable").html(htmls);

                }else{
                    alert(data.msg);
                }
            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //新增操作
    function doAdd(){
        $("#AddUser").show();
        //判断是否为空
        if($("#userId").val()==null||$("#userId").val()==""){
            $("#msg").html("用户ID必填");
            return;
        }

        if($("#userName").val()==null||$("#userName").val()==""){
            $("#msg").html("用户姓名必填");
            return;
        }

        if($("#userPwd").val()==null||$("#userPwd").val()==""){
            $("#msg").html("用户密码必填");
            return;
        }

        if($("#numb").val()==null||$("#numb").val()==""){
            $("#msg").html("用户电话必填");
            return;
        }

        // 复杂json参数格式时，要使用json字符串方式提交
        var param = {
            userId:$("#userId").val(),
            userPwd:$("#userPwd").val(),
            userName:$("#userName").val(),
            numb:$("#numb").val(),
        };


        $.ajax({
            url:"<%=basePath %>/User/doAdd",
            dataType:"json",//预计后端返回json数据
            type:"POST",
            async:true,
            data:JSON.stringify(param),// 发送的是一个json字符串
            contentType:"application/json;charset=UTF-8",// 设置请求头描述数据内容是一个json
            success:function(data){
                console.dir(data);
                //data 后端响应的数据报文
                if(data.code=="200"){
                    $("#msg").html('');
                    alert("新增成功！")
                    initTable();
                }else if(data.code=="100"){
                    $("#msg").html(data.msg);
                }

            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //打开新增并传递参数
    function toUp(userId){
        $("#upDateForm").show();
        $.ajax({
            url:"<%=basePath %>/User/toUp",
            type:"GET",
            async:false,
            data:{"userId":userId},
            success:function(data){
                alert("正在加载...")
                console.log(data);
                $("#idMsg").html(data.data.userId);
                $("#nameMsg").html(data.data.userName);
                $("#pwdMsg").html(data.data.userPwd);
                $("#numbMsg").html(data.data.numb);
            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //编辑操作
    function doUp(){
        $("#upDateForm").show();
        //判断是否为空

        if($("#upName").val()==null||$("#upName").val()==""){
            $("#upMsg").html("用户姓名必填");
            return;
        }

        if($("#upPwd").val()==null||$("#upPwd").val()==""){
            $("#upMsg").html("用户密码必填");
            return;
        }

        if($("#upNumb").val()==null||$("#upNumb").val()==""){
            $("#upMsg").html("用户电话必填");
            return;
        }

        // 复杂json参数格式时，要使用json字符串方式提交
        var param = {
            userPwd:$("#upPwd").val(),
            userName:$("#upName").val(),
            numb:$("#upNumb").val(),
        };


        $.ajax({
            url:"<%=basePath %>/User/doUp",
            dataType:"json",//预计后端返回json数据
            type:"POST",
            async:true,
            data:JSON.stringify(param),// 发送的是一个json字符串
            contentType:"application/json;charset=UTF-8",// 设置请求头描述数据内容是一个json
            success:function(data){
                console.log(data);
                //data 后端响应的数据报文
                if(data.code=="200"){
                    $("#msg").html('');
                    alert("修改成功！")
                    initTable();
                }else if(data.code=="100"){
                    $("#msg").html(data.msg);
                    alert("错误！")
                }

            },
            error:function(){
                alert("请求错误");
            }
        });
    }

    //进行删除
    function doDelete(userId){
        $.ajax({
            url:"<%=basePath %>/User/doDelete",
            type:"GET",
            async:false,
            data:{"userId":userId},
            success:function(data){
                alert("正在删除...")
                console.log(data);
                //data 后端响应的数据报文
                if(data.code=="200"){
                    $("#msg").html('');
                    alert("删除！")
                    initTable();
                }else if(data.code=="100"){
                    $("#msg").html(data.msg);
                    alert("错误！")
                }
            },
            error:function(){
                alert("请求错误");
            }
        });
    }

</script>
</html>
