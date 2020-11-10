<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${logStatus==null || logStatus!='Y' }">
	<script>
		alert("로그인하세요");
		location.href="/myapp/login";
	</script>
</c:if>
<div id="uploadform1">
	<h1>자료실 글올리기 - 방법2(파일네임똑같이설정)</h1>
	<form method="post" action="/myapp/fileUpload2Ok" enctype="multipart/form-data">
		제목 : <input type="text" name="title" style="width:80%"/><br/>
		글내용 : <textarea name="content" style="width:80%; height:200px;"></textarea><br/>
		첨부파일1 : <input type="file" name="filename"/><br/> <!-- 파일 네임 똑같이 했다. -->
		첨부파일2 : <input type="file" name="filename"/><br/>
		<input type="submit" value="업로드하기"/>
	</form>
</div>