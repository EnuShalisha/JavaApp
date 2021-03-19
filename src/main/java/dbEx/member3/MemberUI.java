package dbEx.member3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class MemberUI {
	private MemberDAO dao = new MemberDAOImpl();
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void insert() {
		System.out.println("\nȸ������...");

		try {
			MemberDTO dto = new MemberDTO();

			System.out.print("���̵� ? ");
			dto.setId(br.readLine());

			System.out.print("�н����� ? ");
			dto.setPwd(br.readLine());

			System.out.print("�̸� ? ");
			dto.setName(br.readLine());

			System.out.print("������� ? ");
			dto.setBirth(br.readLine());

			System.out.print("�̸��� ? ");
			dto.setEmail(br.readLine());

			System.out.print("��ȭ��ȣ ? ");
			dto.setTel(br.readLine());

			int result = dao.insertMember(dto);
			if (result >= 1) {
				System.out.println("ȸ�� ��� ����...\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("ȸ�� ��� ����...\n");
		}

	}

	public void update() {
		System.out.println("\nȸ����������...");

		try {
			MemberDTO dto = new MemberDTO();

			System.out.print("������ ���̵� ? ");
			dto.setId(br.readLine());

			System.out.print("�н����� ? ");
			dto.setPwd(br.readLine());

			System.out.print("������� ? ");
			dto.setBirth(br.readLine());

			System.out.print("�̸��� ? ");
			dto.setEmail(br.readLine());

			System.out.print("��ȭ��ȣ ? ");
			dto.setTel(br.readLine());

			int result = dao.updateMember(dto);

			if (result >= 1) {
				System.out.println("ȸ������ ���� ����...\n");
			} else {
				System.out.println("��ϵ� �ڷᰡ �ƴմϴ�.\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("ȸ������ ���� ����...\n");
		}
	}

	public void delete() {
		System.out.println("\nȸ��Ż��...");

		try {
			String id;
			System.out.print("Ż���� ���̵� ? ");
			id = br.readLine();

			int result = dao.deleteMember(id);

			if (result >= 1) {
				System.out.println("ȸ��Ż�� ����...\n");
			} else {
				System.out.println("��ϵ� ���̵� �ƴմϴ�...\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("ȸ�� Ż�� ����...\n");
		}
	}

	public void findById() {
		System.out.println("\n���̵� �˻�...");

		try {
			String id;
			System.out.print("�˻��� ���̵� ? ");
			id = br.readLine();

			MemberDTO dto = dao.readMember(id);
			if (dto == null) {
				System.out.println("��ϵ� ������ �ƴմϴ�.\n");
				return;
			}

			System.out.print(dto.getId() + "\t");
			System.out.print(dto.getName() + "\t");
			System.out.print(dto.getPwd() + "\t");
			System.out.print(dto.getBirth() + "\t");
			System.out.print(dto.getEmail() + "\t");
			System.out.print(dto.getTel() + "\n");

			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findByName() {
		System.out.println("\n�̸� �˻�...");

		try {
			String name;
			System.out.print("�˻��� �̸� ? ");
			name = br.readLine();

			List<MemberDTO> list = dao.listMember(name);
			for (MemberDTO dto : list) {
				System.out.print(dto.getId() + "\t");
				System.out.print(dto.getName() + "\t");
				System.out.print(dto.getPwd() + "\t");
				System.out.print(dto.getBirth() + "\t");
				System.out.print(dto.getEmail() + "\t");
				System.out.print(dto.getTel() + "\n");
			}
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void list() {
		System.out.println("\n��ü ����Ʈ...");

		List<MemberDTO> list = dao.listMember();
		for (MemberDTO dto : list) {
			System.out.print(dto.getId() + "\t");
			System.out.print(dto.getName() + "\t");
			System.out.print(dto.getPwd() + "\t");
			System.out.print(dto.getBirth() + "\t");
			System.out.print(dto.getEmail() + "\t");
			System.out.print(dto.getTel() + "\n");
		}
		System.out.println();
	}
}
