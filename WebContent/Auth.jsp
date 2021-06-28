<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" import="java.sql.ResultSet"%>

<html>
<head>
	<meta http-equiv="Content-Language" content="ja" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <title>権限管理</title>
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link rel="stylesheet" type="text/css" href="common.css" />


<link rel="stylesheet" type="text/css" href="/css/admin/auth.css" />

</head>
	<body>



<%
	ResultSet rs=(ResultSet)request.getAttribute("Result");
	String ID=(String)request.getAttribute("ID");
	String AUTH_NAME=(String)request.getAttribute("AUTH_NAME");
	String UPDATE_USER_ID=(String)request.getAttribute("UPDATE_USER_ID");
	String STATUS=(String)request.getAttribute("STATUS");
	String REGIST_DATE=(String)request.getAttribute("REGIST_DATE");
	String UPDATE_DATE=(String)request.getAttribute("UPDATE_DATE");
	String errmsg=(String)request.getAttribute("errmsg");
	String rtnFlg=(String)request.getAttribute("rtnFlg");
	int listCnt=0;
	String nowPage="";
	int maxPage=0;
	
	
	

%>

<%!
	String SearchNamePage="";	

%>


<div class="logo"></div>
		<div id="wrapper">
			<div id="container">
<div class="clear"></div>
<div id='left_content'>
    <a href='/admin/index'>TOP</a><a href='/admin/login/logout'>ログアウト</a>
    <br /><br />
    <div class='menu_group'>■業務関連</div>
            <div class='menu_item'>
                    <a href='/admin/case'>案件管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/estimate'>見積管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/orders'>受注管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/arrangement'>発注管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/claim'>請求管理</a>
                </div>
        <div class='menu_group'>■物件関連</div>
            <div class='menu_item'>
                    <a href='/admin/floorplanname'>物件間取り名称管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/floor'>物件管理</a>
                </div>
        <div class='menu_group'>■工程表</div>
            <div class='menu_item'>
                    <a href='/admin/processsheet'>月別工程表出力</a>
                </div>
        <div class='menu_group'>■工事区分関連</div>
            <div class='menu_item'>
                    <a href='/admin/costgroup'>工事区分分類管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/cost'>工事区分管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/maker'>資材メーカー管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/trader'>業者・仕入先管理</a>
                </div>
        <div class='menu_group'>■チェックリスト関連</div>
            <div class='menu_item'>
                    <a href='/admin/check'>チェック項目管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/checkgroupadd'>チェック項目グループ登録・変更</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/checkcostcollabo'>チェック項目工事区分管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/checkgroup'>チェック項目グループ管理</a>
                </div>
        <div class='menu_group'>■マスタ関連</div>
            <div class='menu_item'>
                    <a href='/admin/user'>ユーザ管理</a>
                </div>
            <div class='menu_item'>
                    権限管理
                </div>
            <div class='menu_item'>
                    <a href='/admin/client'>顧客管理</a>
                </div>
            <div class='menu_item'>
                    <a href='/admin/owner'>オーナー管理</a>
                </div>
    </div><!-- end left_content -->

                <!-- Start Right content -->
				<div id="right_content">
					<div id="condition" style="width:1200px;">
                            <div class="left_condition">
                                <div class="condition_item">
                                    <div class="regist_area">
                                        <b>■権限登録</b><br />
                                        <form  method="GET"  action="./Auth">
                                        <input tabindex="1" class="regist_text" type="text" id="AUTH_NAME" name="AUTH_NAME" value="" style="ime-mode:active"  maxlength="10"/>(全角10文字以内)&nbsp;&nbsp;
                                        <input type="hidden" name="FLG" value="1">
                                         <input tabindex="2" class="regist_btn" type="submit" value="新規登録" onClick="alert('新規登録完了しました');">
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="errmsg"><%if(errmsg!=null){ %>
								<%= errmsg%>
								<%} %></div>
<%! String createPagenation(int maxPage, String nowPage) {
	   //ページネーション部のHTML格納用変数
    String pagenation = "<ul>";
    //現在表示中のページ (引数として受け取ったnowPageをint型に変換)
    int currentPage = Integer.parseInt(nowPage);
    //表示させたいページリンク数の最大数
	 int PAGE_RANGE = 10;
   //現在表示中のページの前後に表示したいページリンク数
   int PAGE_GAP = 3;
   //現在表示中のページを基準にしたページリンクの開始ページ
   int currentStartPage;
   //現在表示中のページを基準にしたページリンクの終了ページ
   int currentEndPage;
   //currentStartPageとcurrentEndPageを決定する。
   if(PAGE_RANGE < maxPage) {
     //最大ページ数がPAGE_RANGEより大きい場合
     currentStartPage = currentPage - PAGE_GAP;
     currentEndPage = currentPage + PAGE_GAP;
     //開始ページが１より小さい時、開始ページに１を代入し、終了ページに開始ページ＋ページリンクの最大数から１引いたものを代入する
     if(currentStartPage < 1) {
       currentStartPage = 1;
       currentEndPage = currentStartPage + (PAGE_RANGE - 1);
     }
     //終了ページが最大ページより大きい時、終了ページと最大ページを同じにし、開始ページに終了ページ-ページリンクの最大数から１引いたものを代入する
     if(currentEndPage > maxPage) {
       currentEndPage = maxPage;
       currentStartPage = currentEndPage - (PAGE_RANGE - 1);
     }
   } else {
     //最大ページ数がページリンクの最大数以下の場合
     currentStartPage = 1;
     currentEndPage = maxPage;
   }
%>
<%!
    //最初のページへのリンク「<<」と 現在表示ページの1つ前のページへのリンク「<」を作成
    //現在表示中のページが１の時
    if(currentPage == 1) {
      pagenation += "<li><<</li>";
      pagenation += "<li><</li>";
      //そうじゃない時
    } else {
      pagenation += "<li><a href=\"./ListBL?page=1\"><<</a></li>";
      pagenation += "<li><a href=\"./ListBL?page=" + (currentPage - 1) + SearchNamePage + "\"><</a></li>";
    }
    //ページ数リンクの作成
    for(int i = currentStartPage; i <= currentEndPage; i++) {
      if(currentPage == i) {
        pagenation += "<li class=\"current-page\">" + i + "</li>";
      } else {
        pagenation += "<li><a href=\"./ListBL?page=" + i + SearchNamePage + "\">" + i + "</a></li>";
      }
    }
    //現在表示ページの1つ先のページへのリンク「>」と 最後のページへのリンク「>>」を作成
    if(currentPage == maxPage) {
      pagenation += "<li>></li>";
      pagenation +=	"<li>>></li>";
    } else {
      pagenation += "<li><a href=\"./ListBL?page=" + (currentPage + 1) + SearchNamePage + "\">></a></li>";
      pagenation += "<li><a href=\"./ListBL?page=" + maxPage + SearchNamePage +"\">>></a></li>";
    }
    pagenation += "</ul>";
    return pagenation;
}
%>
<%
	request.setCharacterEncoding("UTF-8");
	//nowPageにリクエスト("Page")を設定、Stringに変換
	nowPage = request.getAttribute("page").toString();
	//listCntにリクエスト("listCnt")を設定、intに直してからStringに変換
	listCnt = Integer.parseInt(request.getAttribute("listCnt").toString());
	maxPage = listCnt / 10;
	 if(maxPage == 0) {
		  maxPage = 1;
	  } else {
		  if(!(listCnt % 10 == 0)) maxPage += 1 ;
	  }
	//rsにリクエスト//("Result")を設定
	rs=(ResultSet)request.getAttribute("Result");
%>

    						<div class="clear"></div>
    						<br />
                            <table class="rounded-corner">
                                <thead>
                                    <tr>
                                        <th scope="col">ステータス</th>
                                        <th scope="col">
                                            権限
                                        </th>
                                        <th scope="col">
                                            登録日<br />
                                            更新日
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                <%//繰り返し処理%>
								<% while(rs.next()){ %>
                                        <td class="status_area">
                                		<form method="get" action="./Auth">
                                            <select id="status1" name="status1">
                                                <option value="1"  <%if(rs.getString("STATUS").equals("1")){ %>selected<%} %>>有効</option>
                                                <option value="2" <%if(rs.getString("STATUS").equals("2")){ %>selected<%} %>>無効</option>

                                            </select>
                                            <input type="hidden" name="ID" value="<%= rs.getString("ID")%>">
	                                        <input type="hidden" name="FLG" value="2">
                                            <br/><input type="submit" value="実行"  formaction="./Auth"/>
										</form>
    									</td>
                                        <td class="conf_area">
                                		<form method="get" action="./Auth">
                                            <input class="conf_text" type="text" id="AUTH_NAME" name="AUTH_NAME" value="<%= rs.getString("AUTH_NAME")%>" style="ime-mode:active"  maxlength="10"/>
                                            <input type="hidden" id="ID" name="ID" value="<%= rs.getString("ID")%>">
                                           	<input type="hidden" id="STATUS" name="STATUS" value="<%= rs.getString("STATUS")%>">
                                            <input type="hidden" id="UPDATE_USER_ID" name="UPDATE_USER_ID" value="<%= rs.getString("UPDATE_USER_ID")%>">
                                            <input type="hidden" id="REGIST_DATE" name="REGIST_DATE" value="<%= request.getAttribute("REGIST_DATE")%>">
                                        	<input type="hidden" id="UPDATE_DATE" name="UPDATE_DATE" value="<%= request.getAttribute("UPDATE_DATE")%>">
	                                        <input type="hidden" name="FLG" value="3">
                                            <input type="submit" value="変更"  onClick="alert('変更完了しました');">
										</form>
                                        </td>
                                        <td class="date_area">
                                           <%= rs.getString("REGIST_DATE")%><input class="" type="hidden" id="REGIST_DATE" name="REGIST_DATE" style="ime-mode:active"  maxlength="10"/><br />
                                           <%= rs.getString("UPDATE_DATE")%><input class="" type="hidden" id="UPDATE_DATE" name="UPDATE_DATE" style="ime-mode:active"  maxlength="10"/><br />
                                        </td>
                                    </tr>
                                    <% }%>
                                </tbody>
        					</table>
        					<div class="pagenation"><%= createPagenation(maxPage, nowPage) %></div>
            				<br/>
                            <div class="clear"></div>
                    </div>
                </div><!-- End Right content -->
            </div>
        </div>
	</body>
</html>