package com.shoppingmall.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingmall.goods.dao.GoodsDAO;
import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.goods.vo.ImageFileVO;

@Service("goodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	GoodsDAO goodsDAO;

	@Override
	public Map<String, List<GoodsVO>> listGoods() throws Exception {
		Map<String, List<GoodsVO>> goodsMap = new HashMap<String, List<GoodsVO>>();
		List<GoodsVO> goodsList = goodsDAO.selectGoodsList("bestseller");
		goodsMap.put("bestseller", goodsList);
		
		goodsList = goodsDAO.selectGoodsList("newproduct");
		goodsMap.put("newproduct", goodsList);
		
		goodsList = goodsDAO.selectGoodsList("steadyseller");
		goodsMap.put("steadyseller", goodsList);
		
		return goodsMap;
	}

	@Override
	public Map goodsDetail(String _goods_id) throws Exception {
		Map goodsMap = new HashMap();
		GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id);
		goodsMap.put("goodsVO", goodsVO);
		
		List<ImageFileVO> imageList = goodsDAO.selectGoodsDetailImage(_goods_id);
		goodsMap.put("imageList", imageList);
		return goodsMap;
	}

	@Override
	public List<String> keywordSearch(String keyword) throws Exception {
		List<String> list = goodsDAO.selectKeywordSearch(keyword);
		return list;
	}

	@Override
	public List<GoodsVO> searchGoods(String searchWord) throws Exception {
		List goodsList = goodsDAO.selectGoodsBySearchWord(searchWord);
		return goodsList;
	}

	@Override
	public List<String> goodsSize(String _goods_id) throws Exception {
		List goodsSize = goodsDAO.selectGoodsSize(_goods_id);
		return goodsSize;
	}

}
