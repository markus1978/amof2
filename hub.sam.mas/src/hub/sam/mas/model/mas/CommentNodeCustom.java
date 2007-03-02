package hub.sam.mas.model.mas;

public class CommentNodeCustom extends CommentedNodeDlg {

	@Override
	public String getQualifiedName() {
		return self.getComment();
	}

}
