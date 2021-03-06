package com.shoppingmall.goods.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.goods.vo.ImageFileVO;

public interface GoodsDAO {
	public List<GoodsVO> selectGoodsList(String goodsStatus) throws DataAccessException;
	public List<String> selectKeywordSearch(String keyword) throws DataAccessException;
	public GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException;
	public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException;
	public List<GoodsVO> selectGoodsBySearchWord(String searchWord) throws DataAccessException;
	public List<String> selectGoodsSize(String goods_id) throws DataAccessException;
	public List<GoodsVO>selectBySort(String goods_sort) throws DataAccessException;
}
