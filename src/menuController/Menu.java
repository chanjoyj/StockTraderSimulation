package menuController;

public abstract class Menu {
	private Menu parentMenu;

	public void setParentMenu(Menu m) {
		parentMenu = m;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public abstract void printMenu();

	public abstract int getSelection();

	public abstract NavigationData performAction(int optionIndex);
}
