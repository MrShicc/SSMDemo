<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../Common.jsp" %>
<html>
<head>
    <title>登录页面</title>
    <script src="<%=basePath%>/static/lib/jquery-3.5.1.min.js"></script>
    <style>
        .Login{
            float: right;
            height: 300px;
            border: 5px solid #408763;
            padding: 30px;
        }
    </style>
</head>
<body>
<div class="Login">
    <form>
        <h1>欢迎</h1>
        <label>账号:</label>
        <input id="userId" name="userId" type="text" placeholder="请输入用户名" /><br/>
        <label>密码:</label>
        <input id="userPwd" name="userPwd" type="password" placeholder="请输入密码" /><br/>
        <label>验证码</label>
        <input id="reqCodeText" name="reqCodeText" type="text" placeholder="请输入验证码" />
        <img id="reqCode" src=""  onclick="initReqCode()" style="cursor: pointer;" /><br/>
        <input type="button" onclick="doLogin()" value="登录" /><br/>
        <span style="color:red" id="msg"></span>
    </form>
</div>
</body>
<script>
    $(function(){

        initReqCode();


    });

    //请求验证码
    function initReqCode(){
        // var param = {
        //     reqCode:$("#reqCode").val(),}

        $.ajax({
            url:"<%=basePath %>/User/toReqCode",
            type:"GET",
            async:false,
            // data:JSON.stringify(param),
            success:function(data){
                if (data.code=="200"){
                    $("#reqCode").attr("src",data.data);
                }

            },
            error:function(){
                alert("请求错误");
            }
        });
    }


    function doLogin(){
        // 复杂json参数格式时，要使用json字符串方式提交
        var param = {
            userId:$("#userId").val(),
            userPwd:$("#userPwd").val(),
            reqCodeText:$("#reqCodeText").val(),
            // isRemember:$("#isRemember").val(),
            // reqCode:$("#reqCode").val()
        };

        $.ajax({
            url:"<%=basePath %>/User/doLogin",
            dataType:"json",//预计后端返回json数据
            type:"POST",
            async:true,
            data:JSON.stringify(param),// 发送的是一个json字符串
            contentType:"application/json;charset=UTF-8",// 设置请求头描述数据内容是一个json
            success:function(data){
                //data 后端响应的数据报文
                if(data.code=="200"){
                    $("#msg").html('');
                    alert("登录成功！")
                    location.href = "<%=basePath %>/User/toMain";
                }else if(data.code=="500"){
                    $("#msg").html(data.msg);
                    // initReqCode();
                }
            },
            error:function(){
                alert("请求错误");
            }
        });
    }
</script>
</html>
