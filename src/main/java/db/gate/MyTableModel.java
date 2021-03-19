package db.gate;

import javax.swing.table.DefaultTableModel;

// JTable �� Ŭ����
// DefaultTableModel : AbstractTableModel �߻�Ŭ������ ������ ���� Ŭ����
public class MyTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 2L;
	
	protected String title[]; // ���̺� ���
	
	public MyTableModel(String[] title) {
		this.title = title;
	}

	// �÷��� ����
	@Override
	public int getColumnCount() {
		if(title == null)
			return 0;
		
		return title.length;
	}

	// �÷��� ��� ����
	@Override
	public String getColumnName(int column) {
		if(title == null || title.length == 0)
			return null;
		
		return title[column];
	}
}
