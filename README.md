# gas-station-demo
Project to setup pumps in a gas station and serve the gas from different pumps based on customer requirements and availability concurrently



# Completed :
- Created new module and added given module as a dependency
- Implemented all the functionalities in the new module
- Added junit test cases for happy scenarios (9 test cases)

# Pending: 
 - Multithreading implementation
 - Proper logging
 - Test case improvements for edge cases
 
# Considerations:
 - Used spring boot to create the new module. Added H2 dependency with the assumption that the data can be saved in in-memory DB if time permits.
 - No DB integration is done, instead using memory to store and manipulate data.
 - Assumption is that the amount in GasPump is the available quantity in litres.
