<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<nav>
<ul>
<c:choose>
<c:when test="${side_menu=='admin_mode' }">
   <li>
		<H3>주요기능</H3>
		<ul>
			<li><a href="${contextPath}/admin/goods/adminGoodsMain.do">상품관리</a></li>
			<li><a href="${contextPath}/admin/order/adminOrderMain.do">주문관리</a></li>
			<li><a href="${contextPath}/admin/member/adminMemberMain.do">회원관리</a></li>
			<li><a href="#">배송관리</a></li>
			<li><a href="#">게시판관리</a></li>
		</ul>
	</li>
</c:when>
<c:when test="${side_menu=='my_page' }">
	<li>
		<h3>주문내역</h3>
		<ul>
			<li><a href="${contextPath}/mypage/listMyOrderHistory.do">주문내역/배송 조회</a></li>
		</ul>
	</li>
	<li>
		<h3>정보내역</h3>
		<ul>
			<li><a href="${contextPath}/mypage/myDetailInfo.do">회원정보관리</a></li>
		</ul>
	</li>
</c:when>
<c:otherwise>
	<li>
		<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 축구용품</h3>
		<ul>
			<li><a href="${contextPath}/goods/goodsList.do?goods_sort=footballshoes">축구화</a></li>
			<li><a href="${contextPath}/goods/goodsList.do?goods_sort=uniform_top">유니폼 상의</a></li>
			<li><a href="${contextPath}/goods/goodsList.do?goods_sort=uniform_pants">유니폼 하의</a></li>
			<li><a href="${contextPath}/goods/goodsList.do?goods_sort=sweater">크루탑</a></li>
			<li><a href="${contextPath}/goods/goodsList.do?goods_sort=train">트레이닝 복</a></li>
			<li><a href="${contextPath}/goods/goodsList.do?goods_sort=accessorie">악세사리</a></li>
		</ul>
	</li>
<!-- 	<li>
		<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;음반</h3>
		<ul>
			<li><a href="#">가요</a></li>
			<li><a href="#">록</a></li>
			<li><a href="#">클래식</a></li>
			<li><a href="#">뉴에이지</a></li>
			<li><a href="#">재즈</a></li>
			<li><a href="#">기타</a></li>
		</ul>
	</li> -->
 </c:otherwise>
</c:choose>	
</ul>
</nav>
<div class="clear"></div>
<div id="banner">
	<a href="#"><img width="190"  src="${contextPath}/resources/image/side_bn2.jpg"> </a>
</div>
<nav>
<ul>
	<li><a href="#">축구용품계의 신 풋볼몰!!</a></li>
</ul>
</nav>
<div class="clear"></div>
<div id="banner">
	<a href="#"><img width="190"  src="${contextPath}/resources/image/side_bn3.jpg"> </a>
</div>
</html>