package movie;

public class Movie {

	private int mno;
	private String title;
	private String score;
	private String tit;
	private String director;
	private String actor;
	private String info;
	private String image_name;
	
	public Movie() {}
	
	public Movie(int mno, String title, String score, String tit, String director, String actor, String info,
			String image_name) {
		super();
		this.mno = mno;
		this.title = title;
		this.score = score;
		this.tit = tit;
		this.director = director;
		this.actor = actor;
		this.info = info;
		this.image_name = image_name;
	}

	public int getMno() {
		return mno;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getTit() {
		return tit;
	}

	public void setTit(String tit) {
		this.tit = tit;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getImage_name() {
		return image_name;
	}

	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	@Override
	public String toString() {
		return mno + ", " + title + ", " + score + ", " + tit + ", " + director
				+ ", " + actor + ", " + info + ", " + image_name;
	}

}
