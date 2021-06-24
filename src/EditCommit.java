

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditCommit
 */
@WebServlet("/EditCommit")
public class EditCommit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditCommit() {
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
		
		//DB取得用のクエリを作成しUpdQueryへ設定している
		String UpdQuery="UPDATE mst_auth SET AUTH_NAME= ? ,STATUS= ? "+
				",UPDATE_DATE= ? "+
				",UPDATE_USER_ID= ? WHERE ID = ? ";


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

			//PreparedStatement ps = conn .prepareStatement(UpdQuery)){
	    		PreparedStatement ps = conn .prepareStatement(UpdQuery)){
	    		ps.setString(1,AUTH_NAME);
	    		ps.setString(2,STATUS);
	    		ps.setString(3,UPDATE_DATE);
	    		ps.setString(4,UPDATE_USER_ID);
	    		ps.setString(5,ID);


			//DBに変更をかけている
		    int i = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

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
