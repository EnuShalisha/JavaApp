package db.member1;

import java.util.List;

public interface MemberDAO {
	public int insertMember(MemberDTO dto) throws Exception;
	public int updateMember(MemberDTO dto) throws Exception;
	public int deleteMember(String id) throws Exception;
	public MemberDTO readMember(String id);
	public List<MemberDTO> listMember();
	public List<MemberDTO> listMember(String name);
}
