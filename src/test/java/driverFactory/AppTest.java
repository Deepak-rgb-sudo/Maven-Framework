package driverFactory;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import constant.AppUtil;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtil {
	String inputpath ="D:\\Deepakseleniumdata\\Maven_Project\\TestInput\\DataEngine.xlsx";
	String outputpath ="D:\\Deepakseleniumdata\\Maven_Project\\TestOutput\\HybridReult.xlsx";
	String TCSheet ="MasterTCS";
	String TSSheet ="TestSteps";
	ExtentReports report;
	ExtentTest test;
	@Test
	public void startTest()throws Throwable
	{
		report= new ExtentReports("./Reports/HybridTest.html");
		boolean res=false;
		String tcres="";
		//access excel mehods
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		//count no of rows in sheets
		int TCCount =xl.rowCount(TCSheet);
		int TSCount =xl.rowCount(TSSheet);
		Reporter.log("No of rows in TCSheet::"+TCCount+"    "+"No of rows in TSSheet::"+TSCount,true);
		for(int i=1;i<=TCCount;i++)
		{
			
			String ModuleName =xl.getCellData(TCSheet, i, 1);
			test=report.startTest(ModuleName);
			//read modulestatus cell
			String Modulestatus =xl.getCellData(TCSheet, i, 2);
			if(Modulestatus.equalsIgnoreCase("Y"))
			{
				//read tcid cell
				String tcid =xl.getCellData(TCSheet, i, 0);
				for(int j=1;j<=TSCount;j++)
				{
					String Description= xl.getCellData(TSSheet, j, 2);
					String tsid=xl.getCellData(TSSheet, j, 0);
					if(tcid.equalsIgnoreCase(tsid))
					{
						String keyword =xl.getCellData(TSSheet, j, 4);
						if(keyword.equalsIgnoreCase("AdminLogin"))
						{
							String para1 =xl.getCellData(TSSheet, j, 5);
							String para2 =xl.getCellData(TSSheet, j, 6);
						res=FunctionLibrary.verifyLogin(para1, para2);
						test.log(LogStatus.INFO, Description);
						}
						else if(keyword.equalsIgnoreCase("BranchCreation"))
						{
							String para1 =xl.getCellData(TSSheet, j, 5);
							String para2 =xl.getCellData(TSSheet, j, 6);
							String para3 =xl.getCellData(TSSheet, j, 7);
							String para4 =xl.getCellData(TSSheet, j, 8);
							String para5 =xl.getCellData(TSSheet, j, 9);
							String para6 =xl.getCellData(TSSheet, j, 10);
							String para7 =xl.getCellData(TSSheet, j, 11);
							String para8 =xl.getCellData(TSSheet, j, 12);
							String para9 =xl.getCellData(TSSheet, j, 13);
							FunctionLibrary.clickBranches();
							res =FunctionLibrary.verifyBranchCreation(para1, para2, para3, para4, para5, para6, para7, para8, para9);
							test.log(LogStatus.INFO, Description);
						}
						else if(keyword.equalsIgnoreCase("BranchUpdate"))
						{
							String para1 =xl.getCellData(TSSheet, j, 5);
							String para2 =xl.getCellData(TSSheet, j, 6);
							String para5 =xl.getCellData(TSSheet, j, 9);
							String para6 =xl.getCellData(TSSheet, j, 10);
							FunctionLibrary.clickBranches();
							res=FunctionLibrary.verifyBranchUpdation(para1, para2, para5, para6);
							test.log(LogStatus.INFO, Description);
						}
						else if(keyword.equalsIgnoreCase("AdminLogout"))
						{
							res =FunctionLibrary.verifyLogout();
							test.log(LogStatus.INFO, Description);
						}
						String tsres ="";
						if(res)
						{
						//write as pass into status cell
							tsres="Pass";
							xl.setCellData(TSSheet, j, 3, tsres, outputpath);
							test.log(LogStatus.PASS, Description);
						}
						else
						{
							tsres="Fail";
							xl.setCellData(TSSheet, j, 3, tsres, outputpath);
							test.log(LogStatus.FAIL, Description);
						}
						tcres=tsres;
						
					}
					report.endTest(test);
					report.flush();
				}
				xl.setCellData(TCSheet, i, 3, tcres, outputpath);
			}
			else
			{
				//write as blocked which are flag to N
				xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
			}
			
		}
	}

}



















