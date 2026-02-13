package com.ui.opencart.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListener implements IRetryAnalyzer {

int retryCount = 0;

int maxRetryCount = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if(!iTestResult.isSuccess())
        {
            if(retryCount < maxRetryCount)
            {
                retryCount++;
                return true;
            }else {
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        }else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
