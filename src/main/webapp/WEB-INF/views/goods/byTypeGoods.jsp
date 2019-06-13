<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  

<div id="ad_main_banner">
	<ul class="bjqs">	 	
	  <li><img width="775" height="300" src="${contextPath}/resources/image/main_plan_bn01.jpg"></li>
		<li><img width="775" height="150" src="${contextPath}/resources/image/main_bn.png">></li>
		<li><img width="775" height="300" src="https://www.soccermall.kr/images/main2013/b_main2014_plan_520.jpg"></li> 
	</ul>
</div>
<div class="main_book">
   <c:set  var="goods_count" value="0" />
   <c:choose>
   	<c:when test="${goods_sort == 'footballshoes'}">
		<h1>축구화</h1>
   	</c:when>
   	<c:when test="${goods_sort == 'uniform_top'}">
		<h1>유니폼 상의</h1>
   	</c:when>
   	<c:when test="${goods_sort == 'uniform_pants'}">
		<h1>유니폼 하의</h1>
   	</c:when>
   	<c:when test="${goods_sort == 'sweater'}">
		<h1>크루탑</h1>
   	</c:when>
   	<c:when test="${goods_sort == 'sox'}">
		<h1>스타킹</h1>
   	</c:when>
   	<c:when test="${goods_sort == 'train'}">
		<h1>트레이닝복</h1>
   	</c:when>
   	<c:when test="${goods_sort == 'accessorie'}">
		<h1>악세사리</h1>
   	</c:when>
   </c:choose>
	<c:forEach var="item" items="${goodsList }">
	   <c:set  var="goods_count" value="${goods_count+1 }" />
		<div class="book">
			<a href="${contextPath}/goods/goodsDetail.do?goods_id=${item.goods_id }">
			<img class="link"  src="${contextPath}/resources/image/1px.gif"> 
			</a> 
				<img width="121" height="154" 
				     src="${contextPath}/thumbnails.do?goods_id=${item.goods_id}&fileName=${item.goods_fileName}">

			<div class="title">${item.goods_title }</div>
			<div class="price">
		  	   <fmt:formatNumber  value="${item.goods_price}" type="number" var="goods_price" />
		          ${goods_price}원
			</div>
		</div>
	   <c:if test="${goods_count==15   }">
         <div class="book">
           <font size=20> <a href="#">more</a></font>
         </div>
     </c:if>
  </c:forEach>
</div>
<div class="clear"></div>
<div id="ad_sub_banner">
	<img width="770" height="300" src="${contextPath}/resources/image/190514_fb_slp_p1_bg.jpg">
</div>


   
   