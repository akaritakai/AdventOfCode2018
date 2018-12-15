# Advent Of Code 2018 Solutions

[![Build Status](https://travis-ci.org/akaritakai/AdventOfCode2018.svg)](https://travis-ci.org/akaritakai/AdventOfCode2018)
[![Coverage Status](https://coveralls.io/repos/akaritakai/AdventOfCode2018/badge.svg)](https://coveralls.io/r/akaritakai/AdventOfCode2018)


This repo contains my Advent Of Code 2018 (currently ongoing) solutions in Java 11.

##Providing Your Puzzle Input

There are two supported methods for inputting your puzzle data into this application.

### Automatic Puzzle Fetcher (via Session Cookie)

First, get your cookie session data.

You will need to log into the Advent of Code website and then inspect your cookies.
If you are using Chrome, you can follow the directions [here](https://developers.google.com/web/tools/chrome-devtools/manage-data/cookies).

You will be looking for a cookie called `session`. It will contain a long sequence of hexadecimal digits.

Place that data into a file called `cookie.txt` in the project directory.

The application will use that data to automatically fetch your puzzle input for each day.

### Manual Input

This code will also look in a particular location on your local machine for puzzle input.

In the project directory, it will check a directory called `puzzle`.
Within that directory it will expect Day 1's input to be in a file called `1`, Day 2's input to be in a file called `2`, etc.
 
You can find your puzzle input for a given day by logging into the Advent of Code website and then navigating to the URL
for that puzzle's input.

The URL for your puzzle input will be at:
```
https://adventofcode.com/2018/day/${DAY}/input
```
where `${DAY}` is the day number of the puzzle.

As an example, Day 1's input is at https://adventofcode.com/2018/day/1/input,
Day 2's input is at https://adventofcode.com/2018/day/2/input, etc.

## Windows Instructions

1. Follow the instructions above for providing your puzzle input. 
2. Install JDK 11. You can follow the installation instructions [here](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html).
3. Open a command prompt and navigate to the project directory.
4. Run `.\gradlew.bat run`

## Mac Instructions

1. Follow the instructions above for providing your puzzle input. 
2. Install JDK 11. You can follow the installation instructions [here](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html). 
3. Open Terminal and navigate to the project directory.
4. Run `./gradlew run`


## Linux Instructions

1. Follow the instrcutions above for providing your puzzle input.
2. Install JDK 11. You can follow the generic installation instructions [here](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html).
   Alternatively, you may want to search on Google for installation instructions specific to your distro.
3. Open a shell and navigate to the project directory.
4. Run `./gradlew run`



