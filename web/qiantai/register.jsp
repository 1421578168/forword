<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
<title></title>
	<link href="css/style.css" rel="stylesheet" type="text/css" />
	<script src="jquery-1.3.2.min.js"></script>
    <script>
        $(function () {
            $("#username").blur(function () {
                var name = this.text;
                alert(name);
                $.get("",function (data) {
                    if (data == "true") {
                        $("#username_notice").text("用户名重复,请重新输入!");
                    }
                })
            })
        });
    </script>
	<script type="text/javascript">
		function checkForm(){
			if(document.getElementById("agreement").checked==true){
				return true;
			}
			return false;
		}

		function checkUserNameValue(val){
			if(val==""){
				document.getElementById("username_notice").innerHTML="用户名不能为空";
			}else{
				document.getElementById("username_notice").innerHTML="<img src='images/icon_gou.gif'/>";
			}
		}

		function checkEmailValue(val){
			if(val==""){
				document.getElementById("email_notice").innerHTML="用户名不能为空";
			}else{
				document.getElementById("email_notice").innerHTML="<img src='images/icon_gou.gif'/>";
			}
		}
		
		function checkPass(val) {
			var password = document.getElementById("password").value;
			if (val != password){
				document.getElementById("conform_password_notice").innerHTML = "两次输入密码不一致!";
			} 
		}
	</script>
</head>
<body>
<!--LOGO欢迎信息和登陆注册功能-->
<div class="block clearfix">
	<div class="f_l">
		<a href="index.php.htm" name="top"><img src="images/logo.gif"/></a>
	</div>
 	<div class="f_r log">
		<ul>
	    	<li class="userInfo">
  				<font id="ECS_MEMBERZONE">
					<div id="append_parent"></div>
 					欢迎光临本店&nbsp;&nbsp;&nbsp;&nbsp;
 					<a href="login.jsp"><img src="images/bnt_log.gif"/></a>
 					<a href="register.jsp"><img src="images/bnt_reg.gif"/></a>
 				</font>
   			</li>
      	</ul>
 	</div>
</div>
<div  class="blank"></div>

<!--顶层功能导航栏-->
<div id="mainNav" class="clearfix">
	<a href="IndexServlet" class="cur">首页<span></span></a>
	<a href="###">买家必看<span></span></a>
	<a href="###">优惠活动<span></span></a>
	<a href="flow.jsp">查看购物车<span></span></a>
	<a href="###">报价单<span></span></a>
	<a href="###">留言板<span></span></a>
	<a href="###">团购商品<span></span></a>
</div>
<div class="block">
	<div class="box">
		<div class="helpTitBg clearfix"></div>
	</div>
</div>
<div class="blank"></div>

<div class="usBox">
	<div class="usBox_2 clearfix">
		<div class="regtitle"></div>
		<form action="userSvl?method=register" method="post" name="formUser" onsubmit="checkForm()" >
			<table width="100%"  border="0" align="left" cellpadding="5" cellspacing="3">
				<tr>
					<td width="11%" align="right">用户名</td>
					<td width="89%">
						<input name="username" type="text" size="25" id="username" onblur="checkUserNameValue(this.value)" class="inputBg"/>
						<span id="username_notice" style="color:#FF0000"> *</span>
						
					</td>
				</tr>
				<tr>
					<td align="right">Email</td>
					<td>
						<input name="email" type="text" size="25" id="email" onblur="checkEmailValue(this.value)"  class="inputBg"/>
						<span id="email_notice" style="color:#FF0000"> *</span>
					</td>
				</tr>
				<tr>
					<td align="right">密码</td>
					<td>
						<input name="password" type="password" id="password" onblur="" class="inputBg"  />
						<span style="color:#FF0000" id="password_notice"> *</span>
					</td>
				</tr>
				<tr>
					<td align="right">确认密码</td>
					<td>
						<input name="confirm_password" type="password" id="conform_password" onblur="checkPass(this.value)"  class="inputBg"/>
						<span style="color:#FF0000" id="conform_password_notice"> *</span>
					</td>
				</tr>
				<tr>
					<td align="right">QQ</td>
					<td>
						<input name="qqcode" type="text" size="25" class="inputBg"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><label>
						<input id="agreement" name="agreement" type="checkbox" value="1" checked="checked" />
						我已看过并接受《<a href="##" style="color:blue" target="_blank">用户协议</a>》</label>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left">
						<input name="Submit" type="submit" value="" class="us_Submit_reg"/>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="actionSub">
					<a href="login.jsp" >我已有账号，我要登录</a><br />
					<a href="###" >您忘记密码了吗？</a>
					</td>
				</tr>
			</table>
			${sessionScope.info}
		</form>
	</div>
</div>

<div class="blank5"></div>

<!--友情连接区-->
<div id="bottomNav" class="box">
	<div class="box_1">
		<div class="links clearfix">
			[<a href="###" target="_blank" title="友情连接1">友情连接1</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接2</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接3</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接4</a>]
			[<a href="###" target="_blank" title="友情连接1">友情连接5</a>]
		</div>
	</div>
</div>
<div class="blank"></div>

<!--底层导航栏-->
<div id="bottomNav" class="box">
	<div class="box_1">
		<div class="bNavList clearfix">
   			<div class="f_l"></div>
   			<div class="f_r">
				<a href="#top"><img src="images/bnt_top.gif" /></a> 
				<a href="###"><img src="images/bnt_home.gif"/></a>
   			</div>
  		</div>
 	</div>
</div>
<div class="blank"></div>

<!--版权信息栏-->
<div class="text"align="center">
 &copy; 2010-2015 网上商城 版权所有，并保留所有权利。<br />
          E-mail: 123456@qq.com<br />
    ICP备案证书号:<a href="###" target="_blank">粤ICP备1234568</a><br />
          <div align="center"  id="rss"><a href="###"><img src="images/xml_rss2.gif"alt="rss" /></a></div>
</div>
</body>
</html>
