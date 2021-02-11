# am-vodafone/sonar-swift 

this repo is a fork repo from [Idean/sonar-swift](https://github.com/Idean/sonar-swift)

## Purpose 

this fork repo has been created to add some custom rule definition for sonar-swift opensource plugin  (linked above)



## Steps to add custom  rules to current code 

### files to be changed
        we need to define rule names in different files of the repo with the current structure
	Structure may be changed in the future for new versions of the plugin 
	so make sure to use the fork , and if the newer version has different structure feel free to create a PR to this fork. 


	1- swiftlang/src/main/resources/com/sonar/sqale/swiftlint-model.xml
	2- swiftlang/src/main/resources/org/sonar/plugins/swiftlint/profile-swiftlint.xml 
	3- swiftlang/src/main/resources/org/sonar/plugins/swiftlint/rules.json
	

	Please refere to [this commit](https://github.com/am-vodafone/sonar-swift/commit/7d469b2eb6b88fa090be17de5d567f4318b8e085) to know how to edit each file. 

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.
