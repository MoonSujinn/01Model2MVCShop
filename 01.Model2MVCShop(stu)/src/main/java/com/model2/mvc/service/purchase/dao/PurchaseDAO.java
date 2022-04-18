package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;



public class PurchaseDAO {
   
   public PurchaseDAO(){
   }

   public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
      
      Connection con = DBUtil.getConnection();

      String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,?,sysdate,?)";
         
         PreparedStatement stmt = con.prepareStatement(sql);
         stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
         stmt.setString(2, purchaseVO.getBuyer().getUserId());
         stmt.setString(3, purchaseVO.getPaymentOption());
         stmt.setString(4, purchaseVO.getReceiverName());
         stmt.setString(5, purchaseVO.getReceiverPhone());
         stmt.setString(6, purchaseVO.getDivyAddr());
         stmt.setString(7, purchaseVO.getDivyRequest());
         stmt.setString(8, purchaseVO.getTranCode());
         stmt.setString(9, purchaseVO.getDivyDate().replace("-", ""));
         
         stmt.executeUpdate();
      
        con.close();
   }
   
   public PurchaseVO findPurchase(int tranNo) throws Exception {
	      
	      Connection con = DBUtil.getConnection();

	      String sql = "SELECT * FROM transaction WHERE tran_no=?";
	      
	      PreparedStatement stmt = con.prepareStatement(sql);
	      stmt.setInt(1, tranNo);

	      ResultSet rs = stmt.executeQuery();

	      UserVO uvo=new UserVO();
	      ProductVO pvo=new ProductVO();
	      PurchaseVO purchaseVO = null;
	      
	      while (rs.next()) {
	         purchaseVO = new PurchaseVO();
	         uvo.setUserId(rs.getString("buyer_id"));
	         purchaseVO.setBuyer(uvo);
	         pvo.setProdNo(rs.getInt("prod_no"));
	         purchaseVO.setPurchaseProd(pvo);
	         purchaseVO.setTranNo(rs.getInt("tran_no"));
	         purchaseVO.setPaymentOption(rs.getString("payment_option"));
	         purchaseVO.setReceiverName(rs.getString("receiver_name"));
	         purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
	         purchaseVO.setDivyAddr(rs.getString("demailaddr"));
	         purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
	         purchaseVO.setDivyDate(rs.getString("dlvy_date"));
	         purchaseVO.setOrderDate(rs.getDate("order_data"));
	      }
	      
	      con.close();

	      return purchaseVO;
	   }

   
   public HashMap<String,Object> getPurchaseList(SearchVO searchVO, String userId) throws Exception {
      
	   System.out.println("start");
      Connection con = DBUtil.getConnection();

      String sql = "SELECT * FROM transaction WHERE buyer_id='"+userId+"'";
      
      sql += " order by tran_no";


      PreparedStatement stmt = 
         con.prepareStatement(   sql,
                                          ResultSet.TYPE_SCROLL_INSENSITIVE,
                                          ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = stmt.executeQuery();

      rs.last();
      int total = rs.getRow();
      System.out.println("로우의 수:" + total);

      HashMap<String,Object> map = new HashMap<String,Object>();
      map.put("count", new Integer(total));

      rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
      System.out.println("searchVO.getPage():" + searchVO.getPage());
      System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

      ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
      if (total > 0) {
         for (int i = 0; i < searchVO.getPageUnit(); i++) {
           
        	ProductVO pvo=new ProductVO();
            UserVO uvo=new UserVO();
            PurchaseVO vo = new PurchaseVO();
            
            uvo.setUserId(rs.getString("buyer_id"));
            vo.setBuyer(uvo);
            
            pvo.setProdNo(rs.getInt("prod_no"));
            vo.setPurchaseProd(pvo);
                  
            vo.setDivyAddr(rs.getString("demailaddr"));
            vo.setDivyDate(rs.getString("dlvy_date"));
            vo.setDivyRequest(rs.getString("dlvy_request"));
            vo.setOrderDate(rs.getDate("order_data"));
            vo.setPaymentOption(rs.getString("payment_option"));
            vo.setReceiverName(rs.getString("receiver_name"));
            vo.setReceiverPhone(rs.getString("receiver_phone"));
            vo.setTranCode(rs.getString("tran_status_code"));
            vo.setTranNo(rs.getInt("tran_no"));

            list.add(vo);
            if (!rs.next())
               break;
         }
      }
      System.out.println(list.get(0));
      System.out.println("list.size() : "+ list.size());
      map.put("list", list);
      System.out.println("map().size() : "+ map.size());

      con.close();
         
      return map;
   }
   
   public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
	      
	      Connection con = DBUtil.getConnection();

	      String sql = "UPDATE transaction set payment_option=?,receiver_name=?,receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";
	      
	      PreparedStatement stmt = con.prepareStatement(sql);
	      stmt.setString(1, purchaseVO.getPaymentOption());
	      stmt.setString(2, purchaseVO.getReceiverName());
	      stmt.setString(3, purchaseVO.getReceiverPhone());
	      stmt.setString(4, purchaseVO.getDivyAddr());
	      stmt.setString(5, purchaseVO.getDivyRequest());
	      stmt.setString(6, purchaseVO.getDivyDate());
	      stmt.setInt(7, purchaseVO.getTranNo());
	      stmt.executeUpdate();
	      
	      con.close();
	   } 
   
   public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
	      
	      Connection con = DBUtil.getConnection();

	      String sql = "UPDATE transaction set tran_status_code=? WHERE prod_no=?";
	      
	      PreparedStatement stmt = con.prepareStatement(sql);
	      
	      stmt.setString(1, purchaseVO.getTranCode());
	      stmt.setInt(2, purchaseVO.getPurchaseProd().getProdNo());

	      stmt.executeUpdate();
	      
	      con.close();
	   }
   
   
	public HashMap<String,Object> getSaleList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " WHERE tran_no LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " WHERE prod_no LIKE '%" + searchVO.getSearchKeyword()
						+ "%'";
			} else if (searchVO.getSearchCondition().equals("2")) {
				sql += " where tran_status_code LIKE '%" + searchVO.getSearchKeyword()
				+ "%'";
			} 
		}
		sql += " order by tran_no";

		PreparedStatement stmt = 
			con.prepareStatement(	sql,
														ResultSet.TYPE_SCROLL_INSENSITIVE,
														ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				UserVO uvo = new UserVO();
				ProductVO pvo = new ProductVO();
				PurchaseVO vo = new PurchaseVO();
				pvo.setProdNo(rs.getInt("prod_no"));
				vo.setPurchaseProd(pvo);
				uvo.setUserId(rs.getString("buyer_id"));
				vo.setBuyer(uvo);
				vo.setTranNo(rs.getInt("tran_no"));
				vo.setTranCode(rs.getString("tran_status_code"));

				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();
			
		return map;
	}
   
  
}

   