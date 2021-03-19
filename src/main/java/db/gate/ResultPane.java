package db.gate;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableColumn;

public class ResultPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	private ResultQueryPanel resultQueryPanel;
	private ResultMessagePanel resultMsgPanel;
	
	public ResultPane() {
		resultQueryPanel = new ResultQueryPanel();
		resultMsgPanel = new ResultMessagePanel();
		addTab("�� ���� ���", resultQueryPanel);
		addTab("�޽���", resultMsgPanel);
	}
	
	public void clearResult() {
		resultQueryPanel.clear();
		resultMsgPanel.clear();
		this.repaint();
	}
	
	public void resultQuery(String columnNames[], List<String[]> list) {
		resultQueryPanel.resultView(columnNames, list);
	}
	
	public void resultQuery(String columnNames[], String[] items) {
		resultQueryPanel.resultView(columnNames, items);
	}
	
	public void message(String msg) {
		resultMsgPanel.setMessage(msg);
	}

	// SELECT ���� ��� ��� Panel
	class ResultQueryPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private JTable table;
		private JScrollPane sp;
		
		public ResultQueryPanel() {
			setLayout(new BorderLayout());
		}
		
		public void clear() {
			if (sp != null) {
				remove(sp);
				sp=null;
				table = null;
			}
		}
		
		public void resultView(String columnNames[], List<String[]> list) {
			addTable(columnNames);
			
			for(String[] items: list) {
				tableInsertRow(items);
			}
		}

		public void resultView(String columnNames[], String[] items) {
			addTable(columnNames);
			
			tableInsertRow(items);
		}
		
		private void addTable(String columnNames[]) {
			clear();

			MyTableModel model = new MyTableModel(columnNames);
			table = new JTable(model);

			// JTable�� ��ũ�ѹٸ� �߰��ϱ� ���� JScrollPane�� �߰�
			sp = new JScrollPane(table);

			// JTable�� �߰�
			add(sp);

			// JTable ���� �ڵ����� �������� ���ϵ��� ����(�⺻ �ڵ�����)
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			// �÷��� ����
			for (int i = 0; i < columnNames.length; i++) {
				TableColumn tc = table.getColumnModel().getColumn(i);
				tc.setPreferredWidth(100);
			}
		}
		
		private void tableInsertRow(String[] items) {
			// ���̺� �ڷ� �߰�
			((MyTableModel)table.getModel()).addRow(items);
		}
	} // end_class ResultQueryPanel
	
	// �޽��� ��� Panel
	class ResultMessagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private JTextPane tp;
		
		public ResultMessagePanel() {
			setLayout(new BorderLayout());
			
			tp=new JTextPane();
			tp.setEditable(false);
			JScrollPane sp=new JScrollPane(tp);
			add(sp);
		}
		
		public void setMessage(String msg) {
			tp.setText(msg);
		}
		
		public void clear() {
			tp.setText("");
		}
		
	} // end_class ResultMsgPanel
} // end_class ResultPane
