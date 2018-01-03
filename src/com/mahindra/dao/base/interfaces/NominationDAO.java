package com.mahindra.dao.base.interfaces;

import java.util.ArrayList;

import com.mahindra.dao.base.GenericDAO;
import com.mahindra.database.pojo.CompanyPojo;
import com.mahindra.database.pojo.FeedbackPojo;
import com.mahindra.database.pojo.NominationPojo;
import com.mahindra.database.pojo.SectorPojo;
import com.mahindra.database.pojo.TemplatePojo;
import com.mahindra.database.pojo.UserPojo;

public interface NominationDAO extends GenericDAO {

	public ArrayList<SectorPojo> getSectorList();

	public ArrayList<CompanyPojo> getCompanyList(String nominationCat);

	public ArrayList<TemplatePojo> getTemplateList();
	
	public ArrayList<TemplatePojo> getTemplateListAll();
	
	public UserPojo nominateEmployee(NominationPojo obj);
	
	public String extendSurvey(FeedbackPojo obj) ;
	
	public ArrayList<NominationPojo> fetchPendingRequest(UserPojo obj) ;

	public UserPojo fetchDetailsForEmp(NominationPojo obj);
}
