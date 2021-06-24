

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddErr
 */
@WebServlet("/AddErr")
public class AddErr extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddErr() {
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

		String AUTH_NAME=request.getParameter("AUTH_NAME");
		String errmsg="";
		String ID=request.getParameter("ID");
		String STATUS=request.getParameter("STATUS");
		String REGIST_DATE=request.getParameter("REGIST_DATE");
		String UPDATE_DATE=request.getParameter("UPDATE_DATE");
		String UPDATE_USER_ID=request.getParameter("UPDATE_USER_ID");
		ResultSet rs=(ResultSet)request.getAttribute("Result");
		//DB接続用変数
		//ResultSet rs=;

		final String ERR_MESSAGE="未入力で登録することはできません";


		//AUTH_NAMEに何も入力されていない時のエラーメッセージ
		if(AUTH_NAME.getBytes().length == 0) {
			errmsg= errmsg + ERR_MESSAGE + "<BR>";
		}
		request.setAttribute("errmsg",errmsg);
		request.setAttribute("AUTH_NAME",AUTH_NAME);
		request.setAttribute("STATUS",STATUS);
		request.setAttribute("REGIST_DATE",REGIST_DATE);
		request.setAttribute("UPDATE_DATE",UPDATE_DATE);
		request.setAttribute("UPDATE_USER_ID",UPDATE_USER_ID);
		request.setAttribute("Result",rs);
		request.setAttribute("ID",ID);


		//errmsgがブランクの場合とそれ以外の遷移
		if(errmsg == "") {
			getServletContext().getRequestDispatcher("/AuthCommit").forward(request, response);
		}else{
			getServletContext().getRequestDispatcher("/Auth.jsp").forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
