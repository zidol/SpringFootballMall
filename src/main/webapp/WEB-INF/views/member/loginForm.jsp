<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	 isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<c:if test='${not empty message }'>
<script>
window.onload = function() {
  result();
}

function result() {
	alert("아이디나  비밀번호가 틀립니다. 다시 로그인해주세요");
}
</script>

</c:if>
 <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
     </style>
     <!-- Custom styles for this template -->
    <link href="${contextPath}/resources/bootstrap-3.3.2-dist/css/floating-labels.css" rel="stylesheet">
</head>
<body>
	
	<form class="form-signin" action="${contextPath}/member/login.do" method="post">
	<H3>회원 로그인 창</H3>
	<div class="form-label-group">
        <input name="member_id" id="inputid"type="text" size="20" class="form-control" placeholder="account" required autofocus/>
        <label for="inputid" >아이디</label>
    </div>
    <div class="form-label-group">
	    <input name="member_pw" id="inputpassword" type="password" size="20" class="form-control" placeholder="Password" required/>
	     <label for="inputpassword">비밀번호</label>
    </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <input class="btn btn-lg btn-primary"  type="submit" value="로그인">
        <br>
        <br>
        <a href="${contextPath}/member/memberForm.do">회원가입</a> &nbsp;&nbsp;&nbsp;
        <a href="${contextPath}/main/main.do">메인</a> 
      </form>
      
</body>
</html>