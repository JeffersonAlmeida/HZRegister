package ranzo.hzregister.json;

import java.util.List;

public class Field {

    private long id;
	private String name;
	private String jsonName;
	private String type;
	private String validation;
	private boolean mandatory;
	private boolean isJsonData;
	private int maxSize;
	private int minSize;
	private boolean editProfile;
	private String mask;
	private List<String> combo;
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getJsonName() {
		return jsonName;
	}

	public String getType() {
		return type;
	}

	public String getValidation() {
		return validation;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public boolean isJsonData() {
		return isJsonData;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public int getMinSize() {
		return minSize;
	}

	public boolean isEditProfile() {
		return editProfile;
	}

	public String getMask() {
		return mask;
	}

	public List<String> getCombo() {
		return combo;
	}

	@Override
	public String toString() {
		return "Field [id=" + id + ", name=" + name + ", jsonName=" + jsonName
				+ ", type=" + type + ", validation=" + validation
				+ ", mandatory=" + mandatory + ", isJsonData=" + isJsonData
				+ ", maxSize=" + maxSize + ", minSize=" + minSize
				+ ", editProfile=" + editProfile + ", mask=" + mask
				+ ", combo=" + combo + "]";
	}
}
