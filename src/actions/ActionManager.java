package actions;

public class ActionManager {

	private GenerateAction generateAction;
	private ExecuteAction executeAction;
	private AddAction addAction;
	private DeleteAction deleteAction;
	private UpdateAction updateAction;
	private SortAction sortAction;
	private CountAction countAction;
	private AverageAction averageAction;
	private RefreshAction refreshAction;
	private SearchAction searchAction;

	public ActionManager() {
		initialization();
	}

	public void initialization() {
		// TODO Auto-generated method stub
		generateAction = new GenerateAction();
		executeAction = new ExecuteAction();
		addAction = new AddAction();
		deleteAction = new DeleteAction();
		updateAction = new UpdateAction();
		sortAction = new SortAction();
		countAction = new CountAction();
		averageAction = new AverageAction();
		refreshAction = new RefreshAction();
		searchAction = new SearchAction();
	}

	public GenerateAction getGenerateAction() {
		return generateAction;
	}

	public ExecuteAction getExecuteAction() {
		return executeAction;
	}

	public AddAction getAddAction() {
		return addAction;
	}
	public DeleteAction getDeleteAction() {
		return deleteAction;
	}
	public RefreshAction getRefreshAction() {
		return refreshAction;
	}
	public UpdateAction getUpdateAction() {
		return updateAction;
	}
	public SortAction getSortAction() {
		return sortAction;
	}
	public CountAction getCountAction() {
		return countAction;
	}
	public AverageAction getAverageAction() {
		return averageAction;
	}
	public SearchAction getSearchAction() {return searchAction; }
}
