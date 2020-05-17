package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {
	String charset = null;
	HashMap<String, Controller> list = null;

	private final String prefix = "/WEB-INF/view/";
	private final String postfix = ".jsp";

	@Override
	public void init(ServletConfig config) throws ServletException {
		list = new HashMap<>();
		list.put("/", new MainController());
		list.put("/input", new InputController());
		list.put("/list", new ListController());
		list.put("/rent", new RentController());
		list.put("/update", new UpdateController());

		charset = config.getInitParameter("charset"); // web.xml에 작성한 UTF-8이라는 값이 넘어오게 된다.
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청 처리 루틴이 여기 들어갑니다.
		req.setCharacterEncoding(charset);

		String url = req.getRequestURI(); // 사용자가 요청한 URI
		String contextPath = req.getContextPath();
		String path = url.substring(contextPath.length());

		Controller c = list.get(path);

		String result = "notfound";
		if (c != null) {
			result = c.execute(req, resp);
		}

		if (result.startsWith("redirect::")) {
			url = result.substring("redirect::".length()); // 이 길이부터 시작해서 잘라내 (redirect::(/asd))
			resp.sendRedirect(url);
		} else {
			RequestDispatcher dispatcher = req.getRequestDispatcher(prefix + result + postfix);
			dispatcher.forward(req, resp);
		}

	}
}
