package main;

public class StatusEffect {
	
	private int duration;
	private int timeLeft;
	private int val;
	private boolean bad;
	private int currentHealth;
	private String name;
	
	public StatusEffect(int duration, String name, int val, boolean bad) {
		this.duration = duration;
		this.val = val;
		this.name = name;
		this.bad = bad;
		
		timeLeft = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setTime() {
		timeLeft = duration;
	}
	
	public void setDuration(int x) {
		duration = x;
		setTime();
	}
	
	public int getVal() {
		return val;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTime() {
		return timeLeft;
	}
	
	public void runTime() {
		timeLeft--;
	}
	
	public boolean getMorality() {
		return bad;
	}
	
	public int[] runStatus(int health, int mod) {
		int[] retArr = {health, mod};
		if (timeLeft > 0) {
			if (val == 1) {
				retArr[0]--;
			}
			else if (val == 2) {
				retArr[0]++;
			}
			else if (val == 3) {
				retArr[0]++;
			}
			else if (val == 4) {
				retArr[0] -= 2;
			}
			else if (val == 5) {
				retArr[1] = 5;
			}
			runTime();
		}
		return retArr;
	}
	
}
