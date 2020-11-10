<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h1>
	Hello world!  
</h1>

<P>
<pre>
	1.한글인코딩<br/>
	2. pom.xml<br/>
	3. ojdbc6.jar<br/>
	4. servlet-context.xml<br/>
	5. Constants 클래스 생성<br/>
	6. HomeController에 template객체 생성<br/>


	파일업로드 프레임워크
	- commons-fileupload, commons-io를 pom.xml에 Dependency 한다.
	
	파일업로드를 하기 위해서는 파일업로드 객체를 만들어야 한다.	
	- root-context.xml에 만들어야 좋다. multipartRequest하는 개념이다.
	- root-context.xml에 MultipartResolve객체를 생성하고
	- web.xml에 있는 DispacherServlet에 root-context.xml의 경로를 추가한다.
</pre>
</P>
