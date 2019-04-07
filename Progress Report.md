# Progress Report

## 2019/04/02
- Modify the _README.md_ for brief introduction, acknowledgement, and installation process
- Modify all rules in **main** by combining variables and functions in Lua files back to the corresponding Java file
- Modify test rules in **test** for success build by maven

## 2019/04/03
- Generate and format _UserInput.csv_ file for user to input values of parameters
- Create class _ParameterFetch_ to fetch user input and assign values
- Change property names in _Monopoly Map.csv_ to US version
- Change property names in _ExampleOfCards.csv_ to US version
### New features
- Support user input and assign values to corresponding parameters, including:
    - Number of simulation to run
    - Number of turns to determine an endless game
    - Number of players in a simulation, and their initial endowments
    - Salary amount
    - Income tax amount
    - Luxury tax amount

## 2019/04/05
- Modify _UserInput.csv_, _ParameterFetch.java_ and related files to support calculation and choosing between a percentage of wealth and a fix amount
- Set a percentage to 0 means to use the fix amount only
### New Features
- Support calculation and choosing between a percentage of wealth and a fix amount of the following:
    - Salary
    - Income tax
    - Luxury tax

## 2019/04/06
- Modify _UserInput.csv_, _ParameterFetch.java_ and related files to support applying an amount to a player when one visits a property or every fixed number of turns
- Set a turn to 0 means apply on visit
### New Features
- Support applying an amount to a player when one visits a property or every fixed number of turns for the following:
    - Salary
    - Income tax
    - Luxury tax

## To-Do
- [ ] Figure out the differences between the functions _canBuildHouseLua_ and _canBuildHouse_, and hotel ones the in _BuildRules.java_
- [ ] Implement features
    - [ ] Turn logger
    - [ ] Determine endless turn