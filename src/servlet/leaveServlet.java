package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

public class leaveServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		//��ʼ��
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://wuqy66.cn:3308/hotel_DB?useSSL=false";
		String user = "root";
		String pwd = "123456";
		String customerIDCard = request.getParameter("customerIDCard");
		String sql_leave = "update orders set orderStatus = '已退房' where orders.customerIDCard = '"+customerIDCard+"'";
		String sql_query = "select * from orders where customerIDCard = '"+customerIDCard+"'";
		Connection conn = null;
		try {
			Class.forName(driverName);
			try {
				conn = DriverManager.getConnection(url,user,pwd);
				Statement st = conn.createStatement();
				st.execute(sql_leave);
				System.out.print("�˷��ɹ�!");
				ResultSet rs = st.executeQuery(sql_query);
				List<Map> list = new ArrayList<Map>();
 				while(rs.next()){
					String orderNumber = rs.getString("orderNumber");
					String orderStatus = rs.getString("orderStatus");
					customerIDCard = rs.getString("customerIDCard");
					String roomNumber = rs.getString("roomNumber");
					String checkInTime = rs.getString("checkInTime");
					String checkOutTime = rs.getString("checkOutTime");
					String totalMoney = rs.getString("totalMoney");
					String orderTime = rs.getString("orderTime");
					Map e = new HashMap();
					e.put("orderNumber", orderNumber);
					e.put("orderStatus",orderStatus);
					e.put("customerIDCard", customerIDCard);
					e.put("roomNumber",roomNumber);
					e.put("checkInTime",checkInTime);
					e.put("checkOutTime",checkOutTime);
					e.put("totalMoney",totalMoney);
					e.put("orderTime",orderTime);
					list.add(e);
				}
				JSONArray json = JSONArray.fromObject(list);
				out.print(json);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doGet(request,response);
	}

}
