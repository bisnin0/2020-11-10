<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
	$(function(){
		//ajax로 서버에서 문자열을 결과로 리턴받은경우
		$("#ajaxString").click(function(){
			var url = "/ajaxtest/ajaxString";
			var data = "no=1234&username=홍길동&userid=goguma"; //폼이 아니라서 이렇게씀. 폼이면 시리얼라이즈?
			$.ajax({
				url : url,
				data : data,
				type : 'GET',
				success:function(result){
					$("#resultData").append(result);
				},error:function(){
					console.log("문자열 받기 에러");
				}
			
			});
		});
		
		//ajax로 서버에서 객체 리턴받은 경우
		$("#ajaxObject").click(function(){
			var url="/ajaxtest/ajaxObject";
			//data 서버로 보내는건 위에서 해봐서 여기선 안했음
			$.ajax({
				url : url,
				success:function(result){
					var tag = "<ul>";
					tag += "<li>번호="+result.no+"</li>";
					tag += "<li>이름="+result.username+"</li>";
					tag += "<li>주소="+result.addr+"</li></ul>";
					$("#resultData").append(tag);
				},error:function(){
					console.log("객체 받기 에러");
				}
			});
		});
		
		//List
		$("#ajaxList").click(function(){
			var url = "/ajaxtest/ajaxList";
			var params = "no=900&username=이순신&addr=서울시 마포구"; 
			$.ajax({
				url : url,
				data : params,
				type : 'GET',
				success:function(result){
					var $result = $(result); //result 선택자 만들기 $result
					var tag ="<ol>";
					$result.each(function(idx, gVo){ //처음에 들어오면 첫번째 vo를 gVo에 담고 idx는 0.. 이렇게 반복 <--반복문
													// idx= 0,1,2,3,4  gVo에는 vo,vo,vo,vo,vo
						tag += "<li>"+gVo.no+", "+gVo.username+", "+gVo.addr+"</li>";							
					});
					tag += "</ol>"
					$("#resultData").append(tag);
				},error:function(){
					console.log("List 받기 에러");
				}
			});
		});
		
		//ajaxMap
		$("#ajaxMap").click(function(){
			var url ="/ajaxtest/ajaxMap";
			$.ajax({
				url : url,
				success:function(result){
					var tag ="<ol>";					//map.key.value
					tag += "<li>"+ result.k1.no +", "+ result.k1.username +", "+ result.k1.addr +"</li>"; //Map에서 데이터 꺼내기.
					tag += "<li>"+ result.k2.no +", "+ result.k2.username +", "+ result.k2.addr +"</li>"; //Map에서 데이터 꺼내기.
					tag += "<li>"+ result.k3.no +", "+ result.k3.username +", "+ result.k3.addr +"</li>"; //Map에서 데이터 꺼내기.
					tag += "<li>"+ result.k4.no +", "+ result.k4.username +", "+ result.k4.addr +"</li>"; //Map에서 데이터 꺼내기.
					tag += "</ol>";				
					$("#resultData").append(tag);
				},error:function(){
					console.log("Map 받기 에러");
				}
			});
		});
		
		//json  -> json 형식으로 되어있는 문자열 받아오기..
		$("#ajaxJson").click(function(){
			var url = "/ajaxtest/ajaxJson";
			$.ajax({
				url : url,
				success:function(result){
					//문자열로 되어있는 Json데이터를 파싱(parse)한다.
					var jsonData = JSON.parse(result); //문자열이 json형식으로 리턴되어서 돌아온다.
					var tag = "<ul>";
					tag += "<li>번호 : "+jsonData.no+"</li>";
					tag += "<li>이름 : "+jsonData.username+"</li>";
					tag += "<li>연락처 : "+jsonData.tel+"</li>";
					tag += "<li>주소 : "+jsonData.addr+"</li>";
					
					$("#resultData").append(tag);
				},error:function(){
					console.log("json받기 에러");
				}
			});
		});
	});
</script> 
</head>
<body>
	<h1>Spring에서 Ajax : 비동기식으로 controller에 접속하여 정보를 리턴받는다.</h1>
	<input type="button" id="ajaxString" value="ajax 문자열"/>
	<input type="button" id="ajaxObject" value="ajax Object"/>
	<input type="button" id="ajaxList" value="ajax List"/>
	<input type="button" id="ajaxMap" value="ajax Map"/>
	<input type="button" id="ajaxJson" value="ajax Json"/>
	<hr/>
	<div id="resultData" style="background-color:#ddd"></div>
	
</body>
</html>