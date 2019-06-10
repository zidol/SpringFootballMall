package com.shoppingmall.order.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.shoppingmall.order.vo.OrderVO;

public interface OrderDAO {
	public List<OrderVO> listMyOrderGoods(OrderVO orderBean)throws DataAccessException;
	public void insertNewOrder(List<OrderVO> myuOrderList) throws DataAccessException;
	public OrderVO findMyOrder(String order_id) throws DataAccessException;
	public void removeGoodsFromCart(List<OrderVO>myOrderList) throws DataAccessException;
	public List selectGoodsList() throws DataAccessException;
	public void decreaseGoodsQty(Map orderMap) throws DataAccessException;
}
