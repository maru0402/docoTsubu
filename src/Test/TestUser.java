package Test;

import model.User;

public class TestUser {

	public static  void main(String[] args) {
	
		User user = new User("ユーザー１", "1234");
		
		if(!"ユーザー１".equals(user.getName())) {
			System.out.println("エラー!User.javaに不具合の可能性");
		}
		
		

	}

}
