### Assessment for Lab 10

#### Total score: _100.0_ / _100_

Run on March 03, 13:54:04 PM.


### Necessary Files and Structure

+  _10_ / _10_ : Pass: Check that directory "labs" exists.

+  _10_ / _10_ : Pass: Check that directory "labs/lab10_advanced_git" exists.


### Git Usage

+  _10_ / _10_ : Pass: Run git ls-remote to check for existence of specific branch- Branch devel found

+ Pass: Checkout devel branch.



+ Pass: Run git ls-remote gather all branches in repo

		b6b28fc47ae16f3849c6fc9f8613c4752b7ab7a9	refs/heads/devel

		77ff5ef80043d072e9a1ca3938e82fc91e6e9dbc	refs/heads/enhancement/17-google-style-not-compliant

		6dfc7e20a87d3398ebb8070ae7fcf84712039821	refs/heads/fix/16-compile-issue

		b362cfaddaf9aa7c7b722f9d1d3262a1a11c1af8	refs/heads/master

		9da6b83259c78606116a4fd5c4e51075e232016a	refs/heads/support-code



+  _10_ / _10_ : Pass: Checking for the correct number of branches

Sufficient branches found (found=2, required=2):

enhancement/17-google-style-not-compliant

fix/16-compile-issue


#### Counting commits on devel

+ Pass: Checkout devel branch.



+ Pass: Gather commit history

		[Sami Frank] 2020-02-25 (HEAD -> devel, origin/devel) regenerate feedback 

		[Sami Frank] 2020-02-25 regenerate feedback 


		[Sami Frank] 2020-02-24 hides /html 

		[Sami Frank] 2020-02-24 regenerate feedback 


		[Sami Frank] 2020-02-24 (origin/enhancement/17-google-style-not-compliant, enhancement/17-google-style-not-compliant) makes code google style compliant 

		[Sami Frank] 2020-02-24 makes code google style compliant 

		[Sami Frank] 2020-02-24 rmv file 



		[Sami Frank] 2020-02-24 (origin/fix/16-compile-issue, fix/16-compile-issue) update gitignore 

		[Sami Frank] 2020-02-24 rmv build files 

		[Sami Frank] 2020-02-24 fixed issue 16 compile error. reverted back to prior code 

		[Sami Frank] 2020-02-24 test 

		[Sami Frank] 2020-02-24 test 

		[Sami Frank] 2020-02-24 test 

		[Sami Frank] 2020-02-24 test rmv 

		[Sami Frank] 2020-02-24 test 

		[Sami Frank] 2020-02-24 adds src folder from lab10 

		[Sami Frank] 2020-02-24 rmv project/src 

		[Sami Frank] 2020-02-24 test 


		[Sami Frank] 2020-02-24 Merge branch 'support-code' of https://github.umn.edu/umn-csci-3081-s20/csci3081-shared-upstream into support-code 



		[Sami Frank] 2020-02-24 test 

		[Sami Frank] 2020-02-24 style 

		[Sami Frank] 2020-02-24 style 

		[Sami Frank] 2020-02-24 test 

		[Sami Frank] 2020-02-24 add files as per lab09 states 

		[Sami Frank] 2020-02-24 test 





		[Sami Frank] 2020-02-24 test 

		[Sami Frank] 2020-02-24 add gitignore 

		[Sami Frank] 2020-02-24 pull upstream 












+  _5_ / _5_ : Pass: Check git commit history
Sufficient commits (found=28,required=4)


### Git Issue Usage

+ Pass: Configuring GHI

+ Pass: Run ghi for total number of open issues in Github repo (Found: 0)

+ Pass: Run ghi for total number of closed issues in Github repo (Found: 5)

[CLOSED issue #19] :  Enhancement/17 google style not compliant ↑

[CLOSED issue #18] :  Fix/16 compile issue ↑

[CLOSED issue #17] :  google style not-compliant [enhancement] @fran0942

[CLOSED issue #16] :  compile issue [bug] @fran0942

[CLOSED issue #15] :  Support code ↑





+  _10.0_ / _10_ : Pass: Run ghi for total number of issues in Github repo (Found: 5, Expected: 2) 

 [OPEN issue #] : 

[CLOSED issue #19] :  Enhancement/17 google style not compliant ↑

[CLOSED issue #18] :  Fix/16 compile issue ↑

[CLOSED issue #17] :  google style not-compliant [enhancement] @fran0942

[CLOSED issue #16] :  compile issue [bug] @fran0942

[CLOSED issue #15] :  Support code ↑

 




### Test that code on  devel compiles

+ Pass: Checkout devel branch.



+  _10_ / _10_ : Pass: Check that directory "project/src" exists.

+ Pass: Change into directory "project/src".

+  _5_ / _5_ : Pass: Check that file "makefile" exists.

+  _30_ / _30_ : Pass: Check that make compiles.



#### Total score: _100.0_ / _100_

