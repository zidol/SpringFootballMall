<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" 	isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="goods"  value="${goodsMap.goodsVO}"  />
<c:set var="imageList"  value="${goodsMap.imageList }"  />
<c:set var="goodsSize"  value="${goodsSizeMap.goodsSize }"  />
 <%
     //치환 변수 선언합니다.
      pageContext.setAttribute("crcn", "\r\n"); //개행문자
      pageContext.setAttribute("br", "<br/>"); //br 태그
%>  
<html>
<head>
<style>
#layer {
	z-index: 2;
	position: absolute;
	top: 0px;
	left: 0px;
	width: 100%;
}

#popup {
	z-index: 3;
	position: fixed;
	text-align: center;
	left: 50%;
	top: 45%;
	width: 300px;
	height: 200px;
	background-color: #dfdfdf ;
	border: 3px solid #000000;
}

#close {
	z-index: 4;
	float: right;
}
</style>
<script type="text/javascript">

	function add_cart(goods_id) {
		var order_goods_qty = document.getElementById("order_goods_qty").value;
		var order_goods_size = document.getElementById("order_goods_size").value;
		console.log(order_goods_qty);
		console.log(order_goods_size);
		$.ajax({
			type : "post",
			async : false, //false인 경우 동기식으로 처리한다.
			url : "${contextPath}/cart/addGoodsInCart.do",
			data : {
				goods_id:goods_id,
				order_goods_qty: order_goods_qty,
				order_goods_size:order_goods_size
				
			},
			success : function(data, textStatus) {
				if(data.trim()=='add_success'){
					imagePopup('open', '.layer01');	
				}else if(data.trim()=='already_existed'){
					alert("이미 카트에 등록된 상품입니다.");	
				}
				
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다."+data);
			},
			complete : function(data, textStatus) {
			}
		}); //end ajax	
	}

	function imagePopup(type) {
	zx5	if (type == 'open') {
			// 팝업창을 연다.
			jQuery('#layer').attr('style', 'visibility:visible');

			// 페이지를 가리기위한 레이어 영역의 높이를 페이지 전체의 높이와 같게 한다.
			jQuery('#layer').height(jQuery(document).height());
		}

		else if (type == 'close') {

			// 팝업창을 닫는다.
			jQuery('#layer').attr('style', 'visibility:hidden');
		}
	}
	
function fn_order_each_goods(goods_id,goods_title,goods_sales_price,fileName){
	var _isLogOn=document.getElementById("isLogOn");
	var isLogOn=_isLogOn.value;
	
	 if(isLogOn=="false" || isLogOn=='' ){
		alert("로그인 후 주문이 가능합니다!!!");
	}
	var total_price,final_total_price;
	var order_goods_qty=document.getElementById("order_goods_qty");
	var order_goods_size=document.getElementById("order_goods_size");
	
	var formObj=document.createElement("form");
	var i_goods_id = document.createElement("input"); 
    var i_goods_title = document.createElement("input");
    var i_goods_sales_price=document.createElement("input");
    var i_fileName=document.createElement("input");
    var i_order_goods_qty=document.createElement("input");
    var i_order_goods_size=document.createElement("input");
    
    i_goods_id.name="goods_id";
    i_goods_title.name="goods_title";
    i_goods_sales_price.name="goods_sales_price";
    i_fileName.name="goods_fileName";
    i_order_goods_qty.name="order_goods_qty";
    i_order_goods_size.name="order_goods_size";
    
    i_goods_id.value=goods_id;
    i_order_goods_qty.value=order_goods_qty.value;
    i_order_goods_size.value=order_goods_size.value;
    i_goods_title.value=goods_title;
    i_goods_sales_price.value=goods_sales_price;
    i_fileName.value=fileName;
    
    formObj.appendChild(i_goods_id);
    formObj.appendChild(i_goods_title);
    formObj.appendChild(i_goods_sales_price);
    formObj.appendChild(i_fileName);
    formObj.appendChild(i_order_goods_qty);
    formObj.appendChild(i_order_goods_size);

    document.body.appendChild(formObj); 
    formObj.method="post";
    formObj.action="${contextPath}/order/orderEachGoods.do";
    formObj.submit();
	}	
</script>
</head>
<body>
	<hgroup>
		<h1>축구용품</h1>
		<h3>${goods.goods_title }</h3>
		<h4>${goods.goods_manufacturer} &nbsp;</h4>
	</hgroup>
	<div id="goods_image">
		<figure>
			<img class="img-thumbnail" alt="HTML5 &amp; CSS3"
				src="${contextPath}/thumbnails.do?goods_id=${goods.goods_id}&fileName=${goods.goods_fileName}">
		</figure>
	</div>
	<div id="detail_table">
		<table>
			<tbody>
				<tr>
					<td class="fixed">정가</td>
					<td class="active"><span >
					   <fmt:formatNumber  value="${goods.goods_price}" type="number" var="goods_price" />
				         ${goods_price}원
					</span></td>
				</tr>
				<tr class="dot_line">
					<td class="fixed">판매가</td>
					<td class="active"><span >
					   <fmt:formatNumber  value="${goods.goods_price*0.9}" type="number" var="discounted_price" />
				         ${discounted_price}원(10%할인)</span></td>
				</tr>
				<tr>
					<td class="fixed">포인트적립</td>
					<td class="active">${goods.goods_point}P(10%적립)</td>
				</tr>
				<tr class="dot_line">
					<td class="fixed">포인트 추가적립</td>
					<td class="fixed">만원이상 구매시 1,000P, 5만원이상 구매시 2,000P추가적립 편의점 배송 이용시 300P 추가적립</td>
				</tr>
				<tr>
					<td class="fixed">발행일</td>
					<td class="fixed">
					   <c:set var="pub_date" value="${goods.goods_production_date}" />
					   <c:set var="arr" value="${fn:split(pub_date,' ')}" />
					   <c:out value="${arr[0]}" />
					</td>
				</tr>
				<tr>
					<td class="fixed">배송료</td>
					<td class="fixed"><strong>무료</strong></td>
				</tr>
				<tr>
					<td class="fixed">배송안내</td>
					<td class="fixed"><strong>[당일배송]</strong> 당일배송 서비스 시작!<br> <strong>[휴일배송]</strong>
						휴일에도 배송받는 쇼핑몰</TD>
				</tr>
				<tr>
					<td class="fixed">도착예정일</td>
					<td class="fixed">지금 주문 시 내일 도착 예정</td>
				</tr>
				<tr>
					<td class="fixed">수량</td>
					<td class="fixed">
			      <select style="width: 60px;" id="order_goods_qty">
			      	<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
			     </select>
					 </td>
				</tr>
				<tr>
					<td class="fixed">사이즈</td>
					<td class="fixed">
					     <select style="width: 60px;" id="order_goods_size">
					      	<c:forEach items="${goodsSize }" var="list">
								<option value="${list.goods_size}">${list.goods_size}</option>						
							</c:forEach>
					     </select>
					 </td>
				</tr>
			</tbody>
		</table>
		<ul>
			<li><input type="button" class="btn btn-danger btn-lg" 
			onclick="javascript:fn_order_each_goods('${goods.goods_id }','${goods.goods_title }','${goods.goods_sales_price}','${goods.goods_fileName}');" 
			value="구매하기"></li>
			<li><input type="button" class="btn btn-warning btn-lg" 
			onclick="javascript:add_cart('${goods.goods_id }')" 
			value="장바구니"></li>
		</ul>
	</div>
	<div class="clear"></div>
	<!-- 내용 들어 가는 곳 -->
	<div id="container">
		<ul class="tabs">
			<li><a href="#tab1">상품 소개</a></li>
			<li><a href="#tab2">교환/반품/배송</a></li>
		</ul>
		<div class="tab_container">
			<div class="tab_content" id="tab1">
				<h4>상품 소개</h4>
				<p>${fn:replace(goods.goods_intro,crcn,br)}</p>
				<c:forEach var="image" items="${imageList }">
					<img 
						src="${contextPath}/download.do?goods_id=${goods.goods_id}&fileName=${image.fileName}">
				</c:forEach>
			</div>
			<div class="tab_content" id="tab2">
				<h4>교환/반품/배송</h4>
				<div class="detail_delivery">
					<ul>
						<img src="${contextPath}/resources/image/t_product_detail_04_1.gif" width="50" height="17" alt="배송">
						<li class="bullet_circle_gray">배송기간은 평균 <span class="BlSt">결제 완료 후 1~3일 정도 소요</span>되며, 제품에 따라 약간의 차이가 생길 수 있습니다.</li>
						<li class="bullet_circle_gray"><span class="BuSt">배송비는 건당  2,500원의 배송료가 부과됩니다.</span> <span class="ReSt">(제주도,도서 지방은 별도 요금 부과)</span></li>
						<li class="bullet_circle_gray">배송 업체는 <span class="BlSt">“CJ대한통운” 이며, 평일 오후 5시까지 발송 업무를 처리</span> 합니다.토요일,일요일,공휴일은 발송처리가 되지 않습니다.</li>
						<li class="delivery_line"><img src="${contextPath}/resources/image/t_product_detail_04_1_1.gif" width="133" height="17" alt="배송비 부담 책임"></li>
						<li class="bullet_circle_gray">제품하자에 의한 교환/반품 배송비는 쇼핑몰에서 전액 부담합니다.</li>
						<li class="bullet_circle_gray">제품하자가 아닌 교환/반품의 경우 배송비는 전액 고객님께서 부담하셔야 합니다.<br><strong>(교환시 왕복배송비: 6,000원 , 반품시 편도배송비 : 3,000원)</strong></li>
						<li class="delivery_line"><img src="${contextPath}/resources/image/t_product_detail_04_2.gif" width="87" height="17" alt="교환/반품"></li>
						<li class="delivery_gap"><img src="${contextPath}/resources/image/t_product_detail_04_05.gif" width="78" height="14" alt="교환/반품 방법"></li>
						<li class="bullet_circle_gray">교환/반품 요청서를 동봉하고 제품, 사은품 및 정품박스가 손상되지 않도록 잘 포장한뒤 반드시 CJ대한통운 택배접수(☎ 1588-1255, 편의점 택배 제외)후 택배기사 방문시 해당제품을 보내주시면 됩니다.</li>
						<li class="bullet_circle_gray"><strong>교환시 왕복배송비 6,000원 / 반품시 편도배송비 3,000원을 입금</strong>하시면 교환,반품이 진행됩니다.(제주도,도서 지방은 추가 요금 발생) </li>
						<li class="bullet_circle_gray">타택배 이용시 추가비용이 발생합니다. </li>
						<li class="bullet_circle_gray">편도 배송비를 초과하는 일부 대형제품의 경우 배송비가 추가됩니다.</li>
						<li class="delivery_gap"><img src="${contextPath}/resources/image/t_product_detail_04_.gif" width="141" height="14" alt="반품 및 교환이 불가능한 경우"></li>
						<li class="bullet_circle_gray">제품을 사용하거나 훼손된 흔적이 있는 경우</li>
						<li class="bullet_circle_gray">상품 수령일로부터 7일이 경과한 경우 </li>
						<li class="bullet_circle_gray">제품에 붙어있는 텍(tag)을 떼어 내거나 정품포장(박스, 비닐)이 훼손된 경우</li>
						<li class="bullet_circle_gray">마킹을 추가로 하신 제품의 경우 </li>
						<li class="bullet_circle_gray">주문 생산 제품 및 제품 상세설명에 교환/반품 불가능이라 명시된 제품의 경우</li>
						<li class="box"><img src="${contextPath}/resources/image/t_product_detail_04_4.jpg" width="870" height="551" alt="정품박스 훼손시 교환/반품불가"></li>
					</ul>
				</div>
				
			</div>
			<%-- <div class="tab_content" id="tab3">
				<h4>책목차</h4>
				<p>${fn:replace(goods.goods_contents_order,crcn,br)}</p> 
			</div>
			<div class="tab_content" id="tab4">
				<h4>출판사서평</h4>
				 <p>${fn:replace(goods.goods_publisher_comment ,crcn,br)}</p> 
			</div>
			<div class="tab_content" id="tab5">
				<h4>추천사</h4>
				<p>${fn:replace(goods.goods_recommendation,crcn,br) }</p>
			</div> --%>
			<div class="tab_content" id="tab6">
				<h4>리뷰</h4>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div id="layer" style="visibility: hidden">
		<!-- visibility:hidden 으로 설정하여 해당 div안의 모든것들을 가려둔다. -->
		<div id="popup">
			<!-- 팝업창 닫기 버튼 -->
			<a href="javascript:" onClick="javascript:imagePopup('close', '.layer01');"> <img
				src="${contextPath}/resources/image/close.png" id="close" />
			</a> <br /> <font size="12" id="contents">장바구니에 담았습니다.</font><br>
<form   action='${contextPath}/cart/myCartList.do'  >				
		<input  type="submit" value="장바구니 보기">
</form>			
</body>
</html>
<input type="hidden" name="isLogOn" id="isLogOn" value="${isLogOn}"/>