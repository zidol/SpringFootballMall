package com.shoppingmall.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingmall.common.base.BaseController;
import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.member.vo.MemberVO;
import com.shoppingmall.order.service.OrderService;
import com.shoppingmall.order.vo.OrderVO;

@Controller("orderController")
@RequestMapping(value="/order")
public class OrderControllerImpl extends BaseController implements OrderController{
	@Autowired
	OrderService orderService;
	@Autowired
	OrderVO orderVO;
	
	// 장바구니 각각의 항목에 대한 처리, 상품 상세보기에수 구매하기에 대한 처리
	@RequestMapping(value="/orderEachGoods.do", method= RequestMethod.POST)
	public ModelAndView orderEachGoods(@ModelAttribute("orderVO") OrderVO _orderVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Boolean isLogOn = (Boolean)session.getAttribute("isLogOn");
		String action = (String)session.getAttribute("action");
		System.out.println("1"+isLogOn);
		if(isLogOn==null || isLogOn==false) {	//로그인 하지 않았을때
			session.setAttribute("orderInfo", _orderVO);
			session.setAttribute("action", "/order/orderEachGoods.do");
			return new ModelAndView("redirect:/member/loginForm.do");//로그인 하지 않고 상세보기에서 구매하기
		} else {	//로그인 하고 구매하기 하였을때
			if(action != null && action.equals("/order/orderEachGoods.do")) {	
				orderVO = (OrderVO)session.getAttribute("orderInfo");
				session.removeAttribute("action");
			} else {
				orderVO = _orderVO;
			}
		}
		
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		System.out.println("2"+viewName);
		List myOrderList = new ArrayList<OrderVO>();
		System.out.println("2"+orderVO.toString());
		myOrderList.add(orderVO);
		
		MemberVO memberInfo = (MemberVO)session.getAttribute("memberInfo");
		
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberInfo);
		return mav;
	}
	
	// 전체 장바구니 항목에 대한 요청 매핑
	@RequestMapping(value="/orderAllCartGoods.do", method=RequestMethod.POST)
	public ModelAndView orderAllCartGoods(@RequestParam("cart_goods_qty")String[] cart_goods_qty
										, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		Map cartMap = (Map)session.getAttribute("cartMap");
		List myOrderList = new ArrayList<OrderVO>();
		
		List<GoodsVO> myGoodsList = (List<GoodsVO>)cartMap.get("myGoodsList");
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		
		for(int i=0; i<cart_goods_qty.length; i++) {
			String[]cart_goods = cart_goods_qty[i].split(":");
			for(int j = 0; j < myGoodsList.size(); j++) {
				GoodsVO goodsVO = myGoodsList.get(j);
				int goods_id = goodsVO.getGoods_id();
				if(goods_id == Integer.parseInt(cart_goods[0])) {
					OrderVO _orderVO = new OrderVO();
					String goods_title = goodsVO.getGoods_title();
					int goods_sales_price = goodsVO.getGoods_sales_price();
					String goods_fileName = goodsVO.getGoods_fileName();
					_orderVO.setGoods_id(goods_id);
					_orderVO.setGoods_title(goods_title);
					_orderVO.setGoods_sales_price(goods_sales_price);
					_orderVO.setGoods_fileName(goods_fileName);
					_orderVO.setOrder_goods_qty(Integer.parseInt(cart_goods[1]));
					_orderVO.setOrder_goods_size(cart_goods[2]);
					myOrderList.add(_orderVO);
					break;
				}
			}
		}
		session.setAttribute("myOrderList", myOrderList);
		session.setAttribute("orderer", memberVO);
		return mav;
	}

	@RequestMapping(value="/payToOrderGoods.do", method=RequestMethod.POST)
	public ModelAndView payToOrderGoods(@RequestParam Map<String, String> receiverMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("orderer");
		String member_id = memberVO.getMember_id();
		String orderer_name = memberVO.getMember_name();
		String orderer_hp = memberVO.getHp1() + "-" + memberVO.getHp2() + "-" + memberVO.getHp3();
		List<OrderVO> myOrderList = (List<OrderVO>)session.getAttribute("myOrderList");
		
		for(int i = 0; i < myOrderList.size(); i++) {
			OrderVO orderVO = (OrderVO)myOrderList.get(i);
			orderVO.setMember_id(member_id);
			orderVO.setOrderer_name(orderer_name);
			orderVO.setReceiver_name(receiverMap.get("receiver_name"));
			
			orderVO.setReceiver_hp1(receiverMap.get("receiver_hp1"));
			orderVO.setReceiver_hp2(receiverMap.get("receiver_hp2"));
			orderVO.setReceiver_hp3(receiverMap.get("receiver_hp3"));
			orderVO.setReceiver_tel1(receiverMap.get("receiver_tel1"));
			orderVO.setReceiver_tel2(receiverMap.get("receiver_tel2"));
			orderVO.setReceiver_tel3(receiverMap.get("receiver_tel3"));
			
			orderVO.setDelivery_address(receiverMap.get("delivery_address"));
			orderVO.setDelivery_message(receiverMap.get("delivery_message"));
			orderVO.setDelivery_method(receiverMap.get("delivery_method"));
			orderVO.setGift_wrapping(receiverMap.get("gift_wrapping"));
			orderVO.setPay_method(receiverMap.get("pay_method"));
			orderVO.setCard_com_name(receiverMap.get("card_com_name"));
			orderVO.setCard_pay_month(receiverMap.get("card_pay_month"));
			orderVO.setPay_orderer_hp(receiverMap.get("pay_orderer_hp"));
			orderVO.setOrderer_hp(orderer_hp);
			myOrderList.set(i, orderVO);
		}
		
		orderService.addNewOrder(myOrderList);
		mav.addObject("myOrderInfo", receiverMap);
		mav.addObject("myOrderList", myOrderList);
		return mav;
	}

}
