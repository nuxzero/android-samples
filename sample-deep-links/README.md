# Sample Deep Links

## Test

To test deep links you can use ADB to test with the Activity Manager `am`. 

The general syntax for testing an intent filter URL with ADB is:
```bash 
adb shell am start -W -a android.intent.action.VIEW -d <URI> <PACKAGE>
```
Example:
```bash 
adb shell am start -W -a android.intent.action.VIEW -d "example://test" com.example.app
```
Example with query parameter:
```bash 
adb shell am start -W -a android.intent.action.VIEW -d "example://test?message=Hello" com.example.app
```
