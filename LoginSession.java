package jp.co.internous.react.model.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginSession implements Serializable {
	
	private static final long serialVersionUID = 5396993491003800240L;
	
	private long userId;
	private long tmpUserId;
	private String userName;
	private String password;
	private boolean logined;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getTmpUserId() {
		return tmpUserId;
	}
	public void setTmpUserId(long tmpUserId) {
		this.tmpUserId = tmpUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLogined() {
		return logined;
	}
	public void setLogined(boolean logined) {
		this.logined = logined;
	}
	@Override
	public String toString() {
		return "LoginSession [userId=" + userId + ", tmpUserId=" + tmpUserId + ", userName=" + userName + ", password="
				+ password + ", logined=" + logined + "]";
	}
	
	//	LoginSessionに値を代入するメソッド
	public void setLoginSession(long userId, long tmpUserId, String userName, String password, boolean Logined) {
		setUserId(userId);
		setTmpUserId(tmpUserId);
		setUserName(userName);
		setPassword(password);
		setLogined(Logined);
	}
	

}
