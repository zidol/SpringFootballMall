package com.shoppingmall.admin.goods.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingmall.admin.goods.dao.AdminGoodsDAO;
import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.goods.vo.ImageFileVO;
import com.shoppingmall.order.vo.OrderVO;


@Service("adminGoodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminGoodsServiceImpl implements AdminGoodsService {
	@Autowired
	AdminGoodsDAO adminGoodsDAO;
	
	@Override// 새상품 등록
	public int addNewGoods(Map newGoodsMap) throws Exception{
		int goods_id = adminGoodsDAO.insertNewGoods(newGoodsMap);
		ArrayList<ImageFileVO> imageFileList = (ArrayList)newGoodsMap.get("imageFileList");
		for(ImageFileVO imageFileVO : imageFileList) {
			imageFileVO.setGoods_id(goods_id);
		}
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
		return goods_id;
	}
	
	@Override//등록한 상품 조회
	public List<GoodsVO> listNewGoods(Map condMap) throws Exception{
		return adminGoodsDAO.selectNewGoodsList(condMap);
	}
	@Override //상품수정페이지에 나오는 품목 상세 조회
	public Map goodsDetail(Map condMap) throws Exception {
		Map goodsMap = new HashMap();
		GoodsVO goodsVO=adminGoodsDAO.selectGoodsDetail(condMap);
		List imageFileList =adminGoodsDAO.selectGoodsImageFileList((int)condMap.get("goods_id"));
		goodsMap.put("goods", goodsVO);
		goodsMap.put("imageFileList", imageFileList);
		return goodsMap;
	}
	@Override //싱품 이미지 조회
	public List goodsImageFile(int goods_id) throws Exception{
		List imageList =adminGoodsDAO.selectGoodsImageFileList(goods_id);
		return imageList;
	}
	
	@Override// 상품 정보 수정
	public void modifyGoodsInfo(Map goodsMap) throws Exception{
		adminGoodsDAO.updateGoodsInfo(goodsMap);
	}	
	@Override//상품이미지 수정
	public void modifyGoodsImage(List<ImageFileVO> imageFileList) throws Exception{
		adminGoodsDAO.updateGoodsImage(imageFileList); 
	}
	
	@Override//주문 상품 조회
	public List<OrderVO> listOrderGoods(Map condMap) throws Exception{
		return adminGoodsDAO.selectOrderGoodsList(condMap);
	}
	@Override
	public void modifyOrderGoods(Map orderMap) throws Exception{
		adminGoodsDAO.updateOrderGoods(orderMap);
	}
	
	@Override//상품이미지 삭제
	public void removeGoodsImage(int image_id) throws Exception{
		adminGoodsDAO.deleteGoodsImage(image_id);
	}
	
	@Override//이미지 추가
	public void addNewGoodsImage(List imageFileList) throws Exception{
		adminGoodsDAO.insertGoodsImageFile(imageFileList);
	}

	@Override//상품이미지 조회
	public void addNewGoodsDetail(Map detailMap) throws Exception {
		adminGoodsDAO.insertNewGoodsDetail(detailMap);
	}

	@Override//goods_detail테이블 수정(상품 사이즈)
	public void modifyGoodsDetailInfo(Map goodsMap) throws Exception {
		adminGoodsDAO.updateGoodsDetailInfo(goodsMap);
	}
	

	
}
