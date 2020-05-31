package actions;



public class ActionManager {

	private AddAction addAction;
	private UpdateAction updateAction;
	private DeleteAction deleteAction;
	private ChangeNortTab changeNortTab;
	private ClickOnRow clickOnRow;
	private  FilterAndSort filterAndSort;
	private Search search;
	private  Average average;
	private Count count;

	public ActionManager() {
		addAction = new AddAction();
		updateAction = new UpdateAction();
		deleteAction = new DeleteAction();
		changeNortTab = new ChangeNortTab();
		clickOnRow = new ClickOnRow();
		 filterAndSort= new FilterAndSort();
		search = new Search();
		average = new Average();
		count =  new Count();



	}

	public ClickOnRow getClickOnRow() {
		return clickOnRow;
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

	public FilterAndSort getFilterAndSort() {
		return filterAndSort;
	}

	public Search getSearch() {
		return search;
	}

	public Average getAverage() {
		return average;
	}

	public Count getCount() {
		return count;
	}
}