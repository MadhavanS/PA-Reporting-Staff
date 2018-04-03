package com.snow.gk;

import com.snow.gk.core.exception.CustomException;
import com.snow.gk.core.log.Assert;
import com.snow.gk.core.log.ListenerClass;
import com.snow.gk.core.supers.AllPages;
import com.snow.gk.core.supers.BaseTest;
import com.snow.gk.core.ui.elements.impl.Table;
import com.snow.gk.core.utils.Config;
import com.snow.gk.gurukula.beans.*;
import com.snow.gk.gurukula.pages.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({ ListenerClass.class })
public class GurukulaTest extends BaseTest {
    private MainPage getMainPage() { return AllPages.getPage(MainPage.class); }
    private HeaderPage getHeaderPage() { return AllPages.getPage(HeaderPage.class); }
    private CreateEditBranchPage getCreateEditBranchPage() { return AllPages.getPage(CreateEditBranchPage.class); }
    private CreateEditStaffPage getCreateEditStaffPage() { return AllPages.getPage(CreateEditStaffPage.class); }
    private HomePage getHomePage() { return AllPages.getPage(HomePage.class); }
    private LoginPage getLoginPage() { return AllPages.getPage(LoginPage.class); }
    private RegistrationPage getRegistrationPage() { return AllPages.getPage(RegistrationPage.class); }
    private ViewPage getViewPage() { return AllPages.getPage(ViewPage.class); }
    private BranchPage getBranchPage() { return AllPages.getPage(BranchPage.class); }
    private StaffPage getStaffPage() { return AllPages.getPage(StaffPage.class); }
    private DeletePage getDeletePage() { return AllPages.getPage(DeletePage.class); }
    private AccountSettingsPage getAccountSettingsPage() { return AllPages.getPage(AccountSettingsPage.class); }
    private AccountsPasswordPage getAccountPasswordPage() { return AllPages.getPage(AccountsPasswordPage.class); }

    private CreateStaffData createStaffData;
    private RegistrationBean registrationBean;
    private AccountBean accountBean;

    @BeforeClass(alwaysRun = true)
    public void navigateToURL() throws CustomException {
        prepareLoginTestData(Config.getConfig().getConfigProperty("username"),
                Config.getConfig().getConfigProperty("password"),
                Config.getConfig().getConfigProperty("username"));

        gotoUrl(baseURL);
        // Login
        getHomePage().clickLogin();
        Assert.verifyTrue("Authentication".equals(getLoginPage().getHeaderAuthentication().getLabel()), "Login page is displayed successfully");
        getLoginPage().login(strUsername, strPassword);
    }

    @Test(priority = 1)
    public void createBranch() {
        createStaffData = testDataForCreateStaff(new CreateStaffData());

        Assert.verifyTrue(getMainPage().validatePage(strUsername), "Navigated to HomePage");
        getHeaderPage().clickBranch();

        boolean branchCreated = getCreateEditBranchPage().createNewBranch(createStaffData.getBranchBean());
        Assert.verifyTrue(branchCreated, "New Branch created with ID " + createStaffData.getBranchBean().getId());
    }

    @Test(priority = 2)
    public void createStaff() {
        createStaffData = testDataForCreateStaff(new CreateStaffData());

        createStaffData = testDataForCreateStaff(new CreateStaffData());

        // Create new branch
        getHeaderPage().clickBranch();

        boolean branchCreated = getCreateEditBranchPage().createNewBranch(createStaffData.getBranchBean());
        Assert.verifyTrue(branchCreated, "New Branch created with ID " + createStaffData.getBranchBean().getId());

        // Create new staff
        getHeaderPage().clickStaff();
        boolean staffCreated = getCreateEditStaffPage().createStaff(createStaffData.getStaffBean());
        Assert.verifyTrue(staffCreated, "New Staff created with ID " + createStaffData.getStaffBean().getId());


    }

    @Test(priority = 3)
    public void cancelStaff() {
        gotoUrl(baseURL);
        createBranch();
        CreateStaffData createStaffData2 = testDataForCreateStaff(new CreateStaffData());

        // Navigate to Staff page
        getHeaderPage().clickStaff();
        Assert.verifyTrue("Staffs".equalsIgnoreCase(getStaffPage().getStaffsHeader().getLabel()),
                "Staff page is displayed");

        // Verifying the cancel button in the create/edit staff
        int rows = getCreateEditStaffPage().getTable().getRowCount();
        getCreateEditStaffPage().clickCreateNewStaff();
        getCreateEditStaffPage().fillStaffForm(createStaffData.getStaffBean());
        getCreateEditStaffPage().clickCancel();
        Assert.verifyTrue("Staffs".equalsIgnoreCase(getStaffPage().getStaffsHeader().getLabel()),
                "Staff page is displayed");
        boolean result = (getCreateEditStaffPage().getTable().getRowCount() == rows);
        Assert.verifyTrue(result, "Cancel staff button works as expected.");

        // Create staff
        createStaffData.getStaffBean().getBranchBean().setName("");
        boolean staffCreated = getCreateEditStaffPage().createStaff(createStaffData.getStaffBean());
        Assert.verifyTrue(staffCreated, "New Staff created with ID " + createStaffData.getStaffBean().getId());

        // Delete the edited staff
        rows = getCreateEditStaffPage().getTable().getRowCount();
        getCreateEditStaffPage().staffOperations(createStaffData.getStaffBean(),"ID", Table.AXNS.DELETE);
        getDeletePage().clickCancel();
        Assert.verifyTrue(getCreateEditStaffPage().getTable().getRowCount() == rows, "Cancel button of delete window works as expected.");

    }

    @Test(priority = 4)
    public void searchBranches() {
        gotoUrl(baseURL);
        createBranch();
        int countOfStaff = getStaffPage().getTable().getRowCount();
        // Search Branch based on Name
        boolean searchResult = getBranchPage().searchBranch(createStaffData.getBranchBean(), BranchPage.Columns.NAME);
        Assert.verifyTrue(searchResult, "Search was successful based on Name.");

        // Verify clearing the query, displays the created branches
        getBranchPage().setQuery("");
        getBranchPage().clickSearchBranch();
        int emptySearchCountBranches = getBranchPage().getTable().getRowCount();
        Assert.verifyTrue(countOfStaff == emptySearchCountBranches, "Successfully displays all the rows if we clear the search query to empty.");

        // Search Branch based on Code
        searchResult = getBranchPage().searchBranch(createStaffData.getBranchBean(), BranchPage.Columns.CODE);
        Assert.verifyTrue(searchResult, "Search was successful based on Code");

        // Search Branch based on Id
        searchResult = getBranchPage().searchBranch(createStaffData.getBranchBean(), BranchPage.Columns.ID);
        Assert.verifyTrue(searchResult, "Search was successful based on ID.");
    }

    @Test(priority = 4)
    public void searchStaff() {
        gotoUrl(baseURL);
        // Login
        getHomePage().clickLogin();
        Assert.verifyTrue("Authentication".equals(getLoginPage().getHeaderAuthentication().getLabel()), "Login page is displayed successfully");
        getLoginPage().login(strUsername, strPassword);

        createBranch();
        getHeaderPage().clickStaff();
        int countOfStaff = getStaffPage().getTable().getRowCount();

        // Search Staff based on Name
        boolean searchResult = getStaffPage().searchStaff(createStaffData.getStaffBean(), StaffPage.Columns.NAME);
        Assert.verifyTrue(searchResult, "Search was successful based on Name.");

        // Verify clearing the query, displays the created branches
        getStaffPage().setQuery("");
        getStaffPage().clickSearchStaff();
        int emptySearchCountStaffs = getStaffPage().getTable().getRowCount();
        Assert.verifyTrue(countOfStaff == emptySearchCountStaffs, "Successfully displays all the rows if we clear the search query to empty.");

        // Search Staff based on Id
        searchResult = getStaffPage().searchStaff(createStaffData.getStaffBean(), StaffPage.Columns.ID);
        Assert.verifyTrue(searchResult, "Search was successful based on ID.");

        // Search Staff based on Branch
        searchResult = getStaffPage().searchStaff(createStaffData.getStaffBean(), StaffPage.Columns.BRANCH);
        Assert.verifyTrue(searchResult, "Search was successful based on Branch");
    }

    @Test(priority = 5)
    public void cancelBranch() {
        createStaffData = testDataForCreateStaff(new CreateStaffData());
        gotoUrl(baseURL);
        getHeaderPage().clickBranch();

        // Verifying the cancel button in the create/edit branch
        int rows = getCreateEditBranchPage().getTable().getRowCount();
        getCreateEditBranchPage().clickCreateNewBranch();

        getCreateEditBranchPage().fillForm(createStaffData.getBranchBean());
        getCreateEditBranchPage().clickCancel();
        Assert.verifyTrue(getCreateEditBranchPage().getTable().getRowCount() == rows, "Cancel branch button works as expected.");

        // create new branch
        boolean branchCreated = getCreateEditBranchPage().createNewBranch(createStaffData.getBranchBean());
        Assert.verifyTrue(branchCreated, "New Branch created with ID " + createStaffData.getBranchBean().getId());

        // Delete cancel button verification
        rows = getCreateEditBranchPage().getTable().getRowCount();
        getCreateEditBranchPage().branchOperations(createStaffData.getBranchBean(),"ID", Table.AXNS.DELETE);
        getDeletePage().clickCancel();
        Assert.verifyTrue(getCreateEditBranchPage().getTable().getRowCount() == rows, "Cancel delete button works as expected.");
    }

    @Test(priority = 6)
    public void viewEditDeleteBranch() {
        gotoUrl(baseURL);
        createBranch();
        // Select & View the created branch
        getCreateEditBranchPage().branchOperations(createStaffData.getBranchBean(),"ID", Table.AXNS.VIEW);

        Assert.verifyTrue(getViewPage().verifyBranchBean(createStaffData.getBranchBean()),
                "View page of the created branch is displayed correctly.");
        getViewPage().clickBack();

        Assert.verifyTrue("Branches".equalsIgnoreCase(getBranchPage().getHeaderBranch().getLabel()),
                "Branches page is displayed");

        // Select & Edit the created branch
        String beanName = createStaffData.getBranchBean().getName();
        createStaffData.getBranchBean().setRandomStringToName(6);
        createStaffData.getBranchBean().setRandomStringToCode(3);

        getCreateEditBranchPage().branchOperations(createStaffData.getBranchBean(),
                "ID", Table.AXNS.EDIT);

        getCreateEditBranchPage().editForm(createStaffData.getBranchBean());
        getCreateEditBranchPage().clickSave();
        // View the edited branch
        getCreateEditBranchPage().branchOperations(createStaffData.getBranchBean(),
                "ID", Table.AXNS.VIEW);

        Assert.verifyTrue(getViewPage().verifyBranchBean(createStaffData.getBranchBean()),
                "View page of the created branch is displayed correctly.");
        getViewPage().clickBack();

        Assert.verifyTrue("Branches".equalsIgnoreCase(getBranchPage().getHeaderBranch().getLabel()),
                "Branches page is displayed");

        // Delete the edited branch
        getCreateEditBranchPage().branchOperations(createStaffData.getBranchBean(),"ID", Table.AXNS.DELETE);

        Assert.verifyTrue(getDeletePage().deleteBranch(createStaffData.getBranchBean()), "Branch is deleted successfully.");

        Assert.verifyTrue("Branches".equalsIgnoreCase(getBranchPage().getHeaderBranch().getLabel()),
                "Branches page is displayed");

    }

    @Test(priority = 7)
    public void viewEditDeleteStaff() {
        gotoUrl(baseURL);
        createBranch();

        CreateStaffData createStaffData2 = testDataForCreateStaff(new CreateStaffData());

        // Create another branch
        boolean branchCreated = getCreateEditBranchPage().createNewBranch(createStaffData2.getBranchBean());
        Assert.verifyTrue(branchCreated, "New Branch created with ID " + createStaffData2.getBranchBean().getId());

        // Navigate to Staff page
        getHeaderPage().clickStaff();
        Assert.verifyTrue("Staffs".equalsIgnoreCase(getStaffPage().getStaffsHeader().getLabel()),
                "Staff page is displayed");

        // Create staff
        boolean staffCreated = getCreateEditStaffPage().createStaff(createStaffData.getStaffBean());
        Assert.verifyTrue(staffCreated, "New Staff created with ID " + createStaffData.getStaffBean().getId());

        // Select & View the created Staff
        getCreateEditStaffPage().staffOperations(createStaffData.getStaffBean(),"ID", Table.AXNS.VIEW);

        Assert.verifyTrue(getViewPage().verifyStaffBean(createStaffData.getStaffBean()),
                "View page of the created staff is displayed successfully.");
        getViewPage().clickBack();

        Assert.verifyTrue("Staffs".equalsIgnoreCase(getStaffPage().getStaffsHeader().getLabel()),
                "Staff page is displayed");

        // Select & Edit the created staff
        createStaffData.getStaffBean().setRandomStringToName();
        createStaffData.getStaffBean().setBranchBean(createStaffData2.getBranchBean());

        getCreateEditStaffPage().staffOperations(createStaffData.getStaffBean(),
                "ID", Table.AXNS.EDIT);

        getCreateEditStaffPage().editStaffForm(createStaffData.getStaffBean());

        // View the edited staff
        getCreateEditBranchPage().staffOperations(createStaffData.getStaffBean(),
                "ID", Table.AXNS.VIEW);

        Assert.verifyTrue(getViewPage().verifyStaffBean(createStaffData.getStaffBean()),
                "View page of the created staff is displayed correctly.");
        getViewPage().clickBack();

        Assert.verifyTrue("Staffs".equalsIgnoreCase(getStaffPage().getStaffsHeader().getLabel()),
                "Staffs page is displayed");

        // Delete the edited staff
        getCreateEditBranchPage().staffOperations(createStaffData.getStaffBean(),
                "ID", Table.AXNS.DELETE);

        Assert.verifyTrue(getDeletePage().deleteStaff(createStaffData.getStaffBean()), "Staff is deleted successfully.");

        Assert.verifyTrue("Staffs".equalsIgnoreCase(getStaffPage().getStaffsHeader().getLabel()),
                "Staffs page is displayed");

    }

    @Test(priority = 8)
    public void editAccountSettings() {
        accountBean = new AccountBean();
        testdataForAccountSettings();
        // Navigate to Account > Settings page
        getHeaderPage().clickSettings();
        boolean blnResult = getAccountSettingsPage().fillSettingsForm(accountBean);
        Assert.verifyTrue(blnResult, "Account settings are successfully updated.");
    }

    @Test(priority = 9)
    public void updatePassword() {
        accountBean = new AccountBean();
        testdataForAccountSettings();

        // Navigate to Account > Settings page
        getHeaderPage().clickPassword();
        boolean blnResult = getAccountPasswordPage().fillUpdatePassword(accountBean);
        Assert.verifyTrue(blnResult, "New password was updated successfully.");

        // Logout
        getHeaderPage().clickLogout();
        Assert.verifyTrue(getHomePage().getWelcomeHeader().getLabel().equals("Welcome to Gurukula!"), "Logged out successfully");
    }

    @Test(priority = 9)
    public void registerNewAccount() {
        String registrationFailedText = "Registration failed! Please try again later.";

        gotoUrl(baseURL);
        registrationBean = new RegistrationBean();
        getHomePage().clickRegisterNewAccount();

        registerNewAccountData();
        getRegistrationPage().fillRegistration(registrationBean);
        Assert.verifyFalse(registrationFailedText.equalsIgnoreCase(getRegistrationPage().getRegistrationFailed().getText()) , "Registration for new user failed");
        Assert.verifyFalse(getRegistrationPage().getHeaderRegistration().isDisplayed() , "Registration for new user failed");
    }

    @Test(priority = 10)
    public void loginWithInvalidCredentials() {
        String authFailedText = "Authentication failed! Please check your credentials and try again.";

        gotoUrl(baseURL);
        registrationBean = new RegistrationBean();
        getHomePage().clickLogin();

        registerNewAccountData();
        getLoginPage().login(registrationBean.getLogin(), registrationBean.getPassword());

        Assert.verifyTrue(authFailedText.equals(getLoginPage().getAuthenticationFailure().getContent()),
                "Authentication failure message is validated.");
    }


    // TestData
    private CreateStaffData testDataForCreateStaff(CreateStaffData createStaffData) {
        BranchBean branchBean = new BranchBean();
        /*String branchName = StringUtil.randomString(5);
        String staffName = StringUtil.randomString(5);
        String branchCode = StringUtil.randomString(1).toUpperCase() +
                    StringUtil.randomNumber(1).toString();*/
        branchBean.setRandomStringToName(5);
        branchBean.setRandomStringToCode(3);
        createStaffData.setBranchBean(branchBean);
        StaffBean staffBean = new StaffBean();
        staffBean.setRandomStringToName();
        staffBean.setBranchBean(branchBean);
        createStaffData.setStaffBean(staffBean);
        return createStaffData;
    }

    private void registerNewAccountData() {
        registrationBean = new RegistrationBean();
        registrationBean.setLogin("testuser");
        registrationBean.seteMail("TestUser@localhost");
        registrationBean.setPassword("testuser");
        registrationBean.setConfirmPassword("testuser");
    }

    private void testdataForAccountSettings() {
        accountBean = new AccountBean();
        accountBean.setFirstName("Test");
        accountBean.setLastName("User");
        accountBean.seteMail("testuser@localhost");
        accountBean.setLanguage("English");
        accountBean.setPassword("testuser");
    }
}
