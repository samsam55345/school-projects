/* Copyright [year] <Copyright Owner>
 *
 * ! \mainpage My Personal Index Page
 *
 *
 Welcome to Sami Franks implimentation of new features/code rework for CSCI 3081W Transit
 simulator.

 Here you will find a little more about the project, how to configure it,
 compile and execute.

 Transit Simulator:
    This project foundation was given to me by the instructor/TA's of CSCI 3081W
    at UMN. The basic functionality of this simulator is to look at specified routes(configured
    in config/config.txt) and send busses along those routes. Within this simulator there
    is functionality to create passengers and manage these passengers as they wait for the bus,
    ride the bus, all until they are set to get off the bus.

to configure/compile:
    configure new work mainly in src/ or in web_code/web/.
    I was provided a make file when given this project to work on. This make file will
    automatically look at the project/ directory and compile .cc files within it.
    to run make, go to directory project/src. once in the proper directory type 'make' into
    bash. type 'make clean' to remove compilation.

to execute:
    (This was copied from CSCI 3081W project readme)
    Step by step instructions for ssh users
    You can SSH using a Windows machine with Git Bash.

    **<port_number> below should be a number above 8000 that includes the last 3 digits of your student id.**
    **So, if your student id number is: 1459345, use 8345, or 9345**

    ssh -L <port number>:127.0.0.1:<port_number> <x500>@<cse_labs_computer>.cselabs.umn.edu

    for example ssh -L 8695:127.0.0.1:8695 fran0942@csel-kh1250-01.cselabs.umn.edu

    Navigate to base project directory(project/)
        make and start server:
        $ cd src
        $ make
        $ cd ..
        $./build/bin/vis_sim <port_number>
    You must run by doing ./build/bin/vis_sim <port_number>.
    You cannot cd to bin/ and run ./vis_sim <port_number>
    Navigate to the following address into the address bar of a browser (Firefox/Chrome) running on your LOCAL machine (e.g, your PC):
    http://127.0.0.1:<port_number>/web_graphics/project.html
    ex: http://127.0.0.1:8695/web_graphics/project.html


BUS FACTORY DISCUSSION:
    For the bus factory, there were two implimentations I considered.
    The first was an abstract factory, the second was a concrete factory.

    The abstract factory solves problems such as keeping an application
    independant of how its objects are created, and that a class can have
    a factory object created, instead of having objects declared directly
    from the class. An abstract factory would contain an interface that
    houses related objects without having to specify a concrete class
    for those objects. There is a UML of abstract factory structure on canvas.
    A pro for abstract is that it promotes consistency among objects, since all
    classes using the factory pattern inherit from an interface. One con is that
    its more complex to impliment. Because of this complexity, I decided to impliment
    a concrete factory pattern.

    A concrete factory pattern is also an interface for creating an object.
    the main difference between concrete vs abstract is that concrete factory
    pattern will know about subclasses where instantiation will happen. It is
    up to the subclasses that get called to create the object, but the factory
    facilitates which subclass should be called. There is a UML of concrete factory
    structure on canvas. A pro is that it allows for loose-coupling, meaning that
    while everything that needs to be connected to eachother is, its connected in
    a way that functions arent unnecessarily tied to other funcitons. A con to This
    implimentation is that the more types of busses needed could add congestion
    to the code.

    both are used as an abstraction in the code, so that the details
    of whats needed in the bus can be handled elsewhere, and not directly
    when the program needs to create a bus.

    I chose to implement a concrete factory in my enhancements to this project.


Designing and Implementing the Observer Pattern
    The observer pattern is essentially made up of a subject (in this case
    its called IObservable) and one or many obervers (called IObserver). In the
    subject 3 methods were implimented (Notify, register and clear observers).
    NotifyObservers was the hardest for me to implement since it took an observer
    as a parameter given the provided UML, but after clarifying in office hours that
    NotifyObservers should take BusData* as a parameter, it made more sense.
    RegisterObserver was more simple since it took an observer object and just added
    it to a vector of the same type. Similarly ClearObservers was also simple since
    it just had to clear a vactor of observers.

    There was also the IObserver class that needed to be made. It was pretty simple
    to impliment the Notify method and then it was called from NotifyObservers.

    Overall this observer pattern allowed another view that persisted and continually
    updated on the visual simulator to make it easier to track a specific bus.

Designing and Implementing the Decorator Pattern:

    The design and implimentation for the decorator pattern was to be used to easily
    show whether a route was inbound or outbound. The plan would be to also add to the
    decorator to change the intensity of the bus color based on how full a given bus
    was. The purpose for this priority was to use a decorator pattern. There would a way
    to complete this priority of coloring a route as well as changing intensity just by
    using an inhertiance structure, but givent the assignment that implimentation wasn't
    used. If it were to be used the base class would be IObservable. There could then
    be a Bus interface that inherits IObservable. Then, there could be a bus class
    and a bus decorator class which would inherit from the Bus interface. The problem
    with an inhertiance based implimentation of this priority is that it's not dynamic.
    It's not dynamic in the sense that more responsibilities/changes can be made come run-time
    for example variably changing the intensity of the bus color based on percentage of full capacity.
    While it would be easy to create, it isn't a decorator.
    But, by using a decorator, the code can be more flexible and it becomes easier to add additional
    functionality down the road. With a decorator pattern, we have a main component of what we'd want
    to decorate (in this case a bus). There would a concrete component maybe outlining a base case,
    then there would be a decorator that that can have multiple child classes outlining concrete decorators.



    *To view the UML to back up this discussion on decorator patterns, view the UML submitted on canvas.*













 */
