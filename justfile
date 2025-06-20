default: run-debug

alias rd := refresh-dependencies

gradlew := if os() == 'windows' { './gradlew.bat' } else { './gradlew' }

refresh-dependencies:
  {{gradlew}} --refresh-dependencies

run-debug:
  {{gradlew}} app:assembleDebug
  {{gradlew}} app:installDebug
  just run-on-device

adb-logcat:
  adb logcat | grep com.coinhub.android

run-on-device:
  adb shell am start -n com.coinhub.android/.MainActivity

scrcpy:
  scrcpy

scrcpy-30:
  scrcpy --max-fps=30

gen-ctags:
  ctags -R --languages=Kotlin --kinds-Kotlin=+p ./app
