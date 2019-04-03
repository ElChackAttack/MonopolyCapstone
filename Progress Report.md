# Progress Report

## Changes 
### 2019/04/02
- Modify the _README.md_ for brief introduction, acknowledgement, and installation process
- Modify all rules in **main** by combining variables and functions in Lua files back to the corresponding Java file
- Modify test rules in **test** for success build by maven

## To-Do
- [x] Modify the rules by combining _*Rule.java_ with _*Rules.lua_
    - [x] where to put the variable values?
- [ ] Add functions to read values of specific variables from outside (csv file)
    - [ ] initial endowment (in Main: when initializing players)
    - [ ] taxation (in TaxRules)
    - [ ] income (random: in GoRules, fixed: in Main)
- [ ] Figure out the differences between the functions _canBuildHouseLua_ and _canBuildHouse_, and hotel ones the in _BuildRules.java_
- [ ] Implement features