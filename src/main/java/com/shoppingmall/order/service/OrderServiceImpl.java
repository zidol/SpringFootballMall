package com.shoppingmall.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.order.dao.OrderDAO;
import com.shoppingmall.order.vo.OrderVO;


@Service("orderService")
@Transactional(propagation=Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService{
	@Autowired
	OrderDAO orderDAO;
	
	@Override
	public List<OrderVO> listMyOrderGoods(OrderVO orderVO) throws Exception {
		List<OrderVO> orderGodosList;
		orderGodosList = orderDAO.listMyOrderGoods(orderVO);
		return orderGodosList;
	}

	@Override
	public void addNewOrder(List<OrderVO> myOrderList) throws Exception {
		orderDAO.insertNewOrder(myOrderList);
		orderDAO.removeGoodsFromCart(myOrderList);
		List<GoodsVO> goodsList = selectGoods();
		Map goodsMap = null;

		System.out.println("order1");
		for(int i=0; i< myOrderList.size(); i++) {
			goodsMap = new HashMap();
			if(goodsList.contains(myOrderList.get(i).getGoods_id())) {
				goodsMap.put("goods_id", myOrderList.get(i).getGoods_id());
				goodsMap.put("order_goods_qty", myOrderList.get(i).getOrder_goods_qty());
				goodsMap.put("order_goods_size", myOrderList.get(i).getOrder_goods_size());
				orderDAO.decreaseGoodsQty(goodsMap);
				System.out.println("order2");
			}
			
		}
		
	}

	@Override
	public OrderVO findMyOrder(String order_id) throws Exception {
		return orderDAO.findMyOrder(order_id);
	}

	@Override
	public List<GoodsVO> selectGoods() throws Exception {
		return orderDAO.selectGoodsList();
	}

}
