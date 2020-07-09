# Class mocking
General design document of how class mocks can be handled in MMock

## Constraints
Due to code generation constraints of MMock (dictated by lack of reflection in Kotlin Native)
class mocks have to comply to several restrictions.

To class be mocked it:
- must be open
- constructor arguments:
    - are non existent
    - are primitives
    - are interfaces (tentative: maybe codegen will help here)
    - have default values
    - their class constructor complies to the same constraints as above 
    
- unsafe mode:
    constructor can have any kind of arguments, as long they are not used for anything
    more then assigning to member during construction.