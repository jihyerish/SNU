package bang;

import org.json.simple.*;

public class Job {
	public static String[] JOBS = {"Sheriff", "Outlaw", "Outlaw", "Renegade", "Deputy", "Outlaw", "Deputy"};

	private String job;

	public Job(String job) {
		this.job = job;
	}

	public String getJob() {
		return job;
	}

	public String getGoal() {
		if(job == "Sheriff"){
			return "Kill the outlaws and renegade";
		} else if(job == "Outlaw"){
			return "Kill the sheriff";
		} else if(job == "Deputy"){
			return "Kill the outlaws and renegade";
		} else if(job == "Renegade"){
			return "Be the last one alive";
		} else {
			throw new RuntimeException("Invalid job");
		}
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("image", job + ".jpg");
		json.put("name", job);
		json.put("mission", this.getGoal());
		return json;
	}
}
