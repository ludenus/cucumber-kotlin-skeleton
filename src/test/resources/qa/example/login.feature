Feature: Login

  Scenario: Admin is able to login
    Given ADMIN navigates Login page
    When ADMIN provides correct email, password and clicks 'log in' button
    Then Landing page is opened

  Scenario: TenantAdmin is able to login
    Given TENANT_ADMIN navigates Login page
    When TENANT_ADMIN provides correct email, password and clicks 'log in' button
    Then Landing page is opened

  Scenario: RestrictedUser is able to login
    Given RESTRICTED_USER navigates Login page
    When RESTRICTED_USER provides correct email, password and clicks 'log in' button
    Then Landing page is opened
