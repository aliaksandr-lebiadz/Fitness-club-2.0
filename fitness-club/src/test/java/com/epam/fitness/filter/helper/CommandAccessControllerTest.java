package com.epam.fitness.filter.helper;

import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class CommandAccessControllerTest {

    private static final User ADMIN = mock(User.class);
    private static final User CLIENT = mock(User.class);
    private static final User TRAINER = mock(User.class);
    private static final String ADMIN_COMMAND = "setUserDiscount";
    private static final String SHOW_HOME_PAGE_COMMAND = "showHomePage";

    private CommandAccessController controller = new CommandAccessController();

    @BeforeClass
    public static void createMocks(){
        when(ADMIN.getRole()).thenReturn(UserRole.ADMIN);
        when(CLIENT.getRole()).thenReturn(UserRole.CLIENT);
        when(TRAINER.getRole()).thenReturn(UserRole.TRAINER);
    }

    @DataProvider
    public static Object[][] adminCommandsDataProvider(){
        return new Object[][]{
                { "showClients" },
                { "setUserDiscount" }
        };
    }

    @DataProvider
    public static Object[][] trainerCommandsDataProvider(){
        return new Object[][]{
                { "assignNutritionType" },
                { "showTrainerClients" },
                { "showAssignments" }
        };
    }

    @DataProvider
    public static Object[][] clientCommandsDataProvider(){
        return new Object[][]{
                { "getMembership" },
                { "showOrderPage" },
                { "showOrders" },
                { "sendFeedback "},
                { "showAssignments" },
                { "changeAssignment" },
                { "changeAssignmentStatus" },
                { "showAssignments" }
        };
    }

    @Test
    @UseDataProvider("adminCommandsDataProvider")
    public void testHasAccessShouldReturnTrueWhenAdminAndAdminCommandSupplied(String command){

        //given

        //when
        boolean actual = controller.hasAccess(command, ADMIN);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    @UseDataProvider("trainerCommandsDataProvider")
    public void testHasAccessShouldReturnTrueWhenTrainerAndTrainerCommandSupplied(String command){

        //given

        //when
        boolean actual = controller.hasAccess(command, TRAINER);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    @UseDataProvider("clientCommandsDataProvider")
    public void testHasAccessShouldReturnTrueWhenClientAndClientCommandSupplied(String command){

        //given

        //when
        boolean actual = controller.hasAccess(command, CLIENT);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testHasAccessShouldReturnTrueWhenNullUserAndShowHomePageCommandSupplied(){
        //given

        //when
        boolean actual = controller.hasAccess(SHOW_HOME_PAGE_COMMAND, null);

        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testHasAccessShouldReturnFalseWhenNullCommandSupplied(){
        //given

        //when
        boolean actual = controller.hasAccess(null, CLIENT);

        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testHasAccessShouldReturnFalseWhenHasNotAccess(){
        //given

        //when
        boolean actual = controller.hasAccess(ADMIN_COMMAND, CLIENT);

        //then
        Assert.assertFalse(actual);
    }

}