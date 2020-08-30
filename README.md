#  Open Liberty Additional Testsuite
-------------------------------------
## A PROJECT UNDER THE ΙΔΕΑ STATEMENT
--------------------------------------

How to run :

1. export OPENLIBERTY_VERSION='the version of OpenLiberty features-bom you would like to use'
2. mvn clean install -Dopenliberty (It should be executed with appropriate apache maven version, e.g. 3.5.3 works)


### USING THE AT FEATURE STRUCTURE 
-----------------------------------

How to enable AT Feature Sturcture :

1. export OPENLIBERTY_VERSION='the version of OpenLiberty features-bom you would like to use'
2. export FEATURE_LIST='path to the feature list of the openliberty server' 
   - e.g. an example of such feature list can be found here  :
     https://github.com/panossot/OAT/blob/master/features.txt
3. mvn clean install -Dopenliberty 
   - only if the all required features of the each test are included in the server the test is enabled for the specific server, e.g. https://github.com/panossot/OAT/blob/master/modules/src/main/java/io/openliberty/oat/jaxb/JaxbTest.java#L96

## License

Code distributed under [GNU Lesser General Public License Version 2.1](http://www.gnu.org/licenses/lgpl-2.1-standalone.html).

