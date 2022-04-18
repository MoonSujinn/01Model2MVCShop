package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseViewAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		//HttpSession session=request.getSession();
		//UserVO userVO=(UserVO)session.getAttribute("user");
			
		
		String prodNo=request.getParameter("prodNo");

		ProductDAO productDAO= new ProductDAO();
		
		ProductVO productVO=productDAO.findProduct(prodNo);
		
		request.setAttribute("productVO", productVO);
		
		System.out.println("productVOÅ×½ºÆ®"+productVO);
		
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}