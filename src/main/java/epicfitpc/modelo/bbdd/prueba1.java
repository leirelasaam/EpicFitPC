package epicfitpc.modelo.bbdd;

import db.management.UserManager;

public class prueba1 {

	public boolean checkBlockedAcount(String login) throws Exception {
		UserManager userManager = new UserManager();
		boolean ret = false;
		ret = userManager.getAcountIsBlocked(login);
		return ret;
	}

	private boolean checkLogIn(String login) throws Exception {
		UserManager userManager = new UserManager();
		boolean ret = false;
		if (login.equalsIgnoreCase(userManager.getLogin(login)))
			ret = true;
		return ret;
	}

	private boolean checkPassword(String login, String password) throws Exception {
		UserManager userManager = new UserManager();
		boolean ret = false;
		if (password.equals(userManager.getPassword(login)))
			ret = true;
		return ret;
	}

	public void changeUserToBlock(String login) throws Exception {
		UserManager userManager = new UserManager();
		userManager.blockUser(login);
	}
	
	public void changeLastConection(String login) throws Exception {
		UserManager userManager = new UserManager();
		userManager.updateLastConection(login);
	}

	public boolean logInPasswordCorrect(String login, String password) throws Exception {
		boolean ret = false;
		if (checkLogIn(login) && checkPassword(login, password)) 
			ret = true;
		return ret;
	}

}
LoginPanelController.java
Mostrando LoginPanelController.java.