package db.gate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

public class Worksheet extends JTabbedPane {
	private static final long serialVersionUID = 1L;

	private MyGateFrame frame;
	private ResultPane resultPane;
	private WorksheetPanel worksheetPanel;

	public Worksheet(JFrame frame, ResultPane resultPane) {
		this.frame = (MyGateFrame) frame;
		this.resultPane = resultPane;
		
		worksheetPanel = new WorksheetPanel();
		addTab("��ũ��Ʈ", worksheetPanel);
	}
	
	// ���� �Է� ���� �����
	public void clearQuery() {
		worksheetPanel.clear();
	}

	// ���� �Է� ȭ��
	class WorksheetPanel extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		private JButton btnResult;
		private JButton btnClear;
		private JTextPane textQuery;

		public WorksheetPanel() {
			setLayout(new BorderLayout());

			JToolBar tb = new JToolBar();
			tb.setFloatable(false); // ���� ����
			tb.addSeparator();
			
			btnResult = new JButton(" �� ");
			btnResult.setToolTipText("����");
			btnResult.addActionListener(this);
			btnResult.setBackground(Color.WHITE);
			btnResult.setOpaque(true);
			tb.add(btnResult);

			btnClear = new JButton("  C  ");
			btnClear.setToolTipText("Clear");
			btnClear.addActionListener(this);
			btnClear.setBackground(Color.WHITE);
			btnClear.setOpaque(true);
			tb.add(btnClear);

			add(tb, BorderLayout.NORTH);

			textQuery = new JTextPane();
			textQuery.setFont(new Font("Consolas", Font.PLAIN, 20));
			textQuery.addKeyListener(new KeyHandler());
			add(textQuery);
		}
		
		public void clear() {
			textQuery.setText("");
			textQuery.requestFocus();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnResult) {
				execute();
			} else if (e.getSource() == btnClear) {
				int result;
				result=JOptionPane.showConfirmDialog(this,
						"�Է��� ������ ���� �Ͻðڽ��ϱ� ?",
						"Ȯ��",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if(result==JOptionPane.YES_OPTION) {
					clear();
					resultPane.clearResult();
				}
				
			}
		}
		
		// ���� ����
		public void execute() {
			String query=getQuery();
			
			MyGateDAO dao = frame.getMyGateDAO();
			if(dao==null) {
				JOptionPane.showMessageDialog(this, "�����ͺ��̽� ������ ���� �����ؾ� �մϴ�.",
						"����", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			query=query.trim();
			if(query.length()==0) return;

			if(query.lastIndexOf(";")!=-1)
				query=query.substring(0, query.lastIndexOf(";"));
			
			if(query.toLowerCase().startsWith("select"))
				executeQuery(query);
			else if(query.toLowerCase().startsWith("desc"))
				executeMeta(query);
			else
				executeUpdate(query);
		}
		
		// ����â�� �Է��� ����
		private String getQuery() {
			String query = textQuery.getText().trim();
			if(query.length()==0)
				return "";
			
			if(textQuery.getSelectedText()!=null) {
				String s=textQuery.getSelectedText().trim();
				if(s.length()!=0)
					query=s;
			}
			return query;
		}

		// SELECT �� ����
		private void executeQuery(String sql) {
			MyGateDAO dao = frame.getMyGateDAO();
			if(dao==null) {
				return;
			}
			
			List<String[]> list = dao.getResultList(sql);

			if (list == null) {
				resultPane.requestFocus();
				resultPane.setSelectedIndex(1);
				resultPane.message(dao.getMessage());
				return;
			}

			String columnNames[] = dao.getColumnNames();

			// ��� ���
			resultPane.requestFocus(); // ����� table ������ ����ϱ� ����
			resultPane.setSelectedIndex(0);
			resultPane.resultQuery(columnNames, list);
			
			textQuery.requestFocus();
		}
		
		// INSERT, UPDATE, DELETE, CREATE �� ����
		private void executeUpdate(String sql) {
			MyGateDAO dao = frame.getMyGateDAO();
			if(dao==null) {
				return;
			}
			
			dao.execute(sql);
			
			// ���� ��� �޽��� ���
			resultPane.requestFocus();
			resultPane.setSelectedIndex(1);
			resultPane.message(dao.getMessage());
			
			textQuery.requestFocus();
		}
		
		// DESC ��� ����
		private void executeMeta(String sql) {
			MyGateDAO dao = frame.getMyGateDAO();
			if(dao==null) {
				return;
			}
			
			String str[] = sql.split("\\s");
			if(str.length != 2)  {
				JOptionPane.showMessageDialog(this, "���� ���� !!!", "����",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			// cols �Ǵ� user_tab_columns �ڷ������ �̿��Ͽ� ������ ���� �� �ִ�.
			sql = "SELECT * FROM " + str[1];
			List<String[]> list = dao.getDescList(sql);

			if (list == null) {
				JOptionPane.showMessageDialog(this, dao.getMessage(), "����",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			String columnNames[] = {"�÷���", "��", "����"};
			
			// ��� ���
			resultPane.requestFocus();
			resultPane.setSelectedIndex(0);
			resultPane.resultQuery(columnNames, list);
			
			textQuery.requestFocus();
		}
		
		class KeyHandler extends KeyAdapter {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if(e.isControlDown() && keyCode == KeyEvent.VK_ENTER) {
					if(e.getSource()==textQuery) {
						execute();
					}
				}
			}
		} // end_class KeyHandler
	} // end_class WorksheetPanel
} // end_class Worksheet