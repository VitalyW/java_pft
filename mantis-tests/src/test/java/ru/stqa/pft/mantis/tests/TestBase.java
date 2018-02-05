package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;


public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"),
            "config_inc.php", "config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    return new MantisConnectLocator().getMantisConnectPort(
            new URL(app.getProperty("web.baseUrl") + "/api/soap/mantisconnect.php"));
  }

  private boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    boolean isOpen = true;
    IssueData issueData = mc.mc_issue_get(app.getProperty("web.adminLogin"),
                                          app.getProperty("web.adminPassword"), BigInteger.valueOf(issueId));
    String status = issueData.getStatus().getName();
     if (status.equals("closed") || status.equals("resolved")) {
       isOpen = false;
     }
     return isOpen;
  }

  public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}