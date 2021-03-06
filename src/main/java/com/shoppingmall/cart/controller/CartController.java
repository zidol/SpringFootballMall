package com.shoppingmall.cart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public interface CartController {
	public ModelAndView myCartList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public @ResponseBody String addGoodsInCart(@RequestParam("goods_id") int goods_id, @RequestParam("cart_goods_qty")int cart_goods_qty,
			   @RequestParam("order_goods_size")String order_goods_size, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public @ResponseBody String modifyCartQty(@RequestParam("goods_id") int goods_id, @RequestParam("cart_goods_qty") int cart_goods_qty,
			 HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView removeCartGoods(@RequestParam("cart_id") int cart_id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
