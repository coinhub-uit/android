default: run

alias r := run
alias b := build

gradlew := if os() == 'windows' { './gradlew.bat' } else { './gradlew' }

build:
  {{gradlew}} build

run:
  {{gradlew}} run
