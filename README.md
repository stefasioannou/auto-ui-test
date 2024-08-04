Certainly! Here is the corrected `README.md` file with proper closing of the code blocks:

```markdown
# Automation Testing Task UI

This project contains a series of UI tests to automate interactions on the xm.com website using different browser screen resolutions. The tests are organized to run with the following screen resolutions:

1. Maximum (supported by your display)
2. 1024 x 768
3. 800 x 600

```
```sh
This project contains a series of tests to validate different browser resolutions using different browsers. The tests are:

- `MaxResolutionTest`
- `MediumResolutionTest`
- `SmallResolutionTest`

```
```sh
## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven 3.6.0 or higher
- Browsers: Chrome, Edge, Firefox (make sure they are installed and available in your system's PATH)

```
```sh
## Use Case

1. Open the Home page (make any check here if needed).
2. Click the “Trading” link located at the top menu (make any check here if needed).
3. Click on “Stocks” (make any check here if needed).
4. Choose the "Norway" stock filter.
5. Get data from the table for the "Orkla ASA (ORK.OL)" (make any check here if needed).
6. Click on the "READ MORE" at this brand.
7. Compare the data from the previous table with data in "Trading Conditions".
   
```

## Running Tests

To run the tests with different browsers, use the following Maven commands. Replace `your_browser_value` with the desired browser (`chrome`, `edge`, `firefox`).

### Running MaxResolutionTest

#### Chrome
```sh
mvn test -Dtest=MaxResolutionTest -DBrowser=chrome
```

#### Edge
```sh
mvn test -Dtest=MaxResolutionTest -DBrowser=edge
```

#### Firefox
```sh
mvn test -Dtest=MaxResolutionTest -DBrowser=firefox
```

### Running MediumResolutionTest

#### Chrome
```sh
mvn test -Dtest=MediumResolutionTest -DBrowser=chrome
```

#### Edge
```sh
mvn test -Dtest=MediumResolutionTest -DBrowser=edge
```

#### Firefox
```sh
mvn test -Dtest=MediumResolutionTest -DBrowser=firefox
```

### Running SmallResolutionTest

#### Chrome
```sh
mvn test -Dtest=SmallResolutionTest -DBrowser=chrome
```

#### Edge
```sh
mvn test -Dtest=SmallResolutionTest -DBrowser=edge
```

#### Firefox
```sh
mvn test -Dtest=SmallResolutionTest -DBrowser=firefox
```

## Notes

- Ensure that the browsers specified (Chrome, Edge, Firefox) are installed on your system and available in your system's PATH.
- Adjust the test logic as needed for your specific testing requirements.
```

This should properly format the code blocks in the `README.md` file.