package reporter;

public class ReportFactory {
	private ReportFactory instance;
	
	private ReportFactory(){
		
	}
	
	public ReportFactory getInstance(){
		if (instance == null)
			instance = new ReportFactory();
		
		return instance;
	}
	
	public StudentReport getStudentReport(){
		return null;
	}
}
