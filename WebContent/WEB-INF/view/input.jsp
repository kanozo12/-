<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="./master/header.jsp"></jsp:include>

<div class="container">
	<h2>회원 등록</h2>

	<form action="/input" method="post" onsubmit="checkForm()" name="frm">
		<div class="form-group">
			<label>회원번호(자동발생)</label>
			<div>
				<input type="text" name="custno" value="${no }" readonly/>
			</div>
		</div>

		<div class="form-group">
			<label>회원성명</label>
			<div>
				<input type="text" name="custname" id="custname"/>
			</div>
		</div>

		<div class="form-group">
			<label>가입일자</label>
			<div>
				<input type="date" name="joindate" id="joindate"/>
			</div>
		</div>

		<div class="form-group">
			<label>회원등급</label>
			<div>
				<select name="grade" id="grade">
					<option value="A">VIP</option>
					<option value="B">일반</option>
					<option value="C">직원</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label>주소</label>
			<div>
				<input type="text" name="address" id="address"/>
			</div>
		</div>
		<input class="btn" type="submit" value="회원가입 " onclick="return checkForm()" />
	</form>
	
</div>

<script>
	function checkForm() {
		const d = document;
		if (d.frm.custname.value.trim() == "") {
			alert("회원성명을 입력하세요.");
			d.frm.custname.focus();
			return false;
		}
		if (d.frm.custname.value.length > 3) {
			alert("이름은 3글자를 넘을 수 없습니다.");
			d.frm.custname.focus();
			return false;
		}
		//이부분에 비어있는지에 대한 체크
		if (d.frm.custname.value.trim() == "") {
			alert("회원성명을 입력하세요.");
			d.frm.custname.focus();
			return false;
		}
		if (d.frm.joindate.value.trim() == "") {
			alert("날짜 선택하세요.");
			d.frm.joindate.focus();
			return false;
		}
		if (d.frm.grade.value.trim() == "") {
			alert("고객등급을 선택하세요.");
			d.frm.grade.focus();
			return false;
		}
		if (d.frm.address.value.trim() == "") {
			alert("주소를 입력하세요.");
			d.frm.address.focus();
			return false;
		}
		
		alert("성공적으로 회원가입되었습니다.");
		d.frm.submit();
		
	}
</script>

<jsp:include page="./master/footer.jsp"></jsp:include>