package com.shoppingmall.order.service;

import java.util.List;

import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.order.vo.OrderVO;

public interface OrderService {
	public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception;
	public void addNewOrder(List<OrderVO> myOrderList)throws Exception;
	public OrderVO findMyOrder(String order_id) throws Exception;
	public List<GoodsVO> selectGoods() throws Exception;
}
