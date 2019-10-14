package hello;

public class Asset {

	private String companyCode;
	private String assetClass;
    private String assetNum;
    private String description;

    public Asset(String companyCode, String assetClass, String assetNum, String description) {

        this.companyCode = companyCode;
        this.assetClass = assetClass;
        this.assetNum = assetNum;
        this.description = description;
    }

    public String getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setAssetNum(String assetNum) {
		this.assetNum = assetNum;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getDescription() {
		return description;
	}

	public String getAssetNum() {
        return assetNum;
    }
    
}
