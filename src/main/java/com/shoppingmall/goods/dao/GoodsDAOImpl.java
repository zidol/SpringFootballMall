package com.shoppingmall.goods.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.goods.vo.ImageFileVO;



@Repository("goodsDAO")
public class GoodsDAOImpl implements GoodsDAO{
	@Autowired
	private SqlSession sqlSession;

	@Override//상품 전체 목록
	public List<GoodsVO> selectGoodsList(String goodsStatus ) throws DataAccessException {
		System.out.println("dao1");
		List<GoodsVO> goodsList=(ArrayList)sqlSession.selectList("mapper.goods.selectGoodsList",goodsStatus);
		System.out.println("dao2");
	   return goodsList;	
	}
	@Override// 상품 이름 입력시 가져오는 상품이름목록
	public List<String> selectKeywordSearch(String keyword) throws DataAccessException {
	   List<String> list=(ArrayList)sqlSession.selectList("mapper.goods.selectKeywordSearch",keyword);
	   return list;
	}
	
	@Override// 상품 검색 후 가져오는 상품 목록
	public ArrayList selectGoodsBySearchWord(String searchWord) throws DataAccessException{
		ArrayList list=(ArrayList)sqlSession.selectList("mapper.goods.selectGoodsBySearchWord",searchWord);
		 return list;
	}
	
	@Override//상품 상세보기 페이지에서 가져오는 목록
	public GoodsVO selectGoodsDetail(String goods_id) throws DataAccessException{
		GoodsVO goodsVO=(GoodsVO)sqlSession.selectOne("mapper.goods.selectGoodsDetail",goods_id);
		return goodsVO;
	}
	
	@Override//상품 상세보기 페이지에서 사용하는 이미지 목록 
	public List<ImageFileVO> selectGoodsDetailImage(String goods_id) throws DataAccessException{
		List<ImageFileVO> imageList = (ArrayList)sqlSession.selectList("mapper.goods.selectGoodsDetailImage",goods_id);
		return imageList;
	}
	@Override//상품 사이즈 목록
	public List<String> selectGoodsSize(String goods_id) throws DataAccessException {
		ArrayList list = (ArrayList)sqlSession.selectList("mapper.goods.selectGoodsSize",goods_id);
		return list;
	}
	@Override// 상품 종류별로 보는 페이지에서 가져오는 목록
	public List<GoodsVO> selectBySort(String goods_sort) throws DataAccessException {
		List<GoodsVO> goodsList=(ArrayList)sqlSession.selectList("mapper.goods.selectBySort",goods_sort);
		return goodsList;
	}
	
}
