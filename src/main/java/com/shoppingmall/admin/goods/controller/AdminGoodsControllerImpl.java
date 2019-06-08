package com.shoppingmall.admin.goods.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.shoppingmall.admin.goods.service.AdminGoodsService;
import com.shoppingmall.common.base.BaseController;
import com.shoppingmall.goods.vo.GoodsVO;
import com.shoppingmall.goods.vo.ImageFileVO;
import com.shoppingmall.member.vo.MemberVO;

@Controller("adminGoodsController")
@RequestMapping(value="/admin/goods")
public class AdminGoodsControllerImpl extends BaseController implements AdminGoodsController{
//	private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping\\file_repo";
	private static final String CURR_IMAGE_REPO_PATH = "/Users/zidol/Desktop/shopping/file_repo";
	@Autowired
	AdminGoodsService adminGoodsService;

	@RequestMapping(value="/adminGoodsMain.do", method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView adminGoodsMain(Map<String, String> dateMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		session = request.getSession();
		session.setAttribute("side_menu","admin_mode");
		
		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
		String section = dateMap.get("section");
		String pageNum = dateMap.get("pageNum");
		String search_type = dateMap.get("search_type");
		String search_word = dateMap.get("search_word");
		String beginDate=dateMap.get("beginDate");
		String endDate=dateMap.get("endDate");
		
		if(beginDate == null) {
			String [] tempDate=calcSearchPeriod(fixedSearchPeriod).split(",");
			beginDate=tempDate[0];
			endDate=tempDate[1];
		}
		
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		
		Map<String, Object> condMap = new HashMap<String, Object>();
		if(section == null) {
			section = "1";
		}
		condMap.put("section", section);
		if(pageNum ==null) {
			pageNum = "1";
		}
		
		condMap.put("pageNum", pageNum);
		condMap.put("beginDate", beginDate);
		condMap.put("endDate", endDate);
		condMap.put("search_type",search_type);
		condMap.put("search_word", search_word);
		List<GoodsVO> newGoodsList = adminGoodsService.listNewGoods(condMap);
		mav.addObject("newGoodsList", newGoodsList);
		
		String beginDate1[] = beginDate.split("-");
		String endDate2[] = endDate.split("-");
		mav.addObject("beginYear", beginDate1[0]);
		mav.addObject("beginMonth", beginDate1[1]);
		mav.addObject("beginDay", beginDate1[2]);
		mav.addObject("endYear", endDate2[0]);
		mav.addObject("endMonth", endDate2[1]);
		mav.addObject("endDay", endDate2[2]);
		
		mav.addObject("section", section);
		mav.addObject("pageNum", pageNum);
		return mav;
	}

	@RequestMapping(value="/addNewGoods.do", method = {RequestMethod.POST})
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String imageFileName = null;
		
		Map newGoodsMap = new HashMap();
		List detail = new ArrayList();
		Enumeration enu = multipartRequest.getParameterNames();
		Enumeration enuDetail = multipartRequest.getParameterNames();
		String arr[] = null;
		
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value = multipartRequest.getParameter(name);
			newGoodsMap.put(name, value);
		}
		System.out.println(detail);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO)session.getAttribute("memberInfo");
		System.out.println(memberVO.getMember_id());
		String reg_id = memberVO.getMember_id();
		
		List<ImageFileVO>imageFileList = upload(multipartRequest);
		if(imageFileList != null && imageFileList.size() != 0) {
			for(ImageFileVO imageFileVO : imageFileList) {
				imageFileVO.setReg_id(reg_id);
			}
			newGoodsMap.put("imageFileList", imageFileList);
		}
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
			Map<String, List> paramMap = new HashMap<String, List>();
			Map newGoodsDetailMap;
			while(enuDetail.hasMoreElements()) {
				String name = (String) enuDetail.nextElement();
				if(name.equals("goods_size")) {
					 arr = multipartRequest.getParameterValues(name);
					for(int i=0; i < arr.length; i++) {
						newGoodsDetailMap = new HashMap();
						newGoodsDetailMap.put("goods_id", goods_id);
						newGoodsDetailMap.put("goods_color", multipartRequest.getParameter("goods_color"));
						newGoodsDetailMap.put("goods_qty", multipartRequest.getParameter("goods_qty"));
						newGoodsDetailMap.put(name, arr[i]);
						detail.add(newGoodsDetailMap);
					}
				}
			}
			paramMap.put("detailMap", detail);
			adminGoodsService.addNewGoodsDetail(paramMap);
			if(imageFileList != null && imageFileList.size() != 0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "/" + "temp" + "/" + imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH + "/" + goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
				}
			}
			message = "<script>";
			message += "alert('성공적으로 입력 하였습니다');";
			message += "location.href='" + multipartRequest.getContextPath() + "/admin/goods/addNewGoodsForm.do';";
			message += ("</script>");
		} catch (Exception e) {
			if(imageFileList != null && imageFileList.size() != 0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH + "\\" + "temp" + "\\" + imageFileName);
					srcFile.delete();
				}
			}
			message = "<script>";
			message += "alert('등록 실패 하였습니다');";
			message += "location.href='" + multipartRequest.getContextPath() + "/admin/goods/addNewGoodsForm.do';";
			message += ("</script>");
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@RequestMapping(value="/modifyGoodsForm.do", method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView modifyGoodsForm(@RequestParam("goods_id")int goods_id, @RequestParam("goods_size")int goods_size,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		Map condMap = new HashMap<>();
		condMap.put("goods_id", goods_id);
		condMap.put("goods_size", goods_size);
		Map goodsMap = adminGoodsService.goodsDetail(condMap);
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}

	@RequestMapping(value="/modifyGoodsInfo.do", method= {RequestMethod.POST})
	public ResponseEntity modifyGoodsInfo(@RequestParam("goods_id") String goods_id,
										@RequestParam(value="goods_size", required=false) String goods_size,
										@RequestParam(value="goods_color", required=false) String goods_color,@RequestParam("attribute")String attribute, 
			@RequestParam("value")String value, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> goodsMap = new HashMap<String, String>();
		goodsMap.put("goods_id", goods_id);
		goodsMap.put(attribute, value);
		System.out.println("goods_size: " + goods_size);
		System.out.println("goods_color: " + goods_color);
		System.out.println("goods_qty: " +attribute);
		if(attribute.equals("goods_qty")) {
			System.out.println("1");
			goodsMap.put("goods_size", goods_size);
			goodsMap.put("goods_color", goods_color);
			adminGoodsService.modifyGoodsDetailInfo(goodsMap);
		}else {
			System.out.println("2");
			adminGoodsService.modifyGoodsInfo(goodsMap);
		}
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		message = "mod_success";
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@RequestMapping(value="/modifyGoodsImageInfo.do" ,method={RequestMethod.POST})
	public void modifyGoodsImageInfo(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)  throws Exception {
		System.out.println("modifyGoodsImageInfo");
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName=null;
		
		Map goodsMap = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			goodsMap.put(name,value);
		}
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();
		
		List<ImageFileVO> imageFileList=null;
		int goods_id=0;
		int image_id=0;
		try {
			imageFileList =upload(multipartRequest);
			if(imageFileList!= null && imageFileList.size()!=0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					goods_id = Integer.parseInt((String)goodsMap.get("goods_id"));
					image_id = Integer.parseInt((String)goodsMap.get("image_id"));
					imageFileVO.setGoods_id(goods_id);
					imageFileVO.setImage_id(image_id);
					imageFileVO.setReg_id(reg_id);
				}
				
			    adminGoodsService.modifyGoodsImage(imageFileList);
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"/"+"temp"+"/"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH+"/"+goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
			}
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"/"+"temp"+"/"+imageFileName);
					srcFile.delete();
				}
			}
			e.printStackTrace();
		}
		
	}
	

	@Override
	@RequestMapping(value="/addNewGoodsImage.do" ,method={RequestMethod.POST})
	public void addNewGoodsImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		System.out.println("addNewGoodsImage");
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName=null;
		
		Map goodsMap = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			goodsMap.put(name,value);
		}
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();
		
		List<ImageFileVO> imageFileList=null;
		int goods_id=0;
		try {
			imageFileList =upload(multipartRequest);
			if(imageFileList!= null && imageFileList.size()!=0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					goods_id = Integer.parseInt((String)goodsMap.get("goods_id"));
					imageFileVO.setGoods_id(goods_id);
					imageFileVO.setReg_id(reg_id);
				}
				
			    adminGoodsService.addNewGoodsImage(imageFileList);
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"/"+"temp"+"/"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH+"/"+goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
			}
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"/"+"temp"+"/"+imageFileName);
					srcFile.delete();
				}
			}
			e.printStackTrace();
		}
	}

	@Override
	@RequestMapping(value="/removeGoodsImage.do" ,method={RequestMethod.POST})
	public void  removeGoodsImage(@RequestParam("goods_id") int goods_id,
			                      @RequestParam("image_id") int image_id,
			                      @RequestParam("imageFileName") String imageFileName,
			                      HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		adminGoodsService.removeGoodsImage(image_id);
		try{
			File srcFile = new File(CURR_IMAGE_REPO_PATH+"/"+goods_id+"/"+imageFileName);
			srcFile.delete();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}
