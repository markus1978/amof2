package hub.sam.mof.mas;

public class CommentNodeCustom extends CommentedNodeDlg {

	@Override
	public String getQualifiedName() {
		return self.getComment();
	}

}
