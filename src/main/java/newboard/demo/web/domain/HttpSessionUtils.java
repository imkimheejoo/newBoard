package newboard.demo.web.domain;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String ACCOUNT_SESSION_KEY="loginAccount";


    //로그인 된 사용자정보를 불러옴
    public static Account getLoginAccount(HttpSession session){
        if(session.getAttribute(ACCOUNT_SESSION_KEY)==null){
            return null;
        }
        return (Account)session.getAttribute(ACCOUNT_SESSION_KEY);
    }

    //로그인 유무 확인 메소드
    public static boolean isLogin(HttpSession session){
        if(HttpSessionUtils.getLoginAccount(session)==null){
            return false;
        }
        return true;
    }

}
