# Sample Custom Tabs

## Testing

- Right click on `test-page.html` and `Open In > Browser`.
- Run **Ngrok** `ngrok http [PORT]`.
- Replace url in `MainFragment.kt`
- Test deep link
  ```
  adb shell am start -W -a android.intent.action.VIEW \
    -c android.intent.category.DEFAULT \
    -d "https://example.com/callback?code=1234"
  ```
