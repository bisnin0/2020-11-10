package com.bitcamp.myapp.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DataController {
	@Autowired
	public DataDAO dDao; //servlet-context.xml에 dao세팅함 변수이름은 똑같아야한다. 세팅한것과.. dDao
	
	@RequestMapping("/dataList")
	public ModelAndView dataList() {
		List<DataVO> list = dDao.allList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("data/dataList");
		return mav;
	}
	@RequestMapping("fileUpload1")
	public String fileUpload1() {
		
		return "data/uploadForm1";
	}
	
	////============================파일업로드 방법 1 ============================
	////파일 리네임을 안한방법.
	////파일 리네임이 안되니까 같은이름의 파일을 올리면 전에 올린 파일이 지워지고 새파일이 올라간다.
	////문제점은 파일 내용은 달라도 파일 이름이 같으면 먼저 올린 파일이 지워진다는거다.
	////같은 이름이 있으면 리네임을 해줘야한다. 그걸 2번째 방법에서 할거다. 
	@RequestMapping(value="/fileUpload1Ok", method=RequestMethod.POST)
	public ModelAndView upload1(@RequestParam("title") String title,
								@RequestParam("content") String content,
								@RequestParam("filename1") MultipartFile filename1, 	// 파일 업로드를 위한 변수이름 = MultipartFile.. 임포트필요
								@RequestParam("filename2") MultipartFile filename2,		// 파일 업로드를 위한 변수이름 = MultipartFile.. 임포트필요
								HttpServletRequest request	) {								//업로드하는건 '파일'이다 라는뜻. 아직 업로드 된건 아니다.
								
		DataVO vo = new DataVO();
		vo.setTitle(title);
		vo.setContent(content);
		//아이피
		vo.setIp(request.getRemoteAddr());
		//아이디
		HttpSession session = request.getSession();
				
		vo.setUserid((String)session.getAttribute("logId"));
		
		//------------------------파일업로드------------------------
		// D:\workspaceSpring\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps\WebSpring03FileUpload \ upload 리얼패스. realPath
		//파일이 저장될 리얼패스를 구해야한다. 파일이 저장될곳
		String path = session.getServletContext().getRealPath("/upload");
		System.out.println("path="+path);

		String fileParamName1 = filename1.getName();//폼의 파일첨부 객체 변수 구하기. 이름이 다를 수 있다. 같으면 안해도 된다.
		String oriFileName1 = filename1.getOriginalFilename(); //원래 파일명
		System.out.println(fileParamName1 + "->"+ oriFileName1);
		try {
			if(oriFileName1!=null) { //파일이 있을때만 업로드
				filename1.transferTo(new File(path, oriFileName1)); //여기부터 실제파일업로드 발생. 업로드시작. 예외처리필요
			}
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		
		String oriFileName2 = filename2.getOriginalFilename();
		try {
			if(oriFileName2!=null) {
				filename2.transferTo(new File(path, oriFileName2));
			}
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		
		vo.setFilename1(oriFileName1);
		vo.setFilename2(oriFileName2);
		
		int result = dDao.dataInsert(vo);
		
		//------------------------업로드종료------------------------
		ModelAndView mav = new ModelAndView();
		
		if(result>0) {
			mav.setViewName("redirect:dataList");
		}else {
			//업로드 못한경우(레코드 추가 실패시) 업로드파일 삭제해야한다.
			if(oriFileName1 != null) {
				File f = new File(path, oriFileName1);
				f.delete();
			}
			if(oriFileName2 != null) {
				File f = new File(path, oriFileName2);
				f.delete();
			}
			mav.addObject("msg", "파일업로드 실패");
			mav.setViewName("data/dataResult");
		}
		return mav;
	}
	
	//=============================파일업로드 방법 2=============================
	// 1번보다 2번을 사용하는걸 권장한다. 파일이 100개든 1000개든 업로드가 정상적으로 이뤄진다.
	
	@RequestMapping("/fileUpload2")
	public String fileUpload2() {
		return "data/uploadForm2";
	}
	
	@RequestMapping(value="/fileUpload2Ok", method=RequestMethod.POST)
	public ModelAndView upload2(DataVO vo, HttpServletRequest req, HttpSession ses) {
		//파일 저장할 위치
		String path = ses.getServletContext().getRealPath("/upload");
		
		//파일업로드를 하기 위해서 request에서 MultipartHttpServletRequest를 생성해야한다. (형변환)
		MultipartHttpServletRequest mr = (MultipartHttpServletRequest)req;
		
		//MultipartHttpServletRequest 에서 MultipartFile객체를 얻어온다. --> List로 리턴
		List<MultipartFile> files= mr.getFiles("filename"); //폼에 있는 네임을 넣어주면 된다. 폼에서 네임 같게 설정해놨다.
		
		//파일명을 저장할 변수를 생성
		String fileNames[] = new String[files.size()]; //list.size().. 폼에 2개니까 2개가 만들어질거다.
		
		int idx = 0;
		if(files!=null) { //첨부파일이 있을때
			
			for(int i=0; i<files.size(); i++) {
				MultipartFile mf = files.get(i); //위에서 MultipartFile객체란걸 기억. 
												 //리스트 컬렉션도 MultipartFile이라 MultipartFile만 있다. 리턴타입이 그래서 MultipartFile이다.
				String fName = mf.getOriginalFilename(); //폼의 파일명 얻어오기
				if(fName!=null && !fName.equals("")){
					String oriFileName = fName.substring(0, fName.lastIndexOf("."));//원래 파일의 파일명 앞부분 구함. 파일의 뒤에서 첫 . 을 구해서 앞을 자른다.
					String oriExt = fName.substring(fName.lastIndexOf(".")+1);//확장자 끝까지면 따로 지정 안해도 자동으로 구해준다. 
					
					//이름을 바꿔야한다. 같은이름이 몇개있을지 모르니까 브레이크 걸릴때까지 확인작업 무한루프
					File f = new File(path, fName);
					if(f.exists()) { //원래 파일객체가 서버에 있으면 실행. 
						for(int renameNum=1; ; renameNum++) { //무한루프
							String renameFile = oriFileName+renameNum+"."+oriExt; //변경된 파일명
							f = new File(path, renameFile);
							
							if(!f.exists()) { // 파일이 있으면 true, 없으면 false..파일의 존재유무 확인 .. 부정문으로 바꾸니까 반대
								//!부정으로 반대로 바뀜 .. 파일이 없을때 들어온다.
								
								fName = renameFile; //폼의 파일명을 바꾼다.
								break;
							}
						}//for끝 .. 새로운 파일명이 들어와있다.
					}//if끝	
					////////
					try {
						mf.transferTo(f);
						
					}catch(IOException ie) {
						ie.printStackTrace();
					}
					fileNames[idx++] = fName;
				}//if끝	
			}//for 끝.. 모든 파일의 업로드 다 끝나면 여기. 업로드한 갯수만큼 반복
			
		}
		
		//아이디
		vo.setUserid((String)ses.getAttribute("logId"));
		vo.setFilename1(fileNames[0]);
		vo.setFilename2(fileNames[1]);
		
		int result = dDao.dataInsert(vo); //dao는 똑같음 방법1이랑.
		ModelAndView mav = new ModelAndView();
		if(result>0) {
			mav.setViewName("redirect:dataList");
		}else {//업로드 못했을때
				//폼으로 보내기
			//파일 삭제
			for(int j=0; j<fileNames.length; j++) {
				if(fileNames[j]!=null) {
					File f = new File(path, fileNames[j]);
					f.delete();
				}
			}
			mav.addObject("msg", "업로드 실패하였습니다.");
			mav.setViewName("data/dataResult");
		}
		
		return mav;
		
	}
}
