pipeline {
    agent {
        label 'windows' // Use Windows agent
    }

    parameters {
        // Environment selection
        choice(
                name: 'ENVIRONMENT',
                choices: ['local', 'dev', 'qa', 'prod'],
                description: 'Select the environment to run tests against'
        )

        // Browser selection
        choice(
                name: 'BROWSER',
                choices: ['chrome', 'firefox', 'edge'],
                description: 'Select browser for test execution'
        )

        // Multiple TestNG Suite Selection
        extendedChoice(
                name: 'TEST_SUITES',
                type: 'PT_CHECKBOX',
                value: 'SmokeSuite,CriticalSuite,RegressionSuite',
                defaultValue: 'SmokeSuite',
                description: 'Select one or more TestNG suite XML files to execute (multi-select)',
                visibleItemCount: 9,
                multiSelectDelimiter: ','
        )

        // Execute suites in parallel or sequential
        choice(
                name: 'EXECUTION_MODE',
                choices: ['SEQUENTIAL', 'PARALLEL'],
                description: 'Run selected suites sequentially or in parallel?'
        )

        // Specific test groups/tags (optional)
        string(
                name: 'TEST_GROUPS',
                defaultValue: '',
                description: 'Enter specific TestNG groups to run (comma-separated, e.g., smoke,critical). Leave empty to run all groups in selected suites.'
        )

        // Exclude specific groups
        string(
                name: 'EXCLUDE_GROUPS',
                defaultValue: '',
                description: 'Enter TestNG groups to exclude (comma-separated). Leave empty to include all.'
        )

        // Thread count for parallel execution
        choice(
                name: 'THREAD_COUNT',
                choices: ['1', '2', '3', '4', '5', '8', '10'],
                description: 'Number of parallel threads (for parallel suite execution)'
        )

        // Parallel execution level within suite
        choice(
                name: 'PARALLEL_MODE',
                choices: ['none', 'methods', 'tests', 'classes', 'instances'],
                description: 'TestNG parallel execution mode within each suite'
        )

        // Headless mode
        booleanParam(
                name: 'HEADLESS',
                defaultValue: true,
                description: 'Run tests in headless mode?'
        )

        // Generate reports
        booleanParam(
                name: 'GENERATE_ALLURE_REPORT',
                defaultValue: true,
                description: 'Generate Allure Report?'
        )

        // Take screenshots
        booleanParam(
                name: 'SCREENSHOT_ON_FAILURE',
                defaultValue: true,
                description: 'Capture screenshots on test failures?'
        )

        // Retry failed tests
        choice(
                name: 'RETRY_COUNT',
                choices: ['0', '1', '2', '3'],
                description: 'Number of times to retry failed tests (0 = no retry)'
        )

        // Continue on failure
        booleanParam(
                name: 'CONTINUE_ON_FAILURE',
                defaultValue: true,
                description: 'Continue executing remaining suites if one suite fails?'
        )

        // Clean workspace before build
        booleanParam(
                name: 'CLEAN_WORKSPACE',
                defaultValue: true,
                description: 'Clean workspace before starting the build?'
        )
    }

    environment {
        // Maven and Java settings for Windows
        MAVEN_HOME = 'C:\\Program Files\\apache-maven-3.9.11'
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-24'
        PATH = "${MAVEN_HOME}\\bin;${JAVA_HOME}\\bin;${env.PATH}"

        // Test configuration
        BASE_URL = getBaseUrl("${params.ENVIRONMENT}")
        BROWSER_NAME = "${params.BROWSER}"

        // Workspace paths
        WORKSPACE_DIR = "${env.WORKSPACE}"
        TEST_RESOURCES = "${env.WORKSPACE}\\src\\test\\resources"
        REPORTS_DIR = "${env.WORKSPACE}\\target\\reports"
        SCREENSHOTS_DIR = "${env.WORKSPACE}\\target\\screenshots"
        LOGS_DIR = "${env.WORKSPACE}\\target\\logs"
        ALLURE_RESULTS = "${env.WORKSPACE}\\target\\allure-results"
        SUREFIRE_REPORTS = "${env.WORKSPACE}\\target\\surefire-reports"

        // Maven options
        MAVEN_OPTS = "-Xmx2048m -XX:MaxPermSize=512m"
    }

    stages {
        stage('Pre-Build Setup') {
            steps {
                script {
                    echo '========== Pre-Build Setup =========='

                    // Display build information
                    bat '''
                        echo Build Information:
                        echo ====================
                        echo Computer: %COMPUTERNAME%
                        echo User: %USERNAME%
                        echo Workspace: %WORKSPACE%
                        echo Java Version:
                        java -version
                        echo.
                        echo Maven Version:
                        mvn -version
                    '''
                }
            }
        }

        stage('Display Configuration') {
            steps {
                script {
                    echo "========== Test Execution Configuration =========="
                    echo "Environment: ${params.ENVIRONMENT}"
                    echo "Base URL: ${BASE_URL}"
                    echo "Browser: ${params.BROWSER}"
                    echo "Selected Test Suites: ${params.TEST_SUITES}"
                    echo "Execution Mode: ${params.EXECUTION_MODE}"
                    echo "Test Groups (Include): ${params.TEST_GROUPS ?: 'All groups'}"
                    echo "Exclude Groups: ${params.EXCLUDE_GROUPS ?: 'None'}"
                    echo "Thread Count: ${params.THREAD_COUNT}"
                    echo "Parallel Mode: ${params.PARALLEL_MODE}"
                    echo "Headless Mode: ${params.HEADLESS}"
                    echo "Retry Count: ${params.RETRY_COUNT}"
                    echo "Continue on Failure: ${params.CONTINUE_ON_FAILURE}"
                    echo "=================================================="

                    // Store suite list in environment variable
                    env.SUITE_LIST = params.TEST_SUITES
                }
            }
        }

        stage('Cleanup Workspace') {
            when {
                expression { params.CLEAN_WORKSPACE == true }
            }
            steps {
                echo 'Cleaning workspace...'
                bat '''
                    if exist target (
                        echo Removing target directory...
                        rmdir /s /q target
                    )
                    if exist allure-results (
                        rmdir /s /q allure-results
                    )
                    if exist test-output (
                        rmdir /s /q test-output
                    )
                    echo Workspace cleaned successfully
                '''
            }
        }

        stage('Checkout Code') {
            steps {
                echo 'Checking out source code from SCM...'
                checkout scm
            }
        }

        stage('Validate TestNG Suites') {
            steps {
                script {
                    echo 'Validating TestNG suite XML files...'

                    def suites = params.TEST_SUITES.split(',')
                    def validSuites = []
                    def invalidSuites = []

                    suites.each { suite ->
                        def suiteFile = getSuiteFilePath(suite.trim())

                        // Check if file exists
                        def fileExists = bat(
                                script: "@echo off & if exist \"${suiteFile}\" (echo EXISTS) else (echo NOT_FOUND)",
                                returnStdout: true
                        ).trim()

                        if (fileExists.contains('EXISTS')) {
                            validSuites.add(suite.trim())
                            echo "✓ Found: ${suiteFile}"
                        } else {
                            invalidSuites.add(suite.trim())
                            echo "✗ Not Found: ${suiteFile}"
                        }
                    }

                    if (!invalidSuites.isEmpty()) {
                        echo "WARNING: Following suite files not found: ${invalidSuites.join(', ')}"
                    }

                    if (validSuites.isEmpty()) {
                        error("No valid TestNG suite XML files found. Aborting build.")
                    }

                    env.VALID_SUITES = validSuites.join(',')
                    echo "Valid suites to execute: ${env.VALID_SUITES}"
                }
            }
        }

        stage('Create Required Directories') {
            steps {
                bat '''
                    if not exist target mkdir target
                    if not exist target\\reports mkdir target\\reports
                    if not exist target\\screenshots mkdir target\\screenshots
                    if not exist target\\logs mkdir target\\logs
                    if not exist target\\allure-results mkdir target\\allure-results
                    if not exist target\\surefire-reports mkdir target\\surefire-reports
                    if not exist target\\extent-reports mkdir target\\extent-reports
                    echo All required directories created
                '''
            }
        }

        stage('Build Project') {
            steps {
                echo 'Compiling the project...'
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('Execute Test Suites') {
            steps {
                script {
                    def suites = env.VALID_SUITES.split(',')

                    if (params.EXECUTION_MODE == 'PARALLEL') {
                        echo "Executing ${suites.size()} suites in PARALLEL mode..."
                        executeTestSuitesInParallel(suites)
                    } else {
                        echo "Executing ${suites.size()} suites in SEQUENTIAL mode..."
                        executeTestSuitesSequentially(suites)
                    }
                }
            }
        }

        stage('Merge Test Results') {
            steps {
                script {
                    echo 'Merging test results from all suites...'

                    bat '''
                        echo Checking surefire reports...
                        if exist target\\surefire-reports (
                            dir /b target\\surefire-reports\\*.xml
                        ) else (
                            echo No surefire reports found
                        )
                        
                        echo Checking allure results...
                        if exist target\\allure-results (
                            dir /b target\\allure-results
                        ) else (
                            echo No allure results found
                        )
                    '''
                }
            }
        }

        stage('Generate Allure Report') {
            when {
                expression { params.GENERATE_ALLURE_REPORT == true }
            }
            steps {
                script {
                    echo 'Generating Allure Report...'

                    try {
                        allure([
                                includeProperties: false,
                                jdk: '',
                                properties: [],
                                reportBuildPolicy: 'ALWAYS',
                                results: [[path: 'target/allure-results']]
                        ])
                        echo 'Allure report generated successfully'
                    } catch (Exception e) {
                        echo "Allure report generation failed: ${e.message}"
                    }
                }
            }
        }

        stage('Publish Test Results') {
            steps {
                script {
                    echo 'Publishing JUnit test results...'

                    try {
                        junit(
                                allowEmptyResults: true,
                                testResults: '**/target/surefire-reports/*.xml',
                                skipPublishingChecks: false
                        )
                    } catch (Exception e) {
                        echo "JUnit result publishing failed: ${e.message}"
                    }

                    // Archive artifacts
                    try {
                        archiveArtifacts(
                                artifacts: '**/target/screenshots/**,**/target/logs/**,**/target/surefire-reports/**',
                                allowEmptyArchive: true,
                                fingerprint: true
                        )
                        echo 'Artifacts archived successfully'
                    } catch (Exception e) {
                        echo "Artifact archiving failed: ${e.message}"
                    }
                }
            }
        }

        stage('Test Execution Summary') {
            steps {
                script {
                    echo 'Generating test execution summary...'

                    try {
                        def testResults = junit(testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true)

                        def summary = """
========== TEST EXECUTION SUMMARY ==========
Environment: ${params.ENVIRONMENT}
Browser: ${params.BROWSER}
Suites Executed: ${env.VALID_SUITES}
Execution Mode: ${params.EXECUTION_MODE}

TEST RESULTS:
Total Tests: ${testResults.totalCount}
Passed: ${testResults.passCount} ✓
Failed: ${testResults.failCount} ✗
Skipped: ${testResults.skipCount} ⊘

Pass Rate: ${testResults.totalCount > 0 ? ((testResults.passCount * 100) / testResults.totalCount).round(2) : 0}%
============================================
"""
                        echo summary

                        // Write summary to file
                        writeFile file: 'target/execution-summary.txt', text: summary
                        archiveArtifacts artifacts: 'target/execution-summary.txt', allowEmptyArchive: true

                        // Set build status
                        if (testResults.failCount > 0) {
                            currentBuild.result = 'UNSTABLE'
                            echo "⚠️ Build marked as UNSTABLE due to ${testResults.failCount} test failure(s)"
                        } else {
                            echo "✓ All tests passed successfully!"
                        }

                    } catch (Exception e) {
                        echo "Could not generate test summary: ${e.message}"
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                echo 'Post-build actions...'

                // Generate final build summary
                bat """
                    @echo off
                    echo ========== BUILD SUMMARY ========== > build-summary.txt
                    echo Job Name: ${env.JOB_NAME} >> build-summary.txt
                    echo Build Number: ${env.BUILD_NUMBER} >> build-summary.txt
                    echo Build Status: ${currentBuild.result ?: 'SUCCESS'} >> build-summary.txt
                    echo Environment: ${params.ENVIRONMENT} >> build-summary.txt
                    echo Browser: ${params.BROWSER} >> build-summary.txt
                    echo Test Suites: ${params.TEST_SUITES} >> build-summary.txt
                    echo Execution Mode: ${params.EXECUTION_MODE} >> build-summary.txt
                    echo Build URL: ${env.BUILD_URL} >> build-summary.txt
                    echo Timestamp: %date% %time% >> build-summary.txt
                    echo =================================== >> build-summary.txt
                """

                archiveArtifacts artifacts: 'build-summary.txt', allowEmptyArchive: true
            }
        }

        success {
            echo '✅ Pipeline completed successfully!'
            script {
                sendEmailNotification('SUCCESS')
            }
        }

        failure {
            echo '❌ Pipeline failed!'
            script {
                sendEmailNotification('FAILURE')
            }
        }

        unstable {
            echo '⚠️ Pipeline completed with test failures!'
            script {
                sendEmailNotification('UNSTABLE')
            }
        }

        cleanup {
            echo 'Performing cleanup...'
            // Add any cleanup tasks here if needed
        }
    }
}

// ==================== HELPER FUNCTIONS ====================

// Function to get base URL based on environment
def getBaseUrl(environment) {
    def urls = [
            'local': 'https://naveenautomationlabs.com/opencart/index.php?route=account/login',
            'dev': 'https://naveenautomationlabs.com/opencart/index.php?route=account/login',
            'qa': 'https://naveenautomationlabs.com/opencart/index.php?route=account/login',
            'prod': 'https://naveenautomationlabs.com/opencart/index.php?route=account/login'
    ]
    return urls[environment] ?: urls['qa']
}

// Function to get TestNG suite file path
def getSuiteFilePath(suiteName) {
    return "src\\test\\resources\\testrunners\\${suiteName}.xml"
}

// Function to build Maven command for a specific suite
def buildMavenCommand(suiteName) {
    def suiteFile = getSuiteFilePath(suiteName)
    def command = "mvn test -Dsurefire.suiteXmlFiles=${suiteFile}"

    // Add browser
    command += " -Dbrowser=${params.BROWSER}"

 /*   // Add base URL
    command += " -DbaseUrl=${BASE_URL}"

    // Add environment
    command += " -Denvironment=${params.ENVIRONMENT}"

    // Add groups if specified
    if (params.TEST_GROUPS) {
        command += " -Dgroups=${params.TEST_GROUPS}"
    }

    // Add exclude groups if specified
    if (params.EXCLUDE_GROUPS) {
        command += " -DexcludedGroups=${params.EXCLUDE_GROUPS}"
    }

    // Add parallel mode
    if (params.PARALLEL_MODE != 'none') {
        command += " -Dparallel=${params.PARALLEL_MODE}"
        command += " -DthreadCount=${params.THREAD_COUNT}"
    }

    // Add headless mode
    if (params.HEADLESS) {
        command += " -Dheadless=true"
    }

    // Add screenshot on failure
    if (params.SCREENSHOT_ON_FAILURE) {
        command += " -DscreenshotOnFailure=true"
    }

    // Add retry count
    if (params.RETRY_COUNT != '0') {
        command += " -DretryCount=${params.RETRY_COUNT}"
    }

    // Add suite name for identification
    command += " -DsuiteName=${suiteName}"*/

    // Maven options
    command += " -DargLine=\"-Dfile.encoding=UTF-8\""

    return command
}

// Function to execute test suites sequentially
def executeTestSuitesSequentially(suites) {
    suites.each { suite ->
        def suiteName = suite.trim()

        stage("Execute Suite: ${suiteName}") {
            echo "Executing ${suiteName} suite..."

            def mavenCommand = buildMavenCommand(suiteName)
            echo "Command: ${mavenCommand}"

            try {
                bat """
                    @echo off
                    echo ========== Executing ${suiteName} Suite ==========
                    ${mavenCommand}
                    set EXIT_CODE=%ERRORLEVEL%
                    echo Suite ${suiteName} execution completed with exit code: %EXIT_CODE%
                    exit 0
                """
                echo "✓ ${suiteName} suite execution completed"
            } catch (Exception e) {
                echo "✗ ${suiteName} suite execution failed: ${e.message}"

                if (!params.CONTINUE_ON_FAILURE) {
                    error("Suite ${suiteName} failed and CONTINUE_ON_FAILURE is disabled. Stopping execution.")
                }
            }
        }
    }
}

// Function to execute test suites in parallel
def executeTestSuitesInParallel(suites) {
    def parallelStages = [:]

    suites.each { suite ->
        def suiteName = suite.trim()

        parallelStages["Suite: ${suiteName}"] = {
            stage("Execute: ${suiteName}") {
                echo "Executing ${suiteName} suite in parallel..."

                def mavenCommand = buildMavenCommand(suiteName)
                echo "Command: ${mavenCommand}"

                try {
                    bat """
                        @echo off
                        echo ========== Executing ${suiteName} Suite (Parallel) ==========
                        ${mavenCommand}
                        set EXIT_CODE=%ERRORLEVEL%
                        echo Suite ${suiteName} execution completed with exit code: %EXIT_CODE%
                        exit 0
                    """
                    echo "✓ ${suiteName} suite execution completed"
                } catch (Exception e) {
                    echo "✗ ${suiteName} suite execution failed: ${e.message}"

                    if (!params.CONTINUE_ON_FAILURE) {
                        error("Suite ${suiteName} failed and CONTINUE_ON_FAILURE is disabled.")
                    }
                }
            }
        }
    }

    // Execute all suites in parallel
    parallel parallelStages
}

// Function to send email notifications
def sendEmailNotification(buildStatus) {
    def statusColor = [
            'SUCCESS': 'green',
            'FAILURE': 'red',
            'UNSTABLE': 'orange'
    ]

    def statusIcon = [
            'SUCCESS': '✅',
            'FAILURE': '❌',
            'UNSTABLE': '⚠️'
    ]

    try {
        def testResults = junit(testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true)

        emailext(
                subject: "${statusIcon[buildStatus]} ${buildStatus}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2 style="color: ${statusColor[buildStatus]};">
                        ${statusIcon[buildStatus]} Test Execution ${buildStatus}
                    </h2>
                    
                    <h3>Build Information</h3>
                    <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse;">
                        <tr>
                            <td><b>Job Name:</b></td>
                            <td>${env.JOB_NAME}</td>
                        </tr>
                        <tr>
                            <td><b>Build Number:</b></td>
                            <td>${env.BUILD_NUMBER}</td>
                        </tr>
                        <tr>
                            <td><b>Build Status:</b></td>
                            <td style="color: ${statusColor[buildStatus]}; font-weight: bold;">${buildStatus}</td>
                        </tr>
                        <tr>
                            <td><b>Environment:</b></td>
                            <td>${params.ENVIRONMENT}</td>
                        </tr>
                        <tr>
                            <td><b>Browser:</b></td>
                            <td>${params.BROWSER}</td>
                        </tr>
                        <tr>
                            <td><b>Test Suites:</b></td>
                            <td>${params.TEST_SUITES}</td>
                        </tr>
                        <tr>
                            <td><b>Execution Mode:</b></td>
                            <td>${params.EXECUTION_MODE}</td>
                        </tr>
                    </table>
                    
                    <h3>Test Results</h3>
                    <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse;">
                        <tr>
                            <td><b>Total Tests:</b></td>
                            <td>${testResults.totalCount}</td>
                        </tr>
                        <tr>
                            <td><b>Passed:</b></td>
                            <td style="color: green; font-weight: bold;">${testResults.passCount}</td>
                        </tr>
                        <tr>
                            <td><b>Failed:</b></td>
                            <td style="color: red; font-weight: bold;">${testResults.failCount}</td>
                        </tr>
                        <tr>
                            <td><b>Skipped:</b></td>
                            <td>${testResults.skipCount}</td>
                        </tr>
                        <tr>
                            <td><b>Pass Rate:</b></td>
                            <td><b>${testResults.totalCount > 0 ? ((testResults.passCount * 100) / testResults.totalCount).round(2) : 0}%</b></td>
                        </tr>
                    </table>
                    
                    <h3>Links</h3>
                    <ul>
                        <li><a href="${env.BUILD_URL}">Build Details</a></li>
                        <li><a href="${env.BUILD_URL}console">Console Output</a></li>
                        <li><a href="${env.BUILD_URL}testReport/">Test Results</a></li>
                        <li><a href="${env.BUILD_URL}allure/">Allure Report</a></li>
                    </ul>
                    
                    <p style="margin-top: 20px; color: #666; font-size: 12px;">
                        This is an automated email from Jenkins. Please do not reply.
                    </p>
                </body>
                </html>
            """,
                to: 'gowtham.kandanuru@gmail.com',
                attachLog: buildStatus == 'FAILURE',
                mimeType: 'text/html'
        )

        echo "Email notification sent successfully"
    } catch (Exception e) {
        echo "Failed to send email notification: ${e.message}"
    }
}