package movie;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MoviePro {

	Scanner sc = new Scanner(System.in);
	ArrayList<Movie> movieList = null;
	
	public void crawling() {
		String url = "https://movie.naver.com/movie/running/current.nhn";	//크롤링할 웹페이지 주소
		Document doc = null;
		try {
			doc=Jsoup.connect(url).get();
			Elements movieList = doc.select("ul.lst_detail_t1").select("li");
			for (int i=0; i<movieList.size(); i++) {
				Element movie = movieList.get(i);
				String title = movie.select(".tit").select("a").text();
				//System.out.println(title);
				String score = movie.select(".num").text();
				//String score = movie.select(".num").text();
				//System.out.println(score);
				String level = movie.select(".tit").select("span").text();
				//System.out.println(level);
				
				Elements elements = movie.select(".info_txt1").select("dd");
				String info = null, director = null, actor = null;
				info = elements.get(0).text().replace("|", "-");
				System.out.println(info);
				director = elements.get(1).text();
				
			
				if (elements.size()==3) {
					actor = elements.get(2).text();
				}
				System.out.println(director);
				String strImageUrl = movie.select(".thumb").select("img").attr("src");
				String strImageName = strImageUrl.substring(strImageUrl.lastIndexOf("/")+1, strImageUrl.indexOf("?"));
				String ext = strImageName.substring(strImageName.indexOf("."));
				strImageName = strImageName.substring(0, strImageName.indexOf("."));
				strImageName = strImageName + i + ext;
				
				downloadImage(strImageName,strImageUrl);
				Movie movie1 = new Movie(i, title, score, level, director, actor, info, strImageName);
				DBConn conn = DBConn.getInstance();
				conn.insertMovie(movie1);
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}	
	private static void downloadImage(String strImageName, String strImageUrl) {
		try {
			URL urlImage = new URL(strImageUrl);
			InputStream in = urlImage.openStream();
			byte[] buff = new byte[4096];
			int n = -1;
			OutputStream os = new FileOutputStream("images/"+strImageName);
			while( (n=in.read(buff))!=-1 ) {
				os.write(buff,0,n);
			}
			os.close();
			System.out.println("image saved "+strImageName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void show() {
		DBConn conn = DBConn.getInstance();
		ArrayList<Movie> list = conn.selectAll();
		for (int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	public void search() {
		System.out.println("검색할 영화 제목 입력");
		String title = sc.next();
		DBConn conn = DBConn.getInstance();
		ArrayList<Movie> list = conn.selectSearch(title);
		for (int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	private void updateMovie() {
		System.out.println("업데이트 할 영화 번호 입력");
		int mno = sc.nextInt();
		Movie movie = DBConn.getInstance().selectOne(mno);
		System.out.println(movie);
		System.out.println("제목 입력");
		sc.nextLine();
		movie.setTitle(sc.nextLine());
		System.out.println("평점 입력");
		movie.setScore(sc.next());
		System.out.println("등급 입력");
		movie.setTit(sc.next()+"세 관람가");
		DBConn.getInstance().update(movie);
		System.out.println(movie);
	}
	
	private void deleteMovie() {
		System.out.println("삭제할 영화 번호 입력");
		int mno = sc.nextInt();
		Movie movie = DBConn.getInstance().selectOne(mno);
		System.out.println(movie);
		DBConn.getInstance().selectOne(mno);
		System.out.println(movie);
		System.out.println("정말 삭제할까요?");
		String yesNo = sc.next();
		if (yesNo.equals("yes")) {
			DBConn.getInstance().delete(mno);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MoviePro pro = new MoviePro();
		pro.run();
	}
	
	private void run() {
		System.out.println("영화 검색 프로그램");
		while(true) {
			System.out.println("1. 데이터 저장 |2. 전체보기 |3. 검색 |4. 업데이트 |5. 삭제 |6. 종료  ");
			int num = sc.nextInt();
			switch (num) {
				case 1: crawling(); break;
				case 2: show(); break;
				case 3: search(); break;
				case 4: updateMovie(); break;
				case 5: deleteMovie(); break;
				case 6: break;
				default: System.out.println("입력오류"); 
			}
			if (num ==6) break;
		}
		System.out.println("프로그램 종료");
	}
}
