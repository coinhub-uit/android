default: run-hot

alias r := run-hot
alias rd := refresh-dependencies
alias b := build

gradlew := if os() == 'windows' { './gradlew.bat' } else { './gradlew' }

refresh-dependencies:
  {{gradlew}} --refresh-dependencies

build:
  {{gradlew}} build

gen-ctags:
  ctags -R --languages=Kotlin --kinds-Kotlin=+p ./app
