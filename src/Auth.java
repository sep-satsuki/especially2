

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
 * Servlet implementation class Auth
 */
@WebServlet("/Auth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Auth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");

		//Auth.jspの登録、変更、実行のそれぞれのform内のhiddenからとってきてる
		String FLG =request.getParameter("FLG");

		String errmsg= "";
		//登録処理実行
		String ID="";
		String AUTH_NAME="";
		String STATUS="";
		String REGIST_DATE="";
		String UPDATE_DATE="";
		String UPDATE_USER_ID="";
		String Query= "";
		String STATUS1 = "";

		int listCnt = 0;
		String nowPage="";

		final String ERR_MESSAGE="未入力で登録することはできません";

		if(FLG == null) {
			FLG = "0";
		}

		//登録または変更のときのみ権限名（AUTH_NAME)の未入力チェックを実施
		if(FLG.equals("1") || FLG.equals("2") || FLG.equals("3")) {
			if(FLG.equals("2")) {
				STATUS1 = request.getParameter("status1");
			}else {
				AUTH_NAME=request.getParameter("AUTH_NAME");

				//AUTH_NAMEに何も入力されていない時のエラーメッセージ
				if(AUTH_NAME.getBytes().length == 0) {
					errmsg= errmsg + ERR_MESSAGE + "<BR>";
				}
			}
			if(errmsg.getBytes().length == 0) {
				//更新日
				UPDATE_DATE=new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new java.util.Date());
				UPDATE_USER_ID="1";

				if(FLG.equals("3")){
					ID=request.getParameter("ID");
					STATUS=request.getParameter("STATUS");

					//DB取得用のクエリを作成しUpdQueryへ設定している
					Query="UPDATE mst_auth SET AUTH_NAME= ?  ,UPDATE_DATE= ? "+
							",UPDATE_USER_ID= ? WHERE ID = ? ";

				}else if(FLG.equals("2")) {
					ID=request.getParameter("ID");
					STATUS=STATUS1;

					//DB取得用のクエリを作成しUpdQueryへ設定している
					Query="UPDATE mst_auth SET STATUS= ?  ,UPDATE_DATE= ? "+
							",UPDATE_USER_ID= ? WHERE ID = ? ";

				}else {
					STATUS="1";

					//登録日
					REGIST_DATE=new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(new java.util.Date());


					//DB登録用のクエリを作成し（INSERT文）InsQueryへ設定している
					Query="INSERT INTO mst_auth (AUTH_NAME,STATUS,REGIST_DATE,UPDATE_DATE,UPDATE_USER_ID)VALUES"
							+ "('"+ AUTH_NAME +"','"+ STATUS + "','"+ REGIST_DATE + "','"+ UPDATE_DATE + "','"+ UPDATE_USER_ID + "')";

				}
				ResultSet rs=(ResultSet)request.getAttribute("Result");




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

					PreparedStatement ps = conn .prepareStatement(Query)){
					if(FLG.equals("3")){
			    		ps.setString(1,AUTH_NAME);
			    		ps.setString(2,UPDATE_DATE);
			    		ps.setString(3,UPDATE_USER_ID);
			    		ps.setString(4,ID);
					}
					if(FLG.equals("2")){
			    		ps.setString(1,STATUS);
			    		ps.setString(2,UPDATE_DATE);
			    		ps.setString(3,UPDATE_USER_ID);
			    		ps.setString(4,ID);
					}

					//DBに変更をかけている
				    int i = ps.executeUpdate();

				} catch (SQLException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}

			}
		}



		//DB接続用変数
		Connection connect=null;
		//DB接続用変数
		PreparedStatement ps=null;
		//DB接続用変数
		ResultSet rs=null;

		//表取得用クエリ
		String SelectQuery="SELECT ID,AUTH_NAME,STATUS,UPDATE_USER_ID,REGIST_DATE,UPDATE_DATE FROM mst_auth";
		//取得対象全件数を取得するクエリ
		String CntQuery="SELECT COUNT(*) count FROM mst_auth";

		//pageがnullの場合はnowPageに初期値1を設定、null以外の場合はnowPageにリクエスト("page")を設定
		if(request.getParameter("page")==null) {
			nowPage="1";
		}else {
			nowPage=request.getParameter("page");
		}
		//limitStaに(nowPage-1)*10の値を設定
		int limitSta=(Integer.parseInt(nowPage) -1) * 5;

		String sLimit = " limit " + limitSta + ", 5";

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
	    try {
			connect = DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	    //引数のSQLを設定したものがps（変数）に入ってる、sqlの実行準備ができた
	    try {
			ps= connect.prepareStatement(CntQuery);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	    //excuteQuery(クエリを実行してくれる)が実行したものがrsに入る、rsにはSQL文の結果が入ってる
	    try {
			rs= ps.executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	  //listCntにフィールド(count)の値を取得して、返却している
	    try {
	    	rs.next();
			listCnt= rs.getInt("count");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}



	    //SelectQueryの準備をしている、引数のSQLを設定したものがps（変数）に入ってる、sqlの実行準備ができた
	    try {
			ps= connect.prepareStatement(SelectQuery + sLimit);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	    //rs（変数）にSelectQueryのSQLの実行結果を代入
	    try {
			rs= ps.executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	    //文字コードの設定
	  	request.setCharacterEncoding("UTF-8");


	    //Auth.jspに値を渡している
		request.setAttribute("Result",rs);
		request.setAttribute("errmsg",errmsg);
		request.setAttribute("listCnt",listCnt);
		request.setAttribute("page",nowPage);


		//画面遷移
		getServletContext().getRequestDispatcher("/Auth.jsp").forward(request,response);



	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
