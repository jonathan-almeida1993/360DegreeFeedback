package com.mahindra.database.pojo;

public class SectorPojo extends BasePojo {

	private String sectorId;
	private String sectorName;
	private String secAdminEmailId;

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorName() {
		return sectorName;
	}

	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	public String getSecAdminEmailId() {
		return secAdminEmailId;
	}

	public void setSecAdminEmailId(String secAdminEmailId) {
		this.secAdminEmailId = secAdminEmailId;
	}
}
