package movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBConn {
	private DBConn() {}
	private static DBConn instance=new DBConn();
	public static DBConn getInstance() {
		return instance;
	}
	
	private Connection getConnect() {
		Connection conn =null;
		String url="jdbc:oracle:thin:@localhost:1522:xe";
		String user="pkh";
		String password="1234";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(url,user,password);
		}catch(Exception ex) {
			ex.getStackTrace();
		}
		return conn;
	}
	
	public ArrayList<Movie> selectAll(){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select * from movie";
		
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		try {
			conn = getConnect();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				Movie movie=new Movie();
				movie.setMno(rs.getInt("mno"));
				movie.setTitle(rs.getString("title"));
				movie.setScore(rs.getString("score"));
				movie.setTit(rs.getString("tit"));
				movie.setDirector(rs.getString("director"));
				movie.setActor(rs.getString("actor"));
				movie.setInfo(rs.getString("info"));
				movie.setImage_name(rs.getString("image_name"));
				movieList.add(movie);
			}
		}catch(Exception ex){
			ex.getStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return movieList;
	}
	
	
	public ArrayList<Movie> selectSearch(String title){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select * from movie where title like ?";
		ArrayList<Movie> movieList = new ArrayList<Movie>();
		try {
			conn = getConnect();
			ps=conn.prepareStatement(sql);
			ps.setString(1,"%"+title+"%");
			rs=ps.executeQuery();
			while(rs.next()) {
				Movie movie=new Movie();
				movie.setMno(rs.getInt("mno"));
				movie.setTitle(rs.getString("title"));
				movie.setScore(rs.getString("score"));
				movie.setTit(rs.getString("tit"));
				movie.setDirector(rs.getString("director"));
				movie.setActor(rs.getString("actor"));
				movie.setInfo(rs.getString("info"));
				movie.setImage_name(rs.getString("image_name"));
				movieList.add(movie);
			}
		}catch(Exception ex){
			ex.getStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return movieList;
	}
	
	public Movie selectOne(int mno){
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select * from movie";
		Movie movie = null;
		try {
			conn = getConnect();
			ps=conn.prepareStatement(sql);
			rs=ps.executeQuery();
			if(rs.next()) {
				movie=new Movie();
				movie.setMno(rs.getInt("mno"));
				movie.setTitle(rs.getString("title"));
				movie.setScore(rs.getString("score"));
				movie.setTit(rs.getString("tit"));
				movie.setDirector(rs.getString("director"));
				movie.setActor(rs.getString("actor"));
				movie.setInfo(rs.getString("info"));
				movie.setImage_name(rs.getString("image_name"));
				
			}
		}catch(Exception ex){
			ex.getStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
		return movie;
	}
	
	public void insertMovie(Movie movie) {
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="insert into movie values(?,?,?,?,?,?,?,?)";
		try {
			conn=getConnect();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, movie.getMno());
			ps.setString(2, movie.getTitle());
			ps.setString(3, movie.getScore());
			ps.setString(4, movie.getTit());
			ps.setString(5, movie.getDirector());
			ps.setString(6, movie.getActor());
			ps.setString(7, movie.getInfo());
			ps.setString(8, movie.getImage_name());
			int n=ps.executeUpdate();
			if(n==1)
				System.out.println("데이터 입력 성공");
			
		}catch(Exception ex) {
			ex.getStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
	}
	
	public void update (Movie movie) {
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="update movie set title=?, score=?, tit=? where mno=?";
		try {
			conn=getConnect();
			ps=conn.prepareStatement(sql);
			ps.setString(1, movie.getTitle());
			ps.setString(2, movie.getScore());
			ps.setString(3, movie.getTit());
			ps.setInt(4, movie.getMno());
			int n=ps.executeUpdate();
			if(n>0)
				System.out.println(n+"개의 행이 업데이트 되었습니다.");
			else 
				System.out.println("업데이트 실패");
		}catch(Exception ex) {
			ex.getStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
	}
	public void delete (int mno) {
		Connection conn=null;
		PreparedStatement ps=null;
		String sql="delete from movie where mno=?";
		
		try {
			conn = getConnect();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, mno);
			int n = ps.executeUpdate();
			if (n>0) 
				System.out.println(n+"개의 데이터가 삭제되었습니다.");
			else 
				System.out.println("삭제 실패");
		} catch (Exception ex) {
			ex.getStackTrace();
		} finally {
			try {
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
			}catch(Exception e){
				e.getStackTrace();
			}
		}
	}
}
