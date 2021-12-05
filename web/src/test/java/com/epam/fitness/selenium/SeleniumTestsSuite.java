package com.epam.fitness.selenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AuthorizationWithInvalidPasswordTest.class, AuthorizationTest.class, SendFeedbackTest.class, AcceptAssignmentTest.class, CancelAssignmentTest.class,
		ChangeAssignmentTest.class, AddTrainerTest.class, ChangeDiscountTest.class, ChangeLanguageBETest.class, ChangeLanguageRUTest.class, ChangeLanguageENTest.class})
public class SeleniumTestsSuite {

}