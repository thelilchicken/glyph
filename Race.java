package main;

public abstract class Race {
	
	private String name, abrName, des;
	
	public Race(String name, String abrName, String des){
		this.name = name;
		this.abrName = abrName;
		this.des = des;
	}
	
	public String getDescription() {
		return des;
	}
	
	public String getName(){
		return name;
		
	}
	public String abrName(){
		return abrName;
		
	}
	
	
}
