<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>

<form name="formLogin" id="formLogin" action="<%=request.getContextPath()%>/example.do" method="post">
用户名:<input name="userName" type="text" id="userName" />
密码:<input name="passWord" type="password" id="passWord" />
验证码:<input name="randCode" type="text" id="randCode" />
<br/>
<input type="submit" name="login" id="login"/>
</form>
</body>
</html>