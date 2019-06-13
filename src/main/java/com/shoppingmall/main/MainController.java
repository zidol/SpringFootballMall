package com.shoppingmall.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingmall.common.base.BaseController;
import com.shoppingmall.goods.service.GoodsService;
import com.shoppingmall.goods.vo.GoodsVO;

@Controller("mainController")
@EnableAspectJAutoProxy
public class MainController extends BaseController{
	
	@Autowired
	private GoodsService goodsService;
	
	//메인화면에서 전체 상품분류 별로 나누어 보여줌(인기상품, 새로운 상품 출시, 스테디셀러) 
	@RequestMapping(value="/main/main.do", method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("controller1");
		HttpSession session;
		ModelAndView mav = new ModelAndView();
		String viewName= (String)request.getAttribute("viewName");
		mav.setViewName(viewName);
		System.out.println("controller2");
		session = request.getSession();
		session.setAttribute("side_menu", "user");
		System.out.println("controller3");
		Map<String, List<GoodsVO>>goodsMap = goodsService.listGoods();
		mav.addObject("goodsMap", goodsMap);
		System.out.println(mav);
		return mav;
	}
}
