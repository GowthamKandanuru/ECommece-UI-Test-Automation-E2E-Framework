package com.api.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private Logger Logger = LogManager.getLogger(TestListener.class);

    public void onTestStart(ITestResult result) {
        Logger.info(result.getMethod().getMethodName()+" Test case started");
    }

    public void onTestSuccess(ITestResult result) {
        Logger.info(result.getMethod().getMethodName()+" Test case passed");
    }

    public void onTestFailure(ITestResult result) {
        Logger.error(result.getMethod().getMethodName()+" Test case failed");
    }

    public void onTestSkipped(ITestResult result) {
        Logger.info(result.getMethod().getMethodName()+" Test case Skipped");
    }

    public void onFinish(ITestContext context) {
        Logger.info("Test Suite Completed!!");
    }
    public void onStart(ITestContext context)
    {
        Logger.info("Test Suite Started !!");
    }

}
