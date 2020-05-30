package actions;



public class ActionManager {

	private AddAction addAction;
	private UpdateAction updateAction;
	private DeleteAction deleteAction;
	private ChangeNortTab changeNortTab;


	public ActionManager() {
		addAction = new AddAction();
		updateAction = new UpdateAction();
		deleteAction = new DeleteAction();
		changeNortTab = new ChangeNortTab();




	}

	public AddAction getAddAction() {
		return addAction;
	}

	public UpdateAction getUpdateAction() {
		return updateAction;
	}

	public DeleteAction getDeleteAction() {
		return deleteAction;
	}

	public ChangeNortTab getChangeNortTab() {
		return changeNortTab;
	}
}