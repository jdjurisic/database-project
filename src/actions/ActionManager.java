package actions;



public class ActionManager {

	private AddAction addAction;
	private UpdateAction updateAction;
	private DeleteAction deleteAction;


	public ActionManager() {
		addAction = new AddAction();
		updateAction = new UpdateAction();
		deleteAction = new DeleteAction();




	}

	public AddAction getAddAction() {
		return addAction;
	}

	public void setAddAction(AddAction addAction) {
		this.addAction = addAction;
	}

	public UpdateAction getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(UpdateAction updateAction) {
		this.updateAction = updateAction;
	}

	public DeleteAction getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(DeleteAction deleteAction) {
		this.deleteAction = deleteAction;
	}
}