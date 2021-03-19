package dbEx.member3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class MemberUI {
	private MemberDAO dao = new MemberDAOImpl();
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void insert() {
		System.out.println("\n회원가입...");

		try {
			MemberDTO dto = new MemberDTO();

			System.out.print("아이디 ? ");
			dto.setId(br.readLine());

			System.out.print("패스워드 ? ");
			dto.setPwd(br.readLine());

			System.out.print("이름 ? ");
			dto.setName(br.readLine());

			System.out.print("생년월일 ? ");
			dto.setBirth(br.readLine());

			System.out.print("이메일 ? ");
			dto.setEmail(br.readLine());

			System.out.print("전화번호 ? ");
			dto.setTel(br.readLine());

			int result = dao.insertMember(dto);
			if (result >= 1) {
				System.out.println("회원 등록 성공...\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("회원 등록 실패...\n");
		}

	}

	public void update() {
		System.out.println("\n회원정보수정...");

		try {
			MemberDTO dto = new MemberDTO();

			System.out.print("수정할 아이디 ? ");
			dto.setId(br.readLine());

			System.out.print("패스워드 ? ");
			dto.setPwd(br.readLine());

			System.out.print("생년월일 ? ");
			dto.setBirth(br.readLine());

			System.out.print("이메일 ? ");
			dto.setEmail(br.readLine());

			System.out.print("전화번호 ? ");
			dto.setTel(br.readLine());

			int result = dao.updateMember(dto);

			if (result >= 1) {
				System.out.println("회원정보 수정 성공...\n");
			} else {
				System.out.println("등록된 자료가 아닙니다.\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("회원정보 수정 실패...\n");
		}
	}

	public void delete() {
		System.out.println("\n회원탈퇴...");

		try {
			String id;
			System.out.print("탈퇴할 아이디 ? ");
			id = br.readLine();

			int result = dao.deleteMember(id);

			if (result >= 1) {
				System.out.println("회원탈퇴 성공...\n");
			} else {
				System.out.println("등록된 아이디가 아닙니다...\n");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("회원 탈퇴 실패...\n");
		}
	}

	public void findById() {
		System.out.println("\n아이디 검색...");

		try {
			String id;
			System.out.print("검색할 아이디 ? ");
			id = br.readLine();

			MemberDTO dto = dao.readMember(id);
			if (dto == null) {
				System.out.println("등록된 정보가 아닙니다.\n");
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
		System.out.println("\n이름 검색...");

		try {
			String name;
			System.out.print("검색할 이름 ? ");
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
		System.out.println("\n전체 리스트...");

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
