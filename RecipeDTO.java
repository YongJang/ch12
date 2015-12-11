package ch12;

public class RecipeDTO {
	private int num;	// auto increment
	private String cookname;
	private String ingredient;
	private String seasoned;
	private String recipe;
	private String imgpath;
	private int rate;
	public RecipeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RecipeDTO(int num, String cookname, String ingredient, String seasoned, String recipe, String imgpath,
			int rate) {
		super();
		this.num = num;
		this.cookname = cookname;
		this.ingredient = ingredient;
		this.seasoned = seasoned;
		this.recipe = recipe;
		this.imgpath = imgpath;
		this.rate = rate;
	}
	public int getNum() {
		return num;
	}
	public String getCookname() {
		return cookname;
	}
	public String getIngredient() {
		return ingredient;
	}
	public String getSeasoned() {
		return seasoned;
	}
	public String getRecipe() {
		return recipe;
	}
	public String getImgpath() {
		return imgpath;
	}
	public int getRate() {
		return rate;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setCookname(String cookname) {
		this.cookname = cookname;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public void setSeasoned(String seasoned) {
		this.seasoned = seasoned;
	}
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	
}
