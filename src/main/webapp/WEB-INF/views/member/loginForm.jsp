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
</head>
<body>
	<H3>회원 로그인 창</H3>
	<form class="form-signin" action="${contextPath}/member/login.do" method="post">
        <label for="inputEmail" class="sr-only">아이디</label>
        <input name="member_id" type="text" size="20" class="form-control" placeholder="account" required autofocus/>
        <label for="inputPassword" class="sr-only">비밀번호</label>
        <input name="member_pw" type="password" size="20" class="form-control" placeholder="Password" required/>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <input class="btn btn-lg btn-primary"  type="submit" value="로그인">
        <a href="${contextPath}/member/memberForm.do">회원가입</a> 
      </form>
</body>
</html>