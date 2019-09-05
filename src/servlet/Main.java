package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.GetMutterListLogic;
import model.Mutter;
import model.PosMutterLogic;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//つぶやきリストを取得して、リクエストスコープに保存
		GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
		List<Mutter> mutterList = null;
		try {
			mutterList = getMutterListLogic.execute();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		request.setAttribute("mutterList", mutterList);


		//ログインしているか確認するためセッションスコープからユーザー情報を取得
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");


		if(loginUser == null) {//ログインしていない場合
			//リダイレクト
			response.sendRedirect("/docoTsubu/");
		}else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);
		}
	}

			protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			//リクエストパラメータの取得
				request.setCharacterEncoding("UTF-8");
				String text = request.getParameter("text");
			//入力値をチェック
				if (text != null && text.length() != 0) {
					//セッション スコープに保存されたユーザー情報を取得
					HttpSession session = request.getSession();
					User loginUser = (User) session.getAttribute("loginUser");

			//つぶやきをつぶやきリストに追加
					Mutter mutter = new Mutter(loginUser.getName(), text);
					PosMutterLogic postMutterLogic = new PosMutterLogic();
					postMutterLogic.execute(mutter);
				}else {
					//エラーメッセージをリクエストスコープに保存
					request.setAttribute("errorMsg", "つぶやきが入力されていません");

				}
				//つぶやきリストを取得して、リクエストスコープに保存
				GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
				List<Mutter> mutterList = null;
				try {
					mutterList = getMutterListLogic.execute();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
				request.setAttribute("mutterList", mutterList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/main.jsp");
			dispatcher.forward(request, response);

			}
	}

