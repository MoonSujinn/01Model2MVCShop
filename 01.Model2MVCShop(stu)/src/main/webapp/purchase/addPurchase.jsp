<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>
<%@ page import="com.model2.mvc.service.user.vo.*" %>


<%
	UserVO uvo = (UserVO)session.getAttribute("user");

%>



<%
	PurchaseVO pcvo=(PurchaseVO)request.getAttribute("purchaseVO");
	System.out.println(pcvo);

%>



<html>
<head>
<title>구매 완료 페이지</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
      <td><%=pcvo.getPurchaseProd().getProdNo() %></td>
      	<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td><%=uvo.getUserId() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		
	  <%
            if(pcvo.getPaymentOption().trim().equals("1")){
      %>
			현금구매
      <%
            }else {
      %>
            신용구매
      <%
            }
	  %>
		
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td><%=pcvo.getReceiverName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td><%=pcvo.getReceiverPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td><%=pcvo.getDivyAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td><%=pcvo.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td><%=pcvo.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>