package jp.co.internous.react.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.react.model.domain.MstUser;
import jp.co.internous.react.model.form.UserForm;
import jp.co.internous.react.model.mapper.MstUserMapper;
import jp.co.internous.react.model.mapper.TblCartMapper;
import jp.co.internous.react.model.session.LoginSession;

@Controller
@RequestMapping("/react/auth")
public class AuthController {
	
	@Autowired
	MstUserMapper mstUserMapper;
	
	@Autowired
	TblCartMapper tblCartMapper;
	
	@Autowired
	LoginSession loginSession;
	
	Gson gson = new Gson();
	
	//	ログイン処理
	@ResponseBody
	@PostMapping("/login")
	public String login(@RequestBody UserForm userForm) {

		// userNameとpasswordでユーザー情報をDBから取得
		MstUser mstUser = mstUserMapper.findByUserNameAndPassword(userForm.getUserName(), userForm.getPassword());
		
		// userNameとpasswordが正しいかif文で場合分けして、処理を分ける
		if(mstUser != null ) {	
						
			//	カート情報の紐づけを仮ユーザーIdから本ユーザーIdに変更する
			tblCartMapper.updateUserId(loginSession.getTmpUserId(), mstUser.getId());																
			
			//	loginSessionの内容を変更(メソッドはLoginSession.javaで定義)
			loginSession.setLoginSession(mstUser.getId(), 0, mstUser.getUserName(), mstUser.getPassword(), true);			
			return gson.toJson(loginSession.getUserName());	
			
		//	userNameもしくはpasswordが間違っている場合
		} else {
			return gson.toJson(null);
		}																		

	}

	//	ログアウト処理
	@ResponseBody
	@PostMapping("/logout")
	public String logout() {
		
		//	loginSessionの内容を変更
		loginSession.setLoginSession(0, 0, null, null, false);
		return "1";	
	}

	
	//	パスワード再設定ダイアログ
	@ResponseBody
	@RequestMapping("/resetPassword")
	public String resetPassword(@RequestBody UserForm f) {
		String message = "パスワードが再設定されました。";
		String newPassword = f.getNewPassword();
		String newPasswordConfirm = f.getNewPasswordConfirm();
		
		MstUser user = mstUserMapper.findByUserNameAndPassword(f.getUserName(), f.getPassword());
		if (user == null) {
			return "現在のパスワードが正しくありません。";
		}
		
		if (user.getPassword().equals(newPassword)) {
			return "現在のパスワードと同一文字列が入力されました。";
		}
		
		if (!newPassword.equals(newPasswordConfirm)) {
			return "新パスワードと確認用パスワードが一致しません。";
		}
		// mst_userとloginSessionのパスワードを更新する
		mstUserMapper.updatePassword(user.getUserName(), f.getNewPassword());
		loginSession.setPassword(f.getNewPassword());
		
		
		return message;
	}

}
