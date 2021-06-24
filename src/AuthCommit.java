import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthCommit
 */
@WebServlet("/AuthCommit")
public class AuthCommit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthCommit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		//文字コードの設定
	  	request.setCharacterEncoding("UTF-8");



	  	String ID=request.getParameter("ID");
		String AUTH_NAME=request.getParameter("AUTH_NAME");
		String STATUS=request.getParameter("STATUS");
		String REGIST_DATE=request.getParameter("REGIST_DATE");
		String UPDATE_DATE=request.getParameter("UPDATE_DATE");
		String UPDATE_USER_ID=request.getParameter("UPDATE_USER_ID");
		ResultSet rs=(ResultSet)request.getAttribute("Result");
		String errmsg="";


		//DB登録用のクエリを作成し（INSERT文）InsQueryへ設定している
		String InsQuery="INSERT INTO mst_auth (AUTH_NAME,STATUS,REGIST_DATE,UPDATE_DATE,UPDATE_USER_ID)VALUES"
				+ "('"+ AUTH_NAME +"','"+ STATUS + "','"+ REGIST_DATE + "','"+ UPDATE_DATE + "','"+ UPDATE_USER_ID + "')";

		final String URL
	    = "jdbc:mysql://localhost:3306/especially?serverTimezone=JST";
	    final String USER = "root";
	    final String PASS = "";



	    try {
	    	//Mysqlに繋げている（道順）
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

	    // DBに接続
	    try(Connection
	    		conn  = DriverManager.getConnection(URL, USER, PASS);

			PreparedStatement ps = conn .prepareStatement(InsQuery)){

			//DBに変更をかけている
		    int i = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		request.setAttribute("errmsg",errmsg);
		request.setAttribute("AUTH_NAME",AUTH_NAME);
		request.setAttribute("STATUS",STATUS);
		request.setAttribute("REGIST_DATE",REGIST_DATE);
		request.setAttribute("UPDATE_DATE",UPDATE_DATE);
		request.setAttribute("UPDATE_USER_ID",UPDATE_USER_ID);
		request.setAttribute("Result",rs);
		request.setAttribute("ID",ID);



		//画面遷移
		getServletContext().getRequestDispatcher("/Auth").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
